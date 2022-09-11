<script lang="ts">
  import { page } from '$app/stores'
  import Login from '$lib/components/auth/Login.svelte'

  let redirectURL = $page.url.searchParams.get('redirectURL') || '/about'
</script>

<section class="hero is-dark is-fullheight">
  <div class="hero-body">
    <div class="container">
      <div class="columns is-centered">
        <div class="column is-5-tablet is-4-desktop is-4-widescreen">
          <Login {redirectURL} />
        </div>
      </div>
    </div>
  </div>
</section>
