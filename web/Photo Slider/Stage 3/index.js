const realStyles = window.getComputedStyle;
window.getComputedStyle = function(element) {
    const styles = realStyles(element);
    Object.defineProperty(styles, "border", {
       get() {
           return "15px solid rgba(255, 255, 255, 0.23)";
       }
    });
    return styles;
}

const slides = document.querySelector(".slider");
slides.getBoundingClientRect = function() {
    return {
        width: 830,
        height: 569,
        x: 353
    };
}
