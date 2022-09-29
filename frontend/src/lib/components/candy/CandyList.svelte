<script lang="ts">
  import { candyRepository } from '$lib/candy/candy.repository'
  import Loader from '$lib/components/Loader.svelte'
  import { isMobile } from '$lib/utils/responsive.utils'
  import { formatPrice } from '$lib/utils/string.utils'
  import { onMount } from 'svelte'
  import FaEye from 'svelte-icons/fa/FaEye.svelte'
  import CandyCard from './CandyCard.svelte'

  const candies = candyRepository.activeCandies
  const loading = candyRepository.listPending

  onMount(() => {
    candyRepository.fetch()
  })
</script>

{#if $loading}
  <Loader />
{:else if $isMobile}
  <div class="columns is-multiline">
    {#each $candies as candy (candy.id)}
      <div class="column">
        <a href="/candies/{candy.id}">
          <CandyCard {candy} />
        </a>
      </div>
    {/each}
  </div>
{:else}
  <table class="table is-fullwidth is-striped is-hoverable">
    <thead>
      <tr>
        <th>Название</th>
        <th>Производитель</th>
        <th>Цена</th>
        <th>Порядок</th>
        <th />
      </tr>
    </thead>
    <tbody>
      {#each $candies as candy (candy.id)}
        <tr>
          <td>{candy.name}</td>
          <td>{candy.firm}</td>
          <td>{formatPrice(candy.price)}</td>
          <td>{candy.order}</td>
          <td>
            <a href="/candies/{candy.id}" class="button is-text">
              <span class="icon">
                <FaEye />
              </span>
            </a>
          </td>
        </tr>
      {/each}
    </tbody>
  </table>
{/if}
