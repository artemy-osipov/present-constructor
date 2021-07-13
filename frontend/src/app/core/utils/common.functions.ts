export function nonNullable<T>(x: T | null | undefined): x is NonNullable<T> {
  return x !== null && x !== undefined
}

export function toMap<T, K>(xs: T[], keyExtractor: (x: T) => K): Map<K, T> {
  return new Map(xs.map((x) => [keyExtractor(x), x]))
}

export function isScrolledIntoView(el: HTMLElement): boolean {
  var rect = el.getBoundingClientRect()
  var elemTop = rect.top
  var elemBottom = rect.bottom

  return elemTop >= 0 && elemBottom <= window.innerHeight
}
