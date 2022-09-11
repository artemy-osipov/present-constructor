import type { PageLoad } from './$types'
import { browser } from '$app/environment'
import { fetchPresentView } from '$lib/data/present-view.service'
import type { PresentView } from '$lib/data/present.model'

export const load: PageLoad = async ({ params, parent }) => {
  let present: PresentView | undefined
  const id = params['id']
  if (browser) {
    await parent()
    present = await fetchPresentView(id)
  }
  return { present }
}
