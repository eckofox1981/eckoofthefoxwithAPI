import { listOfObjectOpinions } from "../objects/listOfOpinions.js";
import { Opinion } from "../objects/opinionMaker.js";

const listOfIndexOpinions = listOfObjectOpinions();


const placeHolderOpinions = [
    new Opinion (-1, "1939-04-29", "That Adolf guy is scary", "I am worried about that short man every bo talks about...", "Olga", 0, 0),
    new Opinion (-2, "2016-09-29", "Liberals should accept guns", "Imagine a bun wearing vegan betamale rampaging through a clan meeting with an AR15.", "Olga", 0, 0),
    new Opinion (-3, "2025-03-30", "Flying saucers?", "There's a weird light in the sky above the Eiffel Tower. Anybody knows what it is?", "Marie-Antoinette", 0, 0),
    new Opinion (-4, "1977-05-26", "Space what now?", "Saw this new movie 'Star Wars'. Feels like it’s gonna change things.", "Debbie", 0, 0),
    new Opinion (-5, "2025-01-12", "Too many apps", "My fridge just sent me a push notification asking for a firmware update. I'm scared.", "Leo", 0, 0),
    new Opinion (-6, "2025-02-09", "Virtual relationships", "Just got dumped by my AI girlfriend. She said I lacked emotional nuance.", "Jayden", 0, 0),
    new Opinion (-7, "2025-04-20", "Mars memes", "NASA's rover posted a selfie again. It's honestly more photogenic than me.", "Selma", 0, 0),
    new Opinion (-8, "2025-05-03", "Microdosing Mondays", "Boss says productivity is up ever since we replaced coffee with LSD. Tech startups are wild.", "Rei", 0, 0),
];

let newList = [];
if (listOfIndexOpinions === null || listOfIndexOpinions === undefined) {
    publishEightOpinions(placeHolderOpinions);

} else if (listOfIndexOpinions.length < 8) {
    
    const difference = (placeHolderOpinions.length - listOfIndexOpinions.length);
    
    for (let i = 0; i < listOfIndexOpinions.length; i++) {        
        newList.push(listOfIndexOpinions[i]);
    }
    
    for (let i = 0; i < (difference); i++) {
        console.log(placeHolderOpinions[i]);
        
        newList.push(placeHolderOpinions[i]);
    }

} else {
    for (let i = listOfIndexOpinions.length - 1; i >= listOfIndexOpinions.length - 9; i--) {
        newList.push(listOfIndexOpinions[i]);
    }
}

publishEightOpinions(newList);


function publishEightOpinions(opinionList) {
    console.log(opinionList);
    const indexOpinions = document.getElementById("index-opinions");
    
    for (let i = 0; i < opinionList.length; i++) {
        console.log(i);
        
        const opinionContainer = document.createElement("div");
        const linkToOpinion = document.createElement("a");
        const number = document.createElement("h3");
        const title = document.createElement("h4");
        const text = document.createElement("p");
        const author = document.createElement("i");

        opinionContainer.className = "index-opinion";
        author.className = "author";

        if (opinionList[i].opinionNumber > 0) {
            linkToOpinion.href = "./opinion.html?opinion_number=" + encodeURIComponent(opinionList[i].opinionNumber);
        } else {
            linkToOpinion.href = "./opinion-was-a-placeholder.html";
        }

        number.textContent = i+1;
        title.textContent = opinionList[i].title;
        text.textContent = opinionList[i].text;
        author.textContent = opinionList[i].author;

        linkToOpinion.append(number, title);
        opinionContainer.append(linkToOpinion, text, author);
        indexOpinions.appendChild(opinionContainer);
    }
    
}