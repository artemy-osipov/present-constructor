export function nonNullable<T>(x: T | null | undefined): x is NonNullable<T> {
  return x !== null && x !== undefined
}
