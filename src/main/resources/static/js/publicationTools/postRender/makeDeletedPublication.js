export async function makeDeletedPublication() {
    return {
        "uuid": "deleted-publication-uuid",
        "text": "",
        "repliedTo": null,
        "files": [],
        "publishedAt": new Date().toISOString(),
    };
}
