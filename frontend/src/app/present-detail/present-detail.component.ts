import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Present } from 'app/shared/model/present.model';
import { PresentApi } from 'app/shared/services/present.api.service';

@Component({
  selector: 'app-present-detail',
  templateUrl: './present-detail.component.html'
})
export class PresentDetailComponent {
  present: Present = new Present({});

  constructor(private route: ActivatedRoute, public presentApi: PresentApi) {
    this.route.params.subscribe(params => {
      presentApi
        .get(params['id'])
        .subscribe(present => (this.present = present));
    });
  }
}
