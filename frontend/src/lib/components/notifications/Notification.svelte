<script lang="ts">
  import type { Notification } from '$lib/notification/notification.model'
  import { notificationStore } from '$lib/notification/notification.store'
  import { fade } from 'svelte/transition'

  export let notification: Notification

  function onClose() {
    notificationStore.delete(notification)
  }
</script>

<div
  in:fade
  class="notification is-light"
  class:is-primary={!notification.error}
  class:is-danger={notification.error}
>
  <button class="delete" on:click={onClose} />
  {notification.text}
</div>
