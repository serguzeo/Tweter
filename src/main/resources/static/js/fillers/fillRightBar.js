import {getUserProfile} from "../handlers/getProfile.js";
import {getSubscriptions} from "../handlers/getSubscriptions.js";

export async function fillRightBar(username) {
    const followingList = document.getElementById('followingList');
    followingList.innerHTML = '';

    const user = await getUserProfile(username);
    const subscriptions = await getSubscriptions(user.uuid);

    for (const userObj of subscriptions) {
        const listItem = document.createElement('li');
        listItem.classList.add('following-item');

        const link = document.createElement('a');
        link.href = `/${userObj.username}`;
        link.textContent = `${userObj.firstName} ${userObj.lastName} @${userObj.username}`;
        link.classList.add('following-link');

        listItem.appendChild(link);
        followingList.appendChild(listItem);
    }
}