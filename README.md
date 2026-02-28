
Mikroservisna aplikacija za razmenu običnih i crypto valuta.


Servisi se nalaze na ovim portovima:
1. NamingServerApplication (port 8761)
2. UsersServiceApplication (port 8089)
3. BankAccountApplication (port 8082)
4. CryptoWalletApplication (port 8085)
5. CurrencyExchangeApplication (port 8087)
6. CryptoExchangeApplication (port 8084)
7. CurrencyConversionApplication (port 8086)
8. CryptoConversionApplication (port 8083)
9. TradeServiceApplication (port 8088)
10. ApiGatewayApplication (port 8765)

Sve Docker slike dostupne na:
- `https://hub.docker.com/u/nikolajokovic`

Lista slika:
- `nikolajokovic/naming-server:latest`
- `nikolajokovic/users-service:latest`
- `nikolajokovic/bank-account:latest`
- `nikolajokovic/crypto-wallet:latest`
- `nikolajokovic/currency-exchange:latest`
- `nikolajokovic/currency-conversion:latest`
- `nikolajokovic/crypto-exchange:latest`
- `nikolajokovic/crypto-conversion:latest`
- `nikolajokovic/trade-service:latest`
- `nikolajokovic/api-gateway:latest`

## **API Endpoints (preko API Gateway - port 8765)**

### **Autentifikacija:**
Svi zahtevi zahtevaju **Basic Auth** kredencijale.


### **Users Service**
- `GET /users` - Lista svih korisnika (OWNER)
- `GET /users/{id}` - Korisnik po ID (OWNER, ADMIN)
- `GET /users/email/{email}` - Korisnik po email-u (OWNER, ADMIN)
- `POST /users` - Kreiraj korisnika (OWNER, ADMIN)
- `PUT /users/{id}` - Ažuriraj korisnika (OWNER, ADMIN)
- `DELETE /users/{id}` - Obriši korisnika (OWNER)
---
### **Bank Account**
- `GET /bank-accounts` - Svi računi (ADMIN)
- `GET /bank-accounts/{id}` - Račun po ID (ADMIN)
- `GET /bank-accounts/email/{email}` - Račun po email-u (ADMIN, USER - svoj račun)
- `PUT /bank-accounts/{id}` - Ažuriraj račun (ADMIN)
---
### **Crypto Wallet**
- `GET /crypto-wallets` - Svi novčanici (ADMIN)
- `GET /crypto-wallets/{id}` - Novčanik po ID (ADMIN)
- `GET /crypto-wallets/email/{email}` - Novčanik po email-u (ADMIN, USER - svoj novčanik)
- `PUT /crypto-wallets/{id}` - Ažuriraj novčanik (ADMIN)
---
### **Currency Exchange**
- `GET /currency-exchange` - Svi kursevi (SVI)
- `GET /currency-exchange/{from}/{to}` - Kurs između dve valute (SVI)
---
### **Crypto Exchange**
- `GET /crypto-exchange` - Svi crypto kursevi (SVI)
- `GET /crypto-exchange/{from}/{to}` - Kurs između dve crypto valute (SVI)
---
### **Currency Conversion**
- `GET /currency-conversion?email={email}&from={from}&to={to}&quantity={quantity}` - Razmena valuta (USER)
---
### **Crypto Conversion**
- `GET /crypto-conversion?email={email}&from={from}&to={to}&quantity={quantity}` - Razmena crypto valuta (USER)
---
### **Trade Service**
- `GET /trade-service?email={email}&from={from}&to={to}&quantity={quantity}` - Razmena običnih ↔ crypto (USER)

## **Kredencijali korisnika**

| Email | Password | Uloga | Opis |
|-------|----------|-------|------|
| owner@bank.com | owner123 | OWNER | Može sve, ali ne može menjati valutu |
| admin@bank.com | admin123 | ADMIN | Može upravljati USER-ima i računima, ne može menjati valutu |
| marko@gmail.com | marko123 | USER | Može menjati valutu, vidi samo svoj račun |
