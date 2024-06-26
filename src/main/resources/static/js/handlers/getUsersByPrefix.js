import { getFile } from "./getFile.js";

export async function getUsersByPrefix(prefix) {
    return fetch('/api/v1/users/prefix/' + prefix, {
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
        .then(users => {
            if (users && users.length > 0) {
                const userPromises = users.map(user => {
                    if (user.userPhoto && user.userPhoto.uuid) {
                        return getFile(user.userPhoto.uuid).then(photoUrl => {
                            user.userPhotoUrl = photoUrl;
                            return user;
                        });
                    } else {
                        user.userPhotoUrl = '/img/user.png';
                        return user;
                    }
                });

                return Promise.all(userPromises);
            } else {
                return [];
            }
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            throw error;
        });
}
