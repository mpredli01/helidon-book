#!/bin/zsh

java -cp "target/classes:target/libs/*" org.redlich.webclient.ClientApplication "$1"
