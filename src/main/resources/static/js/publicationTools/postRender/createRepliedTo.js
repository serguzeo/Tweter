import { getUserProfile } from "../../handlers/getProfile.js";
import { getFile } from "../../handlers/getFile.js";
import { makeDeletedPublication } from "./makeDeletedPublication.js";
import { makeDeletedUser } from "./makeDeletedUser.js";
import { renderPost } from "./renderPost.js";

export async function createRepliedTo(post) {
    if (!post.repliedTo.isDeleted) {
        if (post.repliedTo.publication.files) {
            post.repliedTo.publication.files = await Promise.all(post.repliedTo.publication.files.map(async (file) => {
                const url = await getFile(file.uuid);
                return { ...file, url };
            }));
        }

        const repliedToUser = await getUserProfile(post.repliedTo.publication.user.username);
        return await renderPost(post.repliedTo.publication, repliedToUser);
    } else {
        return await renderPost(makeDeletedPublication(), await makeDeletedUser());
    }
}
