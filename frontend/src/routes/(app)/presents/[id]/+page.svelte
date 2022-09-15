<script lang="ts">
  import Loader from '$lib/components/Loader.svelte'
  import PresentShow from '$lib/components/presents/PresentShow.svelte'
  import type { PageData } from './$types'

  export let data: PageData
</script>

{#if data.present === undefined}
  <Loader />
{:else}
  <PresentShow present={data.present} />
{/if}
