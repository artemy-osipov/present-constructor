<script lang="ts">
  import FaPlus from 'svelte-icons/fa/FaPlus.svelte'
  import { useForm } from 'svelte-use-form'

  import { goto } from '$app/navigation'
  import { candyRepository } from '$lib/data/candy.repository'
  import { isMobile } from '$lib/utils/responsive.utils'
  import type { NewCandyRequest } from '$lib/api/candy.api'
  import CandyEditFields from './CandyEditFields.svelte'

  const form = useForm()
  const data: NewCandyRequest = {
    name: '',
    firm: '',
    price: 1,
    order: 0,
  }

  async function onAdd() {
    $form.touched = true
    if ($form.valid) {
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
      on:click={onAdd}
    >
      <span class="icon">
        <FaPlus />
      </span>
      <span>Добавить</span>
    </button>
  </div>
</form>
