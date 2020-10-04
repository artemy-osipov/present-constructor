import { Pipe, PipeTransform } from '@angular/core'

@Pipe({
  name: 'price',
})
export class PricePipe implements PipeTransform {
  transform(value: number, args?: any): string {
    const nonBreakingSpace = '\u00A0'
    const rubleSign = '\u20BD'
    return value.toFixed(2) + nonBreakingSpace + rubleSign
  }
}
