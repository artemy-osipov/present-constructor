<script lang="ts">
  import { formatView, type Candy } from '$lib/candy/candy.model'
  import { candyRepository } from '$lib/candy/candy.repository'
  import CandyCard from '$lib/components/candy/CandyCard.svelte'
  import Loader from '$lib/components/Loader.svelte'
  import type { PresentItem } from '$lib/present/present.model'
  import { toMap } from '$lib/utils/collection.utils'
  import { isMobile } from '$lib/utils/responsive.utils'
  import { formatPrice } from '$lib/utils/string.utils'
  import { firstValueFrom } from 'rxjs'
  import { createEventDispatcher, onMount } from 'svelte'
  import PresentItemCount from './PresentItemCount.svelte'

  export let items: PresentItem[]
  export let highlightSelected = false
  export let skipEmpty = true

  type Events = { change: PresentItem }
  const dispatch = createEventDispatcher<Events>()

  let candies: Candy[] = []
  const loading = candyRepository.listPending
  $: itemMap = toMap(items, 'candyId')

  onMount(async () => {
    await candyRepository.fetch()
    candies = await firstValueFrom(candyRepository.activeCandies)
  })

  function onChangeItem(item: PresentItem) {
    dispatch('change', item)
  }
</script>

{#if $loading}
  <Loader />
{:else if $isMobile}
  <div class="columns is-multiline">
    {#each candies as candy (candy.id)}
      {#if !skipEmpty || itemMap.has(candy.id)}
        <div class="column">
          <CandyCard
            class={(highlightSelected &&
              itemMap.has(candy.id) &&
              'has-background-primary') ||
              ''}
            {candy}
            expanded={false}
            allowToggle={true}
          >
            <div slot="footer">
              <PresentItemCount
                count={itemMap.get(candy.id)?.count || 0}
                on:newValue={(event) =>
                  onChangeItem({
                    candyId: candy.id,
                    count: event.detail,
                  })}
              />
            </div>
          </CandyCard>
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
            <td>{formatView(candy)}</td>
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
