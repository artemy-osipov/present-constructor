import { Component } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';

import { PresentStore } from 'app/shared/services/present.store';

import { Candy } from 'app/shared/candy.model';
import { Present, PresentItem } from 'app/shared/present.model';

@Component({
  selector: 'app-present-detail',
  templateUrl: './present-detail.component.html',
  styleUrls: ['./present-detail.component.css']
})
export class PresentDetailComponent  {
  present: Present = new Present();

  constructor(private route: ActivatedRoute, private presentStore: PresentStore) {
    this.route.params.subscribe(params => {
      presentStore.get(params['id']).subscribe(present => {
        this.present = new Present(present);
      });
    });
  }
}
