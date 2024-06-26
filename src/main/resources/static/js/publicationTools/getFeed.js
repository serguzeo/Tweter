import {makePublicationsWithFilesUrls} from "./makePublicationsWithFilesUrls.js";


export async function getFeed() {
    try {
        const response = await fetch('/api/v1/feed', {
            method: 'GET',
            headers: {
                'Authorization': localStorage.getItem('token')
            },
        });

        if (response.status !== 200) {
            if (response.status === 401) {
                localStorage.clear();
                window.location.href = '/login';
            }
        }

        const publicationData = await response.json();

        return await makePublicationsWithFilesUrls(publicationData);

    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}
