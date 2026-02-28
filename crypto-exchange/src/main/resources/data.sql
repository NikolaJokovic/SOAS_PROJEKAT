-- BTC → ostale crypto valute
INSERT INTO crypto_rates (from_crypto, to_crypto, rate) VALUES ('BTC', 'ETH', 15.00);
INSERT INTO crypto_rates (from_crypto, to_crypto, rate) VALUES ('BTC', 'USDT', 45000.00);
INSERT INTO crypto_rates (from_crypto, to_crypto, rate) VALUES ('BTC', 'BTC', 1.00);

-- ETH → ostale crypto valute
INSERT INTO crypto_rates (from_crypto, to_crypto, rate) VALUES ('ETH', 'BTC', 0.067);
INSERT INTO crypto_rates (from_crypto, to_crypto, rate) VALUES ('ETH', 'USDT', 3000.00);
INSERT INTO crypto_rates (from_crypto, to_crypto, rate) VALUES ('ETH', 'ETH', 1.00);

-- USDT → ostale crypto valute
INSERT INTO crypto_rates (from_crypto, to_crypto, rate) VALUES ('USDT', 'BTC', 0.000022);
INSERT INTO crypto_rates (from_crypto, to_crypto, rate) VALUES ('USDT', 'ETH', 0.00033);
INSERT INTO crypto_rates (from_crypto, to_crypto, rate) VALUES ('USDT', 'USDT', 1.00);