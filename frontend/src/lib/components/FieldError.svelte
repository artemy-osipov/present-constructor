<script lang="ts">
  import type { FormControl } from 'svelte-use-form'
  import ErrorHint from './ErrorHint.svelte'

  export let field: FormControl | undefined

  function resolveMessage(type: string, details: unknown): string {
    switch (type) {
      case 'required':
        return 'Обязательно для заполнения'
      case 'min':
        return `Меньше допустимого (${details})`
      case 'maxFractionLength':
        return 'Неверный формат'
      case 'maxLength':
        return `Превышает максимальную длину (${details})`
      default:
        return '' + details
    }
  }
</script>

{#if field && field.touched}
  {#each Object.entries(field.errors) as [type, details]}
    <ErrorHint message={resolveMessage(type, details)} />
  {/each}
{/if}
