export class User {
        constructor(userID, username, email, role, connection, opinionIDs, commentedopinionIDs, likesMade) {
                this.userID = userID;
                this.username = username;
                this.email = email;
                this.role = role;
                this.connection = connection;
                this.opinionIDs = opinionIDs;
                this.commentedopinionIDs = commentedopinionIDs;
                this.likesMade = likesMade;
        }
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

export async function isLoggedin () { 
        /* different fetch as I need the return to layout the login/userinfo page 
        the whole fetch is awaited and the try/catch ensures a proper boolean-return*/
        try {                
        const response = await fetch("http://localhost:8080/user/info", {
                method: 'GET',
                headers: { 
                        'Authorization': "Bearer " + localStorage.getItem("token")
                        }
                });

                if (!response.ok) {
                        console.log("islogged false");
                        loginBtn[0].textContent = "Login";
                        return false;
                }

                console.log("islogged true");
                loginBtn[0].textContent = "Profile";
                return true;
        

        } catch(error) {
                console.error("Fetch Error: ", error);
                console.log(error.message);
                localStorage.removeItem("token");
                localStorage.removeItem("username");
        }
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