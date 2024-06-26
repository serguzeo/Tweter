import {formatTimeSince} from "./formatTimeSince.js";
import {deletePost} from "./deletePost.js";
import {editPost} from "./editPost.js";

export function renderPost(post, userProfile) {
    const postsContainer = document.querySelector('.posts');

    const postElement = document.createElement('div');
    postElement.classList.add('post');
    postElement.dataset.postId = post.uuid;

    const authorInfo = document.createElement('div');
    authorInfo.classList.add('post-author-info');

    const authorAvatar = document.createElement('img');
    authorAvatar.src = userProfile.userPhotoUrl; // Замените на фактический путь к аватарке пользователя
    authorAvatar.alt = 'Author Avatar';
    authorAvatar.classList.add('post-author-avatar');
    authorInfo.appendChild(authorAvatar);

    const authorDetails = document.createElement('div');
    authorDetails.classList.add('post-author-details');

    const authorName = document.createElement('h4');
    authorName.textContent = `${userProfile.firstName} ${userProfile.lastName}`;
    authorDetails.appendChild(authorName);

    const authorTag = document.createElement('p');
    authorTag.textContent = `@${userProfile.username}`;
    authorDetails.appendChild(authorTag);

    authorInfo.appendChild(authorDetails);

    const metaContainer = document.createElement('div');
    metaContainer.classList.add('meta-container');

    // Добавляем время публикации
    const postTime = document.createElement('span');
    postTime.classList.add('post-time');
    postTime.textContent = formatTimeSince(post.publishedAt);
    metaContainer.appendChild(postTime);

    if (localStorage.getItem('username') === userProfile.username) {
        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete-post-button');
        deleteButton.textContent = '✗️';
        deleteButton.title = "Delete"
        metaContainer.appendChild(deleteButton);

        const editButton = document.createElement('button');
        editButton.classList.add('edit-post-button');
        editButton.textContent = '✎️';
        editButton.title = "Edit"
        metaContainer.appendChild(editButton);

        deleteButton.addEventListener('click', () => {
            deletePost(post.uuid); // Функция для удаления поста
            postElement.remove();
        });

        editButton.addEventListener('click', async () => {
            const postText = postElement.querySelector('.post-text');

            if (editButton.textContent === '✎') {
                editButton.textContent = '✓️';
                editButton.title = "Confirm"
                postText.contentEditable = 'true';
                postText.style.border = '1px solid #ccc';
                postText.style.padding = '5px';
                postText.focus();
            } else {
                editButton.textContent = '✎';
                editButton.title = "Edit"
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
        repostButton.title = "Repost"
        metaContainer.appendChild(repostButton);
    }

    authorInfo.appendChild(metaContainer);
    postElement.appendChild(authorInfo);

    const postText = document.createElement('p');
    postText.textContent = post.text;
    postText.classList.add('post-text');
    postElement.appendChild(postText);

    if (post.files && post.files.length > 0) {
        const filesContainer = document.createElement('div');
        filesContainer.classList.add('files-container');

        post.files.forEach(file => {
            const fileLink = document.createElement('a');
            fileLink.href = file.url; // Замените на фактический путь к файлу
            fileLink.classList.add('file-link');
            fileLink.download = file.originalFilename; // Добавляем атрибут для скачивания

            if (file.contentType.startsWith('image')) {
                const image = document.createElement('img');
                image.src = file.url; // Замените на фактический путь к изображению
                image.alt = 'Attached Image';
                fileLink.appendChild(image);
            } else if (file.contentType.startsWith('video')) {
                const video = document.createElement('video');
                video.src = file.url; // Замените на фактический путь к видео
                video.controls = true;
                fileLink.appendChild(video);
            } else if (file.contentType.startsWith('audio')) {
                const audio = document.createElement('audio');
                audio.src = file.url; // Замените на фактический путь к аудио
                audio.controls = true;
                fileLink.appendChild(audio);
            } else {
                const fileImage = document.createElement('img');
                fileImage.src = "/img/file.png"; // Путь к иконке-заглушке
                fileImage.alt = 'Attached File';
                fileLink.appendChild(fileImage);

                const fileName = document.createElement('span');
                fileName.textContent = file.originalFilename; // Имя файла
                fileLink.appendChild(fileName);
            }

            filesContainer.appendChild(fileLink);
        });

        postElement.appendChild(filesContainer);
    }

    postsContainer.insertBefore(postElement, postsContainer.firstChild);
}