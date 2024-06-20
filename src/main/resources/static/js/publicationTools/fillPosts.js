import {getPublications} from "./getPublications.js";
import {renderPost} from "./renderPost.js";


export async function fillProfilePosts(user) {
    const post_data = await getPublications(user.uuid);

    for (let i = post_data.length - 1; i >= 0; i--) {
        await renderPost(post_data[i], user);
    }
}