import {Opinion} from "../objects/opinionMaker.js";
import { User, showToast } from "../pages/main.js";

const mainElement = document.getElementById("opinion-main");
const pageTitle = document.getElementById("opinionPageTitle");
const opinionsOnPage = document.getElementsByClassName("opinion");
const postOpinionBtn = document.getElementById("postOpinionBtn");

postOpinionBtn.addEventListener('click', showOpinionForm);

gatherOpinions();

async function gatherOpinions() {
        //try/catch to ensure return?
        let message;
        let listOfOpinions = [];
        try {
                const response = await  fetch("http://localhost:8080/opinion", {
                        method: "GET",
                });
                if (!response.ok) {
                        message = "Error: " + response.status + " - " + response.text;
                        showToast(message);
                        throw new Error(message);
                }
                return response.json()
                .then(response => {
                const listOfOpinions = response.map(opinion => new Opinion(opinion.opinionID, opinion.publicationDate, opinion.title, opinion.opinionText, opinion.author));
                        
                        for (let i = listOfOpinions.length-1; i >= 0; i--) {
                               mainElement.append(listOfOpinions[i].publishOpinion());
                        }
                })
        } catch(error) {
                showToast(error.message);
        }
        
}


function showOpinionForm() {
        //TODO uncomment
        /* if (!authorizationToken) {
                window.location.href = "./login.html";
        } */

                makeOpinionFields(localStorage.getItem("username"));

                //todo remeber to add user to authorName element
        
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

        submitBtn.addEventListener("click", () => {postOpinion(titleInput.value, textInput.value)});

        mainElement.append(opinionSection);
        opinionSection.append(descriptionText, titleLabel, titleInput, textLabel, textInput, authorName, submitBtn, messageP);
}

 function postOpinion(title, text) {
        console.log("testing: postOpinion(title, text, picture)");

        let opinionDTO = JSON.stringify({
                "title": title,
                "opinionText": text,

        })
        console.log(opinionDTO);
        

        fetch("http://localhost:8080/opinion/post", {
                method: 'POST',
                headers: {
                        "Authorization" : "Bearer " + localStorage.getItem("token"),
                        "Content-type": "application/json"
                },
                body: opinionDTO
        })
                .then(async response => {
                        if (!response.ok) {
                                message = "Error: " + response.status + " - " + (await response.text());

                                throw  new Error(message);
                        }

                        return response.json();
                })
                .then(o => {
                        let newOpinion = new Opinion (o.opinionID, o.publicationDate, o.title, o.opinionText, o.imgName, o.imgContent, o.imgType, o.comments, o.likes);
                        showToast("Opinion " + newOpinion.opinionNumber + " saved and published. Your voice will be heard!")
                        newOpinion.publishOpinion();
                })
                .catch(error => {
                        console.error(message);
                        showToast(message);
                })
}






