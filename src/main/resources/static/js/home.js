function getUserData() {
    const userDetailContainer = document.querySelector('.user-details');

    fetch('/api/v1/users/me', {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('token')
        }
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else if (response.status === 401) {
                sessionStorage.removeItem('token');
                window.location.href = '/login';
            } else {
                throw new Error('Failed to fetch user data');
            }
        })
        .then(user => {
            // Обновление данных о пользователе на странице
            const userNameElement = userDetailContainer.querySelector('h3');
            const userUsernameElement = userDetailContainer.querySelector('p');

            userNameElement.textContent = user.firstName + ' ' + user.lastName;
            userUsernameElement.textContent = '@' + user.username;
        })
        .catch(error => {
            console.error('Error:', error);
            // В случае ошибки отображаем сообщение об ошибке
            userDetailContainer.innerHTML = '<p>Error fetching user data</p>';
        });
}

function addListeners() {
    const logoutButton = document.querySelector('.sidebar-left ul li:last-child a');

    logoutButton.addEventListener('click', function(event) {
        event.preventDefault();

        localStorage.setItem('token', '');
        window.location.href = '/login';
    });
}


document.addEventListener('DOMContentLoaded', function() {
    addListeners(document);
    getUserData();
});
