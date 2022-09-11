<script lang="ts">
  import FaFile from 'svelte-icons/fa/FaFile.svelte'
  import FaCopy from 'svelte-icons/fa/FaCopy.svelte'
  import FaTrash from 'svelte-icons/fa/FaTrash.svelte'
  import { goto } from '$app/navigation'
  import { costByItemsView } from '$lib/data/present.model'
  import type { PresentView } from '$lib/data/present.model'
  import { formatPrice } from '$lib/utils/string.utils'
  import { presentRepository } from '$lib/data/present.repository'
  import ModalDelete from '$lib/components/ModalDelete.svelte'
  import { presentGateway } from '$lib/api/present.api'
  import { isMobile } from '$lib/utils/responsive.utils'

  export let present: PresentView

  let deleteModalActive = false

  async function gotoNewPresentPage() {
    await goto(`/presents/new?source=${present.id}`)
  }

  async function onDelete() {
    await presentRepository.delete(present.id)
    await goto('/presents')
  }
</script>

<h1 class="title">{present.name}</h1>

<div class="field">
  <div class="label">Цена</div>
  <div>{formatPrice(present.price)}</div>
</div>

<div class="field">
  <div class="label">Себестоимость</div>
  <div>{formatPrice(costByItemsView(present.items))}</div>
</div>

<div class="field">
  <div class="label">Позиций</div>
  <div>{present.items.length}</div>
</div>

<div class="field buttons">
  <a
    class="button is-link"
    class:is-fullwidth={$isMobile}
    href={presentGateway.privateReportLocation(present.id)}
  >
    <span class="icon">
      <FaFile />
    </span>
    <span>Прайс</span>
  </a>
  <a
    class="button is-link"
    class:is-fullwidth={$isMobile}
    href={presentGateway.publicReportLocation(present.id)}
  >
    <span class="icon">
      <FaFile />
    </span>
    <span>Прайс для розницы</span>
  </a>
  <button
    type="button"
    class="button"
    class:is-fullwidth={$isMobile}
    on:click={gotoNewPresentPage}
  >
    <span class="icon">
      <FaCopy />
    </span>
    <span>Создать на основе</span>
  </button>
  <button
    type="button"
    class="button"
    class:is-fullwidth={$isMobile}
    on:click={() => {
      deleteModalActive = true
    }}
  >
    <span class="icon">
      <FaTrash />
    </span>
    <span>Удалить</span>
  </button>
</div>

<div class="field">
  <div class="label">Состав</div>

  {#if $isMobile}
    <div class="columns is-multiline">
      {#each present.items as item (item.candy.id)}
        <div class="column">
          <div class="card">
            <header class="card-header">
              <p class="card-header-title">{item.candy.name}</p>
            </header>
            <div class="card-content">
              <div class="content">
                <p><strong>Производитель: </strong>{item.candy.firm}</p>
                <p><strong>Цена: </strong>{formatPrice(item.candy.price)}</p>
                <p><strong>Количество: </strong>{item.count}</p>
              </div>
            </div>
          </div>
        </div>
      {/each}
    </div>
  {:else}
    <table class="table is-striped is-hoverable">
      <thead>
        <tr>
          <th>Название</th>
          <th>Производитель</th>
          <th>Цена</th>
          <th>Количество</th>
        </tr>
      </thead>

      <tbody>
        {#each present.items as item (item.candy.id)}
          <tr>
            <td>{item.candy.name}</td>
            <td>{item.candy.firm}</td>
            <td>{formatPrice(item.candy.price)}</td>
            <td>{item.count}</td>
          </tr>
        {/each}
      </tbody>
    </table>
  {/if}
</div>

<ModalDelete {onDelete} bind:active={deleteModalActive} />
