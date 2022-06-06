<script lang="ts">
  import Header from '$lib/components/layout/Header.svelte'
</script>

<Header />

<section class="section">
  <div class="container">
    <slot />
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
