<script lang="ts">
  import FaAngleUp from 'svelte-icons/fa/FaAngleUp.svelte'
  import FaAngleDown from 'svelte-icons/fa/FaAngleDown.svelte'

  function scrollToTop() {
    document.body.scrollIntoView(true)
  }

  function scrollToBottom() {
    document.body.scrollIntoView(false)
  }
</script>

<!-- svelte-ignore a11y-missing-attribute -->
<a class="button link-to-top" on:click|preventDefault={scrollToTop}>
  <FaAngleUp />
</a>

<!-- svelte-ignore a11y-missing-attribute -->
<a class="button link-to-down" on:click|preventDefault={scrollToBottom}>
  <FaAngleDown />
</a>

<style>
  .link-to-top,
  .link-to-down {
    position: fixed;
    right: 1rem;
    border-width: 0px;
    background-color: transparent;
  }

  .link-to-top {
    top: 5rem;
  }

  .link-to-down {
    bottom: 20px;
  }
</style>
