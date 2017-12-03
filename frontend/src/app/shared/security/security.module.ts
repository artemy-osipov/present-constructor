import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { JwtModule } from '@auth0/angular-jwt';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { ValidationModule } from 'app/shared/validation';
import { environment } from 'environments/environment';

import { AuthGuard } from './auth-guard.service';
import { AuthenticationService } from './authentication.service';
import { LoginComponent } from './login/login.component';
import { TOKEN_NAME, UserService } from './user.service';

@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: getJwtToken,
        whitelistedDomains: [environment.apiHost]
      }
    }),
    NgbModule,
    ValidationModule
  ],
  providers: [
    AuthGuard,
    AuthenticationService,
    UserService
  ]
})
export class SecurityModule { }

export function getJwtToken(): string {
  return localStorage.getItem(TOKEN_NAME);
}
