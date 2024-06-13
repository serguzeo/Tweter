export function getUserPhoto() {
    const photoUUID = localStorage.getItem('userPhotoUuid');

    if (!photoUUID) {
        return Promise.resolve('/img/user.png');
    }

    return fetch(`/api/v1/files/${photoUUID}`, {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('token')
        }
    })
        .then(response => {
            if (response.status === 200) {
                return response.blob();
            } else {
                throw new Error('Failed to fetch user photo');
            }
        })
        .then(blob => {
            return URL.createObjectURL(blob);
        })
        .catch(error => {
            console.error('Error fetching user photo:', error);
            return '/img/user.png';
        });
}
