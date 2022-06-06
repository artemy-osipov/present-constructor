<script lang="ts">
  import FaPlus from 'svelte-icons/fa/FaPlus.svelte'
  import { goto } from '$app/navigation'
  import CandyList from '$lib/components/candy/CandyList.svelte'
  import LinkBorder from '$lib/components/LinkBorder.svelte'

  function gotoNewCandyPage() {
    goto('/candies/new')
  }
</script>

<h1 class="title">
  Конфеты
  <button class="button" on:click={gotoNewCandyPage}>
    <span class="icon">
      <FaPlus />
    </span>
  </button>
</h1>

<CandyList />
<LinkBorder />
