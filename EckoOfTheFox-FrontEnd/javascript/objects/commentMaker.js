import { showToast } from "../pages/main.js";

export class Comment {
        constructor(publicationDate, opinionNumber, opinionTitle, commentText, author, likes, dislike) {
                this.publicationDate = publicationDate;
                this.opinionNumber = opinionNumber;
                this.opinionTitle = opinionTitle;
                this.commentText = commentText;
                this.author = author;
                this.likes = likes;
                this.dislike = dislike;
        }

        save() {
                
                
        }

        publish() {
                //make or get element
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
                return singleCommentContainer;
                //lägga om användaren samma som author = delete knapp
        }       
}