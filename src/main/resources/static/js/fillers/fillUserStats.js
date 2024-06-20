import {getSubscriptions} from "../handlers/getSubscriptions.js";
import {getFollowers} from "../handlers/getFollowers.js";

export async function fillUserStats(user) {
    const subscriptions = await getSubscriptions(user.uuid);
    const followers = await getFollowers(user.uuid);

    const followingCount = document.getElementById("followingCount");
    const followersCount = document.getElementById("followersCount");

    followingCount.textContent = subscriptions.length;
    followersCount.textContent = followers.length;

    const followingLink = document.getElementById('following');
    const followersLink = document.getElementById('followers');

    followingLink.href = `/${user.username}/following`;
    followersLink.href = `/${user.username}/followers`;

    if (subscriptions.length === 0) {
        followingLink.classList.add('disabled-link');
        followingLink.removeAttribute('href');
    }

    if (followers.length === 0) {
        followersLink.classList.add('disabled-link');
        followersLink.removeAttribute('href');
    }
}