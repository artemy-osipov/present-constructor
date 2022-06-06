<script lang="ts">
  import type { PageData } from './$types'
  import PresentNew from '$lib/components/presents/new/PresentNew.svelte'

  export let data: PageData
</script>

<PresentNew source={data.source} />
