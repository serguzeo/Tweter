export function getFile(userUuid) {
    return fetch(`http://localhost:8080/api/v1/files/${userUuid}`, {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('token'),
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.status === 200) {
                return response.blob(); // Assuming the file is returned as a blob
            } else if (response.status === 401) {
                localStorage.clear();
                window.location.href = '/login';
            } else {
                throw new Error('Failed to fetch file');
            }
        })
        .then(blob => {
            // Create a URL for the blob and return it
            const url = URL.createObjectURL(blob);
            return url;
        })
        .catch(error => {
            console.error('Error:', error);
            throw error;
        });
}
