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

Object.entries(conversionRates).forEach(([currency, equals]) => console.log(`1 USD equals ${equals} ${currency}`));
console.log(`I can convert USD to these currencies: JPY, EUR, RUB, USD, GBP`);
console.log("Type the currency you wish to convert: USD");
const targetCurrency = input("To:").toUpperCase();
if (!(targetCurrency in conversionRates)) {
    console.log("Unknown currency");
    process.exit();
}
const targetValue = Number(input("Amount:"));
if (!Number.isFinite(targetValue)) {
    console.log("The amount has to be a number");
    process.exit();
} else if (targetValue < 1) {
    console.log("The amount cannot be less than 1");
    process.exit();
}

console.log(`Result: ${targetValue} USD equals ${convertCurrency(targetValue, "USD", targetCurrency).toFixed(4)} ${targetCurrency}`);