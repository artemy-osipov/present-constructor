<script lang="ts">
  import Header from '$lib/components/layout/Header.svelte'
  import Notifications from '$lib/components/notifications/Notifications.svelte'
</script>

<div class="body">
  <Header />
  <Notifications />

  <section class="section">
    <div class="container">
      <slot />
    </div>
  </section>
</div>

<!-- workaround https://github.com/sveltejs/svelte/issues/3105 -->

<!-- use custom div instead of has-navbar-fixed-top class on body -->
<style>
  .body {
    padding-top: 3.25rem;
  }
</style>
