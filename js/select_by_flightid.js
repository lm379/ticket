document.getElementById('queryFlightByNumberForm').addEventListener('submit', function (event) {
    event.preventDefault();
    var FlightNumber = document.getElementById('FlightNumberQuery').value;

    axios.get('http://localhost:15379/queryFlightByNumber', {
        params: {
            FlightNumber: FlightNumber
        }
    }).then(function (response) {
        var resultsDiv = document.getElementById('flightQueryResults');
        resultsDiv.innerHTML = ''; // 清空之前的结果

        // 检查响应中是否有message字段，如果有，则显示消息
        if (response.data.message) {
            resultsDiv.innerHTML = response.data.message;
        } else {
            // 创建表格元素
            var table = document.createElement('table');
            table.innerHTML = `
                <tr>
                    <th>航班号</th>
                    <th>航司号</th>
                    <th>机型</th>
                    <th>始发城市</th>
                    <th>目的城市</th>
                    <th>航行里程(Km)</th>
                    <th>起飞时间</th>
                </tr>
            `;

            // 填充表格行
            response.data.forEach(function (flight) {
                var row = table.insertRow();
                row.innerHTML = `
                    <td>${flight.FlightNumber}</td>
                    <td>${flight.companyID}</td>
                    <td>${flight.FlightName}</td>
                    <td>${flight.fromCity}</td>
                    <td>${flight.toCity}</td>
                    <td>${flight.mileAge}</td>
                    <td>${flight.departureTime}</td>
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