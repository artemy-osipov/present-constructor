import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'

const routes: Routes = [
  { path: '', redirectTo: '/about', pathMatch: 'full' },
  {
    path: 'about',
    loadChildren: () =>
      import('./features/about/about.module').then((m) => m.AboutModule),
  },
  {
    path: 'candies',
    loadChildren: () =>
      import('./features/candies/candies.module').then((m) => m.CandiesModule),
  },
  {
    path: 'presents',
    loadChildren: () =>
      import('./features/presents/presents.module').then(
        (m) => m.PresentsModule
      ),
  },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
