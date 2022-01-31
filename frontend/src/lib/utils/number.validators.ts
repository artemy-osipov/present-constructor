import type { Validator } from 'svelte-use-form'

function getFractionLength(n: number): number {
  return (n.toString().split('.')[1] || []).length
}

export function maxFractionLength(max: number): Validator {
  return (value) => {
    return getFractionLength(+value) <= max ? null : { maxFractionLength: {} }
  }
}

export function min(min: number): Validator {
  return (value) => {
    return +value >= min ? null : { min }
  }
}
