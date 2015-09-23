#!/usr/bin/env bash

#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

if [ "$HIVEMALL_HOME" == "" ]; then
  echo env HIVEMALL_HOME not defined
  exit 1
fi

# Loads global variables
. "$HIVEMALL_HOME/conf/load-env.sh"

HIVEMALL_MIX_SERVERS="$HIVEMALL_HOME/conf/servers"
HIVEMALL_SSH_OPTS="-o StrictHostKeyChecking=no"

# Loads host entries from the servers file.
if [ -f "$HIVEMALL_MIX_SERVERS" ]; then
  HOSTLIST=`cat "$HIVEMALL_MIX_SERVERS"`
else
  HOSTLIST=localhost
fi

# Checks if the MIX servers running in remote hosts
for slave in `echo "$HOSTLIST" | sed  "s/#.*$//;/^$/d"`; do
  ssh $HIVEMALL_SSH_OPTS "$slave" "$HIVEMALL_HOME/sbin/mixserv-daemon.sh" status 2>&1 | sed "s/^/$slave: /" &
done

wait
