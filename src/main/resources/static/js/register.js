document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registerForm');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData();
        formData.append('username', document.getElementById('username').value);
        formData.append('firstName', document.getElementById('firstName').value);
        formData.append('lastName', document.getElementById('lastName').value);
        formData.append('dateOfBirth', document.getElementById('dateOfBirth').value);
        formData.append('email', document.getElementById('email').value);
        formData.append('password', document.getElementById('password').value);

        fetch('/api/v1/auth/register', {
            method: 'POST',
            body: formData
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
