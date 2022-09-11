import type { LayoutLoad } from './$types'
import { browser } from '$app/environment'
import { ENABLE_AUTH, USE_MOCKS } from '$lib/config/environment'
import { initialize, initialized } from '$lib/app.store'
import { firstValueFrom } from 'rxjs'
import { guardPage } from '$lib/auth/auth'

export const load: LayoutLoad = async ({ url }) => {
  if (browser) {
    if (USE_MOCKS && !(await firstValueFrom(initialized))) {
      await initMocks()
    }
    if (ENABLE_AUTH) {
      await guardPage(url.pathname)
    }
    initialize()
  }
  return {}
}

async function initMocks() {
  const { init } = await import('$lib/mock/browser')
  init()
}
