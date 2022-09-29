import type { Candy } from '$lib/candy/candy.model'
import { API_URL, AUTH_URL } from '$lib/config/environment'
import type { Present } from '$lib/present/present.model'
import { rest } from 'msw'
import { Mock } from './data'

const mock = new Mock()

export const handlers = [
  rest.post(`${AUTH_URL}/login`, async (req, res, ctx) => {
    const success = req.headers.get('Authorization') === 'Basic OnRlc3Q='
    return res(
      ctx.delay(1000),
      ctx.status(success ? 200 : 401),
      ctx.cookie('X-AUTH-TOKEN', '321')
    )
  }),
  rest.post(`${AUTH_URL}/refresh`, async (req, res, ctx) => {
    const hasToken: boolean = req.cookies['X-AUTH-TOKEN'] !== undefined
    return res(ctx.delay(1000), ctx.status(hasToken ? 200 : 401))
  }),
  rest.get(`${API_URL}/candies`, (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(200), ctx.json(mock.candies))
  }),
  rest.get(`${API_URL}/candies/:id`, (req, res, ctx) => {
    const { id } = req.params
    return res(
      ctx.delay(1000),
      ctx.status(200),
      ctx.json(mock.getCandy(id as string))
    )
  }),
  rest.post(`${API_URL}/candies`, async (req, res, ctx) => {
    const candy: Candy = await req.json()
    const newId = mock.addCandy(candy)
    return res(ctx.delay(1000), ctx.status(200), ctx.json(newId))
  }),
  rest.put(`${API_URL}/candies/:id`, (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(200))
  }),
  rest.delete(`${API_URL}/candies/:id`, (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(200))
  }),
  rest.get(`${API_URL}/presents`, (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(200), ctx.json(mock.presents))
  }),
  rest.get(`${API_URL}/presents/:id`, (req, res, ctx) => {
    const { id } = req.params
    return res(
      ctx.delay(1000),
      ctx.status(200),
      ctx.json(mock.getPresent(id as string))
    )
  }),
  rest.post(`${API_URL}/presents`, async (req, res, ctx) => {
    const present: Present = await req.json()
    const newId = mock.addPresent(present)
    return res(ctx.delay(1000), ctx.status(200), ctx.json(newId))
  }),
  rest.delete(`${API_URL}/presents/:id`, (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(200))
  }),
]
