export class User {
        constructor(username, email, password, opinionIDs, commentedopinionIDs, likesMade) {
                this.username = username;
                this.email = email;
                this.password = password;
                this.commentedopinionIDs = commentedopinionIDs;
                this.likesMade = likesMade;
        }
}
export let listOfOpinions = JSON.parse(localStorage.getItem("opinions"));
if (listOfOpinions === undefined || listOfOpinions === null || listOfOpinions.length === 0) {
        listOfOpinions = [];
        localStorage.setItem('opinions', listOfOpinions);
}

/*Activates click function on hamburger menu for smaller screens (<1024px)
other hover effect still in action, see style.css ln 76 - 130*/
const hamburgerMenu = document.getElementById("hamburger-menu");
const navBar = document.getElementById("navBar");
hamburgerMenu.addEventListener('click', () => {
      navBar.classList.toggle("nav-bar");
})

/*Code for changing header button text LOGIN/PROFILE*/ 
const loginBtn = document.getElementsByClassName("loginBtnHeader");

isLoggedin();

export function isLoggedin () { 
        if (localStorage.getItem("activeUser") === null || localStorage.getItem("activeUser") === undefined) {  
                loginBtn[0].textContent = "Login";
                return false;
        }
        loginBtn[0].textContent = "Profile";
        return true;
}



/**
 * makes the "toastdiv" glide in from the top(making sure the users can't miss it, in theory at least)
 * @param {*} message the message to be displayed in the toast
 */
export function showToast(message) {
        const toastiv = document.getElementById("toast");
        toastiv.textContent = message;
        toastiv.className = "show";
        setTimeout(() => 
                {toastiv.className = toastiv.className.replace("show", "");
                }, 3000);
}