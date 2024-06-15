import {getUserProfile} from "../handlers/getProfile.js";


export async function fillLeftBar(username) {
    const userDetailContainer = document.querySelector('.user-details');
    const profileLink = document.getElementById('profileLink');
    const userNameElement = userDetailContainer.querySelector('h3');
    const userUsernameElement = userDetailContainer.querySelector('p');
    const userImage = document.querySelector('.user-info img');

    try {
        const user = await getUserProfile(username);

        profileLink.href = `/${user.username}`;
        userNameElement.textContent = user.firstName + ' ' + user.lastName;
        userUsernameElement.textContent = '@' + user.username;
        userImage.src = user.userPhotoUrl;

    } catch (error) {
        console.error('Error fetching user data:', error);
        userDetailContainer.innerHTML = '<p>Error fetching user data</p>';
    }
}