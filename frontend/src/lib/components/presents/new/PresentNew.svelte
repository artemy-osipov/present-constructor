<script lang="ts">
  import { candyRepository } from '$lib/candy/candy.repository'
  import CollapsibleCard from '$lib/components/CollapsibleCard.svelte'
  import ErrorHint from '$lib/components/ErrorHint.svelte'
  import FieldError from '$lib/components/FieldError.svelte'
  import ScrollNav from '$lib/components/ScrollNav.svelte'
  import { notificationStore } from '$lib/notification/notification.store'
  import type { NewPresentRequest } from '$lib/present/present.api'
  import {
    costByItems,
    type Present,
    type PresentItem,
  } from '$lib/present/present.model'
  import { presentRepository } from '$lib/present/present.repository'
  import { toMap } from '$lib/utils/collection.utils'
  import { min, numberFormat } from '$lib/utils/number.validators'
  import { formatPrice } from '$lib/utils/string.utils'
  import { onMount } from 'svelte'
  import FaPlus from 'svelte-icons/fa/FaPlus.svelte'
  import { maxLength, required, useForm, validators } from 'svelte-use-form'
  import PresentItemList from './PresentItemList.svelte'
  import SelectCandy from './SelectCandy.svelte'

  export let source: Present | undefined

  let data: NewPresentRequest = initData()

  function initData(): NewPresentRequest {
    return {
      name: '',
      price: 1,
      items: [],
    }
  }

  const form = useForm()
  let processing = false
  let saveButtonElement: HTMLElement
  let cost: number
  $: cost = costByItems(data.items, candyRepository.queryByItems(data.items))
  $: emptyItems = data.items.length === 0

  onMount(async () => {
    await candyRepository.fetch()
    if (source) {
      const activeSourceCandies = toMap(
        candyRepository.queryByItems(source.items),
        'id'
      )

      data = {
        ...source,
        items: source.items.filter((i) => activeSourceCandies.has(i.candyId)),
      }
    }
  })

  function onChangeItem(item: PresentItem) {
    if (item.count > 0) {
      const currentItem = data.items.find((i) => i.candyId === item.candyId)
      if (currentItem) {
        currentItem.count = item.count
      } else {
        data.items.push(item)
      }
      data.items = data.items
    } else {
      data.items = data.items.filter((i) => i.candyId !== item.candyId)
    }
  }

  async function onSave() {
    $form.touched = true
    if (!$form.valid || emptyItems) {
      return
    }
    processing = true
    await presentRepository.add(data)
    processing = false
    notificationStore.addInfo(`Добавлен подарок: ${data.name}`)
    resetForm()
  }

  function resetForm() {
    data = initData()
    $form.touched = false
  }
</script>

<h1 class="title">Новый подарок</h1>

<form use:form on:submit|preventDefault={onSave}>
  <div class="field">
    <label class="label" for="name">Название</label>
    <div class="control">
      <input
        class="input"
        name="name"
        bind:value={data.name}
        use:validators={[required, maxLength(50)]}
      />
    </div>
    <FieldError field={$form.name} />
  </div>
  <div class="field">
    <label class="label" for="price">Цена</label>
    <div class="control">
      <input
        class="input"
        type="number"
        placeholder="Пример: 123.12"
        name="price"
        bind:value={data.price}
        use:validators={[required, min(1), numberFormat(6, 2)]}
      />
    </div>
    <FieldError field={$form.price} />
  </div>
  <div class="field">
    <div class="control">
      <button
        type="button"
        class="button is-primary"
        class:is-loading={processing}
        on:click={onSave}
        bind:this={saveButtonElement}
      >
        <span class="icon">
          <FaPlus />
        </span>
        <span>Добавить</span>
      </button>
    </div>
  </div>
  <div class="field">
    <div class="label">Себестоимость</div>
    <div class="control">
      {formatPrice(cost)}
    </div>
  </div>
  <div class="field">
    <div class="label">Состав</div>

    <CollapsibleCard expanded={false} disabled={emptyItems}>
      <span slot="title">Позиций: {data.items.length}</span>
      <div class="content">
        <PresentItemList
          items={data.items}
          on:change={(event) => onChangeItem(event.detail)}
        />
      </div>
    </CollapsibleCard>
    {#if $form.touched && emptyItems}
      <ErrorHint message="отсутствует" />
    {/if}
  </div>
</form>

<SelectCandy
  items={data.items}
  on:change={(event) => onChangeItem(event.detail)}
/>

<ScrollNav
  message={`Себестоимость: ${formatPrice(cost)}, Позиций: ${data.items.length}`}
  anchorElement={saveButtonElement}
/>
