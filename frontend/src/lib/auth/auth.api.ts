import { AUTH_URL } from '$lib/config/environment'
import { Buffer } from 'buffer'

class AuthGateway {
  async login(password: string): Promise<boolean> {
    const token = Buffer.from(':' + password).toString('base64')
    const resp = await fetch(`${AUTH_URL}/login`, {
      method: 'POST',
      headers: {
        Authorization: `Basic ${token}`,
      },
    })
    return resp.ok
  }

  async refresh(): Promise<boolean> {
    const resp = await fetch(`${AUTH_URL}/refresh`, {
      method: 'POST',
    })
    return resp.ok
  }
}

export const authGateway = new AuthGateway()
