import {getFile} from "../handlers/getFile.js";

export async function postPublication(formData) {
    try {
        const response = await fetch('/api/v1/publications', {
            method: 'POST',
            headers: {
                'Authorization': localStorage.getItem('token')
            },
            body: formData
        });

        if (response.status !== 201) {
            if (response.status === 401) {
                localStorage.clear();
                window.location.href = '/login';
            }
        }

        const publicationData = await response.json();

        if (publicationData.files) {
            publicationData.files = await Promise.all(publicationData.files.map(async (file) => {
                const url = await getFile(file.uuid);
                return {...file, url};
            }));
        }
        return publicationData;

    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}
