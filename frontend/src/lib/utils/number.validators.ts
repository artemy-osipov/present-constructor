import type { Validator } from 'svelte-use-form'

export function min(min: number): Validator {
  return (value) => {
    return +value >= min ? null : { min }
  }
}

export function numberFormat(
  intMaxLength: number,
  fractionMaxLength: number
): Validator {
  return (value) => {
    const [intPart, fractionPart] = value.split('.')
    return checkMaxLength(intMaxLength, intPart) &&
      checkMaxLength(fractionMaxLength, fractionPart)
      ? null
      : { numberFormat: {} }
  }
}

function checkMaxLength(maxLength: number, value: string | undefined): boolean {
  return value === undefined || value.length <= maxLength
}
