import { Component, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/platform-browser';

@Component({
  selector: 'app-link-border',
  templateUrl: './link-border.component.html',
  styleUrls: ['./link-border.component.css']
})
export class LinkBorderComponent {

  constructor(@Inject(DOCUMENT) private document) { }

  scrollToTop() {
    this.document.body.scrollIntoView(true);
  }

  scrollToBottom() {
    this.document.body.scrollIntoView(false);
  }
}
