export async function createDefaultFilesContainer() {
    const defaultFilesContainer = document.createElement('div');
    defaultFilesContainer.classList.add('default-files-container');
    const defaultFiles = document.createElement('div');
    defaultFiles.classList.add('default-files');
    const defaultDownloadIcons = document.createElement('div');
    defaultDownloadIcons.classList.add('default-download-icons');

    defaultFilesContainer.appendChild(defaultFiles);
    defaultFilesContainer.appendChild(defaultDownloadIcons);
    return defaultFilesContainer;
}

export async function createFilesContainer() {
    const filesContainer = document.createElement('div');
    filesContainer.classList.add('files-container');
    return filesContainer;
}