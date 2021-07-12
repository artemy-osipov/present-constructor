import { Component, EventEmitter, Input, Output } from '@angular/core'

@Component({
  selector: 'app-present-item-control',
  templateUrl: './present-item-control.component.html',
})
export class PresentItemControlComponent {
  @Input()
  currentCount: number = 0

  @Output()
  newCount = new EventEmitter<number>()

  decrement(): void {
    this.newCount.emit(Math.max(this.currentCount - 1, 0))
  }

  increment(): void {
    this.newCount.emit(this.currentCount + 1)
  }

  hasSome(): boolean {
    return this.currentCount !== 0
  }
}
