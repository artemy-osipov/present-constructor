<script lang="ts">
  import { onMount } from 'svelte'
  import FaEye from 'svelte-icons/fa/FaEye.svelte'
  import { candyRepository } from '$lib/data/candy.repository'
  import { isMobile } from '$lib/utils/responsive.utils'
  import { formatPrice } from '$lib/utils/string.utils'

  let candies = candyRepository.candies

  onMount(() => {
    candyRepository.fetch()
  })
</script>

{#if $isMobile}
  <div class="columns is-multiline">
    {#each $candies as candy (candy.id)}
      <div class="column">
        <a href="/candies/{candy.id}">
          <div class="card">
            <header class="card-header">
              <div class="card-header-title">{candy.name}</div>
            </header>
            <div class="card-content">
              <div class="content">
                <div><strong>Производитель: </strong>{candy.firm}</div>
                <div><strong>Цена: </strong>{formatPrice(candy.price)}</div>
              </div>
            </div>
          </div>
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
