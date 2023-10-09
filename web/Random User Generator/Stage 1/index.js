const result = fetch("https://randomuser.me/api/")
    .then(result => result.json())
    .then(json => {
        document.documentElement.innerHTML = JSON.stringify(json);
    });
