import { FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';

export class AlertForm{
  private formBuilder: FormBuilder;
  private form: FormGroup;

  constructor(formBuilder: FormBuilder){
    this.formBuilder = formBuilder;
    this.form = this.createFrom();
  }
  createFrom(): FormGroup{
    const form =  this.formBuilder.group({
    });


    return form;
  }
}
