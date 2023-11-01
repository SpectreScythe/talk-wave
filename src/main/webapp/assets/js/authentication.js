const eye = document.querySelector(".eye");
const user = document.querySelector("#usr");
const pass = document.querySelector("#pass");
const loginBtn = document.querySelector(".auth-btn");
const signupBtn = document.querySelector("#next-btn");
const backCard = document.querySelector(".back");
const loginCard = document.querySelector(".login");
const signupCard = document.querySelector(".signup");
const firstPage = document.querySelector(".first-page");
const secondPage = document.querySelector(".second-page");
const authText = document.querySelector(".auth-text h2");
const Auth = document.querySelector(".authentication");
const loginAuth = document.querySelector(".login-btn-xl");
const signupAuth = document.querySelector(".signup-btn-xl");
const errorContainer = document.querySelector(".error-container");
const errorMsg = document.querySelector(".error-msg");
const closeBtn = document.querySelector(".close-btn");
const file = document.querySelector("#file");
const profile = document.querySelector(".select-image > img");


loginAuth.addEventListener("click", () => {
    authText.innerHTML = "Login";
    loginBtn.textContent = "Login";

    loginCard.className = "login active"
    signupCard.className = "signup"

    Auth.setAttribute("action", "/login")
    backCard.classList.add("slide-out");
    clearInput()
    addBackListener();
})

signupAuth.addEventListener("click", () => {
    authText.innerHTML = "SignUp";
    signupBtn.textContent = "Next";

    loginCard.className = "login"
    signupCard.className = "signup active"

    firstPage.className = "first-page active"
    secondPage.className = "second-page"

    Auth.setAttribute("action", "/signup")
    backCard.classList.add("slide-out");
    clearInput()
    addBackListener();
})

loginBtn.onclick = async function () {
    const username = document.querySelector("#usr").value.trim()
    const password = document.querySelector("#pass").value.trim()
    if (!username || !password) {
        errorContainer.style.opacity = "1";
        errorMsg.textContent = 'Error: All fields are required';
        return
    } else {
        errorContainer.style.opacity = "0";
    }

    let res = await fetch(apiAuthenticationURL + "?username=" + username + "&password=" + password, {method: 'GET'})
    let data = await res.json()

    if (data.isValid === "false") {
        errorContainer.style.opacity = "1";
        errorMsg.textContent = 'Error: Username or password is incorrect';
        return
    }

    const userData = {
        id: data.id,
        username: data.username,
        profileName: data.profileName,
        image: data.image,
    }

    console.log(data, userData)
    localStorage.setItem("json", JSON.stringify(userData))
    localStorage.setItem("authenticated", "true")

    handleAuthAnimation()
    setTimeout(() => {
        window.location.href = "index.jsp";
    }, 500)
}

signupBtn.onclick = async function () {
    const username = document.querySelector("#signup-username").value.trim()
    const password = document.querySelector("#signup-password").value.trim()
    const confirm = document.querySelector("#signup-confirm").value.trim()
    const profile = document.querySelector("#signup-profile").value.trim()

    if (!username || !password || !confirm) {
        errorContainer.style.opacity = "1";
        errorMsg.textContent = 'Error: All fields are required';
        return
    } else if (password !== confirm) {
        errorContainer.style.opacity = "1";
        errorMsg.textContent = 'Error: Passwords does not match';
        return
    } else {
        errorContainer.style.opacity = "0";
    }

    let res = await fetch(apiAuthenticationURL + "?username=" + username, {method: 'GET'})
    let data = await res.json()

    if (data.isValid === "true") {
        errorContainer.style.opacity = "1";
        errorMsg.textContent = 'Error: Username already exists';
        return
    }

    if (signupBtn.textContent === "Next") {
        signupBtn.textContent = "SignUp"
        firstPage.className = "first-page"
        secondPage.className = "second-page active"
        return
    }

    if (!profile) {
        errorContainer.style.opacity = "1";
        errorMsg.textContent = 'Error: Profile name cannot be empty';
        return
    }

    if (!profileB64) {
        profileB64 = "assets/img/icon/Default.png"
    }

    const userData = {
        username: username,
        password: password,
        profileName: profile,
        image: profileB64,
    }

    res = await fetch(apiRegisterUserURL, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userData)
    })

    data = await res.json()

    if (data.status === "OK") {
        localStorage.setItem("json", JSON.stringify(userData))
        localStorage.setItem("authenticated", "true")
        handleAuthAnimation()
        setTimeout(() => {
            window.location.href = "index.jsp"
        }, 500)
    } else {
        console.log(data.status)
    }
}

function handleAuthAnimation(type) {
    const cards = document.querySelectorAll(".card")
    cards.forEach(card => {
        card.classList.add("anim")
    })
}

function addBackListener() {
    const backBtn = document.querySelector("#backBtn");
    backBtn.removeEventListener("click", handleBackClick);
    backBtn.addEventListener("click", handleBackClick);
}

function handleBackClick() {
    backCard.classList.remove("slide-out");
}

eye.onclick = function () {
    if (pass.type === "text") {
        pass.type = "password";
        eye.setAttribute("src", "assets/img/icon/eye.svg")
    } else {
        pass.type = "text";
        eye.setAttribute("src", "assets/img/icon/invisible.svg")
    }
}

pass.addEventListener("input", function () {
    if (pass.value) {
        eye.style.display = "unset";
    } else {
        eye.style.display = "none";
    }
})

function clearInput() {
    user.value = ""
    pass.value = ""
    pass.type = "password";
    eye.setAttribute("src", "assets/img/icon/eye.svg")
    eye.style.display = "none";
}

file.onchange = function () {
    const selectedFile = file.files[0]; // Get the selected file
    const maxFileSize = 1024 * 1024;

    if (selectedFile) {
        if (selectedFile.size >= maxFileSize) {
            errorContainer.style.opacity = "1";
            errorMsg.textContent = 'Error: File size exceeds the limit (1MB)';
            file.value = '';
            return
        }

        const reader = new FileReader();
        reader.onload = function (e) {
            profileB64 = profile.src = e.target.result;
        };

        reader.readAsDataURL(selectedFile);
    } else {
        profile.src = '';
    }
}

closeBtn.onclick = function () {
    errorContainer.style.opacity = "0";
}