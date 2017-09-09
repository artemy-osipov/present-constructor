import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Candy } from 'app/shared/candy.model';
import { Present, PresentItem } from 'app/shared/present.model';
import { PresentService } from 'app/shared/services/present.service';

@Component({
  selector: 'app-present-detail',
  templateUrl: './present-detail.component.html',
  styleUrls: ['./present-detail.component.css']
})
export class PresentDetailComponent  {
  present: Present = new Present();

  constructor(private route: ActivatedRoute, private presentSerive: PresentService) {
    this.route.params.subscribe(params => {
      presentSerive.get(params['id']).subscribe(present => {
        this.present = new Present(present);
      });
    });
  }
}
