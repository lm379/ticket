document.getElementById('queryAllFlights').addEventListener('click', handleQueryAllFlights);
document.getElementById('addFlightButton').addEventListener('click', handleAddFlight);
document.getElementById('deleteFlight').addEventListener('click', handleDeleteFlight);
document.getElementById('updateFlight').addEventListener('click', handleUpdateFlight);

function handleQueryAllFlights() {
    axios.get('http://localhost:15379/queryAllFlights').then(function (response) {
        var results = response.data;
        var resultsDiv = document.getElementById('queryResults');
        resultsDiv.innerHTML = ''; // 清空之前的结果

        var table = document.createElement('table');
        var thead = document.createElement('thead');
        thead.innerHTML = '<tr><th>票号</th><th>始发城市</th><th>目的城市</th><th>价格</th><th>折扣率</th><th>航班号</th><th>机型</th><th>航行里程(Km)</th><th>起飞时间</th></tr>';
        table.appendChild(thead);
        var tbody = document.createElement('tbody');
        results.forEach(function (flight) {
            var tr = document.createElement('tr');
            var flightCell = document.createElement('td');
            flightCell.textContent = flight.TicketNumber;
            tr.appendChild(flightCell);
            var fromcityCell = document.createElement('td');
            fromcityCell.textContent = flight.fromCity;
            tr.appendChild(fromcityCell);
            var tocityCell = document.createElement('td');
            tocityCell.textContent = flight.toCity;
            tr.appendChild(tocityCell);
            var priceCell = document.createElement('td');
            priceCell.textContent = flight.Price;
            tr.appendChild(priceCell)
            var discountCell = document.createElement('td');
            discountCell.textContent = flight.discount;
            tr.appendChild(discountCell)
            var flightNumberCell = document.createElement('td');
            flightNumberCell.textContent = flight.FlightNumber;
            tr.appendChild(flightNumberCell);
            var typeCell = document.createElement('td');
            typeCell.textContent = flight.FlightName;
            tr.appendChild(typeCell);

            var mileAgeCell = document.createElement('td');
            mileAgeCell.textContent = flight.mileAge;
            tr.appendChild(mileAgeCell);
            var departureTimeCell = document.createElement('td');
            departureTimeCell.textContent = flight.departureTime;
            tr.appendChild(departureTimeCell);
            tbody.appendChild(tr);
        });
        table.appendChild(tbody);
        resultsDiv.appendChild(table);
    }).catch(function (error) {
        console.error('Error:', error);
        var errorDiv = document.getElementById('error');
        errorDiv.innerHTML = '查询失败: ' + error;
    });
}

function handleAddFlight() {
    var TflightNumber = document.getElementById('TflightNumber').value;
    var TicketNumber = document.getElementById('addTicketNumber').value;
    var Price = document.getElementById('Price').value;
    var Condition = document.getElementById('Condition').value;
    var discount = document.getElementById('discount').value;

    axios.post('http://localhost:15379/addFlight', {
        TflightNumber: TflightNumber,
        TicketNumber: TicketNumber,
        Price: Price,
        Condition: Condition,
        discount: discount,
        worker: "1"
    }).then(function (response) {
        console.log(response.data);
        alert('航班添加成功');
    }).catch(function (error) {
        console.error('Error:', error);
        alert('添加失败');
    });
}

function handleDeleteFlight() {
    var TicketNumber = document.getElementById('TicketNumber').value;
    axios.post('http://localhost:15379/deleteFlight', {
        TicketNumber: TicketNumber
    }).then(function (response) {
        console.log(response.data);
        alert('航班删除成功');
    }).catch(function (error) {
        console.error('Error:', error);
        alert('删除失败');
    });
}

function handleUpdateFlight() {
    var TicketNumber = document.getElementById('TicketNumber').value;
    var SelectTField = document.getElementById('SelectTField').value;
    var newTinfo = document.getElementById('newTinfo').value;

    axios.post('http://localhost:15379/updateFlight', {
        TicketNumber: TicketNumber,
        SelectTField: SelectTField,
        newTinfo: newTinfo
    }).then(function (response) {
        console.log(response.data);
        alert('航班更新成功');
    }).catch(function (error) {
        console.error('Error:', error);
        alert('更新失败');
    });
}