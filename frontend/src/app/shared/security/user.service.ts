import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

export const TOKEN_NAME = 'access_token';

@Injectable()
export class UserService {
  token?: string;

  constructor(private jwtHelper: JwtHelperService) {
  }

  login(token: string) {
    this.token = token;
    localStorage.setItem(TOKEN_NAME, token);
  }

  logout() {
    this.token = undefined;
    localStorage.removeItem(TOKEN_NAME);
  }

  isAuthenticated(): boolean {
    return this.token !== undefined;
  }

  isTokenExpired(): boolean {
    return this.jwtHelper.isTokenExpired(this.token);
  }
}
