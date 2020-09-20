import { Candy } from 'app/core/models/candy.model'
import { Present } from 'app/core/models/present.model';

export const mock: { candies: Candy[], presents: Present[]} = {
  candies: [],
  presents: []
};

for (let index = 0; index < 41; index++) {
  mock.candies.push(sampleCandy(index.toString()));
}
for (let index = 0; index < 11; index++) {
  mock.presents.push(samplePresent(index.toString()));
}

function sampleCandy(id: string): Candy {
  return {
      id: id,
      name: 'some name ' + id,
      firm: 'some firm ' + id,
      price: 123.32,
      order: parseInt(id, 10)
  };
}

function samplePresent(id: string): Present {
  return new Present({
      id: id,
      name: 'some name ' + id,
      price: 123.32,
      date: new Date,
      items: [
          {
              candy: sampleCandy('1'),
              count: 1
          },
          {
              candy: sampleCandy('3'),
              count: 3
          },
          {
              candy: sampleCandy('10'),
              count: 10
          }
      ]
  });
}
