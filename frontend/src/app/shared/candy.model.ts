export class Candy {
  id: string;
  name: string;
  firm: string;
  price: number;
  order: number;

  constructor(obj?: Object) {
    if (obj === undefined) {
      return;
    }

    this.id = obj['id'];
    this.name = obj['name'] && obj['name'].trim();
    this.firm = obj['firm'] && obj['firm'].trim();
    this.price = +obj['price'];
    this.order = +obj['order'];
  }
}
