<script lang="ts">
  import { maxLength, required, useForm, validators } from 'svelte-use-form'
  import FaPlus from 'svelte-icons/fa/FaPlus.svelte'
  import { onMount } from 'svelte'
  import CollapsibleCard from '$lib/components/CollapsibleCard.svelte'
  import ErrorHint from '$lib/components/ErrorHint.svelte'
  import FieldError from '$lib/components/FieldError.svelte'
  import ScrollNav from '$lib/components/ScrollNav.svelte'
  import { costByItems } from '$lib/data/present.model'
  import type { Present, PresentItem } from '$lib/data/present.model'
  import { candyRepository } from '$lib/data/candy.repository'
  import { formatPrice } from '$lib/utils/string.utils'
  import type { NewPresentRequest } from '$lib/api/present.api'
  import { toMap } from '$lib/utils/collection.utils'
  import { maxFractionLength, min } from '$lib/utils/number.validators'
  import { presentRepository } from '$lib/data/present.repository'
  import SelectCandy from './SelectCandy.svelte'
  import PresentItemList from './PresentItemList.svelte'

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
  let added = false
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
    if (!$form.valid || emptyItems) {
      $form.touched = true
      return
    }
    await presentRepository.add(data)
    data = initData()
    added = true
    setTimeout(() => (added = false), 3000)
  }
</script>

<h1 class="title">Новый подарок</h1>

{#if added}
  <div class="notification is-primary">Добавлено</div>
{/if}

<form use:form>
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
        use:validators={[required, min(1), maxFractionLength(2)]}
      />
    </div>
    <FieldError field={$form.price} />
  </div>
  <div class="field">
    <div class="control">
      <button
        type="button"
        class="button is-primary"
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
  message={'Себестоимость: ' + formatPrice(cost)}
  anchorElement={saveButtonElement}
/>
