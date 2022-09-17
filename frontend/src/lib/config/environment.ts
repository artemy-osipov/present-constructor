import { browser, dev } from '$app/environment'

export const development = dev || import.meta.env.VITE_DEV == 'true'

export const ENABLE_AUTH: boolean =
  import.meta.env.VITE_ENABLE_AUTH == 'true' ? true : !dev
export const USE_MOCKS: boolean = development && env('API_URL') === undefined
export const API_URL: string = USE_MOCKS ? 'mocks' : env('API_URL') + ''
export const SENTRY_DSN = development
  ? undefined
  : 'https://ba35e242d21043d0bc99db979b9109f3@o1412690.ingest.sentry.io/6751968'

if (development) {
  console.log('ENABLE_AUTH ' + ENABLE_AUTH)
  console.log('USE_MOCKS ' + USE_MOCKS)
  console.log('API_URL ' + API_URL)
}

/* eslint-disable  @typescript-eslint/no-explicit-any */
function env(name: string): string | undefined {
  if (browser) {
    return (window as any).env?.[name]
  } else {
    return process.env[name]
  }
}
