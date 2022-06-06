import type { LayoutLoad } from './$types'
import { browser } from '$app/environment'
import { USE_MOCKS } from '$lib/config/environment'
import { initialize, initialized } from '$lib/app.store'
import { firstValueFrom } from 'rxjs'

export const load: LayoutLoad = async () => {
  if (browser) {
    if (USE_MOCKS && !(await firstValueFrom(initialized))) {
      await initMocks()
    }
    initialize()
  }
  return {}
}

async function initMocks() {
  const { init } = await import('$lib/mock/browser')
  init()
}
