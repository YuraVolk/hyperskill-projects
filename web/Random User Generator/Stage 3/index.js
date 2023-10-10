(() => {
    const button =  document.getElementById("get-user-button");

    const displayNewUser = () => {
        fetch("https://randomuser.me/api/")
            .then(result => result.json())
            .then(json => {
                const result = json.results[0];
                const birthdayDate = new Date(result.dob.date);

                const users = document.getElementsByClassName("user");
                (users.length === 0 ? button : users[users.length - 1]).insertAdjacentHTML("afterend", `
                    <div class="user">
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
            });
    };

    displayNewUser();
    document.addEventListener("click", ({ target }) => {
        if (target === button) displayNewUser();
    });
})();

