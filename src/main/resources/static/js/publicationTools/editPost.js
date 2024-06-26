export async function editPost(post_uuid, formData) {
    try {
        const response = await fetch('/api/v1/publications/' + post_uuid, {
            method: 'PUT',
            headers: {
                'Authorization': localStorage.getItem('token')
            },
            body: formData
        });

        if (response.status !== 200) {
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
