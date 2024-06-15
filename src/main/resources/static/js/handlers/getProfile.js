import {getFile} from "./getFile.js";

export function getUserProfile(username) {
    return fetch('/api/v1/users/username/' + username, {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('token')
        }
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else if (response.status === 401) {
                localStorage.clear();
                window.location.href = '/login';
            } else if (response.status === 404) {
                window.location.href = '/404';
            }
        })
        .then(user => {
            if (user.userPhoto && user.userPhoto.uuid) {
                return getFile(user.userPhoto.uuid).then(photoUrl => {
                    user.userPhotoUrl = photoUrl;
                    return user;
                });
            } else {
                return user;
            }
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            throw error;
        });
}
