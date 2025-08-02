import { Component } from '@angular/core';

type StatusPagamento = 'PENDENTE_PROCESSAMENTO' | 'PROCESSADO_SUCESSO' | 'PROCESSADO_FALHA'

interface Pagamento {
  identificacao: string;
  metodoPagamento: string;
  valor: number;
  status: StatusPagamento;
}

@Component({
  selector: 'app-lista-pagamento',
  imports: [],
  templateUrl: './lista-pagamento.html',
  styleUrl: './lista-pagamento.css'
})
export class ListaPagamento {
  searchTerm: string = '';
  itemsPerPage: number = 10;
  currentPage: number = 1;
  totalPages: number = 5;
  pagesLength: number[] = [5, 10, 20, 50]
  statusPagamentos : StatusPagamento[] = ["PENDENTE_PROCESSAMENTO", "PROCESSADO_SUCESSO", "PROCESSADO_FALHA"];
  padamentos: Pagamento[] = [
    {
      identificacao: '077.977.610-00',
      metodoPagamento: 'PIX',
      valor: 99.99,
      status: 'PENDENTE_PROCESSAMENTO'
    },
    {
      identificacao: '485.916.740-65',
      metodoPagamento: 'Cartão',
      valor: 88.88,
      status: 'PROCESSADO_SUCESSO'
    },
    {
      identificacao: '544.496.890-89',
      metodoPagamento: 'Boleto',
      valor: 77.77,
      status: 'PROCESSADO_FALHA'
    },
    {
      identificacao: '544.496.890-89',
      metodoPagamento: 'Boleto',
      valor: 77.77,
      status: 'PROCESSADO_FALHA'
    }
  ];

  getStatusClass(status: StatusPagamento): string {
    switch(status) {
      case 'PROCESSADO_SUCESSO':
        return 'bg-green-400 font-bold';
      case 'PROCESSADO_FALHA':
        return 'bg-red-400 font-bold';
      default:
        return 'bg-gray-400';
    }
  }

  getStatusText(status: StatusPagamento): string {
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
