<script lang="ts">
  import { presentRepository } from '$lib/present/present.repository'
  import { isMobile } from '$lib/utils/responsive.utils'
  import { formatPrice } from '$lib/utils/string.utils'
  import { onMount } from 'svelte'
  import FaEye from 'svelte-icons/fa/FaEye.svelte'

  let presents = presentRepository.presents

  onMount(() => {
    presentRepository.fetch()
  })
</script>

{#if $isMobile}
  <div class="columns is-multiline">
    {#each $presents as present (present.id)}
      <div class="column">
        <a href="/presents/{present.id}">
          <div class="card">
            <header class="card-header">
              <div class="card-header-title">{present.name}</div>
            </header>
            <div class="card-content">
              <div class="content">
                <div><strong>Цена: </strong>{formatPrice(present.price)}</div>
                <div><strong>Позиций: </strong>{present.items.length}</div>
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
        <th>Цена</th>
        <th>Позиций</th>
        <th />
      </tr>
    </thead>
    <tbody>
      {#each $presents as present (present.id)}
        <tr>
          <td>{present.name}</td>
          <td>{formatPrice(present.price)}</td>
          <td>{present.items.length}</td>
          <td>
            <a href="/presents/{present.id}" class="button is-text">
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
