import {getFeed} from "./getFeed.js";
import {renderPost} from "./postRender/renderPost.js";
import {getUserProfile} from "../handlers/getProfile.js";

export async function fillFeed(currentUser) {
    const post_data = await getFeed();
    const postsContainer = document.querySelector('.posts');

    for (let i = post_data.length - 1; i >= 0; i--) {
        const user = await getUserProfile(post_data[i].user.username);


        postsContainer.insertBefore(await renderPost(post_data[i], user), postsContainer.firstChild);
    }
}