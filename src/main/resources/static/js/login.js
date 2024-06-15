document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('loginForm');

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData();
        formData.append('login', document.getElementById('login').value);
        formData.append('password', document.getElementById('password').value);

        fetch('/api/v1/auth/login', {
            method: 'POST',
            body: formData
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
