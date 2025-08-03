import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PagamentoListComponent } from "./components/pagamento-list-component/pagamento-list-component";
import { NotificationComponent } from './components/notification-component/notification-component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, PagamentoListComponent, NotificationComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('pagamentos-front');
}
