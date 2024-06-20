import {getMyUsername} from "./handlers/getMyUsername.js";
import {fillLeftBar} from "./fillers/fillLeftBar.js";
import {getSubscriptions} from "./handlers/getSubscriptions.js";
import {getUserProfile} from "./handlers/getProfile.js";
import {getFollowers} from "./handlers/getFollowers.js";
import {createUserListItem} from "./fillers/createUserListItem.js";
import {fillRightBar} from "./fillers/fillRightBar.js";

document.addEventListener('DOMContentLoaded', async function() {
    // fill left and right bar using current authenticated user
    const userUsername = await getMyUsername();
    await fillLeftBar(userUsername);
    await fillRightBar(userUsername);

    // get user from url and it's followers/subscriptions
    const path = window.location.pathname;
    const isFollowers = path.includes('followers');

    const curr_username = path.split('/')[1];
    const curr_user = await getUserProfile(curr_username);

    let userListData;
    if (isFollowers) {
        userListData = await getFollowers(curr_user.uuid);
    } else {
        userListData = await getSubscriptions(curr_user.uuid);
    }

    // fill the list
    const userListContainer = document.getElementById('userListContainer');
    userListContainer.innerHTML = '';

    for (const userObj of userListData) {
        const user = await getUserProfile(userObj.username);
        const listItem = createUserListItem(user);
        userListContainer.appendChild(listItem);
    }

});