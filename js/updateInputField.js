function updateInputField() {
    var selectBox = document.getElementById('selectField');
    var inputField = document.getElementById('Pinfo');

    // 根据选择的选项，更改输入框的属性
    if(selectBox.value === "PName") {
        inputField.placeholder = "姓名";
    } else if(selectBox.value === "PassengerIdentity") {
        inputField.placeholder = "证件号";
    } else if(selectBox.value === "PTele") {
        inputField.placeholder = "电话号码";
    }
}