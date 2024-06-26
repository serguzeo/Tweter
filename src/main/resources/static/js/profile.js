import {getMyUsername} from "./handlers/getMyUsername.js";
import {fillLeftBar} from "./fillers/fillLeftBar.js";
import {updateProfile} from "./handlers/updateProfile.js";
import {getUserProfile} from "./handlers/getProfile.js";
import {subscribe} from "./handlers/subscribe.js";
import {unsubscribe} from "./handlers/unsubscribe.js";
import {fillRightBar} from "./fillers/fillRightBar.js";
import {updateUserProfileUI} from "./fillers/updateUserProfileUI.js";
import {addPhotoListeners} from "./fillers/addPhotoListeners.js";
import {setupEditButton} from "./fillers/setupEditButton.js";
import {setupFollowButton} from "./fillers/setupFollowButton.js";
import {fillUserStats} from "./fillers/fillUserStats.js";
import {fillProfilePosts} from "./publicationTools/fillPosts.js";
import {addSearchFieldListeners} from "./fillers/addSearchFieldListeners.js";
import {addPostButtonListener} from "./publicationTools/postButtonListener.js";
import {fileModal} from "./fillers/fileModal.js";


async function addListeners(user) {
    // add event listeners for edit button
    const editButton = document.querySelector('.edit-button');
    const userBio = document.querySelector('.user-meta .user-bio');
    editButton.addEventListener('click', () => {
        if (editButton.textContent === '✎ Edit') {
            editButton.textContent = 'Save';

            userBio.contentEditable = 'true';
            userBio.style.border = '1px solid #ccc';
            userBio.style.padding = '5px';
            userBio.focus();
        } else {
            editButton.textContent = '✎ Edit';
            userBio.contentEditable = 'false';
            userBio.style.border = 'none';
            userBio.style.padding = '0';

            const formData = new FormData();
            formData.append("bio", userBio.textContent);
            updateProfile(formData);
        }
    });

    // add event listeners for follow button
    const followButton = document.querySelector('.follow-button');
    followButton.addEventListener('click', function () {
        if (followButton.textContent === "Follow") {
            subscribe(user.uuid)
        } else {
            unsubscribe(user.uuid)
        }
    });


}

async function initialize() {
    // fill left and right bar using current authenticated user
    const userUsername = await getMyUsername();
    localStorage.setItem("username", userUsername);
    sessionStorage.setItem("repliedTo", "")
    const myUser = await getUserProfile(userUsername);
    await fillLeftBar(userUsername);
    await fillRightBar(userUsername);
    await fileModal();
    await addSearchFieldListeners();
    await addPostButtonListener(
        myUser, 'postButtonModal', 'postTextModal', 'fileInputModal'
    );


    // get user profile using url path
    const urlPath = window.location.pathname;
    const usernameFromUrl = urlPath.split('/')[1];
    const user = await getUserProfile(usernameFromUrl);

    // update profile UI
    await updateUserProfileUI(user);
    await fillUserStats(user);
    await fillProfilePosts(user);

    // detect if profile is current authenticated user profile or not
    if (usernameFromUrl === userUsername) {
        setupEditButton();
        await addPhotoListeners();
    } else {
        await setupFollowButton(myUser, user);
    }

    await addListeners(user);

    const loadingIndicator = document.getElementById('loadingIndicator');
    loadingIndicator.style.display = 'none';
}

document.addEventListener('DOMContentLoaded', initialize);
