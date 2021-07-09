import { Component, EventEmitter, Input, Output } from '@angular/core'
import { Observable } from 'rxjs'

import { CandyGateway, Candy } from 'app/core/api/candy.gateway'

@Component({
  selector: 'app-present-new-select-candy',
  templateUrl: './present-new-select-candy.component.html',
})
export class PresentNewSelectCandyComponent {
  @Input()
  selectedCandies: Candy[] = []

  @Output()
  selected = new EventEmitter<Candy>()

  @Output()
  unselected = new EventEmitter<Candy>()

  candies$: Observable<Candy[]> = this.candyGateway.list()

  constructor(private candyGateway: CandyGateway) {}

  select(candy: Candy): void {
    if (this.isSelected(candy)) {
      this.unselected.emit(candy)
    } else {
      this.selected.emit(candy)
    }
  }

  isSelected(candy: Candy): boolean {
    return this.selectedCandies.find((c) => c.id === candy.id) !== undefined
  }
}
