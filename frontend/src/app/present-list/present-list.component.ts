import { Component, OnInit } from '@angular/core';

import { PresentQuery, PresentService } from 'app/shared/services/present';
import { Observable } from 'rxjs';
import { Present } from 'app/shared/model/present.model';

@Component({
  selector: 'app-present-list',
  templateUrl: './present-list.component.html'
})
export class PresentListComponent implements OnInit {
  presents$: Observable<Present[]> = this.presentQuery.sortedList();

  constructor(
    private presentService: PresentService,
    private presentQuery: PresentQuery
  ) { }

  ngOnInit() {
    this.presentService.list().subscribe();
  }
}
