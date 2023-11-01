<!DOCTYPE html>
<html lang="en">
<head>
    <link href="assets/css/main.css" rel="stylesheet">
    <link href="assets/css/authentication.css" rel="stylesheet">
    <title>TalkWave | Auth</title>
    <script src="assets/js/config.js"></script>
    <script src="assets/js/authentication.js" defer></script>
</head>
<body>

<div class="container">
    <div class="card back-xl">
        <div class="theme-card">
            <img alt="client" src="assets/img/icon/client.svg">
        </div>
        <div class="card-xl-2">
            <button class="btn login-btn login-btn-xl" type="button">Login</button>
            <button class="btn login-btn signup-btn-xl" type="button">SignUp</button>
        </div>
    </div>


    <div class="card back">
        <div class="auth-text">
            <h2>Login</h2>
            <img alt="" class="back-btn" id="backBtn" src="assets/img/icon/back_btn.svg">
        </div>
        <div class="login">
            <form action="/login" class="authentication" method="post">
                <label>
                    <input autocomplete="off" id="usr" name="username"  placeholder="Enter Username" spellcheck="false"
                           type="text">
                </label>
                <div class="eye-cont">
                    <label class="pass">
                        <input autocomplete="off" id="pass" name="password" placeholder="Enter Password"
                               spellcheck="false"
                               type="password">
                    </label>
                    <img alt="eye" class="eye" src="assets/img/icon/eye.svg" style="display: none">
                </div>
                <button class="btn login-btn auth-btn" type="button">Login</button>
            </form>
        </div>

        <div class="signup active">
            <form action="/signup" class="authentication" method="post">
                <div class="first-page active">
                    <label>
                        <input autocomplete="off" id="signup-username" placeholder="Enter Username" spellcheck="false"
                               type="text">
                    </label>
                    <div class="eye-cont">
                        <label class="pass">
                            <input autocomplete="off" id="signup-password" placeholder="Enter Password" spellcheck="false"
                                   type="password">
                        </label>
                        <img alt="eye" class="eye" src="assets/img/icon/eye.svg" style="display: none">
                    </div>
                    <div class="eye-cont">
                        <label class="pass">
                            <input autocomplete="off" id="signup-confirm" placeholder="Confirm Password" spellcheck="false"
                                   type="password">
                        </label>
                        <img alt="eye" class="eye" src="assets/img/icon/eye.svg" style="display: none">
                    </div>
                </div>
                <div class="second-page">
                    <div class="select-image">
                        <img alt="" src="assets/img/icon/profile.svg">
                        <div class="add-image">
                            <img src="assets/img/icon/plus.svg" alt="">
                            <label>
                                <input id="file" accept=".jpg, .jpeg, .png, .gif" type="file">
                            </label>
                        </div>
                    </div>
                    <label>
                        <input autocomplete="off" id="signup-profile" placeholder="Enter Profile Name" spellcheck="false"
                               type="text">
                    </label>
                </div>
                <button class="btn login-btn" id="next-btn" type="button">Next</button>
            </form>
        </div>
    </div>
    <div class="card front">
        <h1>Talk Wave</h1>
    </div>
</div>

<dialog class="error-container">
    <div class="error-msg">Error: Username or Password is Incorrect</div>
    <img src="assets/img/icon/close.svg" alt="" class="close-btn">
</dialog>

</body>
</html>
