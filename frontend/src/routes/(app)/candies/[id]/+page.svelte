<script lang="ts">
  import CandyEdit from '$lib/components/candy/CandyEdit.svelte'
  import Loader from '$lib/components/Loader.svelte'
  import type { PageData } from './$types'

  export let data: PageData
</script>

{#if data.candy === undefined}
  <Loader />
{:else}
  <CandyEdit candy={data.candy} />
{/if}
