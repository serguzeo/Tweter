export function getFile(fileUuid) {
    return fetch(`/api/v1/files/${fileUuid}`, {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('token'),
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.status === 200) {
                return response.blob();
            } else if (response.status === 401) {
                localStorage.clear();
                window.location.href = '/login';
                throw new Error('Unauthorized');
            } else {
                throw new Error('Failed to fetch file');
            }
        })
        .then(blob => {
            return URL.createObjectURL(blob);
        })
        .catch(error => {
            console.error('Error fetching file:', error);
        });
}
