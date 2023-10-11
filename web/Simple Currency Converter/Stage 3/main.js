const input = require('sync-input');

console.log("Welcome to Currency Converter!");

const conversionRates = {
    USD: 1,
    JPY: 113.5,
    EUR: 0.89,
    RUB: 74.36,
    GBP: 0.75
};

const convertCurrency = (amount, from, to) => {
    const conversionRate = conversionRates[to] / conversionRates[from];
    return amount * conversionRate;
};

const validateCurrency = (currency) => {
    if (!(currency in conversionRates)) {
        console.log("Unknown currency");
        process.exit();
    }
};

Object.entries(conversionRates).forEach(([currency, equals]) => console.log(`1 USD equals ${equals} ${currency}`));
console.log("What do you want to convert?");
const fromCurrency = input("From:").toUpperCase();
validateCurrency(fromCurrency);
const targetCurrency = input("To:").toUpperCase();
validateCurrency(targetCurrency);
const targetValue = Number(input("Amount:"));
if (!Number.isFinite(targetValue)) {
    console.log("The amount has to be a number");
    process.exit();
} else if (targetValue < 1) {
    console.log("The amount cannot be less than 1");
    process.exit();
}

console.log(`Result: ${targetValue} ${fromCurrency} equals ${convertCurrency(targetValue, fromCurrency, targetCurrency).toFixed(4)} ${targetCurrency}`);