import {postPublication} from "./postPublication.js";
import {renderPost} from "./renderPost.js";

export async function addPostButtonListener(userProfile) {
    const postButton = document.getElementById('postButton');
    const postText = document.getElementById('postText');
    const fileInput = document.getElementById('fileInput');

    postButton.addEventListener('click', async () => {
        const files = fileInput.files;
        const formData = new FormData();
        formData.append('text', postText.value);

        for (let i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
        }

        try {
            const newPost = await postPublication(formData);
            renderPost(newPost, userProfile);
            postText.value = '';
            fileInput.value = '';
        } catch (error) {
            console.error('Error posting publication:', error);
        }
    });
}