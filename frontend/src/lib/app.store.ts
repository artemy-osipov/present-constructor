import { createStore, select, withProps } from '@ngneat/elf'
import type { Observable } from 'rxjs'

interface AppProps {
  initialized: boolean
}

const store = createStore(
  { name: 'app' },
  withProps<AppProps>({ initialized: false })
)

export const initialized: Observable<boolean> = store.pipe(
  select((state) => state.initialized)
)

export function initialize(): void {
  store.update((state) => ({
    ...state,
    initialized: true,
  }))
}
