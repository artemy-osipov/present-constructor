<script lang="ts">
  import { page } from '$app/stores'

  type Link = {
    route: string
    text: string
  }

  const links: Link[] = [
    { route: '/about', text: 'Справка' },
    { route: '/candies', text: 'Конфеты' },
    { route: '/presents', text: 'Подарки' },
  ]

  let menuOpened = false
  let currentLink: Link | undefined
  $: currentLink = links.find((l) => l.route === $page.url.pathname)

  function toggleMenu() {
    menuOpened = !menuOpened
  }

  function closeMenu() {
    menuOpened = false
  }
</script>

<nav class="navbar is-fixed-top is-dark">
  <div class="navbar-brand">
    <a class="navbar-item" href="/about">
      <img src="presents.svg" width="30" height="30" alt="logo" />
    </a>
    <!-- svelte-ignore a11y-missing-attribute -->
    <a
      role="button"
      class="navbar-burger"
      on:click={toggleMenu}
      class:is-active={menuOpened}
    >
      {#each links as _}
        <span />
      {/each}
    </a>
  </div>

  <div class="navbar-menu" class:is-active={menuOpened}>
    <div class="navbar-start">
      {#each links as link}
        <a
          class="navbar-item"
          on:click={closeMenu}
          href={link.route}
          class:is-active={currentLink === link}>{link.text}</a
        >
      {/each}
    </div>
  </div>
</nav>
