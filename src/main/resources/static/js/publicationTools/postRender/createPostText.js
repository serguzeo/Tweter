export async function createPostText(post) {
    const postText = document.createElement('p');
    postText.textContent = post.text;
    postText.classList.add('post-text');

    return postText;
}