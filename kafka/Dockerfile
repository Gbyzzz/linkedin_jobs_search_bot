FROM confluentinc/cp-kafka:latest

COPY ./certs /etc/kafka/secrets

#ENTRYPOINT ["top", "-b"]
ENTRYPOINT ["/etc/confluent/docker/run"]