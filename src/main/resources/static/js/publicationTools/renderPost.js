import {formatTimeSince} from "./formatTimeSince.js";

export function renderPost(post, userProfile) {
    const postsContainer = document.querySelector('.posts');

    const postElement = document.createElement('div');
    postElement.classList.add('post');

    const authorInfo = document.createElement('div');
    authorInfo.classList.add('author-info');

    const authorAvatar = document.createElement('img');
    authorAvatar.src = userProfile.userPhotoUrl; // Замените на фактический путь к аватарке пользователя
    authorAvatar.alt = 'Author Avatar';
    authorAvatar.classList.add('author-avatar');
    authorInfo.appendChild(authorAvatar);

    const authorDetails = document.createElement('div');
    authorDetails.classList.add('author-details');

    const authorName = document.createElement('h4');
    authorName.textContent = `${userProfile.firstName} ${userProfile.lastName}`;
    authorDetails.appendChild(authorName);

    const authorTag = document.createElement('p');
    authorTag.textContent = `@${userProfile.username}`;
    authorDetails.appendChild(authorTag);

    authorInfo.appendChild(authorDetails);

    // Добавляем время публикации
    const postTime = document.createElement('span');
    postTime.classList.add('post-time');
    postTime.textContent = formatTimeSince(post.publishedAt);
    authorInfo.appendChild(postTime);

    postElement.appendChild(authorInfo);

    const postText = document.createElement('p');
    postText.textContent = post.text;
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