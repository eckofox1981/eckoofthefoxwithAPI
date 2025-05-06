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

let user;

let registerDivIsVisible = false;

let passwordsAreCorrect = false;

checkLoginStatus();

async function checkLoginStatus() {
        let status = await isLoggedin();
        console.log(status);
        if (await status) {
                console.log("shows info");
                showAccount();
        } else {
          console.log("hmmm.... :/ try loging in(?)");
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

        let message = "";

        let userPasswordDTO = JSON.stringify ({
                username: username,
                password: password,
        });

        fetch("http://localhost:8080/user/login", {
                method: 'PUT',
                headers: { 'Content-type': 'application/json' },
                body: userPasswordDTO
        })
                .then(async response => {
                        if (!response.ok) {
                          message = "Error: " + response.status + " - " + await response.text();
                          throw new Error(message);
                        }
                        return response.text();
                      })
                      .then(data => {
                        console.log("running");
                        
                        localStorage.setItem("token", data);
                        localStorage.setItem("username", username);
                        console.log(localStorage.getItem("token") + " " + localStorage.getItem("username"));
                        isLoggedin();
                        showToast("You are logged in");
                        showAccount();
                      })
                      .catch(error => {
                        console.error('Error:', message);
                        messageP.textContent = message;
                        loginDiv.append(messageP);
                      });

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
                

                let userPasswordDTO = JSON.stringify ({
                        username: username,
                        email: email,
                        password: password,
                        passwordCheck: confirmation,
                });

                fetch("http://localhost:8080/user/register", {
                        method: 'POST',
                        headers: { 'Content-type': 'application/json' },
                        body: userPasswordDTO})
                        .then(async response => {
                                if (!response.ok) {
                                  message = "Error: " + response.status + " - " + await response.text();
                                  messageP.style.borderRadius = "0.5rem";
                                  console.log(message);
                                }
                                return response.json();
                              })
                              .then(data => {
                                message = "User: " + data.username + " created successfully. <br/>. You can now login.";
                              })
                              .catch(error => {
                                console.error('Error:', error);
                              })
                              .then(() => {
                                messageP.innerHTML = message;
                                registerDiv.append(messageP);
                              });
        }
        
}

function showRegister() {
        if (registerDivIsVisible) {
                registerDiv.style = "display: none;";
                registerDivIsVisible = false;
        } else {
                registerDiv.style = "display: flex;";
                registerDivIsVisible = true;
        }
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

async function showAccount() {
        loginDiv.remove();
        registerDiv.remove();

        let message;

        const accountDiv = document.createElement("div");
        const welcomeDiv = document.createElement("div");
        const welcomeH1 = document.createElement("h1");
        const usernameH2 = document.createElement("h2");
        const userIDi = document.createElement("i");
        const emailForm = document.createElement("form");
        const emailInput = document.createElement("input");
        const emailLabel = document.createElement("label");
        const updateBtn = document.createElement("button");
        const logoutBtn = document.createElement("button");
        const deleteBtn = document.createElement("button");

        updateBtn.textContent = "Update User Info";
        logoutBtn.textContent = "Logout"
        deleteBtn.textContent = "Delete Account";
        welcomeH1.textContent = "Welcome ";


        accountDiv.style = ("display: flex; flex-direction: column; gap: 1rem;");
        welcomeDiv.style = ("display: flex, flex-direction: row; gap: 0.5rem;");
        userIDi.style = ("margin-top: -1rem");
        
        logoutBtn.className = "secondaryButton";

        deleteBtn.className = "dangerousBtn";
        deleteBtn.style = ("margin-top: 5rem;");

        emailLabel.setAttribute("for", "userEmail");
        emailInput.setAttribute("id", "userEmail");

        updateBtn.addEventListener('click', () => {updateUser(emailInput)});
        logoutBtn.addEventListener('click', logout);
        updateBtn.addEventListener('click', () => {console.log("deleteBtn");
       })

        mainElement.appendChild(accountDiv);
        accountDiv.append(welcomeDiv, userIDi, emailForm, updateBtn, logoutBtn, deleteBtn);
        welcomeDiv.append(welcomeH1, usernameH2);
        emailForm.append(emailLabel, emailInput);

        fetch("http://localhost:8080/user/info", {
                method: 'GET',
                headers: { 
                        'Authorization': "Bearer " + localStorage.getItem("token")
                }
        })
                .then(async response => {
                        if (!response.ok) {
                                
                                message = "Error: " + response.status + " - " + await response.text();
                                throw new Error(message);
                          
                        }
                        return response.json();
                      })
                      .then(data => {
                                user = new User(data.userID, data.username, data.email, data.role, data.connection, data.opinionIDs, data.commentedOpinionsIds, data.likedOpinionsIDs);
                                console.log(user);
                                usernameH2.textContent = user.username;
                                userIDi.textContent = user.userID;
                                emailInput.value = user.email;
                        })
                      .catch(error => {
                                console.error("Fetch Error: ", error.message);
                                localStorage.removeItem("token");
                                localStorage.removeItem("username");
                      });

}

function logout() {
        localStorage.removeItem("token");
        localStorage.removeItem("username");
        window.location.href= "./login.html";
}

/*
TODO FIX
 */
function updateUser (emailInput) {
        const footers = document.getElementsByTagName("footer");
        let message;
        console.log(user);
        user.email = emailInput.value;
        console.log(user);
        fetch ('http:localhost:8080/user/update-info', {
                method: 'PUT',
                headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem("token"),
                        'Content-type': 'Application/json'
                },
                body: JSON.stringify({
                        "userID": user.userID,
                        "username": user.username,
                        "email": user.email,
                        "role": user.role,
                        "connection": user.connection,
                        "opinionIDs": user.opinionIDs,
                        "commentedOpinionsIds" : user.commentedOpinionsIds,
                        "likesMade": user.likesMade
                })
        })
                .then(async response => {
                        if (!response.ok) {
                                message = "Error:" + response.status + " - " + await response.text();
                                messageP.textContent = message;
                                messageP.style = "margin-bottom: 1rem; text-align: center; color: white; background-color: rgb(255, 104, 0); border-radius: 0.5rem; "
                                document.body.insertBefore(messageP, footers[0]);
                                throw new Error(message);
                        }
                        return response.json();
                })
                .then(data => {
                        user = new User (data.userID, data.username, data.email, data.connection);
                        console.log(user);
                        usernameH2.textContent = user.username;
                        userIDp.textContent = user.userID;
                        emailInput.value = user.email;
                })
              .catch(error => {
                        console.error("Fetch Error: ", error.message);
                        localStorage.removeItem("token");
              });

}