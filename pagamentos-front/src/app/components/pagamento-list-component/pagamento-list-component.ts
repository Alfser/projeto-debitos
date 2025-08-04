import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged, finalize, Subject, takeUntil } from 'rxjs';
import { ErrorResponse, PagamentoDTO } from '../../../lib/clients/gera-pagamento/GeraPagamentoApi';
import { PagamentoListParams } from '../../../lib/clients/services/types';
import { NotificationService } from '../../service/notification-service';
import { PagamentoService } from '../../service/pagamento-service';
import { ObjectUtil } from '../../utils';
import { PagamentoComponent } from "../pagamento-component/pagamento-component";

type StatusPagamento = 'PENDENTE_PROCESSAMENTO' | 'PROCESSADO_SUCESSO' | 'PROCESSADO_FALHA'

@Component({
  selector: 'pagamento-list-component',
  imports: [ReactiveFormsModule, PagamentoComponent],
  templateUrl: './pagamento-list-component.html',
  styleUrl: './pagamento-list-component.css'
})
export class PagamentoListComponent implements OnInit {
  searchForm!: FormGroup;
  currentPage: number = 1;
  page: number = 1;
  nextPage?: number = undefined;
  previousPage?: number = undefined;
  pageSize: number = 5;
  totalPages: number = 0;
  totalElements: number = 0;
  pageSizeOptions: number[] = [5, 10, 20, 50]
  statusPagamentos : StatusPagamento[] = ["PENDENTE_PROCESSAMENTO", "PROCESSADO_SUCESSO", "PROCESSADO_FALHA"];
  pagamentos: PagamentoDTO[] = []
  loading = false;
  private destroy$ = new Subject<void>();

  constructor(
    private pagamentoService: PagamentoService,
    private notification: NotificationService,
    private formBuilder: FormBuilder
  ){
    this.searchForm = this.formBuilder.group({
      searchByIdentificacao: undefined,
      searcByStatus: undefined,
    })
  }
  
  ngOnInit(): void {
    this.setupSearchListener();
    this.carregarPagamentos();
  }

  setupSearchListener() {
    this.searchForm.valueChanges
      .pipe(
        debounceTime(300),
        distinctUntilChanged((prev, curr) => 
          JSON.stringify(prev) === JSON.stringify(curr)
        ),
        takeUntil(this.destroy$)
      )
      .subscribe(() => {
        this.currentPage = 0;
        this.carregarPagamentos();
      });
  }
  
  carregarPagamentos(): void {
    this.loading = true;
    const searchParams: PagamentoListParams = {
      cpfCnpj: this.searchForm.get("searchByIdentificacao")?.value,
      status: this.searchForm.get("searcByStatus")?.value,
      page: this.page,
      size: this.pageSize
    };

    // Remove valores undefined/null 
    ObjectUtil.cleanParams<PagamentoListParams>(searchParams)

    this.pagamentoService.listar(searchParams)
    .pipe(finalize(() => this.loading = false))
    .subscribe({
        next: (response) => {
          console.log(response.page)
          this.pagamentos = response.results || [];
          this.currentPage = response.page?.currentPage!;
          this.pageSize = response.page?.pageSize!;
          this.nextPage = response.page?.nextPage;
          this.previousPage = response.page?.previousPage;
          this.totalElements = response.page?.totalElements!;
          this.totalPages = Math.ceil(this.totalElements/this.pageSize);
        },
        error: (err) => {
          this.onError(err);
        },
      });
  }

  processar(idPagamento: number): void {
    this.loading = true;
    this.pagamentoService.processar(idPagamento)
      .pipe(finalize(() => this.loading = false))
      .subscribe({
        next: () => {
          debounceTime(3000) //Espera para atualizar pagamento consumido do kafka
          this.carregarPagamentos()
          const index = this.pagamentos.findIndex(p => p.idPagamento === idPagamento);
          this.onSuccess(`Pagamento ${this.pagamentos[index].idPagamento} processado com sucesso`)
        },
        error: (err) => {
          this.onError(err);
        }
      });
  }

  inativar(id: string): void{
    this.loading = true;
    this.pagamentoService.inativar(id)
      .pipe(finalize(() => this.loading = false))
      .subscribe({
        next: ()=>{
          this.carregarPagamentos()
          const index = this.pagamentos.findIndex(p => p.id === id)
          this.onSuccess(`Pagamento ${this.pagamentos[index].idPagamento} inativado`)
        },
        error: (err) => {
          this.onError(err);
        }
      })
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
    this.pageSize=Number(pageSize);
    this.carregarPagamentos()
  }

  handleNextPage() {
    this.page = this.nextPage!;
    this.carregarPagamentos()
  }
  handlePreviousPage() {
    this.page = this.previousPage!;
    this.carregarPagamentos()
  }
  
  goToPage(page: number){
    this.page = page;
    this.carregarPagamentos()
  }

  onPagamentoCriado(pagamento: PagamentoDTO) {
    this.carregarPagamentos()
    this.notification.show({
      message: `Pagamento gerado ${pagamento.idPagamento} e pendente para processamento`,
      type: 'info',
      duration: 3000
    })
  }

  onSuccess(message: string) {
    this.notification.show({
      message: message,
      type: 'success',
      duration: 3000
    });
  }

  onError(error: ErrorResponse) {
    this.notification.show({
      message: error.details?.toString()|| "Error ao realizar operação",
      type: 'error'
    });
  }
}
