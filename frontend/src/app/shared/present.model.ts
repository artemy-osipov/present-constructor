import { Candy } from 'app/shared/candy.model';

export class Present {
  constructor(
    public id: string,
    public name: string,
    public price: number,
    public items: Candy[]) {
  }
}
