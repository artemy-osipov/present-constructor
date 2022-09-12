<script lang="ts">
  import FaPen from 'svelte-icons/fa/FaPen.svelte'
  import FaTrash from 'svelte-icons/fa/FaTrash.svelte'
  import { useForm } from 'svelte-use-form'

  import { goto } from '$app/navigation'
  import type { Candy } from '$lib/candy/candy.model'
  import { candyRepository } from '$lib/candy/candy.repository'
  import ModalDelete from '$lib/components/ModalDelete.svelte'
  import { isMobile } from '$lib/utils/responsive.utils'
  import CandyEditFields from './CandyEditFields.svelte'

  export let candy: Candy

  const form = useForm()
  const data: Candy = {
    ...candy,
  }

  let deleteModalActive: boolean

  async function onSave() {
    $form.touched = true
    if ($form.valid) {
      await candyRepository.update(data)
      await goto('/candies')
    }
  }

  async function onDelete() {
    await candyRepository.delete(candy.id)
    await goto('/candies')
  }
</script>

<form use:form on:submit|preventDefault={onSave}>
  <h1 class="title">
    {candy.name}
  </h1>

  <CandyEditFields {form} {data} />

  <div class="field buttons">
    <button
      type="button"
      class="button is-primary"
      class:is-fullwidth={$isMobile}
      on:click={onSave}
    >
      <span class="icon">
        <FaPen />
      </span>
      <span>Сохранить</span>
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
</form>

<ModalDelete {onDelete} bind:active={deleteModalActive} />
