document.getElementById('calculateSales').addEventListener('click', function(event) {
    axios.get('http://localhost:15379/calculateFlightSales').then(function(response) {
        var results = response.data;
        var resultsDiv = document.getElementById('salesResults');
        resultsDiv.innerHTML = ''; // 清空之前的结果

        // 创建表格元素
        var table = document.createElement('table');
        // 添加表头
        var thead = document.createElement('thead');
        thead.innerHTML = '<tr><th>航班号</th><th>乘客人数</th><th>机票销售额</th></tr>';
        table.appendChild(thead);

        // 创建表体元素
        var tbody = document.createElement('tbody');

        results.forEach(function(sale) {
            // 创建表格行
            var tr = document.createElement('tr');
            // 创建航班单元格
            var flightCell = document.createElement('td');
            flightCell.textContent = sale.航班;
            tr.appendChild(flightCell);
            // 创建乘客人数单元格
            var passengerCell = document.createElement('td');
            passengerCell.textContent = sale.乘客人数;
            tr.appendChild(passengerCell);
            // 创建机票销售额单元格
            var salesCell = document.createElement('td');
            // 使用toLocaleString()格式化数字为货币格式
            var formattedSales = parseInt(sale.机票销售额).toLocaleString('zh-CN');
            salesCell.textContent = '¥' + formattedSales;
            tr.appendChild(salesCell);

            // 将行添加到表体中
            tbody.appendChild(tr);
        });

        // 将表体添加到表格中
        table.appendChild(tbody);
        // 将表格添加到结果div中
        resultsDiv.appendChild(table);
    }).catch(function(error) {
        console.error('Error:', error);
        // 在页面上显示错误信息
        var errorDiv = document.getElementById('error');
        errorDiv.innerHTML = '统计失败: ' + error;
    });
});
