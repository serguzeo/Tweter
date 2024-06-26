export function createUserListItem(user, suffix = '') {
    const getClassWithSuffix = (baseClass) => suffix ? `${baseClass}-${suffix}` : baseClass;

    const listItem = document.createElement('li');
    listItem.classList.add(getClassWithSuffix('user-item'));

    const userLink = document.createElement('a');
    userLink.href = `/${encodeURIComponent(user.username)}`;
    userLink.classList.add(getClassWithSuffix('user-link'));

    const userAvatar = document.createElement('img');
    userAvatar.src = user.userPhotoUrl;
    userAvatar.alt = 'User Avatar';
    userAvatar.classList.add(getClassWithSuffix('user-avatar'));

    const userInfo = document.createElement('div');
    userInfo.classList.add(getClassWithSuffix('user-info'));

    const username = document.createElement('p');
    username.textContent = `${user.firstName} ${user.lastName}`;
    username.classList.add(getClassWithSuffix('user-username'));

    const tag = document.createElement('p');
    tag.textContent = `@${user.username}`;
    tag.classList.add(getClassWithSuffix('user-tag'));

    userLink.appendChild(userAvatar);
    userLink.appendChild(userInfo);

    userInfo.appendChild(username);
    userInfo.appendChild(tag);

    listItem.appendChild(userLink);

    return listItem;
}
