import { API_URL } from '$lib/config/environment'
import type { Present } from '$lib/data/present.model'

export type NewPresentRequest = Omit<Present, 'id' | 'date'>

const presentResource = `${API_URL}/api/presents`

class PresentGateway {
  async add(req: NewPresentRequest): Promise<Present['id']> {
    const resp = await fetch(presentResource, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(req),
    })
    return resp.json()
  }

  async get(id: Present['id']): Promise<Present> {
    const resp = await fetch(`${presentResource}/${id}`)
    return resp.json()
  }

  async list(): Promise<Present[]> {
    const resp = await fetch(presentResource)
    return resp.json()
  }

  async delete(id: Present['id']): Promise<void> {
    await fetch(`${presentResource}/${id}`, {
      method: 'DELETE',
    })
  }

  publicReportLocation(id: string): string {
    return `${presentResource}/${id}/public-report`
  }

  privateReportLocation(id: string): string {
    return `${presentResource}/${id}/private-report`
  }
}

export const presentGateway = new PresentGateway()
