(() => {
    const accessibilityButton = document.getElementById("accessibility-button-navigation");
    let isToggled = false;
    const accessibilityPaneIDs = {
        ROOT: "accessibility-pane",
        TEXT: {
            SMALL: "small-text",
            MEDIUM: "medium-text",
            LARGE: "large-text",
            LARGER: "larger-text",
            TITLE: "span-text"
        },
        THEME: {
            WHITE: "white-theme",
            YELLOW: "yellow-theme",
            BLUE: "blue-theme",
            TITLE: "span-color"
        },
        NO_IMAGES: "button-hide-images"
    };
    const htmlClasses = {
      currentTextClass: "",
      currentColorClass: ""
    };

    const updateHTMLClasses = () => {
        document.documentElement.setAttribute("class", htmlClasses.currentTextClass);
    };

    const openButtons = () => {
        const rootElement = document.createElement("div");
        rootElement.setAttribute("id",  accessibilityPaneIDs.ROOT);
        rootElement.insertAdjacentHTML("afterbegin", `
            <div>
                <span id="${accessibilityPaneIDs.TEXT.TITLE}">Change text size</span>
                ${Object.entries(accessibilityPaneIDs.TEXT).map(([key, value]) => {
                    if (key === "TITLE") return "";
                    return `
                       <label>
                         <input type="radio" name="accessibility-text-size" id="${value}" data-value="${value}">
                         ${key[0] + key.toLowerCase().slice(1)}
                       </label>
                    `;
                }).join("")}
            </div>
            <div>
                <span id="${accessibilityPaneIDs.THEME.TITLE}">Change colors</span>
                ${Object.entries(accessibilityPaneIDs.THEME).map(([key, value]) => {
                    if (key === "TITLE") return "";
                    return `
                       <label>
                         <input type="radio" name="accessibility-theme-color" id="${value}" data-value="${value}">
                         ${key[0] + key.toLowerCase().slice(1)}
                       </label>
                    `;
                }).join("")}
            </div>
            <button id="${accessibilityPaneIDs.NO_IMAGES}">Hide images</button>
        `);
        accessibilityButton.after(rootElement);
        [...document.querySelectorAll(`#${accessibilityPaneIDs.ROOT} input[type=radio]`)].forEach((input) => {
           input.addEventListener("change", ({ target }) => {
               if (target.id.includes("text")) {
                   htmlClasses.currentTextClass = target.getAttribute("data-value");
               }
               updateHTMLClasses();
           })
        });
    };

    const closeButtons = () => {
        document.getElementById(accessibilityPaneIDs.ROOT).remove();
    };

    document.addEventListener("click", (event) => {
        if (event.target === accessibilityButton) {
            isToggled ? closeButtons() : openButtons();
            isToggled = !isToggled;
        }
    })
})();