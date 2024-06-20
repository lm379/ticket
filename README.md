# 基于Java Swing+MySQL+Node.js的机票售票管理系统

本项目基于Java Swing框架构建，搭配[Flatlaf主题](https://github.com/JFormDesigner/FlatLaf)实现现代风格的UI

## 功能描述

- [X] 登录/注册
- [X] 乘客信息管理
- [X] 航班信息管理
- [X] 航班销售统计
- [X] 订票
- [X] 退票
- [X] 多级菜单

## 需求分析

- 基于Java Swing GUI提供一个用户友好的界面，使得售票员能够快速有效地处理机票销售。
- 实现航班信息的管理，包括航班添加、修改和删除功能。
- 实现乘客信息管理，包括乘客添加、查询和更新功能。
- 提供销售统计功能，以便管理层能够监控销售情况并做出战略决策。

## 项目开发环境

`OpenJDK 21.0.2` `MySQL 8.0.37` `Node.js v20.14.0`

## 开发工具

`VS Code` `Navicat`

---

## 前端描述

### 项目结构

入口文件：`TicketManangeSystem.java  `

乘客管理：`PassengerManageForm.java`

销售统计：`SalesQueryForm.java`

航班管理：`FlightsAddForm.java` `FlightsDeleteForm.java` `FlightsUpdateForm.java` `FlightsQueryForm.java` `FilghtsSelectForm.java`

注册登录：`LoginForm.java`

### 核心代码

前端核心代码是以GET和POST的方法向后端发送JSON数据

#### POST方式

```java
try {
    var request = HttpRequest.newBuilder().uri(URI.create(apiAddress + apiEndpoint))
    .header("Content-Type", "application/json")
    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString)).build();
    var client = HttpClient.newHttpClient();
    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
} catch (Exception e) {
    // 异常处理
}
```

对应的JSON数据处理示例

```java
// 调用外部库org.json实现JSON格式数据的处理
JSONObject json = new JSONObject();
json.put("TflightNumber", TflightNumber);
json.put("TicketNumber", TicketNumber);
json.put("Price", Price);
json.put("Condition", Condition);
json.put("discount", discount);
String jsonInputString = json.toString();
```

#### GET方式

```java
// 使用URI + toURL方法构建请求URL
URI uri = new URI(apiAddress + apiEndpoint);
URL url = uri.toURL();
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod("GET");
```

#### 事件监听

```java
// 点击按钮后的触发
Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				// 点击后的触发逻辑
            }
        });
```

```java
         // 从身份证号中提取性别和出生日期
         passengerIdTextField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    String idNumber = passengerIdTextField.getText();
                    if (idNumber.length() == 18) {
                        // 提取出生日期
                        String birthDate = idNumber.substring(6, 10) + "-" + idNumber.substring(10, 12) + "-"
                                + idNumber.substring(12, 14);
                        passengerBirthTextField.setText(birthDate);

                        // 提取性别，身份证倒数第二位为奇数表示男，偶数表示女
                        char genderCode = idNumber.charAt(16);
                        int sexIndex = (genderCode % 2 == 0) ? 1 : 0; // 0为男，1为女
                        passengerSexComboBox.setSelectedIndex(sexIndex);
                    } else {
                        JOptionPane.showMessageDialog(null, "身份证号码必须为18位", "输入错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
```



### 前端项目截图及展示

数据库测试账号：

> 管理员 账号：admin 密码：admin
>
> 普通用户 账号：user 密码：123

1. 注册登录

<img src="https://r2.lm379.cn/2024/06/8f91519b024738caca31b7414a523cbd.png" alt="登录页面" style="zoom:50%;" />

2. 非管理员账号登入只能进入购票页面

<img src="https://r2.lm379.cn/2024/06/25a53bfae17b22eac9582be9fc00a178.png" style="zoom:40%;" />

3. 管理员账号登录会显示主界面

<img src="https://r2.lm379.cn/2024/06/02ed0971cea3d6c21fbb4413ec088db7.png" style="zoom:40%;" />

4. 主界面三个二级菜单展示

![](https://r2.lm379.cn/2024/06/71d83dcca8de6ad19a97ca00e8307cbf.png)

其中销售统计按钮点击会直接触发，另外两个还有三级菜单

5. 乘客信息管理菜单展示

   1. 通过姓名或身份证号查询乘客详细信息

   <img src="https://r2.lm379.cn/2024/06/14207d6c69358789bb5480595acf69db.png" style="zoom:25%;" />

   ![](https://r2.lm379.cn/2024/06/c8e4578af657395fed48c584542ec442.png)

   2. 订票系统

   <img src="https://r2.lm379.cn/2024/06/defea5c36ee1a2235467d5f93469bdfd.png" style="zoom:50%;" />

   <img src="https://r2.lm379.cn/2024/06/ed2b7e2cfd91e59b1ad8974b0e3a46e6.png" alt="添加成功检查" style="zoom:50%;" />

   其中性别选项和出生日期可以自动从身份证号中获取，获取后也能自行修改，且身份证号必须位18位，否则程序将报错

   3. 退票管理

   可通过乘客姓名、身份证号、电话号码对乘客进行退款，退款后才能删除乘客信息

   退款成功后将无法通过上面的查询接口查询到乘客信息

   <img src="https://r2.lm379.cn/2024/06/88073b97367069087538d85dfa1d7afa.png" style="zoom:50%;" />

   4. 修改乘客信息

   可通过乘客的姓名、证件号修改相应的姓名、证件号、电话号码、票号

   票号对应飞机航班信息

   ![](https://r2.lm379.cn/2024/06/7967a4b6f94f120a684d7d1633e86864.png)
6. 航班管理菜单展示

   1. 筛选航班

   通过起止城市或航班号查询对应的航班

![示例：查询上海到成都的航班](https://r2.lm379.cn/2024/06/008bcf29cc0ae90423e5c00b105e1d6a.png)

2. 查询所有航班

![](https://r2.lm379.cn/2024/06/ac7ce95f4cfda2e31dc45989a1213851.png)

3. 增加航班

![](https://r2.lm379.cn/2024/06/f42735e93525e9fe6373c3b9494151f9.png)

4. 删除航班

![](https://r2.lm379.cn/2024/06/a7dabb60e43cd6049f1a845a0c3d3ec4.png)

5. 修改航班信息

![](https://r2.lm379.cn/2024/06/d03b53f0acb8b4d84ca4b294f042bd4c.png)

## 后端接口描述

由于项目使用Node.js作为后端API，所以需要自行安装Node.js

[Node.js — 在任何地方运行 JavaScript (nodejs.org)](https://nodejs.org/zh-cn)

然后先前往项目目录下执行下面的命令安装依赖，否则项目无法运行

```bash
npm install express cors body-parser mysql2 bcrypt --save
```

再执行

```bash
node app.js
```

就会启动后端API接口了

默认接口地址：http://localhost:15379/

### 后端核心代码

```javascript
// 数据库配置
const dbConfig = {
  host: 'mysql.example.com', // 数据库地址
  user: 'root', //用户名
  password: 'password', // 密码
  database: 'ticket', // 表名
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0
};
// 数据库连接
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
```

```js
// get路由示例
app.get('/queryTickets', (req, res) => {
  const { 请求体 } = req.query;
  const sql = `SQL语句`;
  executeQuery(sql, [请求体], (err, results) => {
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
```



> 后端接收和返回的数据均为JSON格式

**后端接口一览表**

| 接口                 | 请求参数                                                                             | 请求方式 | 描述                         |
| -------------------- | ------------------------------------------------------------------------------------ | -------- | :--------------------------- |
| queryTickets         | PName                                                                                | get      | 通过乘客姓名查询乘客信息     |
| queryTicketsById     | PassengerIdentity                                                                    | get      | 通过乘客身份证号查询乘客信息 |
| calculateFlightSales | 无                                                                                   | get      | 统计当前总销售额             |
| queryAllFlights      | 无                                                                                   | get      | 查询当前所有航班             |
| queryFlights         | fromCity & toCity                                                                    | get      | 根据起始城市查询航班         |
| queryFlightByNumber  | FlightNumber                                                                         | get      | 根据航班号查询航班           |
| addPassenger         | PName gender IdentityStyle<br />PassengerIdentity birthday PTele <br />PTicketNumber | post     | 添加乘客                     |
| updatePaymentState   | Pinfo selectField                                                                    | post     | 变更乘客支付状态             |
| deletePassenger      | Pinfo selectField                                                                    | post     | 删除乘客数据                 |
| updatePassenger      | oldPinfo  newPinfo<br />oldSelectField  newSelectField                               | post     | 修改乘客信息                 |
| addFlight            | TflightNumber, TicketNumber,<br /> Price, Condition, <br />discount, worker          | post     | 增加航班                     |
| deleteFlight         | TicketNumber                                                                         | post     | 删除航班                     |
| updateFlight         | newTinfo TicketNumber SelectTField                                                   | post     | 修改航班信息                 |
| register             | account password                                                                     | post     | 注册                         |
| login                | account password                                                                     | post     | 登录                         |

其中注册和登录使用 `bcrypt`库对密码进行加密

如两个测试账号再数据库中显示为如下

![](https://r2.lm379.cn/2024/06/5195f7788325978747683c86bcc8b4ca.png)

### API调用示例

```
GET http://localhost:15379/queryFlightByNumber?FlightNumber=MU294
```

返回数据

```json
[
    {
        "FlightNumber": "MU294",
        "TicketNumber": 13,
        "FlightName": "空客320",
        "fromCity": "上海",
        "toCity": "成都",
        "mileAge": 2800,
        "departureTime": "11:15:00",
        "Price": 546,
        "discount": 0.3
    }
]
```

## 数据库描述

### 表结构

<img src="https://r2.lm379.cn/2024/06/6b9e2636376afa2b7b8ca3aad70db569.png" style="zoom:80%;" />
