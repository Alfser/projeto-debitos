package br.com.alfser.projeto.pagamentos.config;

import br.com.alfser.projeto.pagamentos.common.BrokerTopic;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessageBrokerConfig {

    @Bean
    public NewTopic pagamentoStatusTopic() {
        return TopicBuilder.name(BrokerTopic.PAGAMENTO_STATUS)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicPagamentoPendente() {
        return TopicBuilder.name(BrokerTopic.PAGAMENTO_PENDENTE)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
