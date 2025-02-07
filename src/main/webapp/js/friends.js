(async () => {
    'use strict'

    const createCardUserElement = (userId, name, username, path, isRequest = false) => {
        const BASE_IMAGE_URL = "http://10.242.194.182";
        let cardElement = document.createElement("div");
        let textElement = document.createElement("a");
        let nameElement = document.createElement("span");
        let usernameElement = document.createElement("span");
        let imageElement = document.createElement("img");
        let imageFallback = document.createElement("span");
        let acceptButton = document.createElement("button");
        let rejectButton = document.createElement("button");

        imageFallback.classList.add("d-flex", "justify-content-center", "align-items-center", "bg-body-secondary", "rounded-circle");
        imageFallback.style.width = "48px";
        imageFallback.style.height = "48px";

        if (name) {
            imageFallback.innerText = name.charAt(0);
        } else if (username) {
            imageFallback.innerText = username.charAt(0);
        }

        cardElement.classList.add("card", "d-flex", "flex-row", "align-items-center", "gap-2", "p-2", "mb-2");

        textElement.append(nameElement, usernameElement);
        textElement.href = `ControllerServlet?action=viewPost&username=${username}`;

        nameElement.innerText = name ?? "";
        usernameElement.innerText = username ?? "";

        let avatar = imageFallback;

        if (path) {
            imageElement.src = `${BASE_IMAGE_URL}/${path}`;
            avatar = imageElement;
        }

       if(isRequest) {
           acceptButton.classList.add("btn", "btn-primary");
           acceptButton.innerText = "Aceitar";
           acceptButton.addEventListener("click", () => accept(userId));

           rejectButton.classList.add("btn", "btn-outline-danger");
           rejectButton.innerText = "Recusar";
           rejectButton.addEventListener("click", () => reject(userId));

           cardElement.append(avatar, textElement, acceptButton, rejectButton);
       } else {
           cardElement.append(avatar, textElement);

       }

        return cardElement;
    };

    const accept = async (userId) => {
            const url = "ControllerServlet?action=approveFriendRequest&userSubmittedId=" + userId;
            await fetch(url).then(res => res.json());
            window.location.reload();
    }

    const reject = async (userId) => {
        const url = "ControllerServlet?action=rejectFriendRequest&userSubmittedId=" + userId;
        await fetch(url).then(res => res.json());
        window.location.reload();
    }


    const friendRequestsElement = document.getElementById("friend-requests");
    const friendsElement = document.getElementById("friends");
    const buttonsSendRequests = document.getElementsByClassName("send-request");

    const fetchFriendRequests = async () => {
        const url = "ControllerServlet?action=friendRequests";
        return await fetch(url).then(res => res.json());
    }

    const fetchFriends = async () => {
        const url = "ControllerServlet?action=friends";
        return await fetch(url).then(res => res.json());
    }

    const sendFriendRequest = async (userReceivedId) => {
        const url = `ControllerServlet?action=sendFriendRequest&userReceivedId=${userReceivedId}`;
        return await fetch(url).then(res => res.json());
    }

    if(buttonsSendRequests.length > 0){
        Array.from(buttonsSendRequests).forEach( button => {
            button.addEventListener("click", async () => {
                const userReceivedId = button.getAttribute("data-user-received-id");
                const response = await sendFriendRequest(userReceivedId);

                if(response.ok){
                    const toastLiveExample = document.getElementById('sendedRequestToast')
                    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
                    toastBootstrap.show()
                }
            })
        } )
    }

    const friendRequestsResponse = await fetchFriendRequests();
    const friendsResponse = await fetchFriends();

    if(friendRequestsResponse.requests){
        const spinner = friendRequestsElement.getElementsByClassName("spinner-border");
        friendRequestsElement.removeChild(spinner[0]);

        const requests = JSON.parse(friendRequestsResponse.requests);
        if(Array.isArray(requests)){
            if(requests.length === 0){
                friendRequestsElement.innerText = "Nenhuma solicitação";
            }else{
                requests.map( request => friendRequestsElement.append(createCardUserElement(request.userSubmitted.id, request.userSubmitted.name, request.userSubmitted.username, request.userSubmitted.path, true)) )
            }
        }
    }

    if(friendsResponse.friends){
        const spinner = friendsElement.getElementsByClassName("spinner-border");
        friendsElement.removeChild(spinner[0]);

        const friends = JSON.parse(friendsResponse.friends);
        if(Array.isArray(friends)){
            if(friends.length === 0){
                friendsElement.innerText = "Adicione seus amigos agora mesmo!";
            }else{
                friends.map( friend => friendsElement.append(createCardUserElement(friend.id, friend.name, friend.username, friend.path)) )
            }
        }
    }

})()