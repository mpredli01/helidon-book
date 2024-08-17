#!/bin/zsh

mvn clean package && java -jar target/metrics.jar
