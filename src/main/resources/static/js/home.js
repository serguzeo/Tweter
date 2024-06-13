import { getUserProfile } from './handlers/getUserProfile.js';
import { getUserPhoto } from './handlers/getUserPhoto.js';
import { setUserPhoto } from './handlers/setUserPhoto.js';

function getUserData() {
    const userDetailContainer = document.querySelector('.user-details');
    const userImage = document.querySelector('.user-info img');


    getUserProfile()
        .then(user => {
            const userNameElement = userDetailContainer.querySelector('h3');
            const userUsernameElement = userDetailContainer.querySelector('p');

            userNameElement.textContent = user.firstName + ' ' + user.lastName;
            userUsernameElement.textContent = '@' + user.username;

            return getUserPhoto();
        })
        .then(photoUrl => {
            userImage.src = photoUrl;
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            // В случае ошибки отображаем сообщение об ошибке
            userDetailContainer.innerHTML = '<p>Error fetching user data</p>';
        });
}

function addListeners() {
    const logoutButton = document.querySelector('.sidebar-left ul li:last-child a');

    logoutButton.addEventListener('click', function(event) {
        event.preventDefault();

        localStorage.clear();
        window.location.href = '/login';
    });

    const userImage = document.getElementById('userPhoto');
    const loadingOverlay = document.getElementById('loadingOverlay');

    userImage.addEventListener('mouseover', function() {
        userImage.classList.add('darken-image');
    });

    userImage.addEventListener('mouseout', function() {
        userImage.classList.remove('darken-image');
    });

    userImage.addEventListener('click', function() {
        const input = document.createElement('input');
        input.type = 'file';
        input.accept = '.jpg, .jpeg, .png';
        input.addEventListener('change', function() {
            const file = input.files[0];
            if (file) {
                loadingOverlay.style.display = 'flex'; // Показываем загрузочный оверлей

                setUserPhoto(file)
                    .then(photoUrl => {
                        userImage.src = photoUrl;
                        loadingOverlay.style.display = 'none'; // Скрываем загрузочный оверлей
                    })
                    .catch(error => {
                        console.error('Error setting user photo:', error);
                        loadingOverlay.style.display = 'none'; // Скрываем загрузочный оверлей при ошибке
                    });
            }
        });
        input.click();
    });
}

document.addEventListener('DOMContentLoaded', function() {
    addListeners(document);
    getUserData();
});
