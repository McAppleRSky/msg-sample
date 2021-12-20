docker daemon start:
sudo /etc/init.d/docker start

Apache ActiveMQ Artemis. Модель адресации включает три основных понятия: адреса, очереди и типы маршрутизации.
  В конфигурации типичному адресу дается уникальное имя, 0 или более очередей и тип маршрутизации.
Как только входящее сообщение сопоставлено с адресом, сообщение будет отправлено в одну или
несколько его очередей, в зависимости от настроенного типа маршрутизации. Очереди можно настроить
для автоматического создания и удаления.
Anycast - Одиночная очередь в пределах совпадающего адреса в point-to-point режиме;
Multicast - Каждая очередь в соответствующем адресе в режиме publish-subscribe.
  Anycast - message sent by a producer has only one consume. Example AMQP и JMS.
Когда сообщение получено на адрес с использованием anycast, Artemis находит очередь,
связанную с этим адресом, и направляет сообщение в нее. Когда потребители запрашивают
потребление с адреса, брокер находит соответствующую очередь и связывает эту очередь
с соответствующими потребителями. Если несколько потребителей подключены к одной и той же очереди,
сообщения распределяются между каждым потребителем одинаково, при условии, что потребители
в равной степени могут их обрабатывать.
  Configuring an Address to Use the Anycast Routing Type. Open the file /etc/broker.xml for editing.
<configuration ...>
<core ...>
...
<address name="orders">
<anycast>
  <queue name="orders"/>
</anycast></address></core></configuration>
|
  Publish-Subscribe Messaging отправляются каждому потребителю, подписанному на адрес.
Разделы JMS и подписки MQTT - два примера обмена сообщениями публикация-подписка. Чтобы настроить
адрес с семантикой Publish-Subscribe, создайте адрес с многоадресной маршрутизацией tyoe.
  Configuring an Address to Use the Multicast Routing Type. Open the file /etc/broker.xml
for editing.
  Add an address configuration element with multicast routing type.
<configuration ...>
<core ...>
...
<address name="pubsub.foo">
  <multicast/>
</address></core></configuration>
|
  Когда клиенты подключаются к адресу с элементом Multicast рассылки, для него автоматически
создается очередь подписки для клиента. Также можно предварительно настроить очереди подписки
и подключиться к ним напрямую, используя имена полностью квалифицированных очередей очереди.
  Добавьте к адресу еще один элемент очереди и оберните вокруг них элемент многоадресной рассылки.
Этот шаг обычно не требуется, поскольку брокер автоматически создает очередь для каждой подписки,
запрошенной клиентом. 
<configuration ...>
  <core ...>
    ...
    <address name="pubsub.foo">
      <multicast>
        <queue name="client123.pubsub.foo"/>
        <queue name="client456.pubsub.foo"/>
      </multicast></address></core></configration>
|
Point-to-Point Address multiple Queues
  Когда сообщения поступают на такой адрес, они сначала равномерно распределяются по всем
определенным очередям. Используя полностью определенные имена очередей, клиенты могут выбрать очередь,
на которую они хотят подписаться. Если несколько потребителей подключаются напрямую к одной очереди,
Artemis позаботится о распределении сообщений между ними, как в примере выше. так Artemis обрабатывает
балансировку нагрузки очередей между несколькими узлами в кластере.
  Настройка Point-to-Point с двумя очередями Откройте файл /etc/broker.xml для редактирования.
Добавьте конфигурацию адреса с элементом типа маршрутизации Anycast и связанными с ним очередями.
<configuration ...>
  <core ...>
    ...
    <address name="address.foo">
      <anycast>
        <queue name="q1"/>
        <queue name="q2"/>
      </anycast>
    </address></core></configuration>
Point-to-Point and Publish-Subscribe Addresses
  https://activemq.apache.org/components/artemis/documentation/2.0.0/address-model.html
Queue Filter
    <address name="filter">
       <queue name="filter">
          <filter string="color='red'"/>
        </queue></address>
Define an address with a single queue, with no filter applied.
    <address name="filter">
       <queue name="filter">
       </queue>
    </address>
Send some messages
  for (int i = 0; i < 3; i ++) {
    TextMessage redMessage = senderSession.createTextMessage("Red");
    redMessage.setStringProperty("color", "red");
    producer.send(redMessage)
    TextMessage greenMessage = senderSession.createTextMessage("Green");
    greenMessage.setStringProperty("color", "green");
    producer.send(greenMessage)
  }
|
At this point the queue would have 6 messages: red,green,red,green,red,green
  MessageConsumer redConsumer = redSession.createConsumer(queue, "color='red'");

<configuration ...>
  <core ...>
    ...
    <address-settings>
      <address-setting match="/news/politics/#">
        <auto-create-addresses>true</auto-create-addresses>
        <default-address-routing-type>MULTICAST</default-address-routing-type>
      </address-setting></address-settings>
    ...</core></configuration>
|
  Configuring an Anycast Prefix
<acceptor name="artemis">tcp://0.0.0.0:61616?protocols=AMQP;anycastPrefix=anycast://</acceptor>
  Multicast Prefix
<acceptor name="artemis">tcp://0.0.0.0:61616?protocols=AMQP;multicastPrefix=multicast://</acceptor>
  Shared, Durable Subscription Queue using max-consumers
Static Subscription Queues
  Open the file <broker-instance>/etc/broker.xml for editing.
Shared, Durable Subscription Queue using max-consumers
<addresses>
   <address name="durable.foo">
      <multicast>
         <!-- pre-configured shared durable subscription queue -->
         <queue name="q1" max-consumers="10">
            <durable>true</durable>
         </queue>
      </multicast></address></addresses>
Non-shared, Durable Subscription Queue
<addresses>
   <address name="durable.foo">
      <multicast>
         <!-- pre-configured non shared durable subscription queue -->
         <queue name="q1" max-consumers="1">
            <durable>true</durable>
         </queue>
      </multicast></address></addresses>
Non-durable Subscription Queue
<addresses>
   <address name="non.shared.durable.foo">
      <multicast>
         <queue name="orders1" purge-on-no-consumers="true"/>
      </multicast></address></addresses>
Exclusive Consumer Queue
<addresses>
   <address name="foo.bar">
      <multicast>
         <queue name="orders1" exclusive="true"/>
      </multicast></address></addresses>
Disabled Queue
<addresses>
   <address name="foo.bar">
      <multicast>
         <queue name="orders1" enabled="false"/>
      </multicast></address></addresses>
Temporary Queues
Для некоторых протоколов и API, которые поддерживают только монолитные «места назначения»
без разделения адреса / очереди (например, AMQP, JMS и т. Д.), Временные очереди создаются брокером
с использованием UUID...




Add an address configuration element and its associated queue if they do not exist already.
ls /var/lib/broker-instance/etc && cat /var/lib/broker-instance/etc/artemis.profile
artemis-roles.properties
artemis-users.properties
artemis.profile
bootstrap.xml
broker.xml
jolokia-access.xml
logging.properties
login.config
management.xml

# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.

ARTEMIS_HOME='/opt/jms/apache-artemis-2.19.0'
ARTEMIS_INSTANCE='/var/lib/broker-instance'
ARTEMIS_DATA_DIR='/var/lib/broker-instance/data'
ARTEMIS_ETC_DIR='/var/lib/broker-instance/etc'
ARTEMIS_OOME_DUMP='/var/lib/broker-instance/log/oom_dump.hprof'

# The logging config will need an URI
# this will be encoded in case you use spaces or special characters
# on your directory structure
ARTEMIS_INSTANCE_URI='file:/var/lib/broker-instance/'
ARTEMIS_INSTANCE_ETC_URI='file:/var/lib/broker-instance/etc/'

# Cluster Properties: Used to pass arguments to ActiveMQ Artemis which can be referenced in broker.xml
#ARTEMIS_CLUSTER_PROPS="-Dactivemq.remoting.default.port=61617 -Dactivemq.remoting.amqp.port=5673 -Dactivemq.remoting.stomp.port=61614 -Dactivemq.remoting.hornetq.port=5446"


# Hawtio Properties
HAWTIO_ROLE='amq'


# Java Opts
if [ -z "$JAVA_ARGS" ]; then
JAVA_ARGS="-XX:+PrintClassHistogram -XX:+UseG1GC -XX:+UseStringDeduplication -Xms512M -Xmx2G -Dhawtio.disableProxy=true -Dhawtio.realm=activemq -Dhawtio.offline=true -Dhawtio.rolePrincipalClasses=org.apache.activemq.artemis.spi.core.security.jaas.RolePrincipal -Djolokia.policyLocation=${ARTEMIS_INSTANCE_ETC_URI}jolokia-access.xml "
fi

#
# Logs Safepoints JVM pauses: Uncomment to enable them
# In addition to the traditional GC logs you could enable some JVM flags to know any meaningful and "hidden" pause that could
# affect the latencies of the services delivered by the broker, including those that are not reported by the classic GC logs
# and dependent by JVM background work (eg method deoptimizations, lock unbiasing, JNI, counted loops and obviously GC activity).
# Replace "all_pauses.log" with the file name you want to log to.
# JAVA_ARGS="$JAVA_ARGS -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1 -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime -XX:+LogVMOutput -XX:LogFile=all_pauses.log"

#
# Enables the dumping of the java heap when a java.lang.OutOfMemoryError exception is thrown.
# JAVA_ARGS="$JAVA_ARGS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${ARTEMIS_OOME_DUMP}"

# Debug args: Uncomment to enable debug
#DEBUG_ARGS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"

# Debug args: Uncomment for async profiler
#DEBUG_ARGS="-XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints"


<?xml version='1.0'?>
<!--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

<configuration xmlns="urn:activemq"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:xi="http://www.w3.org/2001/XInclude"
xsi:schemaLocation="urn:activemq /schema/artemis-configuration.xsd">

<core xmlns="urn:activemq:core" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="urn:activemq:core ">

      <name>0.0.0.0</name>


      <persistence-enabled>true</persistence-enabled>

      <!-- this could be ASYNCIO, MAPPED, NIO
           ASYNCIO: Linux Libaio
           MAPPED: mmap files
           NIO: Plain Java Files
       -->
      <journal-type>NIO</journal-type>

      <paging-directory>data/paging</paging-directory>

      <bindings-directory>data/bindings</bindings-directory>

      <journal-directory>data/journal</journal-directory>

      <large-messages-directory>data/large-messages</large-messages-directory>

      
      <!-- if you want to retain your journal uncomment this following configuration.

      This will allow your system to keep 7 days of your data, up to 10G. Tweak it accordingly to your use case and capacity.

      it is recommended to use a separate storage unit from the journal for performance considerations.

      <journal-retention-directory period="7" unit="DAYS" storage-limit="10G">data/retention</journal-retention-directory>

      You can also enable retention by using the argument journal-retention on the `artemis create` command -->



      <journal-datasync>true</journal-datasync>

      <journal-min-files>2</journal-min-files>

      <journal-pool-files>10</journal-pool-files>

      <journal-device-block-size>4096</journal-device-block-size>

      <journal-file-size>10M</journal-file-size>
      
      <!--
       This value was determined through a calculation.
       Your system could perform 0.71 writes per millisecond
       on the current journal configuration.
       That translates as a sync write every 1408000 nanoseconds.

       Note: If you specify 0 the system will perform writes directly to the disk.
             We recommend this to be 0 if you are using journalType=MAPPED and journal-datasync=false.
      -->
      <journal-buffer-timeout>1408000</journal-buffer-timeout>


      <!--
        When using ASYNCIO, this will determine the writing queue depth for libaio.
       -->
      <journal-max-io>1</journal-max-io>
      <!--
        You can verify the network health of a particular NIC by specifying the <network-check-NIC> element.
         <network-check-NIC>theNicName</network-check-NIC>
        -->

      <!--
        Use this to use an HTTP server to validate the network
         <network-check-URL-list>http://www.apache.org</network-check-URL-list> -->

      <!-- <network-check-period>10000</network-check-period> -->
      <!-- <network-check-timeout>1000</network-check-timeout> -->

      <!-- this is a comma separated list, no spaces, just DNS or IPs
           it should accept IPV6

           Warning: Make sure you understand your network topology as this is meant to validate if your network is valid.
                    Using IPs that could eventually disappear or be partially visible may defeat the purpose.
                    You can use a list of multiple IPs, and if any successful ping will make the server OK to continue running -->
      <!-- <network-check-list>10.0.0.1</network-check-list> -->

      <!-- use this to customize the ping used for ipv4 addresses -->
      <!-- <network-check-ping-command>ping -c 1 -t %d %s</network-check-ping-command> -->

      <!-- use this to customize the ping used for ipv6 addresses -->
      <!-- <network-check-ping6-command>ping6 -c 1 %2$s</network-check-ping6-command> -->




      <!-- how often we are looking for how many bytes are being used on the disk in ms -->
      <disk-scan-period>5000</disk-scan-period>

      <!-- once the disk hits this limit the system will block, or close the connection in certain protocols
           that won't support flow control. -->
      <max-disk-usage>90</max-disk-usage>

      <!-- should the broker detect dead locks and other issues -->
      <critical-analyzer>true</critical-analyzer>

      <critical-analyzer-timeout>120000</critical-analyzer-timeout>

      <critical-analyzer-check-period>60000</critical-analyzer-check-period>

      <critical-analyzer-policy>HALT</critical-analyzer-policy>

      
      <page-sync-timeout>1408000</page-sync-timeout>


            <!-- the system will enter into page mode once you hit this limit.
           This is an estimate in bytes of how much the messages are using in memory

            The system will use half of the available memory (-Xmx) by default for the global-max-size.
            You may specify a different value here if you need to customize it to your needs.

            <global-max-size>100Mb</global-max-size>

      -->

      <acceptors>

         <!-- useEpoll means: it will use Netty epoll if you are on a system (Linux) that supports it -->
         <!-- amqpCredits: The number of credits sent to AMQP producers -->
         <!-- amqpLowCredits: The server will send the # credits specified at amqpCredits at this low mark -->
         <!-- amqpDuplicateDetection: If you are not using duplicate detection, set this to false
                                      as duplicate detection requires applicationProperties to be parsed on the server. -->
         <!-- amqpMinLargeMessageSize: Determines how many bytes are considered large, so we start using files to hold their data.
                                       default: 102400, -1 would mean to disable large mesasge control -->

         <!-- Note: If an acceptor needs to be compatible with HornetQ and/or Artemis 1.x clients add
                    "anycastPrefix=jms.queue.;multicastPrefix=jms.topic." to the acceptor url.
                    See https://issues.apache.org/jira/browse/ARTEMIS-1644 for more information. -->


         <!-- Acceptor for every supported protocol -->
         <acceptor name="artemis">tcp://0.0.0.0:61616?tcpSendBufferSize=1048576;tcpReceiveBufferSize=1048576;amqpMinLargeMessageSize=102400;protocols=CORE,AMQP,STOMP,HORNETQ,MQTT,OPENWIRE;useEpoll=true;amqpCredits=1000;amqpLowCredits=300;amqpDuplicateDetection=true;supportAdvisory=false;suppressInternalManagementObjects=false</acceptor>

         <!-- AMQP Acceptor.  Listens on default AMQP port for AMQP traffic.-->
         <acceptor name="amqp">tcp://0.0.0.0:5672?tcpSendBufferSize=1048576;tcpReceiveBufferSize=1048576;protocols=AMQP;useEpoll=true;amqpCredits=1000;amqpLowCredits=300;amqpMinLargeMessageSize=102400;amqpDuplicateDetection=true</acceptor>

         <!-- STOMP Acceptor. -->
         <acceptor name="stomp">tcp://0.0.0.0:61613?tcpSendBufferSize=1048576;tcpReceiveBufferSize=1048576;protocols=STOMP;useEpoll=true</acceptor>

         <!-- HornetQ Compatibility Acceptor.  Enables HornetQ Core and STOMP for legacy HornetQ clients. -->
         <acceptor name="hornetq">tcp://0.0.0.0:5445?anycastPrefix=jms.queue.;multicastPrefix=jms.topic.;protocols=HORNETQ,STOMP;useEpoll=true</acceptor>

         <!-- MQTT Acceptor -->
         <acceptor name="mqtt">tcp://0.0.0.0:1883?tcpSendBufferSize=1048576;tcpReceiveBufferSize=1048576;protocols=MQTT;useEpoll=true</acceptor>

      </acceptors>


      <security-settings>
         <security-setting match="#">
            <permission type="createNonDurableQueue" roles="amq"/>
            <permission type="deleteNonDurableQueue" roles="amq"/>
            <permission type="createDurableQueue" roles="amq"/>
            <permission type="deleteDurableQueue" roles="amq"/>
            <permission type="createAddress" roles="amq"/>
            <permission type="deleteAddress" roles="amq"/>
            <permission type="consume" roles="amq"/>
            <permission type="browse" roles="amq"/>
            <permission type="send" roles="amq"/>
            <!-- we need this otherwise ./artemis data imp wouldn't work -->
            <permission type="manage" roles="amq"/>
         </security-setting>
      </security-settings>

      <address-settings>
         <!-- if you define auto-create on certain queues, management has to be auto-create -->
         <address-setting match="activemq.management#">
            <dead-letter-address>DLQ</dead-letter-address>
            <expiry-address>ExpiryQueue</expiry-address>
            <redelivery-delay>0</redelivery-delay>
            <!-- with -1 only the global-max-size is in use for limiting -->
            <max-size-bytes>-1</max-size-bytes>
            <message-counter-history-day-limit>10</message-counter-history-day-limit>
            <address-full-policy>PAGE</address-full-policy>
            <auto-create-queues>true</auto-create-queues>
            <auto-create-addresses>true</auto-create-addresses>
            <auto-create-jms-queues>true</auto-create-jms-queues>
            <auto-create-jms-topics>true</auto-create-jms-topics>
         </address-setting>
         <!--default for catch all-->
         <address-setting match="#">
            <dead-letter-address>DLQ</dead-letter-address>
            <expiry-address>ExpiryQueue</expiry-address>
            <redelivery-delay>0</redelivery-delay>
            <!-- with -1 only the global-max-size is in use for limiting -->
            <max-size-bytes>-1</max-size-bytes>
            <message-counter-history-day-limit>10</message-counter-history-day-limit>
            <address-full-policy>PAGE</address-full-policy>
            <auto-create-queues>true</auto-create-queues>
            <auto-create-addresses>true</auto-create-addresses>
            <auto-create-jms-queues>true</auto-create-jms-queues>
            <auto-create-jms-topics>true</auto-create-jms-topics>
            <auto-delete-queues>false</auto-delete-queues>
            <auto-delete-addresses>false</auto-delete-addresses>
         </address-setting>
      </address-settings>

      <addresses>
         <address name="DLQ">
            <anycast>
               <queue name="DLQ" />
            </anycast>
         </address>
         <address name="ExpiryQueue">
            <anycast>
               <queue name="ExpiryQueue" />
            </anycast>
         </address>

      </addresses>


      <!-- Uncomment the following if you want to use the Standard LoggingActiveMQServerPlugin pluging to log in events
      <broker-plugins>
         <broker-plugin class-name="org.apache.activemq.artemis.core.server.plugin.impl.LoggingActiveMQServerPlugin">
            <property key="LOG_ALL_EVENTS" value="true"/>
            <property key="LOG_CONNECTION_EVENTS" value="true"/>
            <property key="LOG_SESSION_EVENTS" value="true"/>
            <property key="LOG_CONSUMER_EVENTS" value="true"/>
            <property key="LOG_DELIVERING_EVENTS" value="true"/>
            <property key="LOG_SENDING_EVENTS" value="true"/>
            <property key="LOG_INTERNAL_EVENTS" value="true"/>
         </broker-plugin>
      </broker-plugins>
      -->

   </core>
</configuration>

@LogMessage(level = Logger.Level.WARN)
@Message(id = 222212, value = "Disk Full! Blocking message production on address ''{0}''. Clients will report blocked.", format = Message.Format.MESSAGE_FORMAT)
void blockingDiskFull(SimpleString addressName);