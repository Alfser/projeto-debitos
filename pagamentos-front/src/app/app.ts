import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ListaPagamento } from "./components/lista-pagamento/lista-pagamento";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ListaPagamento],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('pagamentos-front');
}
