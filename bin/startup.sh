#!/bin/bash
#This script invoke crossing.sh to start Crossing Server 
#Written by SHC on 2016/06/03

if [[ $1 == "--help" ]] 
then
	echo "usage:"
	echo "startup.sh		start crossing server"
	exit 1;
fi

DIR="$( cd "$( dirname "$0"  )" && pwd  )"
SERVER_HOME=`dirname $DIR`

#$SERVER_HOME/bin/crossing.sh start "$@"
eval "$SERVER_HOME/bin/crossing.sh" start "$@" > /dev/null 2>&1 & 
exit 0
