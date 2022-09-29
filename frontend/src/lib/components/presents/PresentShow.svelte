<script lang="ts">
  import { goto } from '$app/navigation'
  import { formatView } from '$lib/candy/candy.model'
  import CandyCard from '$lib/components/candy/CandyCard.svelte'
  import ModalDelete from '$lib/components/ModalDelete.svelte'
  import { presentGateway } from '$lib/present/present.api'
  import { costByItemsView, type PresentView } from '$lib/present/present.model'
  import { presentRepository } from '$lib/present/present.repository'
  import { isMobile } from '$lib/utils/responsive.utils'
  import { formatPrice } from '$lib/utils/string.utils'
  import FaCopy from 'svelte-icons/fa/FaCopy.svelte'
  import FaFile from 'svelte-icons/fa/FaFile.svelte'
  import FaTrash from 'svelte-icons/fa/FaTrash.svelte'

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
    rel="external"
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
    rel="external"
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
          <CandyCard
            candy={item.candy}
            count={item.count}
            expanded={false}
            allowToggle={true}
          />
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
            <td>{formatView(item.candy)}</td>
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
