import type { PageLoad } from './$types'
import { firstValueFrom } from 'rxjs'
import { browser } from '$app/environment'
import { candyRepository } from '$lib/data/candy.repository'
import { presentRepository } from '$lib/data/present.repository'
import type { Present } from '$lib/data/present.model'

export const load: PageLoad = async ({ parent, url }) => {
  let source: Present | undefined
  const sourcePresentId = browser && url.searchParams.get('source')
  if (sourcePresentId) {
    await parent()
    await Promise.all([presentRepository.fetch(), candyRepository.fetch()])
    source = await firstValueFrom(presentRepository.present(sourcePresentId))
  }
  return { source }
}
