import { rest } from 'msw'
import type { Candy } from '$lib/data/candy.model'
import { Mock } from './data'
import type { Present } from '$lib/data/present.model'

const mock = new Mock()

export const handlers = [
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
