#!/bin/zsh

mvn clean package && java -jar target/health.jar
