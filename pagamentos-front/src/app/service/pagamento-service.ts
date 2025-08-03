import { Injectable } from '@angular/core';
import { PagamentoCreateDTO, PagamentoDTO } from '../../lib/clients/gera-pagamento/GeraPagamentoApi';
import {PagamentoApiService} from "../../lib/clients/services"
@Injectable({
  providedIn: 'root'
})
export class PagamentoService {
    async listar(){
      return await PagamentoApiService.pagamentoList()
    }
    async adicionar(pagamento: PagamentoCreateDTO){
      return await PagamentoApiService.salvarPagamento(pagamento)
    }
    async inativar(id: string){
      return await PagamentoApiService.desativarPagamento(id)
    }
    async processar(idPagamento: number){
      return await PagamentoApiService.processarPagamento(idPagamento)
    }
}
