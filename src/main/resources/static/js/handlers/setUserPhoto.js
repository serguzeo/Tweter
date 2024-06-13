export function setUserPhoto(file) {
    const formData = new FormData();
    formData.append('file', file);

    return fetch('/api/v1/users/me/setProfilePhoto', {
        method: 'POST',
        headers: {
            'Authorization': localStorage.getItem('token')
        },
        body: formData
    })
        .then(response => {
            if (response.status === 201) {
                return response.json();
            } else {
                throw new Error('Failed to set user photo');
            }
        })
        .then(data => {
            const photoUUID = data.userPhoto.uuid;
            return fetch(`/api/v1/files/${photoUUID}`, {
                method: 'GET',
                headers: {
                    'Authorization': localStorage.getItem('token')
                }
            });
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
        });
}
