export function updateProfile(formData) {

    return fetch('/api/v1/users/me', {
        method: 'PUT',
        headers: {
            'Authorization': localStorage.getItem('token')
        },
        body: formData
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else if (response.status === 401) {
                localStorage.clear();
                window.location.href = '/login';
                return Promise.reject('Unauthorized');
            }
        })
        .then(data => {
            const photoUuid = data.userPhoto.uuid;
            return fetch(`/api/v1/files/${photoUuid}`, {
                method: 'GET',
                headers: {
                    'Authorization': localStorage.getItem('token')
                }
            });
        })
        .then(response => {
            if (response.status === 200) {
                return response.blob();
            } else if (response.status === 401) {
                localStorage.clear();
                window.location.href = '/login';
                return Promise.reject('Unauthorized');
            } else {
                return Promise.reject('Failed to fetch photo');
            }
        })
        .then(blob => {
            const objectURL = URL.createObjectURL(blob);

            return objectURL;
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
