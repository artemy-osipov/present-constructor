export function formatPrice(price: number): string {
  const nonBreakingSpace = '\u00A0'
  const rubleSign = '\u20BD'
  return price.toFixed(2) + nonBreakingSpace + rubleSign
}
