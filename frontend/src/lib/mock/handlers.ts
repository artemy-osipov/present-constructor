import type { Candy } from '$lib/candy/candy.model'
import type { Present } from '$lib/present/present.model'
import { rest } from 'msw'
import { Mock } from './data'

const mock = new Mock()

export const handlers = [
  rest.post('/mocks/auth/login', async (req, res, ctx) => {
    const success = req.headers.get('Authorization') === 'Basic :test'
    return res(
      ctx.delay(1000),
      ctx.status(success ? 200 : 401),
      ctx.cookie('X-AUTH-TOKEN', '321')
    )
  }),
  rest.post('/mocks/auth/refresh', async (req, res, ctx) => {
    const hasToken = req.cookies['X-AUTH-TOKEN'] !== undefined
    return res(ctx.delay(1000), ctx.status(hasToken ? 200 : 401))
  }),
  rest.get('/mocks/api/candies', (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(200), ctx.json(mock.candies))
  }),
  rest.get('/mocks/api/candies/:id', (req, res, ctx) => {
    const { id } = req.params
    return res(
      ctx.delay(1000),
      ctx.status(200),
      ctx.json(mock.getCandy(id as string))
    )
  }),
  rest.post('/mocks/api/candies', async (req, res, ctx) => {
    const candy: Candy = await req.json()
    const newId = mock.addCandy(candy)
    return res(ctx.delay(1000), ctx.status(200), ctx.json(newId))
  }),
  rest.put('/mocks/api/candies/:id', (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(200))
  }),
  rest.delete('/mocks/api/candies/:id', (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(200))
  }),
  rest.get('/mocks/api/presents', (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(200), ctx.json(mock.presents))
  }),
  rest.get('/mocks/api/presents/:id', (req, res, ctx) => {
    const { id } = req.params
    return res(
      ctx.delay(1000),
      ctx.status(200),
      ctx.json(mock.getPresent(id as string))
    )
  }),
  rest.post('/mocks/api/presents', async (req, res, ctx) => {
    const present: Present = await req.json()
    const newId = mock.addPresent(present)
    return res(ctx.delay(1000), ctx.status(200), ctx.json(newId))
  }),
  rest.delete('/mocks/api/presents/:id', (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(200))
  }),
]
