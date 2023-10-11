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
        return false;
    }

    return true;
};

const convertCurrenciesPrompt = () => {
    console.log("What do you want to convert?");
    const fromCurrency = input("From:").toUpperCase();
    if (!validateCurrency(fromCurrency)) return;
    const targetCurrency = input("To:").toUpperCase();
    if (!validateCurrency(targetCurrency)) return;
    const targetValue = Number(input("Amount:"));
    if (!Number.isFinite(targetValue)) {
        console.log("The amount has to be a number");
        return;
    } else if (targetValue < 1) {
        console.log("The amount cannot be less than 1");
        return;
    }

    console.log(`Result: ${targetValue} ${fromCurrency} equals ${convertCurrency(targetValue, fromCurrency, targetCurrency).toFixed(4)} ${targetCurrency}`);
};

Object.entries(conversionRates).forEach(([currency, equals]) => console.log(`1 USD equals ${equals} ${currency}`));

let option = -1;
while (option !== 2) {
    console.log(`What do you want to do?
1-Convert currencies 2-Exit program`);
    option = Number(input());

    switch (option) {
        case 1:
            convertCurrenciesPrompt();
            break;
        case 2:
            console.log("Have a nice day!");
            break;
        default:
            console.log("Unknown input");
    }
}
