const input = require('sync-input');
const words = ['python', 'java', 'swift', 'javascript'];
const word = words[Math.floor(Math.random() * words.length)];
const splitWord = word.split("");
const guessedLetters = [];

console.log("H A N G M A N");

for (let i = 0; i < 8; i++) {
    console.log("\n" + splitWord.map(c => guessedLetters.includes(c) ? c : "-").join(""));
    const letter = input("Input a letter: ");
    if (!splitWord.includes(letter)) {
        console.log("That letter doesn't appear in the word.");
    } else guessedLetters.push(letter);
}

console.log("\nThanks for playing!");