<script lang="ts">
  import FaAngleDown from 'svelte-icons/fa/FaAngleDown.svelte'
  import FaAngleUp from 'svelte-icons/fa/FaAngleUp.svelte'

  export let expanded = true
  export let disabled = false

  $: if (disabled) {
    expanded = false
  }

  function toggle() {
    if (!disabled) {
      expanded = !expanded
    }
  }
</script>

<div class="card">
  <header class="card-header" class:disabled on:click={toggle}>
    <p class="card-header-title">
      <slot name="title" />
    </p>
    <button type="button" class="card-header-icon">
      <span class="icon">
        {#if !disabled}
          {#if expanded}
            <FaAngleUp />
          {:else}
            <FaAngleDown />
          {/if}
        {/if}
      </span>
    </button>
  </header>
  <div class="card-content" class:is-hidden={!expanded}>
    <slot />
  </div>
</div>

<style>
  .card-header:not(.disabled) {
    cursor: pointer;
  }
</style>
