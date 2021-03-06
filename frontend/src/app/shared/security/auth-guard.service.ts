import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot
} from '@angular/router';

import { UserService } from './user.service';

import { environment } from 'environments/environment';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private userService: UserService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (
      !environment.enableSecurity ||
      (this.userService.isAuthenticated() && !this.userService.isTokenExpired())
    ) {
      return true;
    } else {
      this.router.navigate(['login'], {
        queryParams: { redirectTo: state.url }
      });
      return false;
    }
  }
}
