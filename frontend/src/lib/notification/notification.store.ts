import { createStore } from '@ngneat/elf'
import {
  addEntities,
  deleteEntities,
  selectAllEntities,
  withEntities,
} from '@ngneat/elf-entities'
import type { Observable } from 'rxjs'
import type { Notification } from './notification.model'

const store = createStore(
  { name: 'notifications' },
  withEntities<Notification>()
)

class NotificationStore {
  list: Observable<Notification[]> = store.pipe(selectAllEntities())

  addInfo(text: string): Notification {
    return this.add({
      text,
      error: false,
    })
  }

  addError(text: string): Notification {
    return this.add({
      text,
      error: true,
    })
  }

  add(req: Omit<Notification, 'id'>): Notification {
    const notification: Notification = {
      ...req,
      id: crypto.randomUUID(),
    }
    store.update(addEntities(notification))
    setTimeout(() => this.delete(notification), 10000)
    return notification
  }

  delete(notification: Notification): void {
    store.update(deleteEntities(notification.id))
  }
}

export const notificationStore = new NotificationStore()
