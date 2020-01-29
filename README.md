# resilence4j-demo
Resilience4J with Spring boot

Resilience4j is a library that helps in building fault tolerant systems that communicate with remote services. It implements the `Circuit Breaker` pattern which helps in preventing failure cascade.

# CIRCUIT BREAKER
The basic idea behind the circuit breaker is : you wrap a protected function call in a circuit breaker object, which monitors for failures. Once the failures reach a certain threshold, the circuit breaker trips, and all further calls to the circuit breaker return with an error, without the protected call being made at all.
For e.g. ClientService in the project makes a call to exception prone RemoteService. The call is decorated with a `CircuitBreaker` object. The circuit breaker is configured with a failure threshold of 20% and min failed 2 calls in a sliding window. Now, when the circuit breaker has encountered a 2 failed calls(in a window), it will not make further calls to the remote service and will simply throw the exception.

# CIRCUIT BREAKER STATE MACHINE
A CircuitBreaker can be in following states at any given point in time:
  1. `CLOSED` : Everything is normal and remote calls are successful
  2. `OPEN` : Remote server is down, all requests are short-circuited
  3. `HALF_OPEN` : Configured amount of time has elapsed after entering the `Open` state and calls to remote server will be tried again.
  4. `DISABLED` : Circuit breaker is not operating and all requests are allowed
  5. `FORCED_OPEN` : Circuit breaker is not operating and no requests are allowed
[See `CircuitBreaker` and `CircuitBreaker.State` for allowed transitions]

# RATE LIMITER
Define at what rate the requests are served. The project defines a RateLimiterConfig which allows 2 calls every second. After these, every call will wait for 100 miliseconds before getting timed out

# Retry
Define the retry mechanism for any service. The project defines a RetryConfig which retries when it encounters `HttpServerErrorException.class`. Max 5 retry attempts can be made and every attempt has a wait duration of 2 seconds. We can also configure the exceptions on which retry is to be ignored, or the result on which retry is to be attempted.
