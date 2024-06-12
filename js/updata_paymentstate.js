// 处理退款按钮点击
document.getElementById('refundButton').addEventListener('click', function () {
    var Pinfo = document.getElementById('Pinfo').value;
    var selectField = document.getElementById('selectField').value;

    axios.post('http://localhost:15379/updatePaymentState', {
        Pinfo: Pinfo,
        selectField: selectField
    }).then(function (response) {
        alert(response.data.message);
    }).catch(function (error) {
        console.error('Error:', error);
        alert('支付状态更新失败');
    });
});

// 处理删除按钮点击
document.getElementById('deleteButton').addEventListener('click', function () {
    var Pinfo = document.getElementById('Pinfo').value;
    var selectField = document.getElementById('selectField').value;

    axios.post('http://localhost:15379/deletePassenger', {
        Pinfo: Pinfo,
        selectField: selectField
    }).then(function (response) {
        alert(response.data.message);
    }).catch(function (error) {
        console.error('Error:', error);
        alert('删除乘客数据失败，可能尚未退款');
    });
});

