// 处理添加乘客的表单提交
function getBirthdayFromId(idNumber) {
    if (idNumber.length !== 18) {
        alert('身份证号码必须为18位');
        return null;
    }
    var year = idNumber.substring(6, 10);
    var month = idNumber.substring(10, 12);
    var day = idNumber.substring(12, 14);
    return year + '-' + month + '-' + day;
}

// 从身份证号码中提取性别
function getGenderFromId(idNumber) {
    if (idNumber.length !== 18) {
        return null;
    }
    // 倒数第二位
    var genderCode = idNumber.substring(16, 17);
    // 奇数为男，偶数为女
    return genderCode % 2 === 1 ? '男' : '女';
}

// 监听身份证号码输入框失去焦点事件
document.getElementById('PassengerIdentity').addEventListener('blur', function (event) {
    var idNumber = event.target.value;
    var birthday = getBirthdayFromId(idNumber);
    var gender = getGenderFromId(idNumber);

    // 如果提取到了出生日期，则显示在出生日期输入框中
    if (birthday) {
        document.getElementById('birthday').value = birthday;
    }

    // 如果提取到了性别，则设置性别选择框
    if (gender) {
        document.getElementById('gender').value = gender;
    }
});

// 处理添加乘客的表单提交
document.getElementById('addPassengerForm').addEventListener('submit', function (event) {
    event.preventDefault();
    var PassengerIdentity = document.getElementById('PassengerIdentity').value;
    var gender = document.getElementById('gender').value;
    var birthday = document.getElementById('birthday').value; // 从只读输入框获取出生日期
    var PName = document.getElementById('PName').value;
    var IdentityStyle = document.getElementById('IdentityStyle').value;
    var PTele = document.getElementById('PTele').value;
    var PTicketNumber = document.getElementById('PTicketNumber').value;

    axios.post('http://localhost:15379/addPassenger', {
        PassengerIdentity: PassengerIdentity,
        gender: gender,
        birthday: birthday,
        PName: PName,
        PTele: PTele,
        IdentityStyle: IdentityStyle,
        PaymentState: "1",
        PTicketNumber: PTicketNumber
    }).then(function (response) {
        console.log(response.data);
        alert('乘客添加成功');
    }).catch(function (error) {
        console.error('Error:', error);
        alert('添加失败');
    });
});