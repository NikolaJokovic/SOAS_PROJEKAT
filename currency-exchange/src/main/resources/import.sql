-- EUR → ostale valute
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('EUR', 'USD', 1.10);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('EUR', 'GBP', 0.85);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('EUR', 'CHF', 0.95);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('EUR', 'RSD', 117.00);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('EUR', 'EUR', 1.00);

-- USD → ostale valute
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('USD', 'EUR', 0.91);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('USD', 'GBP', 0.77);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('USD', 'CHF', 0.86);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('USD', 'RSD', 106.36);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('USD', 'USD', 1.00);

-- GBP → ostale valute
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('GBP', 'EUR', 1.18);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('GBP', 'USD', 1.30);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('GBP', 'CHF', 1.12);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('GBP', 'RSD', 137.65);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('GBP', 'GBP', 1.00);

-- CHF → ostale valute
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('CHF', 'EUR', 1.05);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('CHF', 'USD', 1.16);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('CHF', 'GBP', 0.89);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('CHF', 'RSD', 123.16);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('CHF', 'CHF', 1.00);

-- RSD → ostale valute
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('RSD', 'EUR', 0.0085);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('RSD', 'USD', 0.0094);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('RSD', 'GBP', 0.0073);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('RSD', 'CHF', 0.0081);
INSERT INTO exchange_rates (from_currency, to_currency, rate) VALUES ('RSD', 'RSD', 1.00);