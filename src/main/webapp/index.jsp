<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta
            name="viewport"
            content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
    />
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>TalkWave</title>
    <link rel="stylesheet" href="assets/css/main.css"/>
    <link rel="stylesheet" href="assets/css/loader.css"/>
    <link rel="stylesheet" href="assets/css/qarq90.css"/>
    <link rel="stylesheet" href="assets/css/sidebar.css"/>
    <link rel="stylesheet" href="assets/css/users-container.css"/>
    <link rel="stylesheet" href="assets/css/chat-container.css"/>
    <script src="assets/js/config.js"></script>
    <script src="assets/js/initializer.js" defer></script>
    <script src="assets/js/qarq90.js" defer></script>
    <script src="assets/js/script.js" defer></script>
    <script src="assets/js/websocket.js" defer></script>
    <script>
        let user = JSON.parse(localStorage.getItem("json"))

        sessionUser = {
            id: user.id,
            username: user.username,
            profileName: user.profileName,
            image: user.image,
        }
    </script>
</head>
<body>
<section class="loader">
    <div class="loader-logo">
        <h2>TalkWave</h2>
        <p>Chats are loading...</p>
    </div>
    <div class="loader-icon">
        <span class="dot dot-1"></span>
        <span class="dot dot-2"></span>
        <span class="dot dot-3"></span>
    </div>
</section>

<main>
    <div class="sidebar">
        <div class="sb-top">
            <div
                    class="chat-icon icon"
                    style="background-image: url('assets/img/icon/chat_room.svg')"
                    onclick="handleTabClick('chat-icon')"
            ></div>
            <div
                    class="user-icon icon"
                    style="background-image: url('assets/img/icon/user.svg')"
                    onclick="handleTabClick('user-icon')"
            ></div>
            <div class="pill"></div>
        </div>
        <div class="sb-bottom">
            <div
                    class="settings-icon icon"
                    style="background-image: url('assets/img/icon/settings.svg')" onclick="openProfile()"
            ></div>
        </div>
    </div>

    <div class="my-profile-card slide-hidden" id="myProfile">
        <div class="inner-profile">
            <img id="profile-img" src="" alt="pfp">
            <img class="edit-btn" src="./assets/img/icon/edit.svg" alt="edit-icon">
        </div>
        <div class="inner-profile">
            <h3 id="profile-name"></h3>
            <img class="edit-btn" src="./assets/img/icon/edit.svg" alt="edit-icon">
        </div>
        <div class="inner-profile">
            <h4 id="user-name"></h4>
            <img class="edit-btn" src="./assets/img/icon/edit.svg" alt="edit-icon">
        </div>
    </div>

    <div class="users-container">
        <div class="title-bar">
            <h2>Chats</h2>
            <div class="logout">
                <img src="assets/img/icon/logout.svg" alt=""/>
            </div>
        </div>
        <div class="users" id="myChats"></div>
        <div class="suggestions hidden" id="mySuggestions"></div>
    </div>

    <div class="chat-container">
        <div class="chat-mask">
            <img src="assets/img/icon/chat.svg" alt=""/>
        </div>
        <div class="chat-header">
            <div class="chat-profile">
                <img src="assets/img/Default.png" alt=""/>
                <div class="profile-info">
                    <div class="chat-name"></div>
                    <div class="chat-info"></div>
                </div>
            </div>
        </div>

        <div class="chat-body"></div>

        <div class="chat-down-btn down-btn-hidden">
            <img src="assets/img/icon/down.svg" alt=""/>
        </div>

        <div class="chat-input">
            <div
                    class="x btn"
                    style="background-image: url('assets/img/icon/emoji.svg')"
            ></div>
            <div
                    class="x btn"
                    style="background-image: url('assets/img/icon/attach.svg')"
            ></div>
            <label for="text-box"></label>
            <input class="btn" id="text-box" placeholder="Text a message"/>
            <div
                    class="send-btn btn"
                    style="background-image: url('assets/img/icon/sent.svg')"
                    onclick="sendMsg()"
            ></div>
        </div>
    </div>
</main>
</body>
</html>
