<script lang="ts">
  import FaLock from 'svelte-icons/fa/FaLock.svelte'
  import { required, useForm, validators } from 'svelte-use-form'
  import { goto } from '$app/navigation'
  import { login } from '$lib/auth/auth'
  import FieldError from '$lib/components/FieldError.svelte'

  export let redirectURL: string
  const form = useForm({ password: {} })
  let processing = false

  async function onLogin() {
    $form.touched = true
    if ($form.valid) {
      processing = true
      if (await login($form.values.password)) {
        await goto(redirectURL)
      }
      processing = false
      $form.password.errors['invalid'] = 'Неверный пароль'
    }
  }
</script>

<form use:form on:submit|preventDefault={onLogin} class="box">
  <div class="field">
    <label for="" class="label">Пароль</label>
    <div class="control has-icons-left">
      <input
        name="password"
        type="password"
        placeholder="*******"
        class="input"
        use:validators={[required]}
      />
      <span class="icon is-left">
        <FaLock />
      </span>
    </div>
    <FieldError field={$form.password} />
  </div>
  <div class="field">
    <button
      type="button"
      class="button is-success"
      class:is-loading={processing}
      on:click={onLogin}
    >
      Войти
    </button>
  </div>
</form>
