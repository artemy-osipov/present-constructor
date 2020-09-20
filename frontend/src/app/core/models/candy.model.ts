import { ID } from '@datorama/akita';

export interface Candy {
  id: ID;
  name: string;
  firm: string;
  price: number;
  order: number;
}
