(() => {
    const taskList = document.getElementById("task-list");
    const input = document.getElementById("input-task");
    const createTaskButton = document.getElementById("add-task-button");

    const builtInTasks = JSON.parse(localStorage.getItem("tasks")) ?? [];

    const updateBuiltInTasks = () => {
        builtInTasks.length = 0;
        console.log("yay");
        for (const child of taskList.children) {
            builtInTasks.push({
                title: child.querySelector(".task").textContent,
                isChecked: child.querySelector(".checkbox").checked
            });
        }
        localStorage.setItem("tasks", JSON.stringify(builtInTasks));
    };

    const insertNewTask = (name, checked = false) => {
        taskList.insertAdjacentHTML("beforeend", `
            <li class="main-tasks-item">
                    <label class="main-tasks-item__label">
                        <input class="main-tasks-item__checkbox checkbox" type="checkbox" ${checked ? "checked=checked" : ""}>
                        <span class="main-tasks-item__title task">${name}</span>
                    </label>
               <button class="main-tasks-item__button delete-btn"></button>
            </li>
        `);
    };

    builtInTasks.forEach(task =>  insertNewTask(task.title, task.isChecked));

    document.addEventListener("click", (event) => {
       if (event.target === createTaskButton && input.value.trim().length) {
           insertNewTask(input.value);
           updateBuiltInTasks();
           input.value = "";
       } else if (event.target.classList.contains("delete-btn")) {
           updateBuiltInTasks();
           event.target.parentElement.remove();
       }
    });
    [...document.querySelectorAll("input[type=checkbox]")].forEach(() => document.addEventListener("change", () => {
        updateBuiltInTasks();
    }))
})();