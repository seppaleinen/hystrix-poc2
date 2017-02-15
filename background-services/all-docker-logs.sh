#!/usr/bin/env bash

echo "Press [ENTER] to see log output from all running Docker containers"
echo -n "(press [ENTER] a second time to end the script): "
read input

count=0
for i in $( docker ps -q ); do
  count=$(($count + 1))
  docker logs -f --tail=0 $i &
done
echo ""
echo "Started \"docker logs -f --tail=0\" on $count Docker containers, waiting for output..."
echo ""
read input 

echo "Stop all docker logs commands..."
kill $(jobs -p)

echo "Bye!"
