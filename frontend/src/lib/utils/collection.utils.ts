export function toMap<T, K extends keyof T>(xs: T[], key: K): Map<T[K], T> {
  return new Map(xs.map((x) => [x[key], x]))
}
