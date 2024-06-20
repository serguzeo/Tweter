export function getFollowers(user_uuid) {

    return fetch('/api/v1/users/' + user_uuid + '/followers', {
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
        .catch(error => {
            console.error('Error fetching user data:', error);
            throw error;
        });
}