import { goto } from '$app/navigation'
import { logout } from '$lib/auth/auth'
import { notificationStore } from '$lib/notification/notification.store'

export async function handleError(resp: Response) {
  if (resp.status === 401) {
    logout()
    await goto('/login')
    return resp
  }
  if (!resp.ok) {
    notificationStore.addError('Что-то пошло не так: ' + resp.statusText)
    throw Error(resp.statusText)
  }
  return resp
}
