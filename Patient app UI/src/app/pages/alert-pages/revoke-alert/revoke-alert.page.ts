import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { Alert2Page } from '../../alert2/alert2.page';
import { PersonServiceService } from 'src/app/person-service.service';

@Component({
  selector: 'app-revoke-alert',
  templateUrl: './revoke-alert.page.html',
  styleUrls: ['./revoke-alert.page.scss'],
})
export class RevokeAlertPage implements OnInit {
  otpView: boolean = false;
  authService: string;
  value: string;
  consentid: any;
  showCard: any;
  showCard1: boolean;
  showCard3: boolean;
  otpcard: boolean;
  uin: any;
  otpChannel: any;

  children: any;
  emanasId: any;
  authType: any;
  transactionId: any;

  consentrequestnew = {
    api: 'mosip-emanas.consent.init',
    version: '1.0',
    source: 'PatientApp',

    timestamp: '',
    person: {
      personType: 'PATIENT',
      personId: '',
      authService: '',
      serviceParameters: {
        individualId: '',
        individualIdType: '',
        otpChannel: '',
        authType: '["Demo","OTP","Bio"]'
      }
    },
    consentParameters: {
      consentId: '',
      patientId: '',
    }
  }

  revokeVerify = {
    api: 'mosip-emanas.consent.revoke',
    version: '1.0',
    source: 'PatientApp',
    otp: '',
    transactionId: '',
    timestamp: '',
    authType: ['DEMO', 'OTP', 'BIO'],
  }

  message: string;
  displayString: any;
  otpArray: any[] = [];
  authArray: any[] = [];
  newArray = [];
  purposeArray: any[] = [];
  otpType = 'Mobile';
  artifactObj: any;
  otp: any;
  otpNotMatch: any;
  verifyArray: any[] = [];

  constructor(private modalController: ModalController, private router: Router, private service: PersonServiceService) { }

  ngOnInit() {
    this.consentid = sessionStorage.getItem('consentID');
    console.log("consentid: ", this.consentid);
  }

  revokeRequest() {
    this.displayString = JSON.parse(sessionStorage.getItem('artifact'));
    console.log("displayString: ", this.displayString);
    this.consentrequestnew.timestamp = new Date().toString();
    this.consentrequestnew.person.personId = this.displayString.person.personId;
    this.consentrequestnew.person.authService = "EMANAS";
    this.consentrequestnew.person.serviceParameters.individualIdType = 'VID|UIN';
    this.consentrequestnew.person.serviceParameters.individualId = this.uin;
    this.consentrequestnew.consentParameters.patientId = this.displayString.consentParameters.patientId;

    this.authArray = [];
    this.authArray.push('Demo');
    this.authArray.push('OTP');
    this.authArray.push('Bio');
    this.newArray[0] = this.authArray;
    this.consentrequestnew.person.serviceParameters.authType = this.newArray[0];
    this.consentrequestnew.consentParameters.consentId = sessionStorage.getItem('consentID');

    if (this.otpChannel == 'PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.consentrequestnew.person.serviceParameters.otpChannel = this.newArray[0];
    } else if (this.otpChannel == 'EMAIL') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.newArray[0] = this.verifyArray;
      this.consentrequestnew.person.serviceParameters.otpChannel = this.newArray[0];
    } else if (this.otpChannel == 'EMAIL,PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.consentrequestnew.person.serviceParameters.otpChannel = this.newArray[0];
    }
    console.log(this.consentrequestnew);
    this.service.initConsent(this.consentrequestnew).subscribe(response => {
      console.log("response: ", response);
      if (response.code === 200) {
        var jsonResponse = response;
        if (jsonResponse.code === 200) {
          this.transactionId = jsonResponse.transactionId;
          console.log("transactionId: ", this.transactionId);
          sessionStorage.setItem('transactionId', this.transactionId);
        } else if (jsonResponse.code === 400) {
          alert(jsonResponse.errors);
        }
      }
      this.onNoClick()
    }, (error) => {
      alert('error occurred');
    });
  }

  verifyOtp() {
    this.revokeVerify.transactionId = this.transactionId;
    this.revokeVerify.otp = this.otp;
    this.revokeVerify.timestamp = new Date().toString();
    this.service.manageConsentRequest(this.revokeVerify).subscribe(response => {
      if (response.code === 400) {
        console.log('entered in 400');
      }
      else if (response.code === 200) {
        console.log('response received');
        if (window.confirm(response.message)) {
          this.modalController.dismiss();
          this.router.navigate(['consent']);
        }
      }
    });
  }

  onNoClick(): void {
    sessionStorage.removeItem("artifact");
    sessionStorage.removeItem("patientID");
    sessionStorage.removeItem("consentID");
  }


  home() {
    this.modalController.dismiss();
    this.router.navigate(['homepage']);
  }

  cancel() {
    this.modalController.dismiss();
    this.router.navigate(['homepage']);
  }

  get() {
    var x = sessionStorage.getItem('item1');
    document.getElementById('demo').innerHTML = x;
    var x = sessionStorage.getItem('item2');
    document.getElementById('demo1').innerHTML = x;
    var x = sessionStorage.getItem('item3');
    document.getElementById('demo2').innerHTML = x;
    var x = sessionStorage.getItem('item4');
    document.getElementById('demo3').innerHTML = x;
    var x = sessionStorage.getItem('item5');
    document.getElementById('demo4').innerHTML = x;
    var x = sessionStorage.getItem('item6');
    document.getElementById('demo5').innerHTML = x;
    var x = sessionStorage.getItem('item7');
    document.getElementById('demo6').innerHTML = x;
    var x = sessionStorage.getItem('item8');
    document.getElementById('demo7').innerHTML = x;
    var x = sessionStorage.getItem('mhpname');
    document.getElementById('demo8').innerHTML = x;
  }

  showAlert() {
    this.modalController.dismiss();
    this.showModal('EHR Context Details');
  }

  async showModal(msg) {
    const modal = await this.modalController.create({
      component: Alert2Page,
      componentProps: { message: msg },
      cssClass: 'alert-model'
    });
    await modal.present();

  }

  clickedPlus2(event: Event) {
    this.showCard3 = !this.showCard3;
  }

  clickedotpcard(event: Event) {
    this.otpcard = !this.otpcard;
  }

  clickedPlus(event: Event) {
    this.showCard = !this.showCard;
  }

  clicked(event: Event) {
    this.showCard1 = !this.showCard1;
  }

  onChange(authService) {
    if (this.authService === 'MOSIP') {

      this.value = 'mosip';
    }
    else if (this.authService === 'EMANAS') {
      this.value = '';
    }
  }


  add() {

  }

}

