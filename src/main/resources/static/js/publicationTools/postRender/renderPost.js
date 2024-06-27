import {createPostElement} from "./createPostElement.js";
import {createAuthorInfo} from "./createAuthorInfo.js";
import {createMetaContainer} from "./createMetaContainer.js";
import {createPostText} from "./createPostText.js";
import {setFilesContainerClass} from "./setFilesContainerClass.js";
import {createDefaultFilesContainer, createFilesContainer} from "./createFilesContainer.js";
import {addFileAndDownloadIcon, addImage, addVideo} from "./appendFile.js";
import {createRepliedTo} from "./createRepliedTo.js";

export async function renderPost(post, userProfile) {
    const postElement = await createPostElement(post);
    const authorInfo = await createAuthorInfo(userProfile);
    const metaContainer = await createMetaContainer(post, userProfile, postElement);
    const postText = await createPostText(post);

    authorInfo.appendChild(metaContainer);
    postElement.appendChild(authorInfo);
    postElement.appendChild(postText);

    if (post.files && post.files.length > 0) {
        const filesContainer = await createFilesContainer();
        const defaultFilesContainer  = await createDefaultFilesContainer();
        postElement.appendChild(filesContainer);
        postElement.appendChild(defaultFilesContainer);

        post.files.sort((a, b) => b.size - a.size);

        for (const file of post.files) {
            if (file.contentType.startsWith('image')) {
                await addImage(filesContainer, file);

            } else if (file.contentType.startsWith('video')) {
                await addVideo(filesContainer, file);

            } else {
                await addFileAndDownloadIcon(postElement, file);
            }
        }

        await setFilesContainerClass(filesContainer);
    }

    if (post.repliedTo) {
        postElement.appendChild(await createRepliedTo(post))
    }

    return postElement;
}