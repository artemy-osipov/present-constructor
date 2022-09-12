import { API_URL } from '$lib/config/environment'
import { handleError } from '$lib/utils/fetch.utils'
import type { Candy } from './candy.model'

export type NewCandyRequest = Omit<Candy, 'id' | 'active'>

const candyResource = `${API_URL}/api/candies`

class CandyGateway {
  async add(req: NewCandyRequest): Promise<Candy['id']> {
    const resp = await fetch(candyResource, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(req),
    }).then(handleError)
    return resp.json()
  }

  async get(id: Candy['id']): Promise<Candy> {
    const resp = await fetch(`${candyResource}/${id}`).then(handleError)
    return resp.json()
  }

  async list(): Promise<Candy[]> {
    const resp = await fetch(candyResource).then(handleError)
    return resp.json()
  }

  async update(candy: Candy): Promise<void> {
    await fetch(`${candyResource}/${candy.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(candy),
    }).then(handleError)
  }

  async delete(id: Candy['id']): Promise<void> {
    await fetch(`${candyResource}/${id}`, {
      method: 'DELETE',
    }).then(handleError)
  }
}

export const candyGateway = new CandyGateway()
