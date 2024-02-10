# Cron Expression Parser

Cron Expression Parser is my implementation of Deliveroo's Technical Task.

## Prerequisites

* Maven installed and added to PATH
* Java installed and added to PATH

## Installation

Clone Cron Expression Parser from git:
```bash
git clone
```
Navigate to root of installed files and run:
```bash
mvn clean install
```
Run the created jar file:

```bash
java -jar target/CronExpressionParser-1.0-SNAPSHOT-jar-with-dependencies.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```

## Usage
The cron string will be passed to your application as a single argument. ~$ your-program ＂d＂
The output should be formatted as a table with the field name taking the first 14 columns and the times as a space-separated list following it.
For example, the following input argument:
*/15 0 1,15 * 1-5 /usr/bin/find
```bash
minute 0 15 30 45
hour 0
day of month 1 15
month 1 2 3 4 5 6 7 8 9 10 11 12
day of week 1 2 3 4 5
command /usr/bin/find
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