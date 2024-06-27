import {getPublications} from "./getPublications.js";
import {renderPost} from "./postRender/renderPost.js";


export async function fillProfilePosts(user) {
    const post_data = await getPublications(user.uuid);
    const postsContainer = document.querySelector('.posts');

    for (let i = post_data.length - 1; i >= 0; i--) {
        postsContainer.insertBefore(await renderPost(post_data[i], user), postsContainer.firstChild);
    }
}