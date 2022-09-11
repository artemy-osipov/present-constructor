import { API_URL } from '$lib/config/environment'

const resource = `${API_URL}/auth`

class AuthGateway {
  async login(password: string): Promise<boolean> {
    const resp = await fetch(`${resource}/login`, {
      method: 'POST',
      headers: {
        Authorization: `Basic :${password}`,
      },
    })
    return resp.ok
  }

  async refresh(): Promise<boolean> {
    const resp = await fetch(`${resource}/refresh`, {
      method: 'POST',
    })
    return resp.ok
  }
}

export const authGateway = new AuthGateway()
