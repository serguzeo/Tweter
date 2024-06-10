document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('loginForm');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        const loginData = {
            login: username,
            password: password
        };

        fetch('/api/v1/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.accessToken) {
                    localStorage.setItem('token', data.tokenType + data.accessToken);
                    window.location.href = '/home';
                } else {
                    alert('Login failed!');
                }
            })
            .catch(error => console.error('Error:', error));
    });
});
