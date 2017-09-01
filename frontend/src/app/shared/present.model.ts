import { Candy } from 'app/shared/candy.model';

export class Present {
  id: string;
  name: string;
  price: number;
  date: Date;
  items: PresentItem[] = [];

  get cost(): number {
    return this.items
      .map(i => i.candy.price * i.count)
      .reduce((a, b) => a + b, 0);
  }

  hasCandy(candy: Candy): boolean {
    return this.items.find(item => {
      return item.candy.id === candy.id;
    }) !== undefined;
  }
}

export class PresentItem {
  constructor(
    public candy: Candy,
    public count: number) {
  }
}
