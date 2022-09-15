<script lang="ts">
  import FaPlus from 'svelte-icons/fa/FaPlus.svelte'
  import { useForm } from 'svelte-use-form'

  import { goto } from '$app/navigation'
  import type { NewCandyRequest } from '$lib/candy/candy.api'
  import { candyRepository } from '$lib/candy/candy.repository'
  import { isMobile } from '$lib/utils/responsive.utils'
  import CandyEditFields from './CandyEditFields.svelte'

  const form = useForm()
  let processing = false
  const data: NewCandyRequest = {
    name: '',
    firm: '',
    price: 1,
    order: 0,
  }

  async function onAdd() {
    $form.touched = true
    if ($form.valid) {
      processing = true
      await candyRepository.add(data)
      await goto('/candies')
    }
  }
</script>

<form use:form on:submit|preventDefault={onAdd}>
  <h1 class="title">Новая конфета</h1>

  <CandyEditFields {form} {data} />

  <div class="field buttons">
    <button
      type="button"
      class="button is-primary"
      class:is-fullwidth={$isMobile}
      class:is-loading={processing}
      on:click={onAdd}
    >
      <span class="icon">
        <FaPlus />
      </span>
      <span>Добавить</span>
    </button>
  </div>
</form>
