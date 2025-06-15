import { Component } from '@angular/core';
import {ReactiveFormsModule, UntypedFormBuilder, Validators} from '@angular/forms';
import Validation from '../../../service/utils/validation';
import {AuthService} from '../../../service/auth/auth.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-user-page',
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './user-page.component.html',
  standalone: true,
  styleUrl: './user-page.component.scss'
})
export class UserPageComponent {
  changePasswordForm:  any = {
    code: null,
    newPassword: null
  };

  isPasswordValid: boolean | undefined;
  isThereACode: boolean = false;
  code: string = '';
  isOldPasswordMatch: boolean | undefined;
  isConfirmPasswordMatch: boolean | undefined;
  isNewPasswordUniqueToOld: boolean | undefined;
  oldPasswordValue: string = '';
  newPasswordValue: string = '';
  confirmPasswordValue: string = '';

  constructor(private authService: AuthService, private fb: UntypedFormBuilder) {
    this.changePasswordForm = this.fb.group(
      {
        code: [
          '',
          [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(6)
          ]
        ],
        newPassword: [
          '',
          [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(40)
          ]
        ],
        confirmPassword: ['', Validators.required]
      },
      {
        validators: [Validation.match('newPassword', 'confirmPassword')]
      }
    );

  }

  onInputChange() {
    this.newPasswordValue = this.changePasswordForm
      .get('newPassword').value;

    this.oldPasswordValue = this.changePasswordForm
      .get('code').value;

    this.confirmPasswordValue = this.changePasswordForm
      .get('confirmPassword').value;

    if (this.newPasswordValue.length >= 6) {
      this.isPasswordValid = true;
    } else {
      this.isPasswordValid = false;
    }
    if (this.newPasswordValue != this.oldPasswordValue) {
      this.isNewPasswordUniqueToOld = true
    } else {
      this.isNewPasswordUniqueToOld = false
    }

    if (this.confirmPasswordValue == this.newPasswordValue) {
      this.isConfirmPasswordMatch = true;
    } else {
      this.isConfirmPasswordMatch = false;
    }
  }

  onOldPasswordInputChange() {
    // console.log("oldPassChange")
    // console.log(this.oldPasswordValue);
    // this.onInputChange();
    // this.authService.checkPassword(
    //   new PasswordChange(this.tokenStorage
    //     .getUser().email, this.oldPasswordValue)).subscribe(res => {
    //   this.isOldPasswordMatch = res;
    // });
  }

  onSubmitChangePassword(): void {
    console.log("change pass");
    console.log(this.isThereACode);
    // if (this.isThereACode) {
    //   this.authService.recoverPassword(new PasswordChange('', this.code, this.newPasswordValue)).subscribe(res =>
    //   {
    //     if(res) {
    //       this.router.navigate(['']);
    //     }
    //   });
    // } else {
    //   this.authService.changePassword(new PasswordChange(this.tokenStorage
    //     .getUser().email, this.oldPasswordValue, this.newPasswordValue))
    //     .subscribe(res => {
    //       console.log(1);
    //       if (res) {
    //         console.log(2);
    //         this.dialogRef.close(new DialogResult(DialogAction.SAVE));
    //       }
    //     });
    // }

  }

}
