echo "Waiting for Kafka to be ready..."
dockerize -wait tcp://broker:9092 -timeout 60s

echo "Creating Kafka topics..."
docker exec broker kafka-topics --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 2 --topic toSearch
