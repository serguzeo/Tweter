import {getFile} from "../handlers/getFile.js";

export async function makePublicationsWithFilesUrls(publicationData) {
    return await Promise.all(publicationData.map(async (publication) => {
        if (publication.files && publication.files.length > 0) {
            publication.files = await Promise.all(publication.files.map(async (file) => {
                const url = await getFile(file.uuid);
                return {...file, url};
            }));
        }
        return publication;
    }));
}