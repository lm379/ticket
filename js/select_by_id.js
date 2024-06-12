// 处理根据身份证号查询机票信息的表单提交
document.getElementById('queryTicketByIdForm').addEventListener('submit', function (event) {
    event.preventDefault();
    var PassengerIdentity = document.getElementById('PassengerIdentityQuery').value;

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
});