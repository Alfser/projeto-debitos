package br.com.alfser.projeto.pagamentos.controllers;

import br.com.alfser.projeto.pagamentos.common.PageUtils;
import br.com.alfser.projeto.pagamentos.common.SinglePageResponse;
import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoCreateDTO;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoFilterDTO;
import br.com.alfser.projeto.pagamentos.dtos.UpdateStatusDTO;
import br.com.alfser.projeto.pagamentos.erros.InvalidPagamentoUpdateException;
import br.com.alfser.projeto.pagamentos.erros.PagamentoNotAvailableChangeException;
import br.com.alfser.projeto.pagamentos.erros.PagamentoNotFoundException;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import br.com.alfser.projeto.pagamentos.services.PagamentoService;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoDTO;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springdoc.core.converters.models.DefaultPageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class PagamentoControllers {
    private final PagamentoService pagamentoService;

    public PagamentoControllers(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping("/lista-pagamentos")
    @ResponseStatus(HttpStatus.OK)
    public SinglePageResponse<PagamentoDTO> listarPagamentos(
            @RequestParam(required = false) Long idPagamento,
            @RequestParam(required = false) String cpfCnpj,
            @RequestParam(required = false) StatusPagamento status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idPagamento,desc") String sort){
        Pageable pageable = PageRequest.of(page, size, PageUtils.parseSort(sort));
        Page<Pagamento> resultPage = pagamentoService.listar(
                new PagamentoFilterDTO(idPagamento, cpfCnpj, status),
                pageable
        );
        return new SinglePageResponse<>(
                resultPage.getContent().stream().map(PagamentoDTO::fromPagamentoModel).toList(),
                new SinglePageResponse.PaginationMetadata(resultPage)
        );
    }

    @PostMapping("/pagamentos")
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoDTO salvarPagamento(@Valid PagamentoCreateDTO pagamentoCreateDTO){
        var pagamentoCadastrado = pagamentoService.criar(pagamentoCreateDTO.toPagamentoModel());
        return PagamentoDTO.fromPagamentoModel(pagamentoCadastrado);
    }

    @DeleteMapping("/pagamentos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativarPagamento(@PathVariable String id){
        pagamentoService.desativarPagamento(new ObjectId(id));
    }

    @PutMapping("/pagamento/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarStatusPagamento(@Valid UpdateStatusDTO updateStatusDTO){
        pagamentoService.atualizarStatusPagamento(updateStatusDTO.getIdPagamento(), updateStatusDTO.getStatus());
    }
}
