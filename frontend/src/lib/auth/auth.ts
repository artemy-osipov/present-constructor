import { redirect } from '@sveltejs/kit'
import { authGateway } from './auth.api'

const publicRoutes = ['/', '/about', '/login']

let isLoggedIn = false
let refreshScheduler: NodeJS.Timer | undefined

export async function guardPage(pathname: string) {
  if (isLoggedIn || publicRoutes.includes(pathname)) {
    return
  }
  if (await refresh()) {
    setLogin()
    return
  }
  throw redirect(302, '/login?redirectURL=' + encodeURIComponent(pathname))
}

function setLogin() {
  isLoggedIn = true
  clearInterval(refreshScheduler)
  refreshScheduler = setInterval(async () => await refresh(), 1000 * 60 * 24) // refresh every hour
}

export async function login(password: string): Promise<boolean> {
  const res = await authGateway.login(password)
  if (res) {
    setLogin()
  }
  return res
}

async function refresh(): Promise<boolean> {
  const res = await authGateway.refresh()
  if (!res) {
    logout()
  }
  return res
}

export function logout() {
  clearInterval(refreshScheduler)
  isLoggedIn = false
}
