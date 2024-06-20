import {getSubscriptions} from "../handlers/getSubscriptions.js";

export async function setupFollowButton(myUser, user) {
    const followButton = document.querySelector('.follow-button');
    const mySubscriptions = await getSubscriptions(myUser.uuid);

    const isFollowing = mySubscriptions.some(subscription => subscription.username === user.username);

    if (isFollowing) {
        followButton.classList.add('followed');
        followButton.textContent = 'Followed';
    } else {
        followButton.classList.remove('followed');
        followButton.textContent = 'Follow';

    }

    followButton.style.display = 'block';
}