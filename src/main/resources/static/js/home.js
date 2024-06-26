import {fillLeftBar} from "./fillers/fillLeftBar.js";
import {getMyUsername} from "./handlers/getMyUsername.js";
import {fillRightBar} from "./fillers/fillRightBar.js";
import {getUserProfile} from "./handlers/getProfile.js";
import {addPostButtonListener} from "./publicationTools/postButtonListener.js";
import {fillFeed} from "./publicationTools/fillFeed.js";


document.addEventListener('DOMContentLoaded', async function() {
    const username = await getMyUsername();
    localStorage.setItem("username", username);
    const userProfile = await getUserProfile(username);
    await fillLeftBar(username);
    await fillRightBar(username);
    await fillFeed();


    await addPostButtonListener(userProfile);
});

