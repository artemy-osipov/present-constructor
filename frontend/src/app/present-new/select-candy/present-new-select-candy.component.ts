import {
  Component,
  EventEmitter,
  Input,
  Output,
  OnInit
} from '@angular/core';
import { Observable } from 'rxjs';

import { Candy } from 'app/shared/model/candy.model';
import { CandyQuery, CandyService } from 'app/shared/services/candy';

@Component({
  selector: 'app-present-new-select-candy',
  templateUrl: './present-new-select-candy.component.html'
})
export class PresentNewSelectCandyComponent implements OnInit {
  @Input()
  selectedCandies: Candy[] = [];
  @Output()
  selected = new EventEmitter<Candy>();
  @Output()
  unselected = new EventEmitter<Candy>();
  candies$: Observable<Candy[]> = this.candyQuery.sortedList();

  constructor(
    private candyService: CandyService,
    private candyQuery: CandyQuery
  ) { }

  ngOnInit() {
    this.candyService.list().subscribe();
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
