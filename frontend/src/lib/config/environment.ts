import { dev } from '$app/environment'

export const development = dev || import.meta.env.VITE_DEV == 'true'

export const ENABLE_AUTH: boolean =
  import.meta.env.VITE_ENABLE_AUTH == 'true' ? true : !dev
export const USE_MOCKS: boolean =
  development && !import.meta.env.VITE_BASE_API_URL
export const BASE_API_URL: string = USE_MOCKS
  ? 'mocks'
  : import.meta.env.VITE_BASE_API_URL || ''
export const API_URL: string = BASE_API_URL + '/api'
export const AUTH_URL: string = BASE_API_URL + '/auth'
export const SENTRY_DSN = development
  ? undefined
  : 'https://ba35e242d21043d0bc99db979b9109f3@o1412690.ingest.sentry.io/6751968'

if (development) {
  console.log('ENABLE_AUTH', ENABLE_AUTH)
  console.log('USE_MOCKS', USE_MOCKS)
  console.log('BASE_API_URL', BASE_API_URL)
}
