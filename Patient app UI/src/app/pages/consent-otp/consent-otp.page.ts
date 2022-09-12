/* eslint-disable max-len */
/* eslint-disable prefer-const */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { PersonServiceService } from 'src/app/person-service.service';

@Component({
  selector: 'app-consent-otp',
  templateUrl: './consent-otp.page.html',
  styleUrls: ['./consent-otp.page.scss'],
})
export class ConsentOtpPage implements OnInit {

  transactionId: any;
  otp: any;
  consentID: any;

  initadd = {
    api: 'mosip-emanas.consent.create',
    version: '1.0',
    source: 'PatientApp',
    otp: '',
    transactionId: '',
    timestamp: '',
    authType: ['DEMO', 'OTP', 'BIO'],
  }

  constructor(private router: Router, private service: PersonServiceService, private modalController: ModalController) { }

  ngOnInit() {
  }

  add() {
    this.initadd.timestamp = new Date().toString();
    this.initadd.transactionId = sessionStorage.getItem('consent_transaction');
    this.initadd.otp = this.otp;
    this.service.createConsent(this.initadd).subscribe(
      response => {
        if (response.code === 400) {
          console.log('entered in 400');
        }
        else if (response.code === 200) {
          console.log('response received');
          if (window.confirm('Consent created successfully with ID --> ' + response.response)) {
            this.modalController.dismiss();
            this.router.navigate(['consent']);
          }
        }
      });
  }

}
