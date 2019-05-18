import { Candy } from 'app/shared/model/candy.model';

export class PresentItem {
  constructor(public candy: Candy, public count: number) {}
}
