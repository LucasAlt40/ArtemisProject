(async () => {
    'use strict'

    const createCardUserElement = (userId, name, username, path) => {
        const BASE_IMAGE_URL = "http://10.242.194.182"
        let cardElement = document.createElement("div");
        let textElement = document.createElement("a");
        let nameElement = document.createElement("span");
        let usernameElement = document.createElement("span");
        let imageElement = document.createElement("img")
        let imageFallback = document.createElement("span");

        imageFallback.classList.add("d-flex");
        imageFallback.classList.add("justify-content-center");
        imageFallback.classList.add("align-items-center");
        imageFallback.classList.add("bg-body-secondary");
        imageFallback.classList.add("rounded-circle");
        imageFallback.style.width = "48px";
        imageFallback.style.height = "48px";

        if(name){
            imageFallback.innerText = name.charAt(0);
        }else{
            if(username){
                imageFallback.innerText = username.charAt(0);
            }
        }

        cardElement.classList.add("card");
        cardElement.classList.add("d-flex");
        cardElement.classList.add("flex-row");
        cardElement.classList.add("align-items-center");
        cardElement.classList.add("gap-2");
        cardElement.classList.add("p-2");
        cardElement.classList.add("mb-2");

        textElement.append(nameElement, usernameElement)
        textElement.href = `ControllerServlet?action=viewPost&username=${username}`;

        nameElement.innerText = name ?? "";
        usernameElement.innerText = username ?? "";

        let avatar = imageFallback;

        if(path){
            imageElement.src = `${BASE_IMAGE_URL}/${path}`;
            avatar = imageElement;
        }

        cardElement.append(avatar, textElement);

        return cardElement;
    }

    const friendRequestsElement = document.getElementById("friend-requests");
    const friendsElement = document.getElementById("friends");

    const fetchFriendRequests = async () => {
        const url = "ControllerServlet?action=friendRequests";
        return await fetch(url).then(res => res.json());
    }

    const fetchFriends = async () => {
        const url = "ControllerServlet?action=friends";
        return await fetch(url).then(res => res.json());
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
                let cards = requests.map( request => createCardUserElement(request.id, request.name, request.username, request.path) )
                friendRequestsElement.append(cards);
            }
        }
    }

    if(friendsResponse.friends){
        const spinner = friendsElement.getElementsByClassName("spinner-border");
        friendsElement.removeChild(spinner[0]);

        const friends = JSON.parse(friendsResponse.friends);
        if(Array.isArray(friends)){
            console.log(friends)
           friends.map( friend => friendsElement.append(createCardUserElement(friend.id, friend.name, friend.username, friend.path)) )
        }
    }

})()