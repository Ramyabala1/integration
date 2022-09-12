/* eslint-disable @typescript-eslint/prefer-for-of */
/* eslint-disable @typescript-eslint/member-ordering */
/* eslint-disable no-var */

import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { PersonServiceService } from 'src/app/person-service.service';
import { Alert1Page } from '../alert1/alert1.page';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.page.html',
  styleUrls: ['./alert.page.scss'],
})
export class AlertPage implements OnInit {
  @Input() message: string;
  orgName = '';
  keyword = 'displayname';
  orgName1: any;
  data = [
    {
      displayname: 'Karthik Hospital',
    },
  ];
  personUUID: any;
  displayname: any;
  response: any;
  adRes: any;
  uuid: any;
  uuid1: any;
  orgUUID: any;
  patientId: any;

  ehrContext = {
    api: 'mosip-emanas.get.ehr.context.hip',
    version: '1.0',
    hip: {
      orgUUID: '',
      estName: ''
    },
    patientId: '',
    source: "PatientApp",
    licenseKey: '123e4567-e89b-12d3-a456',
    timestamp: ''
  }

  adRes1: any;
  children: any;
  mhpname: any;
  response1: any;
  name: any;
  item: any;
  compositionid: any;
  adRes2: any;
  adRes3: any;
  transactionId: any;
  adRes4: any;
  createdDate: any;
  templateId: any;
  adRes5: any;
  item1: any;
  item2: any;
  item3: any;
  item4: any;
  item5: any;
  item6: any;
  item7: any;
  item8: any;
  compositionid1: any;
  compositionid2: any;
  compositionid3: any;
  compositionid4: any;
  compositionid5: any;
  compositionid6: any;
  compositionid7: any;
  templateId1: any;
  templateId2: any;
  templateId3: any;
  templateId4: any;

  isItemAvailable = false;
  items = [];

  public type: Array<any> = [];

  constructor(
    private router: Router,
    private modalController: ModalController,
    private service: PersonServiceService
  ) { }

  ngOnInit() { }

  home() {
    this.modalController.dismiss();
    this.router.navigate(['homepage']);
  }

  showAlert() {
    this.modalController.dismiss();
    this.showModal('EHR Context Details');
  }

  async showModal(msg) {
    const modal = await this.modalController.create({
      component: Alert1Page,
      componentProps: { message: msg },
      cssClass: 'alert-model',
    });
    await modal.present();
  }

  onChangeSearch(orgName) {
    if (orgName.length > 5) {
      this.service.findorg(this.orgName).subscribe((response) => {
        this.isItemAvailable = true;
        var jsonResponse = response;
        if (response.code === 400) {
          console.log('entered in 400');
          this.message = 'Invalid Hospital name ';
          alert(jsonResponse.message);
        } else if (response.code === 200) {
          this.adRes = JSON.parse(response.response);
          console.log('this.adRes:', this.adRes);

          for (let i = 0; i < this.adRes.length; i++) {
            var resOutt = this.adRes[i];
            console.log('output', resOutt);
            this.orgName = resOutt.displayname;
            console.log('displayname', this.orgName);
            sessionStorage.setItem('orgName', resOutt.displayname);
            sessionStorage.setItem('mheRegId', resOutt.mheRegId);

            var uuid = response;
            this.orgName1 = resOutt.uuid;
            sessionStorage.setItem('uuid', this.orgName1);
          }
        }
      });
    } else {
    }
  }

  get() {
    this.ehrContext.hip.estName = sessionStorage.getItem('orgName');
    this.ehrContext.hip.orgUUID = sessionStorage.getItem('uuid');
    this.ehrContext.patientId = sessionStorage.getItem('patientId');
    this.ehrContext.timestamp = new Date().toString();

    this.service.getEHR(this.ehrContext).subscribe((response) => {
      if (response.code === 400) {
        console.log('entered in 400');
        this.message = 'Invalid Hospital name ';
        alert(response.message);
      } else if (response.code === 200) {
        console.log('response', response);

        localStorage.setItem('transactionId', response.transactionId);
        sessionStorage.setItem('transactionId', response.transactionId);
        this.adRes = JSON.parse(response.response);
        sessionStorage.setItem('Hospitaluuid', this.adRes.uuid);

        sessionStorage.setItem('mhpname', this.adRes.mhpname);

        console.log('this.adRes', this.adRes);
        for (let i = 0; i < this.adRes.children.length; i++) {
          var resOutt = this.adRes.children[i];
          this.type.push(resOutt.name);
        }
        console.log('type', JSON.stringify(this.type));
        sessionStorage.setItem('typeName', JSON.stringify(this.type));
        this.showAlert();
      }
    });
  }
}
