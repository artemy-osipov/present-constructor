import { Component, OnInit } from '@angular/core'
import { Observable } from 'rxjs'

import { Candy } from 'app/core/api/candy.gateway'
import { CandyQuery, CandyService } from 'app/features/candies/state'

@Component({
  selector: 'app-candy-list',
  templateUrl: './candy-list.component.html',
})
export class CandyListComponent implements OnInit {
  candies$: Observable<Candy[]> = this.candyQuery.sortedList()

  constructor(
    private candyService: CandyService,
    private candyQuery: CandyQuery
  ) {}

  ngOnInit() {
    this.candyService.fetchList().subscribe()
  }
}
