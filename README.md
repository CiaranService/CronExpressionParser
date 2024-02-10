# Cron Expression Parser

Cron Expression Parser is my implementation of Deliveroo's Technical Task.

## Prerequisites

* Maven installed and added to PATH
* Java installed and added to PATH

## Installation

Clone Cron Expression Parser from git:
```bash
git clone https://github.com/CiaranService/CronExpressionParser
```
Navigate to root of installed files and run:
```bash
mvn clean install
```

## Usage
Run the created jar file with cron expression passed as a String:

```bash
java -jar target/CronExpressionParser-1.0-SNAPSHOT-jar-with-dependencies.jar <cron expression>
```

## Example 

```bash
java -jar target/CronExpressionParser-1.0-SNAPSHOT-jar-with-dependencies.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```

Should yield the following output:
```bash
minute 0 15 30 45
hour 0
day of month 1 15
month 1 2 3 4 5 6 7 8 9 10 11 12
day of week 1 2 3 4 5
command /usr/bin/find
```