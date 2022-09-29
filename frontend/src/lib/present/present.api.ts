import { API_URL } from '$lib/config/environment'
import { handleError } from '$lib/utils/fetch.utils'
import type { Present } from './present.model'

export type NewPresentRequest = Omit<Present, 'id' | 'createDate'>

const presentResource = `${API_URL}/presents`

class PresentGateway {
  async add(req: NewPresentRequest): Promise<Present['id']> {
    const resp = await fetch(presentResource, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(req),
    }).then(handleError)
    return resp.json()
  }

  async get(id: Present['id']): Promise<Present> {
    const resp = await fetch(`${presentResource}/${id}`).then(handleError)
    return resp.json()
  }

  async list(): Promise<Present[]> {
    const resp = await fetch(presentResource).then(handleError)
    return resp.json()
  }

  async delete(id: Present['id']): Promise<void> {
    await fetch(`${presentResource}/${id}`, {
      method: 'DELETE',
    }).then(handleError)
  }

  publicReportLocation(id: string): string {
    return `${presentResource}/${id}/public-report`
  }

  privateReportLocation(id: string): string {
    return `${presentResource}/${id}/private-report`
  }
}

export const presentGateway = new PresentGateway()
