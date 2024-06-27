export async function createPostElement(post) {
    const postElement = document.createElement('div');
    postElement.classList.add('post');
    postElement.dataset.postId = post.uuid;
    return postElement;
}
