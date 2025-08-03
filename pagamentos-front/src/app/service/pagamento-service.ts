import { Injectable } from '@angular/core';
import { ErrorResponse, PagamentoCreateDTO, PagamentoDTO } from '../../lib/clients/gera-pagamento/GeraPagamentoApi';
import {PagamentoApiService} from "../../lib/clients/services"
import { Observable } from 'rxjs';
import { PagamentoListParams, ServiceResponsePagination } from '../../lib/clients/services/types';
@Injectable({
  providedIn: 'root'
})
export class PagamentoService {
  
  constructor() {}

  listar(params: PagamentoListParams): Observable<ServiceResponsePagination<PagamentoDTO, ErrorResponse>> {
    return new Observable(observer => {
      PagamentoApiService.pagamentoList(params)
        .then(response => {
          if(response.success){
            observer.next(response);
            observer.complete();
          }else{
            observer.error(this.handleError(response.error))
          }
        })
        .catch(error => {
          observer.error(this.handleError(error));
        });
    });
  }

  adicionar(pagamento: PagamentoCreateDTO): Observable<PagamentoDTO> {
    return new Observable(observer => {
      PagamentoApiService.salvarPagamento(pagamento)
        .then(response => {
          if(response.success){
            observer.next(response.result as PagamentoDTO);
            observer.complete();
          }else{
            observer.error(this.handleError(response.error))
          }
        })
        .catch(error => {
          observer.error(this.handleError(error));
        });
    });
  }

  inativar(id: string): Observable<void> {
    return new Observable(observer => {
      PagamentoApiService.desativarPagamento(id)
        .then((response) => {
          if(response.success){
            observer.next();
            observer.complete();
          }else{
            observer.error(this.handleError(response.error))
          }
        })
        .catch(error => {
          observer.error(this.handleError(error));
        });
    });
  }

  processar(idPagamento: number): Observable<void> {
    return new Observable(observer => {
      PagamentoApiService.processarPagamento(idPagamento)
        .then(response => {
          if(response.success){
            observer.next();
            observer.complete();
          }else{
            observer.error(this.handleError(response.error))
          }
        })
        .catch(error => {
          observer.error(this.handleError(error));
        });
    });
  }

  private handleError(error?: ErrorResponse): ErrorResponse {
    if (error && error.error && error.details) {
      return {
        timestamp: error.timestamp,
        status:  error.status|| 500,
        error: error.error || 'Unknown Error',
        message: error.message || 'An error occurred',
        path: error.path || 'unknown',
        details: error.details || []
      };
    }

    // Handle generic errors
    return {
      timestamp: new Date().toISOString(),
      status: 500,
      error: 'Internal Error',
      message: 'An unexpected error occurred',
      path: 'unknown',
      details: [String(error)]
    };
  }
}