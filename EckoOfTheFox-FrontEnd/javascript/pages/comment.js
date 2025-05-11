import { Comment } from "../objects/commentMaker.js";
import { showToast } from "./main.js";

export function showCommentConsole(incomingCommentDiv, commentBtn, opinionNumber) {
        if (window.location.href.includes("opinions.html")) {
                showToast("Comments are shown on the opinion's page, maybe you should see what other said first.");
        }
        //create console
        commentBtn.disabled = true;
        commentBtn.style = "color: rgb(62, 62, 62); background-color: rgba(62, 62, 62, 0); border: 1px solid rgb(62, 62, 62); max-height: fit-content; margin-right: 1rem;";
        const console = document.createElement("section");
        const textArea = document.createElement("textarea");
        const buttons = document.createElement("div")
        const submitBtn = document.createElement("button");
        const cancelBtn = document.createElement("button");

        //class
        console.className = "comment-console";
        cancelBtn.className = "dangerousBtn";

        //content
        submitBtn.textContent = "Submit";
        cancelBtn.textContent = "Cancel";

        //listeners
        submitBtn.addEventListener('click', () => { submitComment(textArea.value, incomingCommentDiv, opinionNumber)});
        cancelBtn.addEventListener('click', () => {
                commentBtn.style = "max-height: fit-content; margin-right: 1rem;";
                commentBtn.disabled = false;
                console.remove();
        })

        //append TODO insert before the comments (or after????)
        incomingCommentDiv.appendChild(console);
        console.append(textArea, buttons);
        buttons.append(cancelBtn, submitBtn);
}

function submitComment(text, incomingCommentDiv, opinionNumber) {
        console.log(localStorage.getItem(`commentFor${opinionNumber}`));
        
        let opinionCommentList = localStorage.getItem(`commentFor${opinionNumber}`);
        const user = JSON.parse(localStorage.getItem("activeUser"));
        let commentNumber;

        if (opinionCommentList === undefined || opinionCommentList === null || listOfOpinions.length === 0) {
                commentNumber = 1;
        } else {
                commentNumber = opinionCommentList[opinionCommentList.length-1].commentNumber +  1; //to allow same order when deleting (also avoids conflicts in IDs)
        }
        let comment = new Comment(commentNumber, new Date().toDateString(), opinionNumber, text, user.username, 0, 0);
        comment.save();
        incomingCommentDiv.append(comment.publish());
}