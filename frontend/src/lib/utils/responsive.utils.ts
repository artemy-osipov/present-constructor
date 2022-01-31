import { readable } from 'svelte/store'
import { browser } from '$app/env'

const mobileMediaQuery: MediaQueryList | undefined = browser
  ? window.matchMedia('screen and (max-width: 768px)')
  : undefined

export const isMobile = readable(
  mobileMediaQuery?.matches || false,
  function start(set) {
    const handleMobileQuery = function (evt: MediaQueryListEvent) {
      set(evt.matches)
    }

    mobileMediaQuery?.addEventListener('change', handleMobileQuery)

    return function stop() {
      mobileMediaQuery?.removeEventListener('change', handleMobileQuery)
    }
  }
)
