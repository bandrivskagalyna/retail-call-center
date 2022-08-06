# Retail Call Center 

A big retail company has a customer service call-center with employees (STAFF), a
supervisor (SUPERVISOR), and a manager (MANAGER). There are multiple employees but there can be only one supervisor and one
manager.

## Setup requirements

Java JDK 11 , Maven and optional IDE (if you want to run from there)

## Installation

Using Maven
```bash
mvn clean install
```

## How to start

Using Maven (you can skip parameter and they will be set to default predefined values: staffAmount=3 amountOfCalls=9 callDuration=3)
```bash
mvn compile exec:java -Dexec.mainClass="com.main.CallMain" -Dexec.args="staffAmount=4 amountOfCalls=15 callDuration=2"mvn clean install
```
Using IDE (run main class CallMain.java) add Program Arguments
```bash
staffAmount=4 amountOfCalls=15 callDuration=2
```

## Note

Maximum number of employees is 50 and  maximum number of calls is 100. 
All provided perameters should be positive non-zero values

