import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core'
import { Observable } from 'rxjs'

import { Candy } from 'app/core/models/candy.model'
import { CandyQuery, CandyService } from 'app/core/services/candy'

@Component({
  selector: 'app-present-new-select-candy',
  templateUrl: './present-new-select-candy.component.html',
})
export class PresentNewSelectCandyComponent implements OnInit {
  @Input()
  selectedCandies: Candy[] = []
  @Output()
  selected = new EventEmitter<Candy>()
  @Output()
  unselected = new EventEmitter<Candy>()
  candies$: Observable<Candy[]> = this.candyQuery.sortedList()

  constructor(
    private candyService: CandyService,
    private candyQuery: CandyQuery
  ) {}

  ngOnInit() {
    this.candyService.fetchList().subscribe()
  }

  select(candy: Candy): void {
    if (this.isSelected(candy)) {
      this.unselected.emit(candy)
    } else {
      this.selected.emit(candy)
    }
  }

  isSelected(candy: Candy): boolean {
    return this.selectedCandies.find(this.sameId(candy)) !== undefined
  }

  private sameId(candy: Candy): (c: Candy) => boolean {
    return (c) => {
      return c.id === candy.id
    }
  }
}
