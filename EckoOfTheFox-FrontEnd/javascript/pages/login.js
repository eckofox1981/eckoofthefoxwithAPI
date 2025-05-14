import { User, isLoggedin, showToast } from "./main.js";

const mainElement = document.getElementById("login-main");
const loginDiv = document.getElementById("loginDiv");
const registerDiv = document.getElementById("registerDiv");

const usernameInput = document.getElementById("usernameInput");
const passwordInput = document.getElementById("passwordInput");
const registerUsernameInput = document.getElementById("registerUsernameInput");
const registerEmailInput = document.getElementById("registerEmailInput");
const registerPasswordInput = document.getElementById("registerPasswordInput");
const registerPasswordConfirmInput = document.getElementById("registerPasswordConfirmInput");

const warningPassType = document.getElementById("warningPassType");
const warningPassMatch =document.getElementById("warningPassMatch");

const loginBtn = document.getElementById("loginButton");
const googleLoginBtn = document.getElementById("googleLoginButton");
const openRegisterBtn = document.getElementById("openRegisterButton");
const registerBtn = document.getElementById("registerButton");

let registerDivIsVisible = false;

let passwordsAreCorrect = false;

checkLoginStatus();

function checkLoginStatus() {
        let status = isLoggedin();
        if (status) {
                let user = localStorage.getItem("activeUser");
                user = JSON.parse(user);
                showAccount(user);
        } 
}



const messageP = document.createElement("p");
messageP.style = "color: white; background-color: rgb(255, 104, 0); border-radius: 0.5rem; padding: 0.5rem; text-align: right";
        

openRegisterBtn.addEventListener('click', showRegister);
loginBtn.addEventListener('click', () => {login(usernameInput.value, passwordInput.value)});
registerBtn.addEventListener('click',() => {register(registerUsernameInput.value, registerEmailInput.value, registerPasswordInput.value, registerPasswordConfirmInput.value)});

registerPasswordInput.addEventListener("input", () => {checkPasswords(registerPasswordInput.value, registerPasswordConfirmInput.value)});
registerPasswordConfirmInput.addEventListener("input", () => {checkPasswords(registerPasswordInput.value, registerPasswordConfirmInput.value)});



function login(username, password) {
        let localUser;
        let localUsers = localStorage.getItem("localUsers");
        
        localUsers = JSON.parse(localUsers);

        if (localUsers === undefined || localUsers === null) {
                showToast("You could not login. Check username and password.");
        } else {
                for (let user of localUsers) {
                                                
                        if (user.username === username && user.password === password) {
                                localUser = JSON.stringify(user);
                                localStorage.setItem("activeUser", localUser);
                                localUser = user;
                        }

                }
        }
        if (localStorage.getItem("activeUser") === null || localStorage.getItem("activeUser") === undefined) {
                showToast("You could not login. Check username and password.");
        } else {
                showAccount(localUser);
        }



}

function register(username, email, password, confirmation) {
        let message = "";
        let isRegistrable = true

        if (registerDiv.contains(messageP)) {
                messageP.remove();
        }

        if (username === "") {
                message = "Username field cannot be empty."
                isRegistrable = false;
        }

        if (!email.includes('.') && !email.includes('@')) {
                message += "<br/>Emails must include \'@\' and \'.\' (dot)."
                isRegistrable = false;
        }

        if (!passwordsAreCorrect) {
                message += "<br/>Your password is not eligible."
                isRegistrable = false
        }


        if (!isRegistrable) {
                messageP.innerHTML = message;
                
                registerDiv.append(messageP);
        } else {
                let newUser = new User (
                        username,
                        email,
                        password,
                );
                
                saveUserToLocaleStorage(newUser);
                showToast(`${newUser.username} - account has been save. You can now login.`);
        }
        
}

function showRegister() {
        registerDiv.classList.toggle("hidden");
}


function checkPasswords (password, confirmation) {
        let isCorrect = false;
        let isMatching = false;
        const warningStyleHidden = "display: none;";
        const warningStyleShown = "color: red; display: inline; background-color: rgb(255, 104, 0); color: white; border-radius: 0.5rem; padding: 0.5rem;";
        if (password === "") {
                warningPassType.style = "display: none;";
                isCorrect =false;
        } else if (password.length > 5 && /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z0-9]+$/.test(password)) {
                warningPassType.style = "display: none;";
                isCorrect = true;
        } else {
                warningPassType.style ="color: red; display: inline; background-color: rgb(255, 104, 0); color: white; border-radius: 0.5rem; padding: 0.5rem;";
                isCorrect = false;
        }

        if (password === "") {
                warningPassMatch.style = "display: none;";
                isMatching = false;
        } else if (password !== confirmation) {
                warningPassMatch.style = "color: red; display: inline; background-color: rgb(255, 104, 0); color: white; border-radius: 0.5rem; padding: 0.5rem;";
                isMatching = false;
        } else {
                warningPassMatch.style = "display: none;";
                isMatching = true;
        }

        if(isCorrect && isMatching) {
                passwordsAreCorrect = true;
        } else {
                passwordsAreCorrect = false;
        }
}

function showAccount(user) {
        loginDiv.remove();
        registerDiv.remove();

        let message;

        const accountDiv = document.createElement("div");
        const welcomeDiv = document.createElement("div");
        const welcomeH1 = document.createElement("h1");
        const usernameH2 = document.createElement("h2");
        const emailH3 = document.createElement("h3");
        const logoutBtn = document.createElement("button");
        const deleteBtn = document.createElement("button");

        logoutBtn.textContent = "Logout"
        deleteBtn.textContent = "Delete Account";
        welcomeH1.textContent = "Welcome ";

        accountDiv.style = ("display: flex; flex-direction: column; gap: 1rem;");
        welcomeDiv.style = ("display: flex, flex-direction: row; gap: 0.5rem;");
        
        logoutBtn.className = "secondaryButton";

        deleteBtn.className = "dangerousBtn";
        deleteBtn.style = ("margin-top: 5rem;");

        logoutBtn.addEventListener('click', logout);
        deleteBtn.addEventListener('click', () => deleteAccount(user));

        mainElement.appendChild(accountDiv);
        accountDiv.append(welcomeDiv, emailH3, logoutBtn, deleteBtn);
        welcomeDiv.append(welcomeH1, usernameH2);

        usernameH2.textContent = user.username;
        emailH3.textContent = user.email;

}

function logout() {
        localStorage.removeItem("activeUser");
        window.location.href= "./login.html";
}

function saveUserToLocaleStorage(user) {
        let userList = [];
        
        console.log();
        
        if (localStorage.getItem("localUsers") === null) {
                userList.push(user);
                const toSave = JSON.stringify(userList);
                localStorage.setItem("localUsers", toSave);
        } else {
                userList = JSON.parse(localStorage.getItem("localUsers"));
                userList.push(user);
                const toSave = JSON.stringify(userList);
                localStorage.setItem("localUsers", toSave);
        }
}

function deleteAccount(user) {
        console.log(user);
        
        if (confirm("Are you sure you want to delete your account?")) {
                let localUsers = localStorage.getItem("localUsers");
                console.log(localUsers); //list
                localUsers = JSON.parse(localUsers);
                for (let i = 0; i < localUsers.length; i++) {
                        if (localUsers[i].username === user.username) {
                                localUsers.splice(i, 1);
                                break
                        }
                }
                console.log(localUsers); //list
                localUsers = JSON.stringify(localUsers);
                localStorage.setItem("localUsers", localUsers);
                logout();                
        }
        
}