import { Component } from '@angular/core';
import { Present, PresentItem } from 'app/shared/present.model';
import { Candy } from 'app/shared/candy.model';

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

  generatePresent(): Present {
    return new Present('1', 'name', 123.12, new Date(), this.generateItems(10));
  }

  private generateItems(count: number): PresentItem[] {
    const items: PresentItem[] = [];

    for (let i = 1; i <= count; i++) {
      items.push(new PresentItem(this.generateCandy(i), i));
    }

    return items;
  }

  private generateCandy(i: number): Candy {
    return new Candy(i.toString(), 'Название ' + i, 'Производитель ' + i, i, i);
  }
}
