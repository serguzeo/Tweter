export function getMyUsername() {
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
                throw new Error('Unauthorized'); // чтобы прервать цепочку промисов
            } else {
                throw new Error('Failed to fetch user data'); // для других случаев
            }
        })
        .then(user => {
            return user.username;
        })
        .catch(error => {
            console.error('Error fetching username:', error);
            throw error;
        });
}
