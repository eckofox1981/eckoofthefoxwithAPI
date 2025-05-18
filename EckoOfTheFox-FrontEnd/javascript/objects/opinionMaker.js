import { showCommentConsole } from "../pages/comment.js";
import { showToast, isLoggedin } from "../pages/main.js";
import { listOfObjectOpinions, saveObjectListOfOpinions } from "./listOfOpinions.js";
import { deleteListOfObjectComments } from "./listOfComments.js";


export class Opinion  {

        constructor (opinionNumber, publicationDate, title, text, author, likes, dislikes){
                this.opinionNumber = opinionNumber;
                this.publicationDate = publicationDate;
                this.title = title;
                this.text = text;
                this.author = author;
                this.likes = likes;
                this.dislikes = dislikes;
        }

        save() {
                let listOfOpinions = listOfObjectOpinions();
                for (let i = 0; i < listOfOpinions.length; i++) { //tried with enhanced for loop but the update didn't happen as array reference was neeeded (TODO: learn why)
                        if (listOfOpinions[i].opinionNumber === this.opinionNumber) {
                                listOfOpinions[i] = this;
                                saveObjectListOfOpinions(listOfOpinions);
                                
                                console.log(listOfObjectOpinions());
                                
                                showToast(`Opinion "${this.title}" was updated!`);
                                return;
                        }
                }
                listOfOpinions.push(this);
                saveObjectListOfOpinions(listOfOpinions);
                showToast(`Opinion "${this.title}" was saved!`);
                return;
        
        }

        publishOpinion () {
                const opinionContainer = document.createElement("section");
                const opinionDateAndTitle = document.createElement("div");
                const opinionDate = document.createElement("div");
        
                const opinionTitle = document.createElement("h2");
                const opinionParagraph = document.createElement("p");
                const opinionAuthor = document.createElement("i");

                const likeAndCommentDiv = document.createElement("div");
                const likeBar = document.createElement("div");
                const commentBtn = document.createElement("button");
                let likeSymbol = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                let dislikeSymbol = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                const likeCounter = document.createElement("p");
                const dislikeCounter = document.createElement("p");
                const commentDiv = document.createElement("div");
                
                //classname and styling
                opinionContainer.className = "opinion";
                opinionDateAndTitle.className = "date-and-title";
                opinionAuthor.className = "author";

                //id
                commentDiv.id = `commentForOpionionID-${this.opinionNumber}`;
                opinionParagraph.id = this.opinionNumber;
                //styling
                likeBar.style = "display: flex; gap: 0.25rem; align-items: center; justify-content: flex-end; margin-right: 1rem";
                commentBtn.style = "margin-right: 1rem; line-height: 1rem"
                likeSymbol.setAttribute('fill', 'rgb(62, 62, 62)');
                likeSymbol.setAttribute('height', '1rem');
                likeSymbol.setAttribute('width', '1rem');
                likeSymbol.setAttribute('version', '1.1');
                likeSymbol.setAttribute('viewBox', '0 0 486.926 486.926');
                dislikeSymbol.setAttribute('xml:space', 'preserve');
                dislikeSymbol.setAttribute('fill', 'rgb(62, 62, 62)');
                dislikeSymbol.setAttribute('height', '1rem');
                dislikeSymbol.setAttribute('width', '1rem');
                dislikeSymbol.setAttribute('version', '1.1');
                dislikeSymbol.setAttribute('viewBox', '0 0 486.926 486.926');
                dislikeSymbol.setAttribute('xml:space', 'preserve');
        
                //content
                opinionDate.textContent = this.publicationDate;
                opinionTitle.textContent = this.title;
                opinionParagraph.textContent = this.text;
                opinionAuthor.textContent = this.author;
                likeCounter.textContent = this.likes;
                dislikeCounter.textContent = this.dislikes;
                commentBtn.textContent = "Go to article to comment";
                likeSymbol.innerHTML = `
                                        <g>
                                        <path d="M462.8,181.564c-12.3-10.5-27.7-16.2-43.3-16.2h-15.8h-56.9h-32.4v-75.9c0-31.9-9.3-54.9-27.7-68.4
                                        c-29.1-21.4-69.2-9.2-70.9-8.6c-5,1.6-8.4,6.2-8.4,11.4v84.9c0,27.7-13.2,51.2-39.3,69.9c-19.5,14-39.4,20.1-41.5,20.8l-2.9,0.7
                                        c-4.3-7.3-12.2-12.2-21.3-12.2H24.7c-13.6,0-24.7,11.1-24.7,24.7v228.4c0,13.6,11.1,24.7,24.7,24.7h77.9c7.6,0,14.5-3.5,19-8.9
                                        c12.5,13.3,30.2,21.6,49.4,21.6h65.9h6.8h135.1c45.9,0,75.2-24,80.4-66l26.9-166.9C489.8,221.564,480.9,196.964,462.8,181.564z
                                        M103.2,441.064c0,0.4-0.3,0.7-0.7,0.7H24.7c-0.4,0-0.7-0.3-0.7-0.7v-228.4c0-0.4,0.3-0.7,0.7-0.7h77.9c0.4,0,0.7,0.3,0.7,0.7
                                        v228.4H103.2z M462.2,241.764l-26.8,167.2c0,0.1,0,0.3-0.1,0.5c-3.7,29.9-22.7,45.1-56.6,45.1H243.6h-6.8h-65.9
                                        c-21.3,0-39.8-15.9-43.1-36.9c-0.1-0.7-0.3-1.4-0.5-2.1v-191.6l5.2-1.2c0.2,0,0.3-0.1,0.5-0.1c1-0.3,24.7-7,48.6-24
                                        c32.7-23.2,49.9-54.3,49.9-89.9v-75.3c10.4-1.7,28.2-2.6,41.1,7c11.8,8.7,17.8,25.2,17.8,49v87.8c0,6.6,5.4,12,12,12h44.4h56.9
                                        h15.8c9.9,0,19.8,3.7,27.7,10.5C459,209.864,464.8,225.964,462.2,241.764z" />
                                        </g>
                                        `;
                                        dislikeSymbol.innerHTML = ` 
                                        <g transform="translate(0,480) scale(1,-1)">
                                        <path d="M462.8,181.564c-12.3-10.5-27.7-16.2-43.3-16.2h-15.8h-56.9h-32.4v-75.9c0-31.9-9.3-54.9-27.7-68.4
                                        c-29.1-21.4-69.2-9.2-70.9-8.6c-5,1.6-8.4,6.2-8.4,11.4v84.9c0,27.7-13.2,51.2-39.3,69.9c-19.5,14-39.4,20.1-41.5,20.8l-2.9,0.7
                                        c-4.3-7.3-12.2-12.2-21.3-12.2H24.7c-13.6,0-24.7,11.1-24.7,24.7v228.4c0,13.6,11.1,24.7,24.7,24.7h77.9c7.6,0,14.5-3.5,19-8.9
                                        c12.5,13.3,30.2,21.6,49.4,21.6h65.9h6.8h135.1c45.9,0,75.2-24,80.4-66l26.9-166.9C489.8,221.564,480.9,196.964,462.8,181.564z
                                        M103.2,441.064c0,0.4-0.3,0.7-0.7,0.7H24.7c-0.4,0-0.7-0.3-0.7-0.7v-228.4c0-0.4,0.3-0.7,0.7-0.7h77.9c0.4,0,0.7,0.3,0.7,0.7
                                        v228.4H103.2z M462.2,241.764l-26.8,167.2c0,0.1,0,0.3-0.1,0.5c-3.7,29.9-22.7,45.1-56.6,45.1H243.6h-6.8h-65.9
                                        c-21.3,0-39.8-15.9-43.1-36.9c-0.1-0.7-0.3-1.4-0.5-2.1v-191.6l5.2-1.2c0.2,0,0.3-0.1,0.5-0.1c1-0.3,24.7-7,48.6-24
                                        c32.7-23.2,49.9-54.3,49.9-89.9v-75.3c10.4-1.7,28.2-2.6,41.1,7c11.8,8.7,17.8,25.2,17.8,49v87.8c0,6.6,5.4,12,12,12h44.4h56.9
                                        h15.8c9.9,0,19.8,3.7,27.7,10.5C459,209.864,464.8,225.964,462.2,241.764z" />
                                        </g>
                                        `;
                

                //add listener
                opinionTitle.addEventListener('click', () => {showSingleOpinion(this.opinionNumber)});
                commentBtn.addEventListener('click', () => {
                        
                        if (!isLoggedin()) {
                                showToast("You need to be loggedin to comment");
                        } else if (window.location.href.includes("opinions.html")) {
                                window.location.href = "./opinion.html?opinion_number=" + encodeURIComponent(this.opinionNumber);
                        } else {
                                showCommentConsole(commentDiv, commentBtn, this.opinionNumber)
                        }

                });
                likeSymbol.addEventListener("click", () => {
                        this.likeOpinion(likeCounter) }
                );
                likeSymbol.addEventListener('mouseenter', function() {
                        likeSymbol.setAttribute('fill', 'rgb(255, 104, 0)');
                        likeCounter.style = " color: rgb(255,104,0);";
                });
                likeSymbol.addEventListener('mouseleave', function() {
                        likeSymbol.setAttribute('fill', 'rgb(62, 62, 62)');
                        likeCounter.style = "color: rgb(62, 62, 62);";
                });

                dislikeSymbol.addEventListener("click", () => {
                        this.dislikeOpinion(dislikeCounter) }
                );
                dislikeSymbol.addEventListener('mouseenter', function() {
                        likeSymbol.setAttribute('fill', 'rgb(255, 104, 0)');
                        dislikeCounter.style = " color: rgb(255,104,0);";
                });
                dislikeSymbol.addEventListener('mouseleave', function() {
                        likeSymbol.setAttribute('fill', 'rgb(62, 62, 62)');
                        dislikeCounter.style = "color: rgb(62, 62, 62);";
                });

                //append!
                opinionContainer.append(opinionDateAndTitle, opinionParagraph, opinionAuthor, likeAndCommentDiv);
                opinionDateAndTitle.append(opinionDate, opinionTitle);
                likeAndCommentDiv.append(likeBar, commentDiv);
                likeBar.append(commentBtn, likeSymbol, likeCounter, dislikeSymbol, dislikeCounter);
                likeAndCommentDiv.append(commentDiv);

                if (window.location.href.includes("opinion.html")) {   
                        commentBtn.textContent = "Comment"                     
                        const commentContainers = document.createElement("section");
                        commentContainers.className = "comment-containers";
                        commentContainers.id = "commentSection";
                        opinionContainer.appendChild(commentContainers);
                }

                //TODO: lägga om användaren samma som author = delete knapp
                
                let activeUser = localStorage.getItem("activeUser");
                let username;
                if (activeUser !== null) {
                        activeUser = JSON.parse(activeUser);
                        username = activeUser.username;
                } else {
                        username = "@@===usXDSffzs½42312"; //random name to make sure no accidental activation of deleteOpinion
                }
                

                if (this.author === username || false) {
                        const deleteBtn = document.createElement("button");
                        deleteBtn.className = "dangerousBtn";
                        deleteBtn.style.height = "2rem";
                        deleteBtn.textContent = "Delete Opinion";
                        deleteBtn.addEventListener('click', this.delete.bind(this)); //if not bound (".bind") when I use the this-syntax in delete it will reference to the button not the opinion
                        likeBar.insertBefore(deleteBtn, commentBtn);
                }

                return opinionContainer;
        }

        likeOpinion(likeCounter) {
                this.likes = this.likes + 1;
                this.save(); 
                likeCounter.textContent = this.likes;
        }

        dislikeOpinion(dislikeCounter) {
                this.dislikes = this.dislikes +1;
                this.save();
                dislikeCounter.textContent = this.dislikes;
        }

        delete() {
                let listOfOpinions = listOfObjectOpinions();
                for (let i = 0; i < listOfOpinions.length; i++) {
                        if (listOfOpinions[i].opinionNumber === this.opinionNumber) {
                                deleteListOfObjectComments(this.opinionNumber);
                                listOfOpinions.splice(i, 1);
                                saveObjectListOfOpinions(listOfOpinions);
                                
                                showToast(`Opinion "${this.title}" was deleted.`);
                                //gives time for toast to show
                                setTimeout(() => { 
                                        window.location.href = "../opinions.html";
                                }, 3000);
                                return;
                        }
                }
        }
        
}

function showSingleOpinion(opinionNumber) {
        const url = "./opinion.html?opinion_number=" + encodeURIComponent(opinionNumber);
        console.log(url);
        
        window.location.href = url;
}
