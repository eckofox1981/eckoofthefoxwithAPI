import { Opinion } from "../objects/opinionMaker.js";
import { showToast } from "./main.js";
import { Comment } from "../objects/commentMaker.js";
import { listOfObjectOpinions } from "../objects/listOfOpinions.js";
import { listOfObjectComments } from "../objects/listOfComments.js";
let listOfOpinions = listOfObjectOpinions();
publishSingleOpinion();

function publishSingleOpinion() {
    const mainElement = document.getElementById("main-element");
    let numberParam = window.location.search
    numberParam = new URLSearchParams(numberParam).get("opinion_number");
    
    const opinionNumber = parseInt(numberParam);
    let singleOpinion;

    for (let i = 0; i < listOfOpinions.length; i++) {
        if (listOfOpinions[i].opinionNumber === opinionNumber) {
            singleOpinion = new Opinion (listOfOpinions[i].opinionNumber, listOfOpinions[i].publicationDate, listOfOpinions[i].title, listOfOpinions[i].text, listOfOpinions[i].author, listOfOpinions[i].likes, listOfOpinions[i].dislikes)
            mainElement.append(singleOpinion.publishOpinion());
            continue;
        }
    }

    let commentList = listOfObjectComments(opinionNumber);
    const commentContainers = document.getElementById("commentSection");
    
    
    if (commentList === null || commentList === undefined || commentList.length < 1) {
        commentContainers.textContent = "Nobody has commented this yet (be the first!)";
        commentContainers.style = "text-align: center; font-style: italic"
        mainElement.appendChild(commentContainers);
        return;
    }

    for (let i = 0; i < commentList.length; i++) {
        const comment = new Comment(commentList[i].commentNumber, commentList[i].publicationDate, commentList[i].opinionNumber, commentList[i].commentText, commentList[i].author, commentList[i].likes, commentList[i].dislikes).publish();
        
        commentContainers.append(comment);
    }
    mainElement.appendChild(commentContainers);
}

