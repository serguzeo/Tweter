import {fillLeftBar} from "./fillers/fillLeftBar.js";
import {getMyUsername} from "./handlers/getMyUsername.js";


document.addEventListener('DOMContentLoaded', async function() {
    const username = await getMyUsername();
    await fillLeftBar(username);
});
