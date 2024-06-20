import {updateProfile} from "../handlers/updateProfile.js";

export async function addPhotoListeners() {
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
                formData.append('userPhoto', file);
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