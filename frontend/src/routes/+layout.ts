import { browser } from '$app/environment'
import { guardPage } from '$lib/auth/auth'
import { ENABLE_AUTH, USE_MOCKS } from '$lib/config/environment'
import type { LayoutLoad } from './$types'

let initialized = false

export const load: LayoutLoad = async ({ url }) => {
  if (browser) {
    if (!initialized && USE_MOCKS) {
      await initMocks()
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
