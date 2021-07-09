import { Component } from '@angular/core'

interface Link {
  route: string
  text: string
}

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  menuOpened = false

  links: Link[] = [
    { route: '/about', text: 'Справка' },
    { route: '/candies', text: 'Конфеты' },
    { route: '/presents', text: 'Подарки' },
  ]

  toggleMenu() {
    this.menuOpened = !this.menuOpened
  }

  closeMenu() {
    this.menuOpened = false
  }
}
