FROM ibmcom/ace-mq

ENV BAR1=AccountLocks_v2_0_log4j.bar
COPY  ./server.conf.yaml /home/aceuser/ace-server/
COPY  ./datasources.json /home/aceuser/ace-server/config/connectors/loopback/
COPY  $BAR1 /tmp

RUN bash -c 'cd /home/aceuser/ace-server/ && npm install loopback-connector-mongodb --save && mqsibar -w /home/aceuser/ace-server -a /tmp/$BAR1 -c'













