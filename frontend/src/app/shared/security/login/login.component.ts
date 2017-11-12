import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { FormHelper, StringValidators } from 'app/shared/validation';
import { AuthenticationService } from '../authentication.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  loading = false;
  error = null;
  redirectUrl: string;
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private userService: UserService) {
    this.redirectUrl = this.activatedRoute.snapshot.queryParams['redirectTo'];

    this.form = fb.group({
      login: ['', StringValidators.notEmpty],
      password: ['', StringValidators.notEmpty]
    });
  }

  ngOnInit(): void {
    this.userService.logout();
  }

  onSubmit() {
    if (this.form.valid) {
      const formValue = this.form.value;
      this.login(formValue.login, formValue.password);
    } else {
      FormHelper.markFormContolsAsDirty(this.form);
    }
  }

  login(login: string, password: string) {
    this.loading = true;

    this.authenticationService.login(login, password)
      .subscribe(token => {
        this.error = null;
        this.loading = false;
        this.userService.login(token);
        this.navigateAfterSuccess();
      },
      error => {
        this.error = 'Логин или пароль неверны';
        this.loading = false;
      });
  }

  private navigateAfterSuccess() {
    if (this.redirectUrl) {
      this.router.navigateByUrl(this.redirectUrl);
    } else {
      this.router.navigate(['/']);
    }
  }
}

