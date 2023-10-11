const input = require('sync-input');
const words = ['python', 'java', 'swift', 'javascript'];


console.log("H A N G M A N");

function playGame() {
    const word = words[Math.floor(Math.random() * words.length)];
    const splitWord = word.split("");
    const guessedLetters = [];

    let attempts = 8;
    while (attempts > 0) {
        let anyFail = false;
        const result = "\n" + splitWord.map(c => {
            if (guessedLetters.includes(c)) {
                return c;
            } else {
                anyFail = true;
                return "-";
            }
        }).join("");
        if (!anyFail) {
            console.log(`\nYou guessed the word ${word}!\nYou survived!`);
            return true;
        } else console.log(result);

        const letter = input("Input a letter: ");
        if (letter.length !== 1) {
            console.log("Please, input a single letter.");
            continue;
        } else if (!letter.match(/[a-z]/)) {
            console.log("Please, enter a lowercase letter from the English alphabet.");
            continue;
        }

        if (guessedLetters.includes(letter)) {
            console.log("You've already guessed this letter.");
            continue;
        }
        guessedLetters.push(letter);
        if (!splitWord.includes(letter)) {
            console.log("That letter doesn't appear in the word.");
        } else continue;
        attempts--;
    }

    console.log("\nYou lost!");
    return false;
}

let option = "";
let wins = 0, losses = 0;
while (option !== "exit") {
    option = input("Type \"play\" to play the game, \"results\" to show the scoreboard, and \"exit\" to quit: ");
    switch (option) {
        case "play":
            playGame() ? wins++ : losses++;
            break;
        case "results":
            console.log(`You won: ${wins} times.
You lost: ${losses} times.`);
            break;
        case "exit":
        default:
            break;
    }
}
