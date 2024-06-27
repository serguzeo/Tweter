import {postPublication} from "./postPublication.js";
import {renderPost} from "./postRender/renderPost.js";


export async function addPostButtonListener(userProfile, postButtonId, postTextId, fileInputId) {
    const postButton = document.getElementById(postButtonId);

    const postButtonEventListener = async function (){
        const repliedTo = sessionStorage.getItem("repliedTo");

        const postText = document.getElementById(postTextId);
        const fileInput = document.getElementById(fileInputId);
        const postsContainer = document.querySelector('.posts');

        const files = fileInput.files;
        const formData = new FormData();
        formData.append('text', postText.value);

        for (let i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
        }

        if (repliedTo) {
            formData.append('repliedTo', repliedTo);
        }

        try {
            const newPost = await postPublication(formData);

            const urlPath = window.location.pathname;
            const part = urlPath.split('/')[1];

            if (part === "home") {
                postsContainer.insertBefore(await renderPost(newPost, userProfile), postsContainer.firstChild);
            }

            postText.value = '';
            fileInput.value = '';

        } catch (error) {
            console.error('Error posting publication:', error);
        }

        if (repliedTo) {
            const modal = document.getElementById("modal");
            modal.style.display = "none";
        }
    }

    postButton.addEventListener('click', postButtonEventListener)
}