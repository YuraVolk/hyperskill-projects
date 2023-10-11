const input = require('sync-input');
const words = ['python', 'java', 'swift', 'javascript'];
const word = words[Math.floor(Math.random() * words.length)];

console.log("H A N G M A N");
const inputWord = input("Guess the word: ");
console.log(word === inputWord ? "You survived!" : "You lost!");