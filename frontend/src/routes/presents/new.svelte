<script lang="ts" context="module">
  import type { Load } from '@sveltejs/kit'
  import { firstValueFrom } from 'rxjs'
  import { browser } from '$app/env'
  import { presentRepository } from '$lib/data/present.repository'

  export const load: Load = async ({ url }) => {
    const sourcePresentId = url.searchParams.get('source')
    if (browser && sourcePresentId) {
      await Promise.all([presentRepository.fetch(), candyRepository.fetch()])
      const source = await firstValueFrom(
        presentRepository.present(sourcePresentId)
      )
      return {
        props: {
          source,
        },
      }
    }
    return {}
  }
</script>

<script lang="ts">
  import PresentNew from '$lib/components/presents/new/PresentNew.svelte'
  import type { Present } from '$lib/data/present.model'
  import { candyRepository } from '$lib/data/candy.repository'

  export let source: Present | undefined
</script>

<PresentNew {source} />
