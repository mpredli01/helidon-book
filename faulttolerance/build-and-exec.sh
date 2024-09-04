#!/bin/zsh

mvn clean package && java -jar target/faulttolerance.jar
