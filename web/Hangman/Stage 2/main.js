const input = require('sync-input');
const word = 'python';

console.log("H A N G M A N");
const inputWord = input("Guess the word: ");
console.log(word === inputWord ? "You survived!" : "You lost!");