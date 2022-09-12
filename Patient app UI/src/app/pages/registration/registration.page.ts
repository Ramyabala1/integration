/* eslint-disable no-var */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PersonServiceService } from 'src/app/person-service.service';

import { FormBuilder } from '@angular/forms';
import { RegistrationPageForm } from './form/registration.page.form';



@Component({
  selector: 'app-registration',
  templateUrl: './registration.page.html',
  styleUrls: ['./registration.page.scss'],
})
export class RegistrationPage implements OnInit {
  emanasId: any;
  login: any;
  password: any;
  authService: any;
  uin: any;
  otpChannel: any;
  authType: any;
  transactionId: any;
  emanas: any;
  regType: any;
  passwordType = 'password';
  passwordShown = false;
  passwordType1 = 'password';
  patientRequest =
    {
      login: '',
      password: '',
      patient: {
        patientId: '',
        authService: '',
        serviceParameters: {
          individualId: '',
          individualIdType: 'VID|UIN',
          otpChannel: [],
          authType: '["Demo","OTP","Bio"]'
        }
      }
    };

  patnew = {
    api: 'mosip-emanas.init.verify.person',
    version: '1.0',
    source: 'PatientApp',
    login: '',
    password: '',
    person: {
      type: '',
      patientId: '',
      aeId: '',
      authService: '',
      serviceParameters: {
        individualId: '',
        individualIdType: 'VID|UIN',
        otpChannel: [],
        authType: '["Demo","OTP","Bio"]'
      }
    },
    timestamp: '',
  };

  idCheck =
    {
      emanas: ''
    };
  loginCheck =
    {
      login: ''
    };

  message = '';
  message1 = '';
  message2 = '';
  message3 = '';
  registerForm: RegistrationPageForm;
  value: string;
  showCard: boolean;
  showCard1: boolean;
  aeId: any;
  aeIdField: boolean;
  authArray: any[] = [];
  newArray = [];
  verifyArray: any[] = [];

  constructor(private router: Router, private service: PersonServiceService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.createForm();
  }

  loginPage() {
    this.router.navigate(['login']);
  }

  registeruser() {
    this.patientRequest.login = this.login;
    this.patientRequest.password = this.password;
    this.patientRequest.patient.patientId = this.emanasId;
    this.patientRequest.patient.authService = this.authService;
    this.patientRequest.patient.serviceParameters.individualId = this.uin;
    this.patientRequest.patient.serviceParameters.otpChannel = this.otpChannel;
    this.service.doRegistration(this.patientRequest).subscribe(response => {
      if (response.code === 400) {
        console.log('entered in 400');
        this.message = 'Invalid eManas Id ';
        alert(jsonResponse.message);
      } else if (response.code === 200) {
        var jsonResponse = response;
        var result = jsonResponse.entity;
        if (jsonResponse.code === 200) {
          console.log('entered in 200');
          this.transactionId = jsonResponse.transactionId;
          this.emanasId = jsonResponse.emanasId;
          sessionStorage.setItem('jsonResponse', this.transactionId);
          console.log('transactionId: ', this.transactionId);
          console.log('response received');
          this.router.navigate(['otp']);
        }
      }
    },
    );
  }

  registerTypeuser() {
    this.patnew.timestamp = new Date().toString();
    this.patnew.login = this.login;
    this.patnew.password = this.password;
    this.patnew.person.patientId = this.emanasId;
    this.patnew.person.type = this.regType;
    this.patnew.person.aeId = this.aeId;
    this.patnew.person.authService = this.authService;
    this.patnew.person.serviceParameters.individualId = this.uin;
    this.authArray = [];
    this.authArray.push('Demo');
    this.authArray.push('OTP');
    this.authArray.push('Bio');
    this.newArray[0] = this.authArray;
    this.patnew.person.serviceParameters.authType = this.newArray[0];

    if (this.otpChannel == 'PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.patnew.person.serviceParameters.otpChannel = this.newArray[0];
    } else if (this.otpChannel == 'EMAIL') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.newArray[0] = this.verifyArray;
      this.patnew.person.serviceParameters.otpChannel = this.newArray[0];
    } else if (this.otpChannel == 'EMAIL,PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.patnew.person.serviceParameters.otpChannel = this.newArray[0];
    }

    console.log("AE reg:, ", this.patnew);
    this.service.doRegistration(this.patnew).subscribe(response => {
      if (response.code === 400) {
        console.log('entered in 400');
        this.message = 'Invalid eManas Id ';
        alert(jsonResponse.errors);
      }
      else if (response.code === 200) {
        var jsonResponse = response;
        var jsonResponse1 = response;
        var result = jsonResponse.entity;
        if (jsonResponse.code === 200) {
          console.log('entered in 200');
          this.transactionId = jsonResponse.transactionId;
          this.emanasId = jsonResponse1.emanasId;
        }
        if (jsonResponse1.code === 200) {
          console.log('entered in 200');
          this.emanasId = jsonResponse1.emanasId;
        }
      }
      sessionStorage.setItem('jsonResponse', this.transactionId);
      console.log('transactionId: ', this.transactionId);
      console.log('response received');
      this.router.navigate(['otp']);
    },
    );
  }

  check() {
    this.idCheck.emanas = this.emanas;
    this.service.checkId(this.idCheck).subscribe(response => {
      console.log('id available');
    },
      error => {
        this.message2 = 'Emanas Id Exists';
      }
    );
  }

  checkid() {
    this.loginCheck.login = this.login;
    this.service.checklogin(this.loginCheck).subscribe(response => {
      console.log('id available');
    },
      error => {
        this.message3 = 'Login Id Exists';
      });
  }

  public createForm() {
    this.registerForm = new RegistrationPageForm(this.formBuilder);
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

  public togglePassword1() {
    if (this.passwordShown) {
      this.passwordShown = false;
      this.passwordType1 = 'password';
    }
    else {
      this.passwordShown = true;
      this.passwordType1 = 'text';
    }
  }

  onChange(authService) {
    if (this.authService === 'MOSIP') {
      this.value = 'MOSIP';
    }
    else if (this.authService === 'emanas') {
      this.value = '';
    }
  }

  regTypeChange(regType) {
    if (regType === 'patient') {
      this.aeIdField = false;
    }
    else if (regType === 'authorized_entity') {
      this.aeIdField = true;
    }
  }

  clickedPlus(event: Event) {
    this.showCard = !this.showCard;
  }

  clicked(event: Event) {
    this.showCard1 = !this.showCard1;
  }

}
