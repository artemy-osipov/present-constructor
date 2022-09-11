import { goto } from '$app/navigation'
import { logout } from '$lib/auth/auth'

export async function handleError(resp: Response) {
  if (resp.status === 401) {
    logout()
    await goto('/login')
    return resp
  }
  if (!resp.ok) {
    throw Error(resp.statusText)
  }
  return resp
}
