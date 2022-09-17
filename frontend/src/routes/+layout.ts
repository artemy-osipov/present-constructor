import { browser } from '$app/environment'
import { guardPage } from '$lib/auth/auth'
import { ENABLE_AUTH, SENTRY_DSN, USE_MOCKS } from '$lib/config/environment'
import * as Sentry from '@sentry/svelte'
import { BrowserTracing } from '@sentry/tracing'
import type { LayoutLoad } from './$types'

let initialized = false

export const load: LayoutLoad = async ({ url }) => {
  if (browser) {
    if (!initialized) {
      initSentry()
      if (USE_MOCKS) {
        await initMocks()
      }
    }
    if (ENABLE_AUTH) {
      await guardPage(url.pathname)
    }
    initialized = true
  }
}

async function initMocks() {
  const { init } = await import('$lib/mock/browser')
  init()
}

function initSentry() {
  Sentry.init({
    dsn: SENTRY_DSN,
    integrations: [new BrowserTracing()],
    tracesSampleRate: 1.0,
  })
}
