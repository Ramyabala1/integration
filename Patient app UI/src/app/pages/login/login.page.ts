/* eslint-disable no-var */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, NgForm } from '@angular/forms';
import { PersonServiceService } from 'src/app/person-service.service';
import { Person } from 'src/app/Person';
import { LoginPageForm } from './form/login.page.form';


@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  form: FormGroup;
  passwordType = 'password';
  passwordShown = false;
  loginForm: LoginPageForm;
  person = new Person();
  message = '';
  login: any;
  password: any;
  emanas: any;
  patientLogin =
    {
      login: '',
      password: ''
    };
  constructor(private router: Router, private service: PersonServiceService, private formBuilder: FormBuilder) { }

  ngOnInit() {

    this.createForm();
  }
  signup() {
    this.router.navigate(['registration']);
  }
  loginuser() {
    this.patientLogin.login = this.login;
    this.patientLogin.password = this.password;
    this.service.doLogin(this.patientLogin).subscribe(response => {
      var jsonResponse1 = response;
      console.log('response received');
      sessionStorage.setItem('patientId', jsonResponse1.emanas);
      sessionStorage.setItem('personType', jsonResponse1.type);
      sessionStorage.setItem('aeId', jsonResponse1.aeId);
      this.router.navigate(['homepage']);
    },
      error => {
        console.log('exception occured');
        this.message = 'Bad Credentials ';
      }
    );
  }

  public togglePassword() {
    if (this.passwordShown) {
      this.passwordShown = false;
      this.passwordType = 'password';
    }
    else {
      this.passwordShown = true;
      this.passwordType = 'text';

    }

  }
  public createForm() {
    this.loginForm = new LoginPageForm(this.formBuilder);
  }
}
