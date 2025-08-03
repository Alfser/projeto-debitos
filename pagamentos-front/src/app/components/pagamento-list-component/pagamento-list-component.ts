import { Component, OnInit } from '@angular/core';
import { PagamentoService } from '../../service/pagamento-service';
import { PagamentoDTO } from '../../../lib/clients/gera-pagamento/GeraPagamentoApi';
import { tap } from 'rxjs';

type StatusPagamento = 'PENDENTE_PROCESSAMENTO' | 'PROCESSADO_SUCESSO' | 'PROCESSADO_FALHA'

@Component({
  selector: 'pagamento-list-component',
  imports: [],
  templateUrl: './pagamento-list-component.html',
  styleUrl: './pagamento-list-component.css'
})
export class PagamentoListComponent implements OnInit {
  searchTerm: string = '';
  itemsPerPage: number = 10;
  currentPage: number = 1;
  totalPages: number = 5;
  pagesLength: number[] = [5, 10, 20, 50]
  statusPagamentos : StatusPagamento[] = ["PENDENTE_PROCESSAMENTO", "PROCESSADO_SUCESSO", "PROCESSADO_FALHA"];

  pagamentos: PagamentoDTO[] = []
  loading = false;
  error: any = null;

  constructor(private pagamentoService: PagamentoService){}
  
  ngOnInit(): void {
    this.carregarPagamentos()
  }
  
  carregarPagamentos(): void {
    this.loading = true;
    this.error = null;

    this.pagamentoService.listar({})
    .subscribe({
        next: (response) => {
          this.pagamentos = response;
          this.loading = false;
        },
        error: (err) => {
          this.error = err;
          this.loading = false;
        },
        complete: () => console.log('Request completed')
      });
  }

  processarPagamento(idPagamento: number): void {
    this.pagamentoService.processar(idPagamento)
      .subscribe({
        next: () => {
          const index = this.pagamentos.findIndex(p => p.idPagamento === idPagamento);
          if (index !== -1) {
            console.log("Pagamento processado: ", this.pagamentos[index])
          }
        },
        error: (err) => {
          this.error = err;
        }
      });
  }

  getStatusClass(status?: StatusPagamento): string {
    switch(status) {
      case 'PROCESSADO_SUCESSO':
        return 'bg-green-400 font-bold';
      case 'PROCESSADO_FALHA':
        return 'bg-red-400 font-bold';
      default:
        return 'bg-gray-400';
    }
  }

  getStatusText(status?: StatusPagamento): string {
    switch(status) {
      case 'PROCESSADO_SUCESSO':
        return 'Processado com sucesso';
      case 'PROCESSADO_FALHA':
        return 'Processado com falha';
      default:
        return 'Pendente processamento';
    }
  }

  handleSelectPageSize(pageSize: string){
    console.log("Pagina Selecionada:", Number(pageSize))
  }

  handleSelectStatus(pageSize: string){
    console.log("Pagina Selecionada:", Number(pageSize))
  }
  nextPage() {
    throw new Error('Method not implemented.');
  }
  goToPage(page: number) {
    throw new Error('Method not implemented.');
  }
  getPageNumbers(): number[] {
    throw new Error('Method not implemented.');
  }
  previousPage() {
    throw new Error('Method not implemented.');
  }
}
