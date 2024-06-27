export async function createAuthorInfo(userProfile) {
    const authorInfo = document.createElement('div');
    authorInfo.classList.add('post-author-info');

    const authorAvatarLink = document.createElement('a');
    authorAvatarLink.href = `/${userProfile.username}`;
    authorAvatarLink.classList.add('post-author-avatar-link');

    const authorAvatar = document.createElement('img');
    authorAvatar.src = userProfile.userPhotoUrl;
    authorAvatar.alt = 'Author Avatar';
    authorAvatar.classList.add('post-author-avatar');
    authorAvatarLink.appendChild(authorAvatar);
    authorInfo.appendChild(authorAvatarLink);

    const authorDetailsLink = document.createElement('a');
    authorDetailsLink.href = `/${userProfile.username}`;
    authorDetailsLink.classList.add('post-author-details-link');

    const authorDetails = document.createElement('div');
    authorDetails.classList.add('post-author-details');

    const authorName = document.createElement('h4');
    authorName.textContent = `${userProfile.firstName} ${userProfile.lastName}`;
    authorDetails.appendChild(authorName);

    const authorTag = document.createElement('p');
    authorTag.textContent = `@${userProfile.username}`;
    authorDetails.appendChild(authorTag);

    authorDetailsLink.appendChild(authorDetails);
    authorInfo.appendChild(authorDetailsLink);

    return authorInfo;
}
