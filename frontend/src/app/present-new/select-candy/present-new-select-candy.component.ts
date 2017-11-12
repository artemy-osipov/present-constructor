import { Component, EventEmitter, Input, Output } from '@angular/core';

import { Candy } from 'app/shared/model/candy.model';
import { Present, PresentItem } from 'app/shared/model/present.model';
import { CandyService } from 'app/shared/services/candy.service';
import { CandyStore } from 'app/shared/services/candy.store';

@Component({
  selector: 'app-present-new-select-candy',
  templateUrl: './present-new-select-candy.component.html'
})
export class PresentNewSelectCandyComponent {
  @Input() present = new Present();
  @Output() onSelect = new EventEmitter<Candy>();
  @Output() onUnselect = new EventEmitter<Candy>();

  constructor(private candyService: CandyService, private candyStore: CandyStore) {
    this.candyService.list().subscribe(
      candies => this.candyStore.candies = candies
    );
  }

  select(candy: Candy): void {
    if (this.present.hasCandy(candy)) {
      this.onUnselect.emit(candy);
    } else {
      this.onSelect.emit(candy);
    }
  }
}
