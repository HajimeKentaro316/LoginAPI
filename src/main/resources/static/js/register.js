function sendFormData() {
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const data = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password
    }

    fetch('/api/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        if (data.isSuccess) {
            console.log(data);
            displaySuccessMessage();
        } else {
            displayErrorMessage(data.errorMsg);  // エラーメッセージの表示
        }
    })
    .catch(error => {
        showLoginSuccess("APIの接続に問題がありました");
        console.error("APIの接続に問題がありました");
    });
}

function displayErrorMessage(message) {
    const messageDiv = document.getElementById("message");
    messageDiv.innerHTML = `<p>${message}</p>`;
    messageDiv.style.color = "red";
}

function displaySuccessMessage() {
    const messageDiv = document.getElementById("message");
    messageDiv.innerHTML = `
        <p>会員登録に成功しました</p><br><br>
        <a href="/login.html"><button>ログイン</button></a>
    `;
    messageDiv.style.color = "green";
}
