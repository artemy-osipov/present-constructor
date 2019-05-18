import { Candy } from 'app/shared/model/candy.model';
import { PresentItem } from 'app/shared/model/present-item.model';

export class Present {
  id: string;
  name: string;
  price: number;
  date: Date;
  items: PresentItem[] = [];

  constructor(src: any) {
    this.id = src.id;
    this.name = src.name && src.name.trim();
    this.price = +src.price;
    this.date = src.date && new Date(src.date);
    this.items =
      (src.items &&
        src.items.map(
          (item: any) => new PresentItem(new Candy(item.candy), item.count)
        )) ||
      [];
  }

  get cost(): number {
    return +this.items
      .map(i => i.candy.price * i.count)
      .reduce((a, b) => a + b, 0)
      .toFixed(2);
  }

  get candies(): Candy[] {
    return this.items.map(i => i.candy);
  }
}
