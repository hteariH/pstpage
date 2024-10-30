#!/bin/bash
# Find and stop the running Java application
pkill -f pstpage.jar

# Copy the new JAR file to the desired location
cp ./target/pstpage-0.0.1-SNAPSHOT.jar /home/admin/deploy/pstpage.jar

#Start the Java application using the new JAR

#nohup java -jar /home/admin/deploy/pstpage.jar > /dev/null 2>&1 &
java -jar /home/admin/deploy/pstpage.jar