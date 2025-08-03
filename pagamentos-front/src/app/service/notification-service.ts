import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Notification {
  message: string;
  type: 'success' | 'error' | 'info' | 'warning';
  duration?: number;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notifications = new BehaviorSubject<Notification[]>([]);
  notifications$ = this.notifications.asObservable();

  show(notification: Notification) {
    const currentNotifications = this.notifications.value;
    this.notifications.next([...currentNotifications, notification]);

    if (notification.duration) {
      setTimeout(() => {
        this.dismiss(notification);
      }, notification.duration);
    }
  }

  dismiss(notification: Notification) {
    const currentNotifications = this.notifications.value;
    this.notifications.next(
      currentNotifications.filter(n => n !== notification)
    );
  }
}
