# Session Control API

## Summary

Testing efficient database absent approach for implementing HTTP API

## Pre requisites

* Java
* Maven

## Build and run

```
mvn clean package
java -jar target/sessioncontrol-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Features

* Starting a new charging session for the station

```
curl -X POST 'http://localhost:4567/chargingSessions' -d '{"stationId":"ABC-12345"}'
```

* Finishing charging session

```
curl -X PUT 'http://localhost:4567/chargingSessions/1111111111111'
```

* Retrieving all charging sessions

```
curl 'http://localhost:4567/chargingSessions'
```

* Retrieving a summary of submitted charging sessions

```
curl 'http://localhost:4567/chargingSessions/summary'
```

## Further improvements

* Fix one ignored test, that has unexpected result.
* Write more functional tests, matching responses expected response.
