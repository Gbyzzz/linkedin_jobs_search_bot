package com.gbyzzz.jobscanner.config;

import com.gbyzzz.jobscanner.dto.SearchParamsTimeRangeDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${application.kafka.topic.to_check_if_new}")
    private String topicToCheckIfNew;

    @Bean
    public NewTopic toSearchTopic() {
        return TopicBuilder.name(topicToCheckIfNew)
                .partitions(1)
                .replicas(1)
                .build();
    }

    public Map<String, Object> kafkaConsumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "searchParamsTimeRangeDTO:com.gbyzzz.jobscanner.dto.SearchParamsTimeRangeDTO");
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.gbyzzz.jobscanner.dto.SearchParamsTimeRangeDTO");
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.gbyzzz.telegramclient.dto");
        return props;
    }

    @Bean
    public ConsumerFactory<String, SearchParamsTimeRangeDTO> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaConsumerConfig(),
                new StringDeserializer(), new JsonDeserializer<>(SearchParamsTimeRangeDTO.class, false));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SearchParamsTimeRangeDTO>>
    kafkaListenerContainerFactory(ConsumerFactory<String, SearchParamsTimeRangeDTO> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, SearchParamsTimeRangeDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }



    public Map<String, Object> kafkaProducerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS,
                        "searchParamsTimeRangeDTO:com.gbyzzz.jobscanner.dto.SearchParamsTimeRangeDTO");
        return props;
    }

    @Bean
    public ProducerFactory<String, SearchParamsTimeRangeDTO> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, SearchParamsTimeRangeDTO> kafkaTemplate(
            ProducerFactory<String, SearchParamsTimeRangeDTO> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
