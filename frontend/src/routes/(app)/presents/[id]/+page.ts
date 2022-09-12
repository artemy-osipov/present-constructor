import { browser } from '$app/environment'
import { fetchPresentView } from '$lib/present/present-view.service'
import type { PresentView } from '$lib/present/present.model'
import type { PageLoad } from './$types'

export const load: PageLoad = async ({ params, parent }) => {
  let present: PresentView | undefined
  const id = params['id']
  if (browser) {
    await parent()
    present = await fetchPresentView(id)
  }
  return { present }
}
