document.getElementById('queryInfoButton').addEventListener('click', handleQuery);
document.getElementById('updateInfoButton').addEventListener('click', handleQueryUpdate);

function handleQueryUpdate() {
    var oldPinfo = document.getElementById('Pinfo').value; // 获取旧信息
    var newPinfo = document.getElementById('newPinfo').value; // 获取新信息
    var oldSelectField = document.getElementById('selectField').value; // 获取旧信息对应的字段
    var newSelectField = document.getElementById('newselectField').value; // 获取新信息对应的字段

    axios.post('http://localhost:15379/updatePassenger', {
        oldPinfo: oldPinfo,
        newPinfo: newPinfo,
        oldSelectField: oldSelectField,
        newSelectField: newSelectField
    }).then(function (response) {
        alert(response.data.message); // 显示后端返回的消息
    }).catch(function (error) {
        console.error('Error:', error);
        alert('更新失败');
    });
};


function handleQuery() {
    var selectField = document.getElementById('selectField').value;
    var Pinfo = document.getElementById('Pinfo').value;

    // 根据选择的字段调用不同的查询函数
    if (selectField === "PassengerIdentity") {
        queryTicketById(Pinfo);
    } else {
        queryTicketByName(Pinfo);
    }
};

// 查询函数
function queryTicketById(PassengerIdentity) {
    axios.get('http://localhost:15379/queryTicketsById', {
        params: {
            PassengerIdentity: PassengerIdentity
        }
    }).then(function (response) {
        // 显示查询结果
        var resultsDiv = document.getElementById('queryResults');
        resultsDiv.innerHTML = ''; // 清空之前的结果
        // 检查响应中是否有message字段，如果有，则显示消息
        if (response.data.message) {
            resultsDiv.innerHTML = response.data.message;
        } else {
            // 创建表格元素
            var table = document.createElement('table');
            table.innerHTML = `
                <tr>
                    <th>姓名</th>
                    <th>证件类型</th>
                    <th>证件号</th>
                    <th>手机号</th>
                    <th>票号</th>
                    <th>航班号</th>
                    <th>始发城市</th>
                    <th>目的城市</th>
                    <th>航行里程(Km)</th>
                    <th>起飞时间</th>
                </tr>
            `;
            // 填充表格行
            response.data.forEach(function (passenger) {
                var row = table.insertRow();
                row.innerHTML = `
                    <td>${passenger.PName}</td>
                    <td>${passenger.IdentityStyle}</td>
                    <td>${passenger.PassengerIdentity}</td>
                    <td>${passenger.PTele}</td>
                    <td>${passenger.PTicketNumber}</td>
                    <td>${passenger.FlightNumber}</td>
                    <td>${passenger.fromcity}</td>
                    <td>${passenger.tocity}</td>
                    <td>${passenger.mileAge}</td>
                    <td>${passenger.departureTime}</td>
                `;
            });

            // 将表格添加到结果div中
            resultsDiv.appendChild(table);
        }
    }).catch(function (error) {
        console.error('Error:', error);
        alert('查询失败');
    });
};

function queryTicketByName(PName) {
    axios.get('http://localhost:15379/queryTickets', {
        params: {
            PName: PName
        }
    }).then(function (response) {
        // 显示查询结果
        var resultsDiv = document.getElementById('queryResults');
        resultsDiv.innerHTML = ''; // 清空之前的结果
        // 检查响应中是否有message字段，如果有，则显示消息
        if (response.data.message) {
            resultsDiv.innerHTML = response.data.message;
        } else {
            // 创建表格元素
            var table = document.createElement('table');
            table.innerHTML = `
                <tr>
                    <th>姓名</th>
                    <th>证件类型</th>
                    <th>证件号</th>
                    <th>手机号</th>
                    <th>票号</th>
                    <th>航班号</th>
                    <th>始发城市</th>
                    <th>目的城市</th>
                    <th>航行里程(Km)</th>
                    <th>起飞时间</th>
                </tr>
            `;
            // 填充表格行
            response.data.forEach(function (passenger) {
                var row = table.insertRow();
                row.innerHTML = `
                    <td>${passenger.PName}</td>
                    <td>${passenger.IdentityStyle}</td>
                    <td>${passenger.PassengerIdentity}</td>
                    <td>${passenger.PTele}</td>
                    <td>${passenger.PTicketNumber}</td>
                    <td>${passenger.FlightNumber}</td>
                    <td>${passenger.fromcity}</td>
                    <td>${passenger.tocity}</td>
                    <td>${passenger.mileAge}</td>
                    <td>${passenger.departureTime}</td>
                `;
            });
            // 将表格添加到结果div中
            resultsDiv.appendChild(table);
        }
    }).catch(function (error) {
        console.error('Error:', error);
        alert('查询失败');
    });
};

