import { Component, Input, ViewEncapsulation } from '@angular/core';
import { NgControl } from '@angular/forms';

@Component({
  selector: 'app-validation-error',
  templateUrl: './validation-error.component.html',
  styleUrls: ['./validation-error.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ValidationErrorComponent {
  @Input() control: NgControl;
}
