import { Candy } from 'app/shared/model/candy.model';

export class PresentItem {
  constructor(
    public candy: Candy,
    public count: number) {
  }
}

export class Present {
  id: string;
  name: string;
  price: number;
  date: Date;
  items: PresentItem[] = [];

  constructor(obj?: Object) {
    if (obj === undefined) {
      return;
    }

    this.id = obj['id'];
    this.name = obj['name'] && obj['name'].trim();
    this.price = +obj['price'];
    this.date = obj['date'] && new Date(obj['date']);
    this.items = obj['items'] && obj['items'].map(i => new PresentItem(new Candy(i.candy), i.count));
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
