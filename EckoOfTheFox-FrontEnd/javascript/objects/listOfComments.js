//PLANEN:
/** ATT SKAPA EN LISTA FÖR VARJE OPINION, BEHÖVER DÄRFÖR NUMRET */
import { Comment } from "./commentMaker.js";

export function listOfObjectComments (opinionNumber) {
    let commentList = localStorage.getItem(`commentsFor${opinionNumber}`);
    //big failproof if-statements since programming is evil shit
    if (commentList === null || commentList === undefined) {
        localStorage.setItem(`commentsFor${opinionNumber}`, []);
        return [];
    } else if (commentList.length === 0) {
        return [];
    } else {
        commentList = JSON.parse(localStorage.getItem(`commentsFor${opinionNumber}`))?.map(c => new Comment(
            c.commentNumber,
            c.publicationDate,
            c.opinionNumber,
            c.commentText,
            c.author,
            c.likes,
            c.dislikes
        ));
        return commentList;
    }
}

export function saveObjectListOfComments (opinionNumber, commentList) {
    const stringified = JSON.stringify(commentList);
    localStorage.setItem(`commentsFor${opinionNumber}`, stringified);
}