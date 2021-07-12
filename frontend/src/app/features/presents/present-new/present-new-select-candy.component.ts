import { Component, EventEmitter, Input, Output } from '@angular/core'
import { Observable } from 'rxjs'

import { CandyGateway, Candy } from 'app/core/api/candy.gateway'
import { PresentItem } from 'app/features/presents/service/present.model'
import { toMap } from 'app/core/utils'

@Component({
  selector: 'app-present-new-select-candy',
  templateUrl: './present-new-select-candy.component.html',
})
export class PresentNewSelectCandyComponent {
  items: Map<Candy['id'], PresentItem> = new Map()

  @Output()
  changedCount = new EventEmitter<PresentItem>()

  candies$: Observable<Candy[]> = this.candyGateway.list()

  @Input()
  set selectedItems(items: PresentItem[]) {
    this.items = toMap(items, (i) => i.candy.id)
  }

  constructor(private candyGateway: CandyGateway) {}

  onItemChange(candy: Candy, count: number) {
    this.changedCount.emit({
      candy,
      count,
    })
  }

  isSelected(candy: Candy): boolean {
    return this.items.has(candy.id)
  }
}
