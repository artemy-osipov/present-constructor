import { Component, EventEmitter, Input, Output } from '@angular/core';

import { Candy } from 'app/shared/model/candy.model';
import { CandyStore } from 'app/shared/services/candy.store';

@Component({
  selector: 'app-present-new-select-candy',
  templateUrl: './present-new-select-candy.component.html'
})
export class PresentNewSelectCandyComponent {
  @Input() selectedCandies: Candy[] = [];
  @Output() selected = new EventEmitter<Candy>();
  @Output() unselected = new EventEmitter<Candy>();

  constructor(private candyStore: CandyStore) {
    this.candyStore.fetch();
  }

  select(candy: Candy): void {
    if (this.isSelected(candy)) {
      this.unselected.emit(candy);
    } else {
      this.selected.emit(candy);
    }
  }

  private isSelected(candy: Candy): boolean {
    return this.selectedCandies.find(this.sameId(candy)) !== undefined;
  }

  private sameId(candy: Candy): (c: Candy) => boolean {
    return c => {
      return c.id === candy.id;
    };
  }
}
