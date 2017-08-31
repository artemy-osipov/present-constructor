import { Candy } from 'app/shared/candy.model';

export class Present {
  constructor(
    public id: string,
    public name: string,
    public price: number,
    public date: Date,
    public items: PresentItem[]) {
  }

  get cost(): number {
    return this.items
      .map(i => i.candy.price * i.count)
      .reduce((a, b) => a + b, 0);
  }

  hasCandy(candy: Candy): boolean {
    return this.items.find(pi => {
      return pi.candy.id === candy.id;
    }) !== undefined;
  }
}

export class PresentItem {
  constructor(
    public candy: Candy,
    public count: number) {
  }
}
