import type { PageLoad } from './$types'
import { firstValueFrom } from 'rxjs'
import { browser } from '$app/environment'
import { candyRepository } from '$lib/data/candy.repository'
import type { Candy } from '$lib/data/candy.model'

export const load: PageLoad = async ({ params, parent }) => {
  let candy: Candy | undefined
  const candyId = params['id']
  if (browser) {
    await parent()
    await candyRepository.fetch()
    candy = await firstValueFrom(candyRepository.candy(candyId))
  }
  return { candy }
}
