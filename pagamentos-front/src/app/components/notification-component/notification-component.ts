import { Component, Signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { Notification, NotificationService } from '../../services/notification-service';

@Component({
  selector: 'app-notification-component',
  imports: [],
  templateUrl: './notification-component.html',
  styleUrl: './notification-component.css'
})
export class NotificationComponent {
  notifications: Signal<Notification[]>;

  constructor(readonly notificationService: NotificationService) {
    this.notifications = toSignal(this.notificationService.notifications$, {initialValue: []});
  }

  getNotificationClasses(notification: Notification): string {
    const base = 'opacity-70 text-gray-800';
    switch (notification.type) {
      case 'success':
        return `${base} bg-green-500`;
      case 'error':
        return `${base} bg-red-500`;
      case 'warning':
        return `${base} bg-yellow-500`;
      case 'info':
        return `${base} bg-blue-500`;
      default:
        return `${base} bg-gray-500`;
    }
  }

  dismiss(notification: Notification) {
    this.notificationService.dismiss(notification);
  }
}
