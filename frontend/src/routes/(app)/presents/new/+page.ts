import { browser } from '$app/environment'
import { candyRepository } from '$lib/candy/candy.repository'
import type { Present } from '$lib/present/present.model'
import { presentRepository } from '$lib/present/present.repository'
import { firstValueFrom } from 'rxjs'
import type { PageLoad } from './$types'

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
