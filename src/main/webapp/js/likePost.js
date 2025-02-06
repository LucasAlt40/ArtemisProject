(()=> {
    'use strict'

    const ACTIONS = {
        DISLIKE: 'dislikePost',
        LIKE: 'likePost'
    };

    const onAction = async (action, postId) => {
        const url = `ControllerServlet?action=${action}&idPost=${postId}`;
        return await fetch(url).then(res => res.json())
    }

    const getNode = (node, attributes) => {
        node = document.createElementNS("http://www.w3.org/2000/svg", node);
        for (const key in attributes)
            node.setAttributeNS(null, key, attributes[key]);
        return node
    }

    const likedIcon = () => {
        const svg = getNode("svg", { width: 16, height: 16, fill: "currentColor", class: "bi bi-heart-fill", viewBox: "0 0 16 16" } );
        const path = getNode("path", { "fill-rule": "evenodd", d: "M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314" })
        svg.appendChild(path)
        return svg;
    }

    const notLikedIcon = () => {
        const svg = getNode("svg", { width: 16, height: 16, fill: "currentColor", class: "bi bi-heart", viewBox: "0 0 16 16" } );
        const path = getNode("path", { d: "m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143q.09.083.176.171a3 3 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15" })
        svg.appendChild(path)
        return svg;
    }

    const buttons = document.getElementsByClassName("btn-like-dislike-post");

    Array.from(buttons).forEach(button => {
        button.addEventListener("click", async () => {
            const postId = button.getAttribute("data-post-id");
            const action = button.getAttribute("data-action");
            const numLikes = button.getElementsByTagName("span")[0];
            const likesQuantity = Number(button.getAttribute("data-like-quantity"));
            const response = await onAction(action, postId);

            if(response.ok){
                let newLikesQuantity;
                const svg = button.getElementsByTagName("svg")[0];
                button.removeChild(svg)

                if(action === ACTIONS.LIKE){
                    newLikesQuantity = likesQuantity + 1;
                    numLikes.innerText = newLikesQuantity.toString();
                    button.prepend(likedIcon(), button.firstChild);
                    button.setAttribute("data-action", ACTIONS.DISLIKE);
                    button.setAttribute("data-like-quantity", newLikesQuantity.toString())
                }else{
                    if(action === ACTIONS.DISLIKE){
                        newLikesQuantity = likesQuantity - 1;
                        numLikes.innerText = newLikesQuantity.toString();
                        button.prepend(notLikedIcon(), button.firstChild);
                        button.setAttribute("data-action", ACTIONS.LIKE);
                        button.setAttribute("data-like-quantity", newLikesQuantity.toString())
                    }
                }
            }
        })
    })
})()