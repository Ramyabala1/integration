/* eslint-disable @typescript-eslint/naming-convention */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PersonServiceService } from 'src/app/person-service.service';
import { FormBuilder } from '@angular/forms';
import { OtpPageForm } from './form/otp.page.form';

@Component({
  selector: 'app-otp',
  templateUrl: './otp.page.html',
  styleUrls: ['./otp.page.scss'],
})
export class OtpPage implements OnInit {
  otpForm: OtpPageForm;



  transactionId: any;
  otp: any;
  timestamp: any;
  message = '';
  message3 = '';
  verify =
    {
      api: 'mosip-emanas.verify.person', 
      version: 1.0,
      source:"PatientApp",
      transactionId: '',
      otp: {
        value: '',
      },
      timestamp: ''
    };
  passwordType = 'password';
  passwordShown = false;

  constructor(private router: Router, private Pservice: PersonServiceService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.createForm();
  }
  public createForm() {
    this.otpForm = new OtpPageForm(this.formBuilder);
  }



  home(Otp) {
    this.verify.transactionId = sessionStorage.getItem('jsonResponse');
    this.verify.otp.value = this.otp;
    this.verify.timestamp = new Date().toString();
    this.Pservice.verifyOTP(this.verify).subscribe(
      response => {
        if (response.code === 400) {
          console.log('entered in 400');
          this.message = 'OTP INVALID ';
        }
        else if (response.code === 200) {
          console.log('response received');
          alert(response.response);
          this.router.navigate(['login']);
        }
      },
      error => {
        console.log('exception occured');
        this.message = 'Otp Invalid ';
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
}
