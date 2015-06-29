#!/bin/sh
SERVICE_NAME=ssecore
CORE_PATH=/opt/ssecore/
JAR_NAME=SSECoreServer.jar
PID_PATH_NAME=/tmp/ssecore-pid
case $1 in
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -f $PID_PATH_NAME ]; then
            nohup java -Dcore.path=$CORE_PATH -jar $CORE_PATH$JAR_NAME /tmp 2>> /dev/null >> /dev/null &
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is already running ..."
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stopping ..."
            kill $PID;
            echo "$SERVICE_NAME stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stopping ...";
            kill $PID;
            echo "$SERVICE_NAME stopped ...";
            rm $PID_PATH_NAME
            echo "$SERVICE_NAME starting ..."
            nohup java -Dcore.path=$CORE_PATH -jar $CORE_PATH$JAR_NAME /tmp 2>> /dev/null >> /dev/null &
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
		status)
                if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
                        echo "$SERVICE_NAME is running (pid $PID)."
                else
                        echo "$SERVICE_NAME is NOT running."
                fi
        ;;


esac
