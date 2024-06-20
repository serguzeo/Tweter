import {getFile} from "../handlers/getFile.js";

export async function updateUserProfileUI(user) {
    const userFullnameElement = document.querySelector('.user-meta .user-fullname');
    const userTag = document.querySelector('.user-meta .user-tag');
    const userBio = document.querySelector('.user-meta .user-bio');

    userFullnameElement.textContent = `${user.firstName} ${user.lastName}`;
    userTag.textContent = `@${user.username}`;
    userBio.textContent = user.bio;

    if (user.userPhoto) {
        const userProfilePhoto = document.getElementById('profileUserPhoto');
        getFile(user.userPhoto.uuid).then(photoUrl => {
            userProfilePhoto.src = photoUrl;
        });
    }
}