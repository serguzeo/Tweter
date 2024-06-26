import {getFeed} from "./getFeed.js";
import {renderPost} from "./renderPost.js";
import {getUserProfile} from "../handlers/getProfile.js";

export async function fillFeed() {
    const post_data = await getFeed();

    for (let i = post_data.length - 1; i >= 0; i--) {
        const user = await getUserProfile(post_data[i].user.username);

        await renderPost(post_data[i], user);
    }
}