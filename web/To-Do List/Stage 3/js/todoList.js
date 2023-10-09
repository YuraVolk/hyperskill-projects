(() => {
    const taskList = document.getElementById("task-list");
    const input = document.getElementById("input-task");
    const createTaskButton = document.getElementById("add-task-button");

    document.addEventListener("click", (event) => {
       if (event.target === createTaskButton && input.value.trim().length) {
           taskList.insertAdjacentHTML("beforeend", `
                <li class="main-tasks-item">
                    <label class="main-tasks-item__label">
                        <input class="main-tasks-item__checkbox checkbox" type="checkbox">
                        <span class="main-tasks-item__title task">${input.value}</span>
                    </label>
                    <button class="main-tasks-item__button delete-btn"></button>
                </li>
           `);
           input.value = "";
       } else if (event.target.classList.contains("delete-btn")) {
           event.target.parentElement.remove();
       }
    });
})();