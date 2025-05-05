import { showToast } from "../pages/main.js";

export class Comment {
        constructor(commentID, publicationDate, opinionNumber, opinionTitle, commentText, author, likes) {
                this.commentID = commentID;
                this.publicationDate = publicationDate;
                this.opinionNumber = opinionNumber;
                this.opinionTitle = opinionTitle;
                this.commentText = commentText;
                this.author = author;
                this.likes = likes;
        }

        save() {
                let message;
                fetch(`http://localhost:8080/comment/post?opinionID=${this.opinionNumber}`, {
                        method: "POST",
                        headers: {
                                "Authorization": "Bearer " + localStorage.getItem("token"),
                                "Content-type": "application/json"
                        },
                        body: {
                               "commentText": this.commentText,
                        }
                })
                .then(response => {
                        if (!response.ok) {
                                message = "Error: " + response.status + " - " + response.text;
                                throw new Error(message);
                        }
                        return response.json; //REMEMBER TO RETURN!
                })
                .catch(error => {
                        showToast(error.message);
                })
                
        }

        publish(incomingCommentDiv) {
                //make or get element
                const singleCommentContainer = document.createElement("div");
                const publicationDate = document.createElement("i");
                const commentText = document.createElement("p");
                const commentAuthor = document.createElement("i");
                //classname
                singleCommentContainer.className = "comment-Container";
                commentAuthor.className = "author";
                //styling

                //content
                publicationDate.textContent = this.publicationDate;
                commentText.textContent = this.commentText;
                commentAuthor.textContent = this.author;
                //add listen

                //append!
                incomingCommentDiv.append(singleCommentContainer);
                singleCommentContainer.append(publicationDate, commentText, commentAuthor);

                //lägga om användaren samma som author = delete knapp
        }       
}