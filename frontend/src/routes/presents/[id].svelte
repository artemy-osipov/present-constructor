<script lang="ts" context="module">
  import type { Load } from '@sveltejs/kit'
  import { browser } from '$app/env'
  import { fetchPresentView } from '$lib/data/present-view.service'

  export const load: Load = async ({ params }) => {
    const id = params['id']
    if (browser) {
      const present = await fetchPresentView(id)
      return {
        props: {
          present,
        },
      }
    }
    return {}
  }
</script>

<script lang="ts">
  import type { PresentView } from '$lib/data/present.model'
  import PresentShow from '$lib/components/presents/PresentShow.svelte'

  export let present: PresentView
</script>

<PresentShow {present} />
