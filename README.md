# Getting Started

1. Start docker
2. run kafka-compose-yml. This will start all the dependencies needed
3. Kafka UI manager(Redpanda) will start on 8080 port. Create topics and partition necessary.  
4. Run spring boot application. Runs on server port 8090
5. Start sending message via REST. For endpoints check [controller](src/main/java/com/example/kafka/controller/KafkaController.java)

### Topics Covered

1. Producing to Kafka stream(Specifying topic and parition)
2. Consuming from Kafka stream(Listinering to specific parition)
3. Filtering of consumer stream
3. Handling errors(Globally and Locally)
4. DLQ
5. RetryableTopic
6. Accessing Message Headers