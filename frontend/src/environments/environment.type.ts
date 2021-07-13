import { MockData } from 'app/core/mock/mock.data'

export interface Environment {
  production: boolean
  apiUrl: string
  mock: boolean | MockData
}
