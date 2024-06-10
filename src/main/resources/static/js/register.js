document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registerForm');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const firstName = document.getElementById('firstName').value;
        const lastName = document.getElementById('lastName').value;
        const dateOfBirth = document.getElementById('dateOfBirth').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        const registerData = {
            username: username,
            firstName: firstName,
            lastName: lastName,
            dateOfBirth: dateOfBirth,
            email: email,
            password: password
        };

        fetch('/api/v1/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(registerData)
        })
            .then(response => {
                if (response.status === 201) {
                    window.location.href = '/register/success';
                } else {
                    throw new Error('Login failed!'); // Бросаем ошибку, если статус не 201
                }
            })
            .catch(error => console.error('Error:', error));
    });
});
