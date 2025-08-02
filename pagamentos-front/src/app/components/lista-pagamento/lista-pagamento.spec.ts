import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaPagamento } from './lista-pagamento';

describe('ListaPagamento', () => {
  let component: ListaPagamento;
  let fixture: ComponentFixture<ListaPagamento>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaPagamento]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaPagamento);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
