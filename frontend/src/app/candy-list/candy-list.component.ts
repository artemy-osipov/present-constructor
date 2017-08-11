import { Component, OnInit } from '@angular/core';
import { Candy } from '../shared/candy.model';

@Component({
  selector: 'app-candy-list',
  templateUrl: './candy-list.component.html',
  styleUrls: ['./candy-list.component.css']
})
export class CandyListComponent {
  candies: Candy[];

  constructor() {
    const candies: Candy[] = [];

    for (let i = 1; i < 20; i++) {
      candies.push(new Candy('f2e35882-5168-4ca5-8925-dd5f137857eb', 'Название ' + i, 'Производитель ' + i, i, i));
    }

    this.candies = candies;
  }
}
