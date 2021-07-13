import { Component, Input } from '@angular/core'
import { isScrolledIntoView } from 'app/core/utils'

@Component({
  selector: 'app-scroll-nav',
  templateUrl: './scroll-nav.component.html',
})
export class ScrollNavComponent {

  @Input()
  message: string = ''

  @Input()
  scrollElement!: HTMLElement

  constructor() {}

  isVisibleNav(): boolean {
    return isScrolledIntoView(this.scrollElement)
  }

  scrollToElement() {
    this.scrollElement.scrollIntoView()
  }
}
