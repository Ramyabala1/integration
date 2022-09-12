import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';

export class LoginPageForm{

  private formBuilder: FormBuilder;
  private form: FormGroup;

  constructor(formBuilder: FormBuilder){
    this.formBuilder = formBuilder;
    this.form = this.createForm();
  }

  createForm(): FormGroup{
    const form = this.formBuilder.group({
      login: ['',[Validators.required]],
      password: ['',[Validators.required]]

    });
    return form;
  }
  getForm(): FormGroup{
    return this.form;

  }
}
