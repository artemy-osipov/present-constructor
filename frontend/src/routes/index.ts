/** @type {import('@sveltejs/kit').RequestHandler} */
export async function get() {
  return {
    status: 302,
    headers: {
      Location: '/about',
    },
  }
}
