#!/bin/bash
#Crossing Server Bootstrap Script by SHC on 2016/06/03                                                  

OLDPWD=`pwd`
DIR="$( cd "$( dirname "$0"  )" && pwd  )"
NEWPWD=`dirname $DIR`
cd $NEWPWD

SERVER_HOME=$NEWPWD;
SERVER_NAME=`basename $SERVER_HOME`
SERVER_OPT="-DServerName=$SERVER_NAME"
SERVER_CLASSPATH="";
#export SERVER_MEM_OPTS="-Xms512m -Xmx512m -Xmn128m";
SERVER_PIDFILE=crossing.pid


function startup() {
	#Setup the JVM
	if [ "x$JAVA" = "x" ]; then
		if [ "x$JAVA_HOME" != "x" ]; then
			JAVA="$JAVA_HOME/bin/java"
		else
			JAVA="java"
		fi
	fi
	export JAVA
	
	#Setup Java Opts
	if [ "x$SERVER_MEM_OPTS" = "x" ]; then
		JAVA_MEM_OPTS=" -Xmx512m -Xms512m -Xmn256m"
		else
		JAVA_MEM_OPTS=$SERVER_MEM_OPTS
	fi
	JAVA_OPTS=$JAVA_MEM_OPTS" -Xss256k -Xloggc:log/gc.log "
	GC_OPTS="-XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:+CMSIncrementalPacing\
	 -XX:CMSIncrementalDutyCycleMin=0\
	 -XX:CMSIncrementalDutyCycle=10\
	 -XX:+PrintGCTimeStamps\
	 -XX:-TraceClassUnloading"
	JAVA_OPTS=$JAVA_OPTS" "$GC_OPTS;
	JAVA_OPTS=$JAVA_OPTS" "$SERVER_OPT;
	
	#Setup class path
	LIB_CLASSPATH=""
	for file in *.jar;do
		LIB_CLASSPATH=$LIB_CLASSPATH:$file
	done
	for file in lib/*.jar;do
		LIB_CLASSPATH=$LIB_CLASSPATH:$file
	done
	for file in lib/newsharp/*.jar;do
		LIB_CLASSPATH=$LIB_CLASSPATH:$file
	done
	for file in lib/mifty/*.jar;do
		LIB_CLASSPATH=$LIB_CLASSPATH:$file
	done
	
	#Display runtime environment
	echo "====Crossing Runtime Environment===="
	echo "SERVER_HOME: 		$SERVER_HOME"
	echo "SERVER_NAME:		$SERVER_NAME"
	echo "JAVA: 			$JAVA"
	echo "JAVA_HOME: 		$JAVA_HOME"
	echo "JAVA_OPTS: 		$JAVA_OPTS"
	echo "CLASSPATH: 		$LIB_CLASSPATH"
	echo "HOSTNAME:			$HOSTNAME"
	echo "ARGS: 			$@"
	echo "====Crossing Runtime Environment===="
	
	# Execute the JVM in the background
	eval \"$JAVA\" $JAVA_OPTS \
		-classpath \"$LIB_CLASSPATH\" \
		-D \
		club.jmint.crossing.startup.Bootstrap "$@" "&"	
	SERVER_PID=$!
	echo $SERVER_PID > $SERVER_HOME/bin/$SERVER_PIDFILE
	
	# Trap common signals and relay them to the crossing process
	trap "kill -HUP  $SERVER_PID" HUP
	trap "kill -TERM $SERVER_PID" INT
	trap "kill -QUIT $SERVER_PID" QUIT
	trap "kill -PIPE $SERVER_PID" PIPE
	trap "kill -TERM $SERVER_PID" TERM
	
	# Wait until the background process exits
	WAIT_STATUS=128
	while [ "$WAIT_STATUS" -ge 128 ]; do
		wait $SERVER_PID 2>/dev/null
		WAIT_STATUS=$?
		if [ "$WAIT_STATUS" -gt 128 ]; then
			SIGNAL=`expr $WAIT_STATUS - 128`
			SIGNAL_NAME=`kill -l $SIGNAL`
		fi
	done
}

function shutdown() {
	SERVER_PID=`cat $SERVER_HOME/bin/$SERVER_PIDFILE`
	echo "try to stop crossing server[pid:$SERVER_PID]..."
	
	##send INT and TERM signal to crossing process
	kill -2 $SERVER_PID
	kill -15 $SERVER_PID
	echo "crossing server stopped[pid:$SERVER_PID]."
	rm -fr $SERVER_HOME/bin/$SERVER_PIDFILE
}

case "$1" in
    "start")
        startup
        ;;

    "stop")
        shutdown
        ;;

    *)
        #nothing
        ;;
esac
cd $OLDPWD
exit 0

