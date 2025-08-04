import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorResponse, PagamentoCreateDTO, PagamentoDTO } from '../../../lib/clients/gera-pagamento/GeraPagamentoApi';
import { PagamentoService } from '../../service/pagamento-service';
import { finalize } from 'rxjs';
import { NotificationService } from '../../service/notification-service';
import { CommonModule } from '@angular/common';

type PagamentoFormControls = {
  cpfCnpj: FormControl<string>;
  metodoPagamento: FormControl<'BOLETO' | 'PIX' | 'CARTAO_CREDITO' | 'CARTAO_DEBITO'>;
  numeroCartao: FormControl<string | null>;
  valor: FormControl<number>;
};

@Component({
  selector: 'app-pagamento-component',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './pagamento-component.html',
  styleUrl: './pagamento-component.css'
})
export class PagamentoComponent {
  @Output() pagamentoCriado = new EventEmitter<PagamentoDTO>();
  @Output() fecharModal = new EventEmitter<void>();
  isOpen = false;
  loading = false;

  metodosPagamento = [
    { value: 'BOLETO', label: 'Boleto' },
    { value: 'PIX', label: 'PIX' },
    { value: 'CARTAO_CREDITO', label: 'Cartão de Crédito' },
    { value: 'CARTAO_DEBITO', label: 'Cartão de Débito' }
  ];

  form = new FormGroup<PagamentoFormControls>({
    cpfCnpj: new FormControl('', {
      validators: [Validators.required, Validators.minLength(11)],
      nonNullable: true
    }),
    metodoPagamento: new FormControl('BOLETO', {
      validators: [Validators.required],
      nonNullable: true
    }),
    numeroCartao: new FormControl(null),
    valor: new FormControl(0, {
      validators: [Validators.required, Validators.min(0.01)],
      nonNullable: true
    })
  });

  constructor(
    private pagamentoService: PagamentoService,
    private notification: NotificationService,
  ){}

  abrirModal() {
    this.isOpen = true;
    this.form.reset({
      metodoPagamento: 'BOLETO',
      valor: 0
    });
  }

  fechar() {
    this.isOpen = false;
    this.fecharModal.emit();
  }

  fecharAoClickFora(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (target.classList.contains('modal-overlay')) {
      this.fechar();
    }
  }

  metodoSelecionado() {
    return this.form.controls.metodoPagamento.value;
  }

  salvar(data: PagamentoCreateDTO){
    this.loading = true;
    this.pagamentoService.adicionar(data)
      .pipe(finalize(() => this.loading = false))
      .subscribe({
        next: (data)=>{
          this.onSuccess(data)
          console.log("Pagamento gerado: ", data)
        },
        error: (err) => {
          this.onError(err);
        }
      })
  }

  submit() {
    if (this.form.valid) {
      this.salvar(this.form.value as PagamentoCreateDTO)
    } else {
      this.form.markAllAsTouched();
    }
  }

  onSuccess(data: PagamentoDTO) {
    this.notification.show({
        message: `Pagamento ${data.idPagamento} cadastrado com sucesso`,
        type: 'success',
        duration: 3000
      });
      this.pagamentoCriado.emit(data);
      this.fechar()
    }
  
    onError(error: ErrorResponse) {
      this.notification.show({
        message: error.details?.toString()|| "Error ao realizar operação",
        type: 'error'
      });
    }
}
