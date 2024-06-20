import {getFile} from "../handlers/getFile.js";


export async function getPublications(user_uuid) {
    try {
        const response = await fetch('/api/v1/users/' + user_uuid + '/publications', {
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

        return await Promise.all(publicationData.map(async (publication) => {
            if (publication.files && publication.files.length > 0) {
                publication.files = await Promise.all(publication.files.map(async (file) => {
                    const url = await getFile(file.uuid);
                    console.log(url);
                    return {...file, url};
                }));
            }
            return publication;
        }));

    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}
