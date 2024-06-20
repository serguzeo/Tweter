import {fillLeftBar} from "./fillers/fillLeftBar.js";
import {getMyUsername} from "./handlers/getMyUsername.js";
import {fillRightBar} from "./fillers/fillRightBar.js";


document.addEventListener('DOMContentLoaded', async function() {
    const username = await getMyUsername();
    await fillLeftBar(username);
    await fillRightBar(username);
});
