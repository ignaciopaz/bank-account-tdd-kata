package edu.utn.frro.agiles.tdd.bankaccount;

import java.util.Currency;

public interface CurrencyConverter {

	Double convert(Currency currencyFrom, Currency currencyTo);

}
