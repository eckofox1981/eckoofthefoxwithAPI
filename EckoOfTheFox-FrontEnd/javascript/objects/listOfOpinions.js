import { Opinion } from "./opinionMaker.js";

export function listOfObjectOpinions () {
    let opinionList = localStorage.getItem("opinions");
    //big failproof if-statements since programming is evil shit
    if (opinionList === null || opinionList === undefined) {
        localStorage.setItem("opinions", []);
        return [];
    } else if (opinionList.length === 0) {
        return [];
    } else {
        opinionList = JSON.parse(localStorage.getItem("opinions"))?.map(o => new Opinion(
            o.opinionNumber,
            o.publicationDate,
            o.title,
            o.text,
            o.author,
            o.likes,
            o.dislikes
        ));
        return opinionList;
    }
}

export function saveObjectListOfOpinions (opinionList) {
    const stringified = JSON.stringify(opinionList);
    localStorage.setItem("opinions", stringified);
}