export class Candy {
  id: string;
  name: string;
  firm: string;
  price: number;
  order: number;

  constructor(src: any) {
    this.id = src.id;
    this.name = src.name && src.name.trim();
    this.firm = src.firm && src.firm.trim();
    this.price = +src.price;
    this.order = +src.order;
  }
}
