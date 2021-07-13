import { ScrollDispatcher } from '@angular/cdk/scrolling'
import { DOCUMENT } from '@angular/common'
import { Component, Inject, Input, NgZone } from '@angular/core'
import { isScrolledIntoView } from 'app/core/utils'
import { debounceTime, map } from 'rxjs/operators'

@Component({
  selector: 'app-scroll-nav',
  templateUrl: './scroll-nav.component.html',
})
export class ScrollNavComponent {
  @Input()
  message: string = ''

  @Input()
  scrollElement!: HTMLElement

  isVisible: boolean = false

  constructor(
    @Inject(DOCUMENT) private document: Document,
    scrollDispatcher: ScrollDispatcher,
    ngZone: NgZone
  ) {
    scrollDispatcher
      .scrolled()
      .pipe(
        debounceTime(50),
        map(() => !isScrolledIntoView(this.scrollElement))
      )
      .subscribe((res) => ngZone.run(() => this.onVisibleChange(res)))
  }

  onVisibleChange(visible: boolean) {
    this.isVisible = visible
    if (visible) {
      this.document.body.classList.add('has-navbar-fixed-bottom')
    } else {
      this.document.body.classList.remove('has-navbar-fixed-bottom')
    }
  }

  scrollToElement() {
    this.scrollElement.scrollIntoView()
  }
}
