import { DOCUMENT } from '@angular/common';
import { Component, Inject } from '@angular/core';

@Component({
  selector: 'app-link-border',
  templateUrl: './link-border.component.html',
  styleUrls: ['./link-border.component.css']
})
export class LinkBorderComponent {

  constructor(@Inject(DOCUMENT) private document: Document) { }

  scrollToTop() {
    this.document.body.scrollIntoView(true);
  }

  scrollToBottom() {
    this.document.body.scrollIntoView(false);
  }
}
