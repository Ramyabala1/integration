/* eslint-disable prefer-arrow/prefer-arrow-functions */

import { FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';

export class RegistrationPageForm{
  private formBuilder: FormBuilder;
  private form: FormGroup;

  constructor(formBuilder: FormBuilder){
    this.formBuilder = formBuilder;
    this.form = this.createFrom();
  }

   createFrom(): FormGroup{
    const form =  this.formBuilder.group({

      emanasId: ['',[Validators.required]],
      login: ['', Validators.compose([Validators.required,Validators.minLength(6),Validators.maxLength(50),
              Validators.pattern('(?=.*[a-z])(?=.*[0-9])[A-Za-z].{1,50}')])],

      password: ['', Validators.compose([
        Validators.required,
        Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{7,}'),
        Validators.minLength(6),
      ])],
      confirmPassword: [''],
      authService: [''],
      authService1: [''],
      individualId: [''],
      otpChannel: [''],
      regType: [''],
      uin: [''],
      aeId: ['']

    });
    form.get('confirmPassword').setValidators(matchPasswordAndConfirmPassword(form));


    return form;
  }

  getForm(): FormGroup{
    return this.form;

  }

}

function matchPasswordAndConfirmPassword(form: FormGroup): ValidatorFn{

  const password = form.get('password');
  const confirmPassword = form.get('confirmPassword');

  const validator = () =>password.value === confirmPassword.value ? null : {isntMatching: true};
  return validator;



}
