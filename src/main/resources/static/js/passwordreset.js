$(function () {

    var session_validated = document.getElementById('session_validated').value;
    console.log(session_validated);
    var session_success = document.getElementById('session_success').value;
    console.log(session_success);
    console.log(session_validated === 'false');
    console.log(session_success === 'true');
    if (session_validated === 'false') {
        mailvalidated();
    }

    if (session_success === 'true') {
        resetsuccess();
    }

})

function mailvalidated() {
    console.log('mailvalidated');
    alert('重設密碼連結已經寄出，請於信箱中查看');
}


function resetsuccess() {
    console.log('resetsuccess');
    $.get('/login/reset/success', function () {
        alert('密碼已成功更改，將回到登入畫面');
        window.location.href = "/login/";
    });
}