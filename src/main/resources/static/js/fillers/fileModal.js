export async function fileModal() {
    const fileModalEl = document.getElementById('fileModal')
    const fileContainer = document.getElementById('fileModalContent')
    const closeButton = document.getElementById('closeModal')

    closeButton.addEventListener('click', () => {
        fileModalEl.style.display = 'none';

        while (fileContainer.firstChild) {
            fileContainer.removeChild(fileContainer.firstChild);
        }
    })
}