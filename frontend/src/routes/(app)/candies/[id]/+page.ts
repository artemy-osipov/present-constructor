import { browser } from '$app/environment'
import type { Candy } from '$lib/candy/candy.model'
import { candyRepository } from '$lib/candy/candy.repository'
import { firstValueFrom } from 'rxjs'
import type { PageLoad } from './$types'

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
