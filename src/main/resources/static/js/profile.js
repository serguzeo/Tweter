import {getMyUsername} from "./handlers/getMyUsername.js";
import {fillLeftBar} from "./fillers/fillLeftBar.js";
import {updateProfile} from "./handlers/updateProfile.js";
import {getUserProfile} from "./handlers/getProfile.js";
import {getFile} from "./handlers/getFile.js";

function addListeners() {
    const userLeftPhoto = document.getElementById('userPhoto')
    const userProfilePhoto = document.getElementById('profileUserPhoto');
    const loadingOverlay = document.getElementById('loadingOverlay');

    userProfilePhoto.addEventListener('mouseover', function() {
        userProfilePhoto.classList.add('darken-image');
    });

    userProfilePhoto.addEventListener('mouseout', function() {
        userProfilePhoto.classList.remove('darken-image');
    });

    userProfilePhoto.addEventListener('click', function() {
        const input = document.createElement('input');
        input.type = 'file';
        input.accept = '.jpg, .jpeg, .png';
        input.addEventListener('change', function() {
            const file = input.files[0];
            if (file) {
                loadingOverlay.style.display = 'flex';

                const formData = new FormData();
                formData.append('file', file);
                updateProfile(formData)
                    .then(photoUrl => {
                        userLeftPhoto.src = photoUrl;
                        userProfilePhoto.src = photoUrl;
                        loadingOverlay.style.display = 'none';
                    })
                    .catch(error => {
                        console.error('Error setting user photo:', error);
                        loadingOverlay.style.display = 'none';
                    });
            }
        });
        input.click();
    });
}


document.addEventListener('DOMContentLoaded', async function() {
    const userUsername = await getMyUsername();
    await fillLeftBar(userUsername);

    const urlPath = window.location.pathname;
    const usernameFromUrl = urlPath.split('/')[1];

    if (usernameFromUrl) {
        if (usernameFromUrl === userUsername) {
            addListeners();
        }

        const user = await getUserProfile(usernameFromUrl);
        const userFullnameElement = document.querySelector('.user-meta .user-fullname');
        const userTag = document.querySelector('.user-meta .user-tag');
        const userBio = document.querySelector('.user-meta .user-bio');

        userFullnameElement.textContent = user.firstName + ' ' + user.lastName;
        userTag.textContent = '@' + user.username;

        if (user.userPhoto) {
            const userProfilePhoto = document.getElementById('profileUserPhoto');
            userProfilePhoto.src = await getFile(user.userPhoto.uuid);
        }
    }
});
