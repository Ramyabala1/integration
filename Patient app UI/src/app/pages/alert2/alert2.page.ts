/* eslint-disable max-len */
/* eslint-disable no-var */
/* eslint-disable prefer-const */
/* eslint-disable @typescript-eslint/naming-convention */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { PersonServiceService } from 'src/app/person-service.service';

@Component({
  selector: 'app-alert2',
  templateUrl: './alert2.page.html',
  styleUrls: ['./alert2.page.scss'],
})
export class Alert2Page implements OnInit {

  transactionId: any;
  otp: any;
  consentID: any;
  initadd = {
    api: 'mosip-emanas.add.ehr.context.hip',
    version: '1.0',
    source: 'PatientApp',
    otp: '',
    transactionId: '',
    timestamp: '',
    authType: ['DEMO', 'OTP', 'BIO'],
  };
  message: any;


  constructor(private router: Router, private modalController: ModalController, private Pservice: PersonServiceService) { }


  ngOnInit() {
  }
  home() {
    this.modalController.dismiss();
    this.router.navigate(['homepage']);
  }

  add() {
    this.initadd.transactionId = sessionStorage.getItem('initAddEtransactionId');
    this.initadd.otp = this.otp;
    this.initadd.timestamp = new Date().toString();
    this.Pservice.add(this.initadd).subscribe(
      response => {
        if (response.code === 400) {
          console.log('entered in 400');
          this.message = 'OTP INVALID ';
        }
        else if (response.code === 200) {
          var consent = response;
          this.consentID = consent.response;
          sessionStorage.setItem('consent', this.consentID);
          console.log('response received');
          this.modalController.dismiss();
          this.router.navigate(['homepage']);
          let row1 = document.createElement('div');
        }
      });
  }
  add1() {

  }
}
