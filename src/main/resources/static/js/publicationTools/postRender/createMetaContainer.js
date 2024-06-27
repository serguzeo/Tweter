import { formatTimeSince } from "../formatTimeSince.js";
import { deletePost } from "../deletePost.js";
import { editPost } from "../editPost.js";

export async function createMetaContainer(post, userProfile, postElement) {
    const metaContainer = document.createElement('div');
    metaContainer.classList.add('meta-container');

    const postTime = document.createElement('span');
    postTime.classList.add('post-time');
    postTime.textContent = formatTimeSince(post.publishedAt);
    metaContainer.appendChild(postTime);

    if (localStorage.getItem('username') === userProfile.username) {
        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete-post-button');
        deleteButton.textContent = '✗️';
        deleteButton.title = "Delete";
        metaContainer.appendChild(deleteButton);

        const editButton = document.createElement('button');
        editButton.classList.add('edit-post-button');
        editButton.textContent = '✎️';
        editButton.title = "Edit";
        metaContainer.appendChild(editButton);

        deleteButton.addEventListener('click', () => {
            deletePost(post.uuid);
            postElement.remove();
        });

        editButton.addEventListener('click', async () => {
            const postText = postElement.querySelector('.post-text');

            if (editButton.textContent === '✎') {
                editButton.textContent = '✓️';
                editButton.title = "Confirm";
                postText.contentEditable = 'true';
                postText.style.border = '1px solid #ccc';
                postText.style.padding = '5px';
                postText.focus();
            } else {
                editButton.textContent = '✎';
                editButton.title = "Edit";
                postText.contentEditable = 'false';
                postText.style.border = 'none';
                postText.style.padding = '0';

                const formData = new FormData();
                formData.append("text", postText.textContent);
                await editPost(post.uuid, formData);
            }
        });
    } else {
        const repostButton = document.createElement('button');
        repostButton.classList.add('repost-post-button');
        repostButton.textContent = '⚐';
        repostButton.title = "Repost";
        metaContainer.appendChild(repostButton);

        const modal = document.getElementById('modal');
        const closeBtn = modal.querySelector('.close');
        const postText = document.getElementById('postTextModal');
        const replyToText = document.getElementById('replyText');
        const fileInput = document.getElementById("fileInputModal");

        repostButton.addEventListener('click', async function () {
            modal.style.display = 'flex';
            replyToText.textContent = "Reply to " + `${userProfile.firstName} ${userProfile.lastName}`;
            sessionStorage.setItem('repliedTo', post.uuid);
        });

        closeBtn.addEventListener('click', function() {
            modal.style.display = 'none';
            postText.textContent = '';
            fileInput.value = '';
            sessionStorage.setItem('repliedTo', '');
        });

        window.addEventListener('click', function(event) {
            if (event.target === modal) {
                modal.style.display = 'none';
                postText.textContent = '';
                fileInput.value = '';
                sessionStorage.setItem('repliedTo', '');
            }
        });
    }

    return metaContainer;
}
