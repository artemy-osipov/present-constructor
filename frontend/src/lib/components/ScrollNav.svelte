<script lang="ts">
  import FaEye from 'svelte-icons/fa/FaEye.svelte'

  export let message: string
  export let anchorElement: HTMLElement

  let scrollY: number
  let visible = false
  $: scrollY, (visible = anchorElement && !isScrolledIntoView(anchorElement))

  function isScrolledIntoView(el: HTMLElement): boolean {
    const rect = el.getBoundingClientRect()
    return rect.bottom >= 0
  }

  function scrollToAnchor() {
    anchorElement.scrollIntoView()
  }
</script>

<svelte:window bind:scrollY />

{#if visible}
  <nav class="navbar is-transparent is-fixed-bottom">
    <div class="navbar-menu is-active mr-0">
      <div class="navbar-end">
        <div class="navbar-item">
          {message}
        </div>
        <div class="navbar-item">
          <!-- svelte-ignore a11y-missing-attribute -->
          <!-- svelte-ignore a11y-click-events-have-key-events -->
          <a class="button is-primary" on:click|preventDefault={scrollToAnchor}>
            <span class="icon">
              <FaEye />
            </span>
            <span>Подробнее</span>
          </a>
        </div>
      </div>
    </div>
  </nav>
{/if}
