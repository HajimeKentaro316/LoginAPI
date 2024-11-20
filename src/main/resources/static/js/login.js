function sendFormData() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const data = {
        email: email,
        password: password
    }

    fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        // Authorizationヘッダーを取得
        const authHeader = response.headers.get('Authorization');
        if (authHeader) {
            // Authorizationヘッダーが存在する場合、localStorageに保存
            localStorage.setItem('Authorization', authHeader);
        }
        return response.json();
    })
    .then(data => {
        if (data.isSuccess) {
            console.log(data);
            const token = localStorage.getItem('Authorization');
            console.log("token:", token)

            fetch('/home', {
            method: 'GET', // 必要に応じてPOSTに変更
            headers: {
                'Authorization': `${token}`
            }
            })
            .then(response => {
                if (response.ok) {
                return response.json(); // 必要に応じて適切に処理
                } else {
                throw new Error('Failed to fetch');
                }
            })
            .then(data => {
                // サーバからリダイレクト先を取得
                window.location.href = data.redirectUrl;
            })
            .catch(error => console.error("home.htmlへのリダイレクトに失敗しました"));

        } else {
            showLoginSuccess(data.errorMsg);  // エラーメッセージの表示
        }
    })
    .catch(error => {
        showLoginSuccess("APIの接続に問題がありました");
        console.error("APIの接続に問題がありました", error);
    });
}

function showLoginSuccess(message) {
    const messageElement = document.getElementById("loginResult");
    messageElement.innerText = message;
    messageElement.style.color = 'red';
}
