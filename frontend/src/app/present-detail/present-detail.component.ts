import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Candy } from 'app/shared/model/candy.model';
import { Present, PresentItem } from 'app/shared/model/present.model';
import { PresentApi } from 'app/shared/services/present.api.service';

@Component({
  selector: 'app-present-detail',
  templateUrl: './present-detail.component.html'
})
export class PresentDetailComponent  {
  present: Present = new Present({});

  constructor(private route: ActivatedRoute, public presentSerive: PresentApi) {
    this.route.params.subscribe(params => {
      presentSerive.get(params['id']).subscribe(present => {
        this.present = new Present(present);
      });
    });
  }
}
