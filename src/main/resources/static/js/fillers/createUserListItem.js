export function createUserListItem(user) {
    const listItem = document.createElement('li');
    listItem.classList.add('user-item');

    const userLink = document.createElement('a');
    userLink.href = `/${encodeURIComponent(user.username)}`;
    userLink.classList.add('user-link');

    const userAvatar = document.createElement('img');
    userAvatar.src = user.userPhotoUrl;
    userAvatar.alt = 'User Avatar';
    userAvatar.classList.add('user-avatar');

    const userInfo = document.createElement('div');
    userInfo.classList.add('user-info');

    const username = document.createElement('p');
    username.textContent = `${user.firstName} ${user.lastName}`;
    username.classList.add('user-username');

    const tag = document.createElement('p');
    tag.textContent = `@${user.username}`;
    tag.classList.add('user-tag');

    userLink.appendChild(userAvatar);
    userLink.appendChild(userInfo);

    userInfo.appendChild(username);
    userInfo.appendChild(tag);

    listItem.appendChild(userLink);

    return listItem;
}