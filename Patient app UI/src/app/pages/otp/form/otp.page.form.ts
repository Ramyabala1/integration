import { FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { __values } from 'tslib';

export class OtpPageForm{
  private formBuilder: FormBuilder;
  private form: FormGroup;

  constructor(formBuilder: FormBuilder){
    this.formBuilder = formBuilder;
    this.form = this.createFrom();
  }

   createFrom(): FormGroup{
    const form =  this.formBuilder.group({
      otp: ['',[Validators.required]],

    });


    return form;
  }

  getForm(): FormGroup{
    return this.form;

  }

}
