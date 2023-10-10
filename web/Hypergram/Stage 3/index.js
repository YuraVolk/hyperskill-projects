(() => {
   const canvas = document.getElementById("canvas");
   const ctx = canvas.getContext("2d");
   const fileUpload = document.getElementById("file-input");
   const rangeValues = {
       brightness: 0,
       contrast: 0,
       transparent: 1
   };
   let image;

   const repaint = () => {
       ctx.drawImage(image, 0, 0);
       const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
       const data = imageData.data;

       for (let i = 0; i < data.length; i += 4) {
           let red = data[i];
           let green = data[i + 1];
           let blue = data[i + 2];

           const factor = (259 * (255 + rangeValues.contrast)) / (255 * (259 - rangeValues.contrast));
           red = factor * (red - 128) + 128;
           green = factor * (green - 128) + 128;
           blue = factor * (blue - 128) + 128;

           red += rangeValues.brightness;
           green += rangeValues.brightness;
           blue += rangeValues.brightness;

           let newAlpha = data[i + 3];
           if (rangeValues.transparent !== 1) {
               const alpha = data[i + 3];
               const transparent = rangeValues.transparent;
               newAlpha = alpha * transparent;
           }

           data[i] = Math.max(0, Math.min(255, red));
           data[i + 1] = Math.max(0, Math.min(255, green))
           data[i + 2] = Math.max(0, Math.min(255, blue));
           data[i + 3] = Math.max(0, Math.min(255, newAlpha));
       }

       ctx.putImageData(imageData, 0, 0);
   };

   fileUpload.addEventListener("change", ({ target: { files: [file] } }) => {
       image = new Image();
       image.src = URL.createObjectURL(file);
       image.addEventListener("load", () => {
          canvas.width = image.width;
          canvas.height = image.height;
          ctx.drawImage(image, 0, 0);
       });
   });
   [...document.querySelectorAll("input[type=range]")].forEach(e => e.addEventListener("change", (event) => {
        rangeValues[event.target.id] = Number(event.target.value);
        if (image) repaint();
   }));
})();