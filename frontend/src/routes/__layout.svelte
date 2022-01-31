<script lang="ts" context="module">
  import type { Load } from '@sveltejs/kit'
  import { browser } from '$app/env'
  import { USE_MOCKS } from '$lib/config/environment'

  let initializated = false

  export const load: Load = async () => {
    if (browser) {
      if (USE_MOCKS && !initializated) {
        const { init } = await import('$lib/mock/init')
        await init()
      }
      initializated = true
    }
    return {}
  }
</script>

<script lang="ts">
  import Header from '$lib/components/layout/Header.svelte'
</script>

<Header />

<section class="section">
  <div class="container">
    {#if initializated}
      <slot />
    {/if}
  </div>
</section>

<style>
  @import 'bulma/css/bulma.min.css';

  /* svelte-icons */
  /* hack */
  :global(span.icon > svg) {
    height: 1em;
  }
</style>
