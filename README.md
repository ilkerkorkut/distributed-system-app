## Project Goal
The application should coordinate between the nodes and when clusters started it must be print once as "started".

Some nodes start at exact time with others, some nodes may restart, and some nodes may join second or minutes later or some nodes never start.

So decided to implement very very simple like raft consensus algorithm implementation. 

#### Tech Info
- gRPC is used for communication between nodes.

### Prerequisites
- Java 8
- Maven

### Compile project
```
$ cd distributed-system-app
$ mvn clean install
```

### Starting Application Instructions
Enter the project folder.

```
$ cd distributed-system-app
```

Example of three node command list per terminal window.
```
$ java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 8091 8092
$ java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8091 8090 8092
$ java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8092 8091 8090
```

Above command's arguments in order of; first port is current application's port. And rest of the other ports are list of other nodes' ports.

Also in projects folder there is a bash script `run_parallel.sh` it runs applications at the same time. 

#### Start 3 nodes at the same time
```
sh run_parallel.sh "java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 8091 8092" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8091 8090 8092" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8092 8091 8090"
```

#### Start 4 nodes at the same time
```
sh run_parallel.sh "java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 8091 8092 8093" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8091 8090 8092 8093" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8092 8091 8090 8093" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8093 8091 8092 8090"
```

#### Start 5 nodes at the same time
```
sh run_parallel.sh "java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 8091 8092 8093 8094" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8091 8090 8092 8093 8094" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8092 8091 8090 8093 8094" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8093 8091 8092 8090 8094" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8094 8091 8092 8090 8093"
```

#### Start 6 nodes at the same time
```
sh run_parallel.sh "java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 8091 8092 8093 8094 8095" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8091 8090 8092 8093 8094 8095" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8092 8091 8090 8093 8094 8095" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8093 8091 8090 8092 8094 8095" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8094 8091 8090 8092 8093 8095" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8095 8091 8090 8092 8093 8094"
```

#### Start 7 nodes at the same time
```
sh run_parallel.sh "java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 8091 8092 8093 8094 8095 8096" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8091 8090 8092 8093 8094 8095 8096" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8092 8091 8090 8093 8094 8095 8096" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8093 8091 8090 8092 8094 8095 8096" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8094 8091 8090 8092 8093 8095 8096" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8095 8091 8090 8092 8093 8094 8096" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8096 8091 8090 8092 8093 8094 8095"
```

#### Start 8 nodes at the same time
```
sh run_parallel.sh "java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 8091 8092 8093 8094 8095 8096 8097" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8091 8090 8092 8093 8094 8095 8096 8097" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8092 8091 8090 8093 8094 8095 8096 8097" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8093 8091 8090 8092 8094 8095 8096 8097" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8094 8091 8090 8092 8093 8095 8096 8097" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8095 8091 8090 8092 8093 8094 8096 8097" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8096 8091 8090 8092 8093 8094 8095 8097" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8097 8091 8090 8092 8093 8094 8095 8096"
```

#### Start 9 nodes at the same time
```
sh run_parallel.sh "java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 8091 8092 8093 8094 8095 8096 8097 8098" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8091 8090 8092 8093 8094 8095 8096 8097 8098" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8092 8091 8090 8093 8094 8095 8096 8097 8098" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8093 8091 8090 8092 8094 8095 8096 8097 8098" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8094 8091 8090 8092 8093 8095 8096 8097 8098" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8095 8091 8090 8092 8093 8094 8096 8097 8098" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8096 8091 8090 8092 8093 8094 8095 8097 8098" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8097 8091 8090 8092 8093 8094 8095 8096 8098" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8098 8091 8090 8092 8093 8094 8095 8096 8097"
```

#### Start 10 nodes at the same time
```
sh run_parallel.sh "java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 8091 8092 8093 8094 8095 8096 8097 8098 8099" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8091 8090 8092 8093 8094 8095 8096 8097 8098 8099" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8092 8091 8090 8093 8094 8095 8096 8097 8098 8099" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8093 8091 8090 8092 8094 8095 8096 8097 8098 8099" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8094 8091 8090 8092 8093 8095 8096 8097 8098 8099" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8095 8091 8090 8092 8093 8094 8096 8097 8098 8099" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8096 8091 8090 8092 8093 8094 8095 8097 8098 8099" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8097 8091 8090 8092 8093 8094 8095 8096 8098 8099" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8098 8091 8090 8092 8093 8094 8095 8096 8097 8099" \
"java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8099 8091 8090 8092 8093 8094 8095 8096 8097 8098"
```

##### Single testing one by one start and stop
Three nodes:
```
$ java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 8091 8092
$ java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8091 8090 8092
$ java -jar ./target/distributed-system-app-1.0-SNAPSHOT-jar-with-dependencies.jar 8092 8091 8090
```