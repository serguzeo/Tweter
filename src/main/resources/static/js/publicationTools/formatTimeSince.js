export function formatTimeSince(publishedAt) {
    let now = new Date();
    const publishedDate = new Date(publishedAt);
    publishedDate.setUTCHours(publishedDate.getUTCHours() - 3);

    const seconds = Math.floor((now.getTime() - publishedDate.getTime()) / 1000);

    let interval = seconds / 31536000;
    if (interval > 1) {
        return `${Math.floor(interval)} years ago`;
    }
    interval = seconds / 2592000;
    if (interval > 1) {
        return `${Math.floor(interval)} months ago`;
    }
    interval = seconds / 86400;
    if (interval > 1) {
        return `${Math.floor(interval)} days ago`;
    }
    interval = seconds / 3600;
    if (interval > 1) {
        return `${Math.floor(interval)} hours ago`;
    }
    interval = seconds / 60;
    if (interval > 1) {
        return `${Math.floor(interval)} minutes ago`;
    }
    return `${Math.floor(seconds)} seconds ago`;
}