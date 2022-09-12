/* eslint-disable no-var */
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { PersonServiceService } from 'src/app/person-service.service';
import { Alert2Page } from '../alert2/alert2.page';

@Component({
  selector: 'app-alert1',
  templateUrl: './alert1.page.html',
  styleUrls: ['./alert1.page.scss'],
})
export class Alert1Page implements OnInit {
  authService: string;
  showCard: any;
  showCard1: boolean;
  value: string;
  otpChannel: any;
  showCard3: boolean;
  children: any;
  emanasId: any;
  authType: any;
  uin: any;
  transactionId: any;

  healthRequest = {
    api: 'mosip-emanas.init.add.ehr.context.hip',
    version: '1.0',
    source: "PatientApp",
    transactionId: '',
    person: {
      personType: '',
      personId: '',
      authService: '',
      serviceParameters: {
        individualId: '',
        individualIdType: 'VID|UIN',
        otpChannel: [],
        authType: '["Demo","OTP","Bio"]'
      }
    },
    consentParameters: {
      patientId: '',
      aeId: '',
      permission: 'VIEW',
      purpose: ['IPBMR', 'OPBMR', 'RESTRAINT', 'ASMENT', 'THERAPY', 'ADVANCEDIRECTIVE'],
      hresource: {
        resourceType: 'name',
        episodeId: ''
      },
      hip: {
        estId: '',
        estName: 'patientapp',
      },
      hiu: {
        estId: '',
        estName: 'patientapp',
        individualId: '',
        individualName: '',
        individualRole: 'patientadmin',
        hiuType: 'role',
      },
      validity: {
        validityType: 'duration',
        createDate: '',
        validityEndDate: '2023-08-03T02:50:12.208Z',
      }
    }
  };
  message: string;
  public type: Array<any> = [];
  personType: string;

  authArray: any[] = [];
  newArray = [];
  verifyArray: any[] = [];

  constructor(
    private modalController: ModalController,
    private router: Router,
    private service: PersonServiceService
  ) { }

  ngOnInit() {
    this.type = JSON.parse(sessionStorage.getItem('typeName'));
    console.log('type: ', this.type);
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
      cssClass: 'alert-model',
    });
    await modal.present();
  }

  clickedPlus2(event: Event) {
    this.showCard3 = !this.showCard3;
  }
  onChange(authService) {
    if (this.authService === 'MOSIP') {
      this.value = 'mosip';
    } else if (this.authService === 'EMANAS') {
      this.value = '';
    }
  }
  clickedPlus(event: Event) {
    this.showCard = !this.showCard;
  }
  clicked(event: Event) {
    this.showCard1 = !this.showCard1;
  }

  add() {
    this.transactionId = sessionStorage.getItem('transactionId');
    this.personType = sessionStorage.getItem('personType');
    if (this.personType === 'authorized_entity') {
      this.healthRequest.person.personType = 'AUTHORIZEDENTITY';
      this.healthRequest.person.personId = sessionStorage.getItem('aeId');
      this.healthRequest.consentParameters.hiu.individualId = sessionStorage.getItem('aeId');
    } else {
      this.healthRequest.person.personType = 'PATIENT';
      this.healthRequest.person.personId = sessionStorage.getItem('patientId');
      this.healthRequest.consentParameters.hiu.individualId = sessionStorage.getItem('patientId');
    }
    this.healthRequest.consentParameters.hip.estId = sessionStorage.getItem('mheRegId');
    this.healthRequest.consentParameters.hiu.estId = sessionStorage.getItem('mheRegId');
    this.healthRequest.consentParameters.aeId = sessionStorage.getItem('aeId');
    this.healthRequest.consentParameters.patientId = sessionStorage.getItem('patientId');
    this.healthRequest.consentParameters.hresource.episodeId = sessionStorage.getItem('Hospitaluuid');
    this.healthRequest.person.serviceParameters.individualId = this.uin;
    this.healthRequest.person.authService = this.authService;
    this.healthRequest.person.serviceParameters.authType = this.authType;

    if (this.otpChannel == 'PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.healthRequest.person.serviceParameters.otpChannel = this.newArray[0];
    } else if (this.otpChannel == 'EMAIL') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.newArray[0] = this.verifyArray;
      this.healthRequest.person.serviceParameters.otpChannel = this.newArray[0];
    } else if (this.otpChannel == 'EMAIL,PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.healthRequest.person.serviceParameters.otpChannel = this.newArray[0];
    }

    this.healthRequest.consentParameters.validity.createDate = new Date().toString();
    (this.healthRequest.transactionId = this.transactionId),
      this.service.initEhr(this.healthRequest).subscribe((response) => {
        if (response.code === 400) {
          console.log('entered in 400');
          this.message = 'Invalid eManas Id ';
        } else if (response.code === 200) {
          var transaction = response;
          this.transactionId = transaction.transactionId;
          sessionStorage.setItem('initAddEtransactionId', this.transactionId);
          console.log('entered in 200');
        }
      });
  }
}
