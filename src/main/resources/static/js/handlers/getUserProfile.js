export function getUserProfile() {
    const requiredKeys = ['uuid', 'username', 'firstName', 'lastName', 'dateOfBirth', 'email', 'userPhotoUuid'];
    const userDetails = {};

    let allKeysPresent = true;
    requiredKeys.forEach(key => {
        if (!localStorage.getItem(key)) {
            allKeysPresent = false;
        } else {
            userDetails[key] = localStorage.getItem(key);
        }
    });

    if (allKeysPresent) {
        return Promise.resolve(userDetails);
    } else {
        return fetch('/api/v1/users/me', {
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
                } else {
                    throw new Error('Failed to fetch user data');
                }
            })
            .then(user => {
                requiredKeys.forEach(key => {

                    if (key === 'userPhotoUuid' && user["userPhoto"]) {
                        // Assuming userPhoto is an object with an uuid field
                        localStorage.setItem('userPhotoUuid', user["userPhoto"].uuid);
                        userDetails['userPhotoUuid'] = user["userPhoto"].uuid;
                    } else {
                        localStorage.setItem(key, user[key]);
                        userDetails[key] = user[key];
                    }
                });
                return userDetails;
            })
            .catch(error => {
                console.error('Error:', error);
                throw error;
            });
    }
}
