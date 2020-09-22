import { NgModule } from '@angular/core'
import {
  FaIconLibrary,
  FontAwesomeModule,
} from '@fortawesome/angular-fontawesome'
import {
  faAngleDown,
  faAngleUp,
  faCopy,
  faEye,
  faFile,
  faPencilAlt,
  faPlus,
  faRubleSign,
  faTimes,
  faTrash,
} from '@fortawesome/free-solid-svg-icons'

@NgModule({
  exports: [FontAwesomeModule],
})
export class FontModule {
  constructor(library: FaIconLibrary) {
    library.addIcons(
      faAngleDown,
      faAngleUp,
      faCopy,
      faEye,
      faFile,
      faPencilAlt,
      faPlus,
      faRubleSign,
      faTimes,
      faTrash
    )
  }
}
