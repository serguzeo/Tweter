export async function setFilesContainerClass (container) {
    switch (container.childElementCount) {
        case 1:
            container.classList.add('one-file');
            break;
        case 2:
            container.classList.add('two-files');
            break;
        case 3:
            container.classList.add('three-files');
            break;
        case 4:
            container.classList.add('four-files');
            break;
        case 5:
            container.classList.add('five-files');
            break;
        default:
            container.classList.add('six-files');
            break;
    }
}