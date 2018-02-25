import { Component, EventEmitter, Input, Output } from '@angular/core';

import { Candy } from 'app/shared/model/candy.model';
import { Present } from 'app/shared/model/present.model';
import { CandyService } from 'app/shared/services/candy.service';
import { CandyStore } from 'app/shared/services/candy.store';

@Component({
  selector: 'app-present-new-select-candy',
  templateUrl: './present-new-select-candy.component.html'
})
export class PresentNewSelectCandyComponent {
  @Input() selectedCandies: Candy[] = [];
  @Output() selected = new EventEmitter<Candy>();
  @Output() unselected = new EventEmitter<Candy>();

  constructor(private candyService: CandyService, private candyStore: CandyStore) {
    this.candyService.list().subscribe(
      candies => this.candyStore.candies = candies
    );
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
