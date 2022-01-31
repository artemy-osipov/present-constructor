<script lang="ts">
  import FaPlus from 'svelte-icons/fa/FaPlus.svelte'
  import { goto } from '$app/navigation'
  import PresentList from '$lib/components/presents/PresentList.svelte'
  import LinkBorder from '$lib/components/LinkBorder.svelte'

  function gotoNewPresentPage() {
    goto('/presents/new')
  }
</script>

<h1 class="title">
  Подарки
  <button class="button" on:click={gotoNewPresentPage}>
    <span class="icon">
      <FaPlus />
    </span>
  </button>
</h1>

<PresentList />
<LinkBorder />
