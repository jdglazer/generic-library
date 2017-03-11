#!/bin/bash

COLLECTOR_PID=$( ps aux | grep "jdglazer.dataflow.collector.Main" | grep -v grep | awk '{print $2}' )

function collectorStatus {
	
}

function stopCollector {
    /bin/kill -s TERM $COLLECTOR_PID
}
