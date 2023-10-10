(() => {
    const button =  document.getElementById("get-user-button"), saveButton = document.getElementById("save-users-button");
    const usersContainer = document.querySelector(".users"), savedUsersContainer = document.querySelector(".saved-users");
    const users = [], savedUsers = JSON.parse(localStorage.getItem("users")) ?? [];

    const renderUsers = (container, list, className) => {
        container.textContent = "";
        for (const result of list) {
            const birthdayDate = new Date(result.dob.date);
            container.insertAdjacentHTML("beforeend", `
                <div class="${className}">
                    <h2 class="name">${result.name.first} ${result.name.last}</h2>
                    <p class="email">Email: ${result.email}</p>
                    <p class="password">Password: ${result.login.password}</p>
                    <p class="gender">Password: ${result.gender}</p>
                    <p class="phone">Phone: ${result.phone}</p>
                    <p class="location">Location: ${result.location.city}, ${result.location.country}</p>
                    <p class="birthday">Birthday: ${String(birthdayDate.getDay()).padStart(2, "0")}/${String(birthdayDate.getMonth()).padStart(2, "0")}/${birthdayDate.getFullYear()}</p>
                    <img class="photo" alt="${result.name.first}" src="${result.picture.large}">
                </div>
            `);
        }
    };

    const rerenderUsers = () => {
        renderUsers(usersContainer, users, "user");
        renderUsers(savedUsersContainer, savedUsers, "saved");
    };

    const addNewUser = () => {
        fetch("https://randomuser.me/api/")
            .then(result => result.json())
            .then(json => {
                users.push(json.results[0]);
                rerenderUsers();
            });
    };

    const saveUsers = () => {
        savedUsers.length = 0;
        savedUsers.push(...users);
        localStorage.setItem("users", JSON.stringify(savedUsers));
        rerenderUsers();
    };

    addNewUser();
    document.addEventListener("click", ({ target }) => {
        if (target === button) {
            addNewUser();
        } else if (target === saveButton) {
            saveUsers();
        }
    });
})();