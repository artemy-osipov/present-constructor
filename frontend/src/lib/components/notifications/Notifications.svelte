<script lang="ts">
  import type { Notification as INotification } from '$lib/notification/notification.model'
  import { notificationStore } from '$lib/notification/notification.store'
  import { isMobile } from '$lib/utils/responsive.utils'
  import { map, type Observable } from 'rxjs'
  import Notification from './Notification.svelte'

  let notifications: Observable<INotification[]> = notificationStore.list.pipe(
    map((ns) => ns.reverse()),
    map((ns) => ns.slice(0, 3))
  )
</script>

<div
  class="notifications"
  class:notifications-mobile={$isMobile}
  class:notifications-desktop={!$isMobile}
>
  {#each $notifications as notification}
    <div class="notification-container">
      <Notification {notification} />
    </div>
  {/each}
</div>

<style>
  .notifications {
    position: fixed;
    z-index: 30;
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    padding: 1rem;
  }
  .notifications-desktop {
    right: 0;
    width: 33%;
  }
  .notifications-mobile {
    left: 0;
    right: 0;
    bottom: 0;
  }
</style>
