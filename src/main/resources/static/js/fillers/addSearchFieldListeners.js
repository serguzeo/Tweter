import {debounce} from "../debounce.js";
import {getUsersByPrefix} from "../handlers/getUsersByPrefix.js";
import {createUserListItem} from "./createUserListItem.js";


const searchInput = document.getElementById('searchInput');
const searchResults = document.getElementById('searchResults');

async function handleSearch() {
    const prefix = searchInput.value.trim();

    if (prefix.length === 0) {
        searchResults.innerHTML = '';
        searchResults.style.display = 'none';
        return;
    }

    try {
        const userList = await getUsersByPrefix(prefix);

        searchResults.innerHTML = '';

        userList.forEach(user => {
            const listItem = createUserListItem(user, "search");
            searchResults.appendChild(listItem);
        });

        searchResults.style.display = 'block';
    } catch (error) {
        console.error('Error fetching users:', error);
    }
}

export function addSearchFieldListeners() {
    searchInput.addEventListener('input', debounce(handleSearch, 300));
    searchInput.addEventListener('focus', () => {
        searchResults.style.display = 'block';
    });

    searchInput.addEventListener('blur', () => {
        setTimeout(() => {
            searchResults.style.display = 'none';
        }, 200);
    });
}