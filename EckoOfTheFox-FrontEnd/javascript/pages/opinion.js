import {Opinion} from "../objects/opinionMaker.js";
import { User, isLoggedin, showToast } from "../pages/main.js";

const mainElement = document.getElementById("opinion-main");
const pageTitle = document.getElementById("opinionPageTitle");
const opinionsOnPage = document.getElementsByClassName("opinion");
const postOpinionBtn = document.getElementById("postOpinionBtn");
export let listOfOpinions = JSON.parse(localStorage.getItem("opinions"));
postOpinionBtn.addEventListener('click', showOpinionForm);

gatherOpinions();

function gatherOpinions() {
        let message;
        console.log(listOfOpinions);
        
        
        if (listOfOpinions === undefined || listOfOpinions === null) {
                listOfOpinions = [];
                const messageH2 = document.createElement("h2");
                messageH2.textContent = "No one has posted any opinions yet, be the first!"
                mainElement.appendChild(messageH2);
                return;
        } else {
                listOfOpinions = JSON.parse(localStorage.getItem("opinions"));
                
                for (let opinion of listOfOpinions) {
                        
                        
                        const toPublish = new Opinion(opinion.opinionNumber, opinion.publicationDate, opinion.title, opinion.text, opinion.author, opinion.likes, opinion.dislikes);
                        mainElement.appendChild(toPublish.publishOpinion());
                        
                }
        }     
}


function showOpinionForm() {
        if (isLoggedin()) {
                const user = JSON.parse(localStorage.getItem("activeUser"));
                makeOpinionFields(user.username);
        } else {
                showToast("You need to login so people know who you are! Stand for your opinions!")
        }
        
}

function makeOpinionFields(username) {

        const opinionSection = document.createElement("section");
        const descriptionText = document.createElement("p");
        const authorName = document.createElement("h1");
        const titleLabel = document.createElement("h2");
        const titleInput = document.createElement("input");
        const textLabel = document.createElement("h3");
        const textInput = document.createElement("textarea");
        const submitBtn = document.createElement("button");
        const messageP = document.createElement("p");
        
        postOpinionBtn.remove();

        if (opinionsOnPage.length !== 0) {
                console.log("opinions on page: " + opinionsOnPage.length);
                
                for (let i = opinionsOnPage.length-1; i >= 0 ; i--) { //looped backward otherwise the array size shrinks wth iterations
                        opinionsOnPage[i].remove();
                }
        }

        pageTitle.textContent = "Opinions - Post your own!";
        descriptionText.textContent = "Say your piece here! Fill in the fields and press the post button. And remember: be nice!";
        titleLabel.textContent = "Title:";
        textLabel.textContent = "Text:"
        submitBtn.textContent = "Submit";

        authorName.textContent = "by " + username;
        
        //TODO REMOVE:
        messageP.textContent = "Something is wrong message"

        titleInput.setAttribute("placeholder", "Enter title here");
        textInput.setAttribute("placeholder", "Enter text here");
        messageP.setAttribute("id", "message");
        
        //styling and classes
        opinionSection.style = "display: flex; flex-direction: column; align-content: center; gap: 0.5rem; max-width: 1300px;"
        titleLabel.style = "text-align: center;"
        textLabel.style = "text-align: center;"
        textInput.style = "height: 30rem; padding: 0.25rem; white-space: pre-wrap; overflow-wrap: break-word; border: 1px solid rgb(62, 62, 62); border-radius: 0.5rem;";
        authorName.style = "align-self: end;"
        messageP.style = "color: white; background-color: rgb(255, 104, 00); border-radius: 0.5rem; padding: 0.5rem; align-self: end;"

        submitBtn.addEventListener("click", () => {postOpinion(titleInput.value, textInput.value, username)});

        mainElement.append(opinionSection);
        opinionSection.append(descriptionText, titleLabel, titleInput, textLabel, textInput, authorName, submitBtn, messageP);
}

 function postOpinion(title, text, username) {
        //keep going here, make opinoin, don't forget user part constructor (opinionNumber, publicationDate, title, text, author){
        let opinionNumber;
        if (listOfOpinions === undefined || listOfOpinions === null || listOfOpinions.length === 0) {
                opinionNumber = 1;
        } else {
                opinionNumber = listOfOpinions[listOfOpinions.length-1].opinionNumber +  1; //to allow same order when deleting (also avoids conflicts in IDs)
        }
        const opinion = new Opinion(opinionNumber, new Date().toDateString(), title, text, username, 0, 0 );
        opinion.save();
        opinion.publishOpinion();

        window.location.href = "opinions.html";

        showToast(`Opinion "${opinion.title}" has been published.`);
}






