import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PagamentoListComponent } from "./components/pagamento-list-component/pagamento-list-component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, PagamentoListComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('pagamentos-front');
}
