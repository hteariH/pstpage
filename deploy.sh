#!/bin/bash

APP_JAR="/home/admin/deploy/pstpage.jar"
PID_FILE="app.pid"

# Check if the app is already running and stop it
if [ -f "$PID_FILE" ]; then
  echo "Stopping existing application instance..."
  PID=$(cat "$PID_FILE")
  kill "$PID"
  rm "$PID_FILE"
  sleep 2   # Add a small delay to allow the process to terminate
fi

# Copy the new JAR file to the desired location
echo "copy application..."
cp ./target/pstpage-0.0.1-SNAPSHOT.jar /home/admin/deploy/pstpage.jar

# Start the application in the background
echo "Starting application..."
nohup java -jar "$APP_JAR" > /dev/null 2>&1 &

#nohup java -jar /home/admin/deploy/pstpage.jar > /dev/null 2>&1 &
# Write the process id to a file
echo $! > "$PID_FILE"

disown

echo "Application started with PID: $(cat "$PID_FILE")"