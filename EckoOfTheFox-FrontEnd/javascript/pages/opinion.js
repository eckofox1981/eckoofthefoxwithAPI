import { Opinion } from "../objects/opinionMaker.js";
import { listOfOpinions } from "./main.js";
import { Comment } from "../objects/commentMaker.js";

publishSingleOpinion();

function publishSingleOpinion() {
    const mainElement = document.getElementById("main-element");
    const numberParam = new URLSearchParams(window.location.search).get("opinion_number");
    const opinionNumber = parseInt(numberParam);
    let singleOpinion;

    for (let i = 0; i < listOfOpinions.length; i++) {
        if (listOfOpinions[i].opinionNumber === opinionNumber) {
            singleOpinion = new Opinion (listOfOpinions[i].opinionNumber, listOfOpinions[i].publicationDate, listOfOpinions[i].title, listOfOpinions[i].text, listOfOpinions[i].author, listOfOpinions[i].likes, listOfOpinions[i].dislikes)
            mainElement.append(singleOpinion.publishOpinion());
            continue;
        }
    }

    let commentList = localStorage.getItem(`commentsFor${singleOpinion.opinionNumber}`);
    const commentContainer = document.createElement("div");
    commentContainer.className = "comment-container";
    if (commentList === undefined || commentList === null || commentList.length < 1) {
        commentContainer.textContent = "Nobody has commend this yet (be the first!)";
        mainElement.appendChild(commentContainer);
        return
    }

    for (let i = 0; i < commentList.length; i++) {
        const comment = new Comment(new Date().toLocaleString, commentList[i].opinionNumber, commentList[i].opinionTitle, commentList[i].text, commentList[i].author, commentList[i].likes, commentList[i].dislikes);
        commentContainer.append(comment.publish());
    }
    mainElement.appendChild(commentContainer);
}

