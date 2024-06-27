export async function addImage(container, file) {
    const image = document.createElement('img');
    image.src = file.url;
    image.alt = 'Attached Image';
    image.controls = true;

    image.addEventListener('click', function() {
        const modal = document.getElementById('fileModal');
        const modalContentContainer = document.getElementById('fileModalContent');
        const modalImg = document.createElement('img');
        modalImg.classList.add('image-modal')
        modalContentContainer.appendChild(modalImg)
        modal.style.display = 'flex';
        modalImg.src = image.src;
    });

    container.appendChild(image);
}

export async function addVideo(container, file) {
    const video = document.createElement('video');
    video.src = file.url;
    video.controls = true;

    container.appendChild(video);
}

export async function addFileAndDownloadIcon(container, file) {
    const defaultFiles = container.querySelector('.default-files');
    const defaultDownloadIcons = container.querySelector('.default-download-icons');

    const fileName = document.createElement('span');
    fileName.textContent = file.originalFilename;

    const downloadIcon = document.createElement('i');
    downloadIcon.classList.add('download-icon');
    downloadIcon.textContent = '⤓';

    downloadIcon.addEventListener('click', () => {
        const link = document.createElement('a');
        link.href = file.url;
        link.download = file.originalFilename;
        link.click();
    });

    defaultDownloadIcons.appendChild(downloadIcon);
    defaultFiles.appendChild(fileName);
}
