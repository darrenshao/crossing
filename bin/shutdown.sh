#!/bin/bash
#This script invoke crossing.sh to stop Crossing Server 
#Written by SHC on 2016/06/03

DIR="$( cd "$( dirname "$0"  )" && pwd  )"
SERVER_HOME=`dirname $DIR`

$SERVER_HOME/bin/crossing.sh stop "$@"
exit 0

