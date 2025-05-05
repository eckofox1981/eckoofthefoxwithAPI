import { showToast } from "../pages/main.js";

export function likeOpinion(opinionID) {
        let message;
        
        fetch(`http://localhost:8080/like/opinion?opinionID=${opinionID}`, {
                method: "POST",
                headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                },
        })
        .then(response => {
                if (!response.ok) {
                        message = "Error:" + response.status + " - " + response.text();
                        throw new Error(message)
                }
                return response.json;
        })
        .then(data => {
                message = "You've liked that opinion.";
                showToast(message);
        })
        .catch(error => {
                showToast(message);
        })
}

export function dislikeOpinion(opinionID) {
        let message;
        
        fetch(`http://localhost:8080/dislike/opinion?opinionID=${opinionID}`, {
                method: "POST",
                headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                },
        })
        .then(response => {
                if (!response.ok) {
                        message = "Error:" + response.status + " - " + response.text();
                        throw new Error(message)
                }
                return response.json;
        })
        .then(data => {
                message = "You've liked that opinion.";
                showToast(message);
        })
        .catch(error => {
                showToast(message);
        })
}