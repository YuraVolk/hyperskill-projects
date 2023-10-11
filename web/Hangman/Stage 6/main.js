const input = require('sync-input');
const words = ['python', 'java', 'swift', 'javascript'];
const word = words[Math.floor(Math.random() * words.length)];
const splitWord = word.split("");
const guessedLetters = [];

console.log("H A N G M A N");

for (let i = 0; i < 8; i++) {
    let anyFail = false;
    console.log("\n" + splitWord.map(c => {
        if (guessedLetters.includes(c)) {
            return c;
        } else {
            anyFail = true;
            return "-";
        }
    }).join(""));
    if (!anyFail) {
        console.log("You guessed the word!\nYou survived!");
        process.exit();
    }

    const letter = input("Input a letter: ");
    if (!splitWord.includes(letter)) {
        console.log("That letter doesn't appear in the word.");
    } else if (guessedLetters.includes(letter)) {
        console.log("No improvements.");
    } else {
        guessedLetters.push(letter);
        i--;
    }
}

console.log("\nYou lost!");