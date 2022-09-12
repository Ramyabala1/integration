import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { PersonServiceService } from 'src/app/person-service.service';
import { ModalController } from '@ionic/angular';
import { RevokeAlertPage } from '../alert-pages/revoke-alert/revoke-alert.page';

export interface District {
  serialNumber: number;
  status: number;
  modify: string;
  patientID: string;
  consentexpiry: string;
  consentID: string;
  revoke: string;
  more: string;
  artifact: string;
  HIP: string;
  HIU: string;
  purpose: any[];
}

@Component({
  selector: 'app-consent',
  templateUrl: './consent.page.html',
  styleUrls: ['./consent.page.scss'],
})
export class ConsentPage implements OnInit {
  personType: string;

  constructor(private router: Router, private service: PersonServiceService, private modalController: ModalController,) { }

  viewlinkedlist = {
    api: 'mosip-emanas.consent.retrieve',
    version: '1.0',
    source: 'PatientApp',
    patientId: '',
    status: ''
  }

  resmain: any = [];

  res1: any = [];

  distarray: any = [];
  arrObj: any;
  artifact: any;
  artifactJson: any;
  artifactObj: any;

  ngOnInit() {
    this.getBMR();
  }



  getBMR() {
    this.personType = sessionStorage.getItem('personType');
    if (this.personType === 'authorized_entity') {
      this.viewlinkedlist.patientId = sessionStorage.getItem('aeId');
    } else {
      this.viewlinkedlist.patientId = sessionStorage.getItem('patientId');
    }
    this.viewlinkedlist.status = 'active';
    this.service.getConsentForPatient(this.viewlinkedlist).subscribe(response => {
      var res1 = response;
      console.log('adRes: ', res1);
      if (res1.code == 200) {
        try {
          this.distarray = JSON.parse(response.response);
          for (let i = 0; i < this.distarray.length; i++) {
            this.arrObj = this.distarray[i];
            this.artifact = this.arrObj.artifact;
            console.log('artifact: ', this.artifact);
            this.artifactJson = JSON.parse(this.artifact);
            console.log('artifactJson: ', this.artifactJson);
            const fileObj = {
              artifactObj: this.artifactJson,
              fileContent: this.arrObj
            };
            this.res1.push(fileObj);
            console.log("resOut.output:", this.res1);
          }
        }
        catch (err) {
          alert(err);
        }
      } else {
        alert(res1.message);
      }
    }, (error) => {
      alert('error occurred');
    });
  }

  revoke(list: any) {
    console.log("artifactObj.new:", JSON.stringify(list.artifactObj));
    sessionStorage.setItem('artifact', JSON.stringify(list.artifactObj));
    localStorage.setItem('artifact', JSON.stringify(list.artifactObj));
    sessionStorage.setItem('consentID', list.fileContent.consentID);
    this.showModal('Add new Record');
  }

  updateConsent(list: any) {
    console.log("artifactObj.new:", JSON.stringify(list.artifactObj));
    sessionStorage.setItem('artifact', JSON.stringify(list.artifactObj));
    localStorage.setItem('artifact', JSON.stringify(list.artifactObj));
    sessionStorage.setItem('consentID', list.fileContent.consentID);
    this.router.navigate(['update-consent']);
  }

  async showModal(msg) {
    const modal = await this.modalController.create({
      component: RevokeAlertPage,
      componentProps: { message: msg },
      cssClass: 'alert-model'
    });
    await modal.present();
  }

  next() {
    this.router.navigate(['consent1']);
  }

  home() {
    this.router.navigate(['homepage']);
  }



}
