export async function deletePost(post_uuid) {
    try {
        const response = await fetch('/api/v1/publications/' + post_uuid, {
            method: 'DELETE',
            headers: {
                'Authorization': localStorage.getItem('token')
            },
        });

        if (response.status !== 204) {
            if (response.status === 401) {
                localStorage.clear();
                window.location.href = '/login';
            }
        }
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}
