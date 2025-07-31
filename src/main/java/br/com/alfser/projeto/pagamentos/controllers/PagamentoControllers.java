package br.com.alfser.projeto.pagamentos.controllers;

import br.com.alfser.projeto.pagamentos.dtos.PagamentoCreateDTO;
import br.com.alfser.projeto.pagamentos.services.PagamentoService;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PagamentoControllers {
    private final PagamentoService pagamentoService;

    public PagamentoControllers(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping("/lista-pagamentos")
    @ResponseStatus(HttpStatus.OK)
    public List<PagamentoDTO> listarPagamentos(){
        return pagamentoService.listar()
                .stream()
                .map(PagamentoDTO::fromPagamentoModel)
                .toList();
    }

    @PostMapping("/pagamentos")
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoDTO salvarPagamento(@Valid PagamentoCreateDTO pagamentoCreateDTO){
        var pagamentoCadastrado = pagamentoService.criar(pagamentoCreateDTO.toPagamentoModel());
        return PagamentoDTO.fromPagamentoModel(pagamentoCadastrado);
    }
}
