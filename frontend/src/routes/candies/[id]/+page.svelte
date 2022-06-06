<script lang="ts">
  import type { PageData } from './$types'
  import CandyEdit from '$lib/components/candy/CandyEdit.svelte'

  export let data: PageData
</script>

{#if data.candy !== undefined}
  <CandyEdit candy={data.candy} />
{/if}
