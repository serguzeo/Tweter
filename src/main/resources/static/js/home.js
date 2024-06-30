import {fillLeftBar} from "./fillers/fillLeftBar.js";
import {getMyUsername} from "./handlers/getMyUsername.js";
import {fillRightBar} from "./fillers/fillRightBar.js";
import {getUserProfile} from "./handlers/getProfile.js";
import {addPostButtonListener} from "./publicationTools/postButtonListener.js";
import {fillFeed} from "./publicationTools/fillFeed.js";
import {addSearchFieldListeners} from "./fillers/addSearchFieldListeners.js";
import {fileModal} from "./fillers/fileModal.js";




document.addEventListener('DOMContentLoaded', async function() {
    const username = await getMyUsername();
    localStorage.setItem("username", username);
    sessionStorage.setItem("repliedTo", "")
    const userProfile = await getUserProfile(username);
    await fillLeftBar(username);
    await fillRightBar(username);
    await fillFeed(userProfile);
    await fileModal();
    await addSearchFieldListeners();
    await addPostButtonListener(
        userProfile, 'postButton', 'postText', 'fileInput'
    );
    await addPostButtonListener(
        userProfile, 'postButtonModal', 'postTextModal', 'fileInputModal'
    );

    const loadingIndicator = document.getElementById('loadingIndicator');
    loadingIndicator.style.display = 'none';
});

