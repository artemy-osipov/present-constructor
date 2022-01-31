<script lang="ts">
  import FaPen from 'svelte-icons/fa/FaPen.svelte'
  import FaTrash from 'svelte-icons/fa/FaTrash.svelte'
  import { useForm } from 'svelte-use-form'

  import { goto } from '$app/navigation'
  import { candyRepository } from '$lib/data/candy.repository'
  import type { Candy } from '$lib/data/candy.model'
  import { isMobile } from '$lib/utils/responsive.utils'
  import ModalDelete from '$lib/components/ModalDelete.svelte'
  import CandyEditFields from './CandyEditFields.svelte'

  export let candy: Candy

  const form = useForm()
  const data: Candy = {
    ...candy,
  }

  let deleteModalActive: boolean

  async function onSave() {
    if ($form.valid) {
      await candyRepository.update(data)
      goto('/candies')
    }
  }

  async function onDelete() {
    await candyRepository.delete(candy.id)
    goto('/candies')
  }
</script>

<form use:form>
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
