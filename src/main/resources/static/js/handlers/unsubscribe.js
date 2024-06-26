export function unsubscribe(user_uuid) {
    const followButton = document.querySelector('.follow-button');

    return fetch('/api/v1/users/' + user_uuid + '/unsubscribe', {
        method: 'DELETE',
        headers: {
            'Authorization': localStorage.getItem('token')
        }
    })
        .then(response => {
            if (response.status === 204) {
                if (followButton) {
                    followButton.classList.remove('followed');
                    followButton.textContent = 'Follow';
                }
            } else if (response.status === 401) {
                localStorage.clear();
                window.location.href = '/login';
            } else if (response.status === 404) {
                window.location.href = '/404';
            }
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            throw error;
        });
}