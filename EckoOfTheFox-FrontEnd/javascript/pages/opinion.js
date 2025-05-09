import { Opinion } from "../objects/opinionMaker.js";
import { listOfOpinions } from "./main.js";

publishSingleOpinion();

function publishSingleOpinion() {
    const mainElement = document.getElementById("main-element");
    const numberParam = new URLSearchParams(window.location.search).get("opinion_number");
    const opinionNumber = parseInt(numberParam);
    console.log(opinionNumber);

    for (let i = 0; i < listOfOpinions.length; i++) {
        if (listOfOpinions[i].opinionNumber === opinionNumber) {
            console.log(listOfOpinions[i]);
            const singleOpinion = new Opinion (listOfOpinions[i].opinionNumber, listOfOpinions[i].publicationDate, listOfOpinions[i].title, listOfOpinions[i].text, listOfOpinions[i].author, listOfOpinions[i].likes, listOfOpinions[i].dislikes)
            mainElement.append(singleOpinion.publishOpinion());
            
            return;
        }
    }
}