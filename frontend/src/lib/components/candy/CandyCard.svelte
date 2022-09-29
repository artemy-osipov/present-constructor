<script lang="ts">
  import type { Candy } from '$lib/candy/candy.model'
  import { formatPrice } from '$lib/utils/string.utils'
  import FaAngleDown from 'svelte-icons/fa/FaAngleDown.svelte'
  import FaAngleUp from 'svelte-icons/fa/FaAngleUp.svelte'

  export let candy: Candy
  export let count = 0

  export let expanded = true
  export let allowToggle = false

  let view: string[]
  $: expanded, (view = formatTitle())

  function formatTitle(): string[] {
    let title = [candy.name]
    if (!expanded) {
      if (count > 0) {
        title.push(`${formatPrice(candy.price)} X ${count} шт.`)
      } else {
        title.push(`${formatPrice(candy.price)}`)
      }
    }
    return title
  }

  function toggleExpand() {
    expanded = !expanded
  }
</script>

<div class="card {$$props.class || ''}">
  <header
    class="card-header"
    on:click={(allowToggle && toggleExpand) || undefined}
  >
    <p class="card-header-title">
      {#each view as line, i}
        {line}
        {#if i < view.length - 1}<br />{/if}
      {/each}
    </p>
    {#if allowToggle}
      <button type="button" class="card-header-icon">
        <span class="icon">
          {#if expanded}
            <FaAngleUp />
          {:else}
            <FaAngleDown />
          {/if}
        </span>
      </button>
    {/if}
  </header>
  {#if expanded}
    <div class="card-content">
      <div class="content">
        <div><strong>Производитель: </strong>{candy.firm}</div>
        <div><strong>Цена: </strong>{formatPrice(candy.price)}</div>
        {#if count > 0}
          <div><strong>Количество: </strong>{count}</div>
        {/if}
      </div>
    </div>
  {/if}
  {#if $$slots.footer}
    <footer class="card-footer card-footer-item">
      <slot name="footer" />
    </footer>
  {/if}
</div>
