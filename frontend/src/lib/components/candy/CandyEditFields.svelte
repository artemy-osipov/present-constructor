<script lang="ts">
  import { maxLength, required, useForm, validators } from 'svelte-use-form'

  import type { Candy } from '$lib/candy/candy.model'
  import FieldError from '$lib/components/FieldError.svelte'
  import { min, numberFormat } from '$lib/utils/number.validators'

  export let form: ReturnType<typeof useForm>
  export let data: Partial<Candy>
</script>

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
  <label class="label" for="firm">Производитель</label>
  <div class="control">
    <input
      class="input"
      name="firm"
      bind:value={data.firm}
      use:validators={[required, maxLength(50)]}
    />
  </div>
  <FieldError field={$form.firm} />
</div>

<div class="field">
  <label class="label" for="price">Цена</label>
  <div class="control">
    <input
      type="number"
      class="input"
      placeholder="Пример: 123.12"
      name="price"
      bind:value={data.price}
      use:validators={[required, min(1), numberFormat(6, 2)]}
    />
  </div>
  <FieldError field={$form.price} />
</div>

<div class="field">
  <label class="label" for="order">Порядок</label>
  <div class="control">
    <input
      type="number"
      class="input"
      placeholder="Пример: 123.1"
      name="order"
      bind:value={data.order}
      use:validators={[required, numberFormat(4, 2)]}
    />
  </div>
  <FieldError field={$form.order} />
</div>
