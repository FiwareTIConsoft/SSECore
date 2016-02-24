#!/bin/bash
set -e
CORE_PATH=/opt/ssecore/
JAR_NAME=SSECoreServer.jar

echo "Starting sse.. on $CORE_PATH$JAR_NAME"
exec java -Dcore.path=$CORE_PATH -jar "$CORE_PATH$JAR_NAME"
