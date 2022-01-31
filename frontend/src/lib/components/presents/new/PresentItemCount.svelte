<script lang="ts">
  import { createEventDispatcher } from 'svelte'

  import FaMinus from 'svelte-icons/fa/FaMinus.svelte'
  import FaPlus from 'svelte-icons/fa/FaPlus.svelte'

  export let count = 0
  type Events = { newValue: number }
  const dispatch = createEventDispatcher<Events>()

  function decrement() {
    dispatch('newValue', count - 1)
  }

  function increment() {
    dispatch('newValue', count + 1)
  }
</script>

{#if count > 0}
  <div class="buttons has-addons">
    <button type="button" class="button" on:click={decrement}>
      <span class="icon">
        <FaMinus />
      </span>
    </button>
    <button type="button" class="button">{count}</button>
    <button type="button" class="button" on:click={increment}>
      <span class="icon">
        <FaPlus />
      </span>
    </button>
  </div>
{:else}
  <button class="button" on:click={increment}>Добавить</button>
{/if}
