<script lang="ts" context="module">
  import type { Load } from '@sveltejs/kit'
  import { firstValueFrom } from 'rxjs'
  import { browser } from '$app/env'
  import { candyRepository } from '$lib/data/candy.repository'

  export const load: Load = async ({ params }) => {
    const candyId = params['id']
    if (browser) {
      await candyRepository.fetch()
      const candy = await firstValueFrom(candyRepository.candy(candyId))
      return {
        props: {
          candy,
        },
      }
    }
    return {}
  }
</script>

<script lang="ts">
  import CandyEdit from '$lib/components/candy/CandyEdit.svelte'
  import type { Candy } from '$lib/data/candy.model'

  export let candy: Candy
</script>

<CandyEdit {candy} />
