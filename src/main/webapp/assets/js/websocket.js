websocket = new WebSocket(`ws://${domain}/websocket`)

websocket.onopen = function (event) {
    console.log('WebSocket connection established')
    websocket.send(`$id:${sessionUser.id}`)
};

websocket.onmessage = function (event) {
    const data = event.data.toString()
    console.log("DATA", data)
    if (data.includes("RecipientNullException")) {
        console.log("Error: " + data)
        return;
    }

    if (data.includes("$online:")) {
        const id = data.slice(8)
        const friend = friends.find(friend => friend.id === id)
        friend.ref.userStatus.className = "status online"
        friend.status = "online"
        if (receiver && receiver.id === friend.id) {
            receiver.status = "online"
            receiver.ref.chatInfo.innerHTML = "online"
        }
        console.log("$online:", id);
        return;
    } else if (data.includes("$offline:")) {
        const id = data.slice(9)
        const friend = friends.find(friend => friend.id === id)
        friend.ref.userStatus.className = "status offline"
        friend.status = "offline"
        friend.canRead = false

        if (receiver && receiver.id === friend.id) {
            receiver.status = "offline"
            receiver.ref.chatInfo.innerHTML = "offline"
            receiver.canRead = false
        }
        console.log("$offline:", id);
        return;
    }

    if (data.includes("$chat-active:")) {
        const ID = data.slice(13).split("&")
        console.log(data, ID)
        const friend = friends.find(f => f.id === ID[1])
        friend.canRead = true;

        receiver && receiver.unreadMessages.forEach(msg => msg.className = "read-receipt read")
        return;
    }

    if (data.includes("$chat-inactive:")) {
        const ID = data.slice(15).split("&")
        const friend = friends.find(friend => friend.id === ID[1])
        console.log(data, ID)
        friend.canRead = false;
        if (receiver && receiver.id === friend.id) {
            receiver.canRead = false;
        }
        return;
    }

    try {
        const msg = JSON.parse(data)
        if (receiver && receiver.id === msg.senderID) {
            receiver.ref.userLastMsg.title = msg.message
            receiver.ref.userLastMsg.textContent = msg.message
            putReceiverMsg(msg.message, msg.timestamp)
        } else {
            const friend = friends.find(friend => friend.id === msg.senderID)
            friend.ref.userLastMsg.title = msg.message
            friend.ref.userLastMsg.textContent = msg.message
            friend.ref.userLastMsg.style.color = "var(--primary-color)"
        }
    } catch (e) {
        console.log(e)
    }
};

websocket.onclose = function (event) {
    if (event.wasClean)
        console.log('WebSocket connection closed cleanly, code=' + event.code)
    else
        console.log('WebSocket connection abruptly closed')
};

window.addEventListener('beforeunload', () => {
    websocket.send("$offline:" + sessionUser.id)
    websocket.close()
})

