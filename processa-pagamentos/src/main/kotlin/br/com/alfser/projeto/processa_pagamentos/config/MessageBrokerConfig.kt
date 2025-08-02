package br.com.alfser.projeto.processa_pagamentos.config

import br.com.alfser.projeto.processa_pagamentos.common.BrokerTopic
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.*

@Configuration
class MessageBrokerConfig (
   @Value("\${spring.kafka.bootstrap-servers}")
   private val bootstrapServersConfig: String?,
){



    @Bean
    fun producerFactory(): ProducerFactory<String?, String?> {
        val configProps: MutableMap<String?, Any?> = HashMap<String?, Any?>()
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig)
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        return DefaultKafkaProducerFactory<String?, String?>(configProps)
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String?, String?> {
        val configProps: MutableMap<String?, Any?> = HashMap<String?, Any?>()
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig)
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-group")
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
        return DefaultKafkaConsumerFactory<String?, String?>(configProps)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String?, String?> {
        return KafkaTemplate<String?, String?>(producerFactory())
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String?, String?> {
        val factory = ConcurrentKafkaListenerContainerFactory<String?, String?>()
        factory.setConsumerFactory(consumerFactory())
        return factory
    }

    @Bean
    fun pagamentoStatusTopic(): NewTopic {
        return TopicBuilder.name(BrokerTopic.PAGAMENTO_STATUS)
            .partitions(3)
            .replicas(1)
            .build()
    }

    @Bean
    fun topicPagamentoPendente(): NewTopic {
        return TopicBuilder.name(BrokerTopic.PAGAMENTO_PENDENTE)
            .partitions(3)
            .replicas(1)
            .build()
    }
}