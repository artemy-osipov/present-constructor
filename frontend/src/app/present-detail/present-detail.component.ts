import { Component } from '@angular/core';

import { Candy } from 'app/shared/candy.model';
import { Present, PresentItem } from 'app/shared/present.model';

@Component({
  selector: 'app-present-detail',
  templateUrl: './present-detail.component.html',
  styleUrls: ['./present-detail.component.css']
})
export class PresentDetailComponent  {
  present: Present;

  constructor() {
    this.present = this.generatePresent();
  }

  private generatePresent(): Present {
    const present = new Present();
    present.id = '1';
    present.name = 'name';
    present.price = 123.12;
    present.date = new Date();
    present.items = this.generateItems(10);

    return present;
  }

  private generateItems(count: number): PresentItem[] {
    const items: PresentItem[] = [];

    for (let i = 1; i <= count; i++) {
      items.push(new PresentItem(this.generateCandy(i), i));
    }

    return items;
  }

  private generateCandy(i: number): Candy {
    const candy = new Candy();
    candy.id = i.toString();
    candy.name = 'Название ' + i;
    candy.firm = 'Производитель ' + i;
    candy.price = i;
    candy.order = i;

    return candy;
  }
}
