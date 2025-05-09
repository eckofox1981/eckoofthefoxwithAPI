import { Comment } from "../objects/commentMaker.js";
import { showToast } from "./main.js";

export function showCommentConsole(incomingCommentDiv, commentBtn, opinionNumber) {
        showToast("Comments are shown on the opinion's page, maybe you should see what other said first.")
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
        let comment = new Comment(null, null, opinionNumber, "Digital Privacy", text, null, null);
        comment.save();
        comment.publish(incomingCommentDiv);
}