<script lang="ts">
  import type { PageData } from './$types'
  import PresentShow from '$lib/components/presents/PresentShow.svelte'

  export let data: PageData
</script>

{#if data.present !== undefined}
  <PresentShow present={data.present} />
{/if}
