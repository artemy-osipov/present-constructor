<script lang="ts">
  import { createEventDispatcher, onMount } from 'svelte'
  import { firstValueFrom } from 'rxjs'
  import type { Candy } from '$lib/data/candy.model'
  import type { PresentItem } from '$lib/data/present.model'
  import { toMap } from '$lib/utils/collection.utils'
  import { isMobile } from '$lib/utils/responsive.utils'
  import { formatPrice } from '$lib/utils/string.utils'
  import PresentItemCount from './PresentItemCount.svelte'
  import { candyRepository } from '$lib/data/candy.repository'

  export let items: PresentItem[]
  export let highlightSelected = false
  export let skipEmpty = true

  type Events = { change: PresentItem }
  const dispatch = createEventDispatcher<Events>()

  let candies: Candy[] = []
  $: itemMap = toMap(items, 'candyId')

  onMount(async () => {
    await candyRepository.fetch()
    candies = await firstValueFrom(candyRepository.candies)
  })

  function onChangeItem(item: PresentItem) {
    dispatch('change', item)
  }
</script>

{#if $isMobile}
  <div class="columns is-multiline">
    {#each candies as candy (candy.id)}
      {#if !skipEmpty || itemMap.has(candy.id)}
        <div class="column">
          <div
            class="card"
            class:has-background-primary={highlightSelected &&
              itemMap.has(candy.id)}
          >
            <header class="card-header">
              <div class="card-header-title">{candy.name}</div>
            </header>
            <div class="card-content">
              <div class="content">
                <div><strong>Производитель: </strong>{candy.firm}</div>
                <div><strong>Цена: </strong>{formatPrice(candy.price)}</div>
              </div>
            </div>
            <footer class="card-footer card-footer-item">
              <PresentItemCount
                count={itemMap.get(candy.id)?.count || 0}
                on:newValue={(event) =>
                  onChangeItem({
                    candyId: candy.id,
                    count: event.detail,
                  })}
              />
            </footer>
          </div>
        </div>
      {/if}
    {/each}
  </div>
{:else}
  <table class="table is-striped is-hoverable is-fullwidth">
    <thead>
      <tr>
        <th>Название</th>
        <th>Производитель</th>
        <th>Цена</th>
        <th />
      </tr>
    </thead>

    <tbody>
      {#each candies as candy (candy.id)}
        {#if !skipEmpty || itemMap.has(candy.id)}
          <tr
            class:has-background-primary={highlightSelected &&
              itemMap.has(candy.id)}
          >
            <td>{candy.name}</td>
            <td>{candy.firm}</td>
            <td>{formatPrice(candy.price)}</td>
            <td>
              <PresentItemCount
                count={itemMap.get(candy.id)?.count || 0}
                on:newValue={(event) =>
                  onChangeItem({
                    candyId: candy.id,
                    count: event.detail,
                  })}
              />
            </td>
          </tr>
        {/if}
      {/each}
    </tbody>
  </table>
{/if}