package br.com.alfser.projeto.pagamentos.services;

import br.com.alfser.projeto.pagamentos.common.BrokerTopic;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoUpdateStatusDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RokerConsumerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final PagamentoService pagamentoService;

    public RokerConsumerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, PagamentoService pagamentoService) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.pagamentoService = pagamentoService;
    }

    @KafkaListener(topics = BrokerTopic.PAGAMENTO_STATUS)
    public void processMessage(String json) throws JsonProcessingException {
        PagamentoUpdateStatusDTO dto = objectMapper.readValue(json, PagamentoUpdateStatusDTO.class);

        pagamentoService.atualizarStatusPagamento(dto.getIdPagamento(), dto.getStatus());
        System.out.println("CONSUME MESSAGE: "+json);
        System.out.println("MESSAGE OBJECT: "+dto.toString());
    }
}
