import { readable } from 'svelte/store'
import { browser } from '$app/environment'

const mobileMediaQuery: MediaQueryList | undefined = browser
  ? window.matchMedia('screen and (max-width: 768px)')
  : undefined

export const isMobile = readable(
  mobileMediaQuery?.matches || false,
  function start(set) {
    const handleMobileQuery = function (evt: MediaQueryListEvent) {
      set(evt.matches)
    }

    if (mobileMediaQuery?.addEventListener) {
      mobileMediaQuery.addEventListener('change', handleMobileQuery)
    } else {
      // TODO: use legacy mode https://github.com/sveltejs/kit/issues/12
      mobileMediaQuery?.addListener(handleMobileQuery)
    }

    return function stop() {
      if (mobileMediaQuery?.removeEventListener) {
        mobileMediaQuery.removeEventListener('change', handleMobileQuery)
      } else {
        // TODO: use legacy mode https://github.com/sveltejs/kit/issues/12
        mobileMediaQuery?.removeListener(handleMobileQuery)
      }
    }
  }
)
