import { API_URL } from '$lib/config/environment'
import type { Candy } from '$lib/data/candy.model'

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
    })
    return resp.json()
  }

  async get(id: Candy['id']): Promise<Candy> {
    const resp = await fetch(`${candyResource}/${id}`)
    return resp.json()
  }

  async list(): Promise<Candy[]> {
    const resp = await fetch(candyResource)
    return resp.json()
  }

  async update(candy: Candy): Promise<void> {
    await fetch(`${candyResource}/${candy.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(candy),
    })
  }

  async delete(id: Candy['id']): Promise<void> {
    await fetch(`${candyResource}/${id}`, {
      method: 'DELETE',
    })
  }
}

export const candyGateway = new CandyGateway()
