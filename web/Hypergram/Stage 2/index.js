(() => {
   const canvas = document.getElementById("canvas");
   const ctx = canvas.getContext("2d");
   const fileUpload = document.getElementById("file-input");

   fileUpload.addEventListener("change", ({ target: { files: [file] } }) => {
       const image = new Image();
       image.src = URL.createObjectURL(file);
       image.addEventListener("load", () => {
          canvas.width = image.width;
          canvas.height = image.height;
          ctx.drawImage(image, 0, 0);
       });
   });
})();