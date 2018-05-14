### What is this?

Small demo-app that demonstrates how can a "routing data source" be put in use. The application can selectively work with either of the two defined data sources transparently without a need to define special components.

### Components

- [`CustomerEntity`](https://github.com/vrto/routing-ds/blob/0e9e25d5577f71d199f2e9e219894defd95dfac6/src/main/java/net/helpscout/routingds/CustomerEntity.java) - data source agnostic entity
- [`CustomerRepository`](https://github.com/vrto/routing-ds/blob/0e9e25d5577f71d199f2e9e219894defd95dfac6/src/main/java/net/helpscout/routingds/CustomerRepository.java) - data source agnostic repository
- [`CustomerQueries`](https://github.com/vrto/routing-ds/blob/0e9e25d5577f71d199f2e9e219894defd95dfac6/src/main/java/net/helpscout/routingds/CustomerQueries.java) - reading part of the traditional service layer. While this class doesn't make any explicit commitments about which data source it wants to use, its operations will end up in **slave** database, as  they're all `@Transactional(readOnly = true)`
- [`CustomerCommands`](https://github.com/vrto/routing-ds/blob/0e9e25d5577f71d199f2e9e219894defd95dfac6/src/main/java/net/helpscout/routingds/CustomerCommands.java) - writing part of the traditional service layer. While this class doesn't make any explicit commitments about which data source it wants to use, its operations will end up in **master** database, as they're all `@Transactional` (writing)
- [`CustomerController`](https://github.com/vrto/routing-ds/blob/0e9e25d5577f71d199f2e9e219894defd95dfac6/src/main/java/net/helpscout/routingds/CustomerController.java) - can inject _both_ command and queries to perform operations on either master or slave database
- [`DbContextHolder`](https://github.com/vrto/routing-ds/blob/0e9e25d5577f71d199f2e9e219894defd95dfac6/src/main/java/net/helpscout/routingds/DbContextHolder.java) - thread local static data source holder
- [`TransactionInterceptor`](https://github.com/vrto/routing-ds/blob/0e9e25d5577f71d199f2e9e219894defd95dfac6/src/main/java/net/helpscout/routingds/TransactionInterceptor.java) - ensures that the right data source is set in `DbContextHolder` before every `@Transactional` operation

### Routing DS logic

- special kind of `AbstractRoutingDataSource` is used and shared for all DB-level components (entity manager, transaction manager, etc.). This data source is aware of the actual underlying data sources (master, slave in our case).
- every `@Transactional` method is intercepted by `TransactionInterceptor`
- proper data source is set into `DbContextHolder`
- default data source is always `master`

### Proof

See [the bundled test](https://github.com/vrto/routing-ds/blob/0e9e25d5577f71d199f2e9e219894defd95dfac6/src/test/java/net/helpscout/routingds/RoutingdsApplicationTests.java) or run the app locally and use the following:

- `curl -XGET http://localhost:8080/slave/customers` => list customers from slave DS
- `curl -XGET http://localhost:8080/master/customers` => list customers from master DS
- `curl -XPOST http://localhost:8080/master/customers` => creates a new customer in master DS, but not in slave DS
