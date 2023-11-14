<script lang="ts">
  import FaTimes from 'svelte-icons/fa/FaTimes.svelte'
  import FaTrash from 'svelte-icons/fa/FaTrash.svelte'

  export let active = false
  export let onDelete: () => Promise<void>
  let processing = false

  async function onExecute() {
    processing = true
    await onDelete()
    processing = false
    closeModal()
  }

  function closeModal() {
    active = false
  }
</script>

<div class="modal" class:is-active={active}>
  <!-- svelte-ignore a11y-click-events-have-key-events -->
  <!-- svelte-ignore a11y-no-static-element-interactions -->
  <div class="modal-background" on:click={closeModal} />
  <div class="modal-content">
    <div class="box">
      <div class="content">
        <p>Удалить?</p>
      </div>

      <div class="field is-grouped">
        <div class="control">
          <button
            type="button"
            class="button is-primary"
            class:is-loading={processing}
            on:click={onExecute}
          >
            <span class="icon">
              <FaTrash />
            </span>
            <span>Да</span>
          </button>
        </div>
        <div class="control">
          <button type="button" class="button" on:click={closeModal}>
            <span class="icon">
              <FaTimes />
            </span>
            <span>Отмена</span>
          </button>
        </div>
      </div>
    </div>
  </div>
  <button
    class="modal-close is-large"
    aria-label="close"
    on:click={closeModal}
  />
</div>
