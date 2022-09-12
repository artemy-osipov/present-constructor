<script lang="ts">
  import type { PresentItem } from '$lib/present/present.model'
  import PresentItemList from './PresentItemList.svelte'

  export let items: PresentItem[]
</script>

<h1 class="title mt-5">Выбор конфет</h1>

<PresentItemList {items} on:change highlightSelected={true} skipEmpty={false} />
