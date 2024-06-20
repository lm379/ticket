const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const mysql = require('mysql2');
const bcrypt = require('bcrypt');

const app = express();

app.use(cors());

// 解析JSON格式的请求体
app.use(bodyParser.json());

// 设置MySQL数据库连接
const dbConfig = {
  host: 'mysql.example.com',  // MySQL服务器地址
  user: 'user', // MySQL用户名
  password: 'password', // MySQL密码
  database: 'ticket', // MySQL数据库名
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0
};

// 创建数据库连接池
const pool = mysql.createPool(dbConfig);

// 用于简化数据库查询的函数
function executeQuery(sql, data, callback) {
  pool.getConnection((err, connection) => {
    if (err) {
      return callback(err, null);
    } else {
      connection.query(sql, data, (error, results) => {
        connection.release();
        if (error) {
          return callback(error, null);
        }
        return callback(null, results);
      });
    }
  });
}

// 路由：添加乘客
app.post('/addPassenger', (req, res) => {
  const { PName, gender, IdentityStyle, PassengerIdentity, birthday, PTele, PTicketNumber } = req.body;
  const sql = `INSERT INTO passenger (PName, gender, IdentityStyle, PassengerIdentity, birthday, PTele, PTicketNumber, PaymentState) VALUES (?, ?, ?, ?, ?, ?, ?,'1')`;
  executeQuery(sql, [PName, gender, IdentityStyle, PassengerIdentity, birthday, PTele, PTicketNumber], (err, results) => {
    if (err) {
      res.status(500).json({ message: '添加乘客时发生错误', error: err });
    } else {
      res.status(200).json({ message: '乘客添加成功', results: results });
    }
  });
});


// 路由：查询乘客信息
app.get('/queryTickets', (req, res) => {
  const { PName } = req.query;
  const sql = `
  SELECT passenger.PName,IdentityStyle,PassengerIdentity,PTele,PTicketNumber,FlightNumber,fromcity,tocity,mileAge,departureTime FROM flight,ticket,passenger 
  WHERE Flight.FlightNumber = Ticket.TflightNumber AND Passenger.PTicketNumber = Ticket.TicketNumber AND PName IN (SELECT	PName FROM Passenger WHERE PName = ?) AND passenger.PaymentState = '1'
  `;
  executeQuery(sql, [PName], (err, results) => {
    if (err) {
      res.status(500).json({ message: '查询乘客信息时发生错误', error: err });
    } else {
      if (results.length > 0) {
        res.json(results);
      } else {
        res.json({ message: `未找到${PName}的乘客信息` });
      }
    }
  });
});
app.get('/queryTicketsById', (req, res) => {
  const { PassengerIdentity } = req.query;
  const sql = `
  SELECT passenger.PName,IdentityStyle,PassengerIdentity,PTele,PTicketNumber,FlightNumber,fromcity,tocity,mileAge,departureTime FROM flight,ticket,passenger 
  WHERE Flight.FlightNumber = Ticket.TflightNumber AND Passenger.PTicketNumber = Ticket.TicketNumber AND PName IN (SELECT	PName FROM Passenger WHERE PassengerIdentity = ?) AND passenger.PaymentState = '1'
  `;
  executeQuery(sql, [PassengerIdentity], (err, results) => {
    if (err) {
      res.status(500).json({ message: '查询乘客信息时发生错误', error: err });
    } else {
      if (results.length > 0) {
        res.json(results);
      } else {
        res.json({ message: `未找到${PassengerIdentity}的乘客信息` });
      }
    }
  })
});

// 路由：统计销售额
app.get('/calculateFlightSales', (req, res) => {
  const sql = `
      SELECT Flight.FlightNumber, COUNT(*) AS 'PNum', SUM(price) AS 'Sales'
      FROM flight
      JOIN ticket ON Flight.FlightNumber = Ticket.TflightNumber
      JOIN passenger ON Passenger.PTicketNumber = Ticket.TicketNumber
      WHERE passenger.PaymentState = '1'
      GROUP BY Flight.FlightNumber
      ORDER BY SUM(price) DESC, COUNT(*) ASC
  `;

  executeQuery(sql, (error, results) => {
    if (error) {
      res.status(500).json({ message: '查询航班销售额时发生错误', error: error });
    } else {
      res.json(results);
    }
  });
});

// 路由：查询航班
app.get('/queryAllFlights', function (req, res) {
  const sql = `
  SELECT
	CAST( ticket.TicketNumber AS SIGNED ) AS TicketNumber,
	flight.fromCity,
	flight.toCity,
	CAST( ticket.Price AS SIGNED ) AS Price,
	ticket.discount,
	flight.FlightNumber,
	flight.FlightName,
	flight.mileAge,
	flight.departureTime 
FROM
	Flight
	JOIN Ticket ON flight.FlightNumber = ticket.TFlightNumber 
ORDER BY
	TicketNumber ASC,
	Price DESC;
  `;
  executeQuery(sql, (err, results) => {
    if (err) {
      res.status(500).json({ message: '查询航班时发生错误', error: err });
    } else {
      res.json(results);
    }
  });
});
app.get('/queryFlights', function (req, res) {
  const { fromCity, toCity } = req.query;
  const sql = `
  SELECT flight.FlightNumber, TicketNumber, FlightName, fromCity, toCity, mileAge, departureTime,ticket.Price,discount
  FROM Flight 
  JOIN Ticket ON FlightNumber = TflightNumber
  WHERE flight.fromCity = ? AND flight.toCity = ?;
  `;
  executeQuery(sql, [fromCity, toCity], (err, results) => {
    if (err) {
      res.status(500).json({ message: '查询航班时发生错误', error: err });
    } else {
      if (results.length > 0) {
        res.json(results);
      } else {
        res.json({ message: `未找到${fromCity}到${toCity}的航班` });
      }
    }
  });
});
app.get('/queryFlightByNumber', function (req, res) {
  const { FlightNumber } = req.query;
  const sql = `
  SELECT flight.FlightNumber, TicketNumber, FlightName, fromCity, toCity, mileAge, departureTime,ticket.Price,discount
  FROM Flight 
  JOIN Ticket ON FlightNumber = TflightNumber
  WHERE flight.FlightNumber = ?
  `;
  executeQuery(sql, [FlightNumber], (err, results) => {
    if (err) {
      res.status(500).json({ message: '查询航班时发生错误', error: err });
    } else {
      if (results.length > 0) {
        res.json(results);
      } else {
        res.json({ message: `未找到航班号为${FlightNumber}的航班` });
      }
    }
  });
});

// 路由：变更支付状态
app.post('/updatePaymentState', (req, res) => {
  const { Pinfo, selectField } = req.body;
  let columnName = selectField === "PName" ? "PName" :
    selectField === "PassengerIdentity" ? "PassengerIdentity" :
      selectField === "PTele" ? "PTele" : null;

  if (!columnName) {
    return res.status(400).send({ message: '无效的字段选择' });
  }

  const sql = `UPDATE passenger SET PaymentState = '0' WHERE ${columnName} = ? AND PaymentState = '1'`;

  executeQuery(sql, [Pinfo], (err, result) => {
    if (err) {
      return res.status(500).json({ message: '更新失败', error: err });
    }
    if (result.affectedRows === 0) {
      return res.status(404).json({ message: '未找到乘客信息' });
    }
    res.json({ message: '退款成功' });
  });
});

// 路由：删除乘客数据
app.post('/deletePassenger', (req, res) => {
  const { Pinfo, selectField } = req.body;
  let columnName = selectField === "PName" ? "PName" :
    selectField === "PassengerIdentity" ? "PassengerIdentity" :
      selectField === "PTele" ? "PTele" : null;

  if (!columnName) {
    return res.status(400).send({ message: '无效的字段选择' });
  }

  const sql = `DELETE FROM passenger WHERE ${columnName} = ? AND PaymentState = '0'`;
  executeQuery(sql, [Pinfo], (err, result) => {
    if (err) {
      return res.status(500).json({ message: '删除失败，可能是尚未退款', error: err });
    }
    if (result.affectedRows === 0) {
      return res.status(404).json({ message: '未找到乘客信息' });
    }
    res.json({ message: '乘客信息删除成功' });
  });
});

// 路由：修改乘客信息
app.post('/updatePassenger', (req, res) => {
  const { oldPinfo, newPinfo, oldSelectField, newSelectField } = req.body;
  let validFields = ['PName', 'PassengerIdentity', 'PTele', 'PTicketNumber'];
  if (!validFields.includes(oldSelectField) || !validFields.includes(newSelectField)) {
    return res.status(400).json({ message: '无效的字段选择' });
  }
  const sql = `UPDATE passenger SET ${newSelectField} = ? WHERE ${oldSelectField} = ? AND PaymentState = '1'`;
  executeQuery(sql, [newPinfo, oldPinfo], (err, result) => {
    if (err) {
      return res.status(500).json({ message: '更新失败', error: err });
    }
    if (result.affectedRows === 0) {
      return res.status(404).json({ message: '未找到乘客信息或乘客未支付' });
    }
    res.json({ message: '乘客信息更新成功' });
  });
});

// 路由：增加航班
app.post('/addFlight', (req, res) => {
  const { TflightNumber, TicketNumber, Price, Condition, discount, worker } = req.body;
  const sql = `INSERT INTO Ticket (TflightNumber, TicketNumber, Price, \`Condition\`, discount, worker) VALUES (?, ?, ?, ?, ?, ?)`;
  executeQuery(sql, [TflightNumber, TicketNumber, Price, Condition, discount, worker], (err, result) => {
    if (err) {
      return res.status(500).json({ message: '添加航班失败', error: err });
    }
    res.json({ message: '航班添加成功' });
  });
});

// 路由：删除航班
app.post('/deleteFlight', (req, res) => {
  const { TicketNumber } = req.body;
  const sql = `DELETE FROM Ticket WHERE TicketNumber = ?`;
  executeQuery(sql, [TicketNumber], (err, result) => {
    if (err) {
      return res.status(500).json({ message: '删除航班失败', error: err });
    }
    if (result.affectedRows === 0) {
      return res.status(404).json({ message: '未找到航班' });
    }
    res.json({ message: '航班删除成功' });
  });
});

// 路由：修改航班
app.post('/updateFlight', (req, res) => {
  const { newTinfo, TicketNumber, SelectTField } = req.body;
  let validFields = ['TflightNumber', 'Price', 'Condition', 'discount', 'TicketNumber'];
  if (!validFields.includes(SelectTField)) {
    return res.status(400).json({ message: '无效的字段选择' });
  }
  const sql = `UPDATE Ticket SET \`${SelectTField}\` = ? WHERE TicketNumber = ?`;
  executeQuery(sql, [newTinfo, TicketNumber], (err, result) => {
    if (err) {
      return res.status(500).json({ message: '更新航班失败', error: err });
    }
    if (result.affectedRows === 0) {
      return res.status(404).json({ message: '未找到航班' });
    }
    res.json({ message: '航班更新成功' });
  });
});

// 简单注册接口
app.post('/register', async (req, res) => {
  const { account, password } = req.body;
  const hashedPassword = await bcrypt.hash(password, 10);
  const sql = 'INSERT INTO users (account, password, isAdmin) VALUES (?, ?, ?)';
  executeQuery(sql, [account, hashedPassword, 0], (err, result) => {
    if (err) {
      return res.status(500).send('注册失败');
    }
    res.send('注册成功');
  });
});

// 简单登录接口
app.post('/login', (req, res) => {
  const { account, password } = req.body;

  // 确保请求中包含账号和密码
  if (!account || !password) {
    return res.status(400).send('请求中必须包含账号和密码');
  }

  const sql = 'SELECT * FROM users WHERE account = ?';
  executeQuery(sql, [account], async (err, results) => {
    if (err) {
      return res.status(500).send('数据库查询出错');
    }
    if (results.length === 0) {
      return res.status(401).send('账户不存在');
    }
    const user = results[0];
    // console.log(user);

    // 确保数据库中的用户有密码字段
    if (!user.password) {
      return res.status(500).send('用户密码未设置');
    }

    try {
      const isMatch = await bcrypt.compare(password, user.password);
      if (!isMatch) {
        return res.status(401).send('密码错误');
      }
      res.send({ account: user.account, isAdmin: user.isAdmin });
    } catch (compareError) {
      res.status(500).send('密码比对过程中出错');
    }
  });
});


// 启动服务器
const port = 15379;
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
