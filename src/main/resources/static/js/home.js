import {fillLeftBar} from "./fillers/fillLeftBar.js";
import {getMyUsername} from "./handlers/getMyUsername.js";
import {fillRightBar} from "./fillers/fillRightBar.js";
import {postPublication} from "./publicationTools/postPublication.js";
import {getUserProfile} from "./handlers/getProfile.js";
import {renderPost} from "./publicationTools/renderPost.js";
import {addPostButtonListener} from "./publicationTools/postButtonListener.js";


document.addEventListener('DOMContentLoaded', async function() {
    const username = await getMyUsername();
    const userProfile = await getUserProfile(username);
    await fillLeftBar(username);
    await fillRightBar(username);


    await addPostButtonListener(userProfile);
});

