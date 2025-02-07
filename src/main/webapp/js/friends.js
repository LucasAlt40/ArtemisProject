(async () => {
    'use strict'

    const BASE_IMAGE_URL = "http://10.242.194.182/"

    const createCardUserElement = (userId, name, username, path) => {
        let cardElement = document.createElement("div");
        let textElement = document.createElement("div");
        let nameElement = document.createElement("span");
        let usernameElement = document.createElement("span");
        let imageElement = document.createElement("img")

        cardElement.classList.add("card d-flex gap-2 align-items-center");

        textElement.append(nameElement, usernameElement)

        nameElement.innerText = name;
        usernameElement.innerText = username;
        imageElement.src = `${BASE_IMAGE_URL}/${path}`;

        cardElement.append(imageElement, textElement);

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

    const [friendRequestsResponse, friendsResponse, ] = await Promise.all([fetchFriendRequests, fetchFriends]);

    if(friendRequestsResponse.requests){
        const requests = friendRequestsResponse.requests;
        let cards = requests.map( request => createCardUserElement(request.id, request.name, request.username, request.path) )
        friendRequestsElement.append(cards);
    }

    if(friendsResponse.friends){
        const friends = friendsResponse.friends;
        let cards = friends.map( request => createCardUserElement(request.id, request.name, request.username, request.path) )
        friendsElement.append(cards);
    }

})()