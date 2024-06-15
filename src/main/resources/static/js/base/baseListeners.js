export function addBaseListeners() {
    const profileLink = document.getElementById('profileLink');
    const storedUsername = localStorage.getItem('username');
    if (storedUsername) {
        profileLink.href = `/${storedUsername}`;
    } else {
        console.error('Username not found in localStorage');
    }

    const logoutButton = document.querySelector('.sidebar-left ul li:last-child a');
    logoutButton.addEventListener('click', function(event) {
        event.preventDefault();

        localStorage.clear();
        window.location.href = '/login';
    });
}