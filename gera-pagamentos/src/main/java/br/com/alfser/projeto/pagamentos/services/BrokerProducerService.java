package br.com.alfser.projeto.pagamentos.services;

import br.com.alfser.projeto.pagamentos.common.BrokerTopic;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoUpdateStatusDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BrokerProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public BrokerProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(PagamentoUpdateStatusDTO dto) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(dto);
        System.out.println("PRODUCE MESSAGE: "+json);
        kafkaTemplate.send(BrokerTopic.PAGAMENTO_PENDENTE, json);
    }
}
