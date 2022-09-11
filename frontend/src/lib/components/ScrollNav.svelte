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
    <div class="navbar-menu is-active">
      <div class="navbar-end">
        <div class="navbar-item">
          {message}
        </div>
        <div class="navbar-item">
          <div class="field is-grouped">
            <p class="control">
              <!-- svelte-ignore a11y-missing-attribute -->
              <a
                class="button is-primary"
                on:click|preventDefault={scrollToAnchor}
              >
                <span class="icon">
                  <FaEye />
                </span>
                <span>Подробнее</span>
              </a>
            </p>
          </div>
        </div>
      </div>
    </div>
  </nav>
{/if}
