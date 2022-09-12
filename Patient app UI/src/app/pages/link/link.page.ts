/* eslint-disable @typescript-eslint/naming-convention */
/* eslint-disable @typescript-eslint/prefer-for-of */
/* eslint-disable no-var */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { PersonServiceService } from 'src/app/person-service.service';
import { AlertPage } from '../alert/alert.page';
import { Alert1Page } from '../alert1/alert1.page';
import { Alert2Page } from '../alert2/alert2.page';


@Component({
  selector: 'app-link',
  templateUrl: './link.page.html',
  styleUrls: ['./link.page.scss'],
})
export class LinkPage implements OnInit {

  compositionID: any;
  templateId: any;
  patientID: any;
  consentID: any;

  viewHealth = {
    compositionID: '',
    templateId: '',
    patientId: '',
    consentId: ''
  };

  viewlinkedlist = {
    patientId: '',
  };

  adRes: any;
  Output: any;
  resultSet: any;
  author: any;
  res1: [];
  Record: boolean;

  constructor(private router: Router, private modalController: ModalController, private service: PersonServiceService) { }

  ngOnInit() {
    this.getPatientHospitalMap();
  }
  showAlert() {
    this.showModal('Add new Record');
  }

  async showModal(msg) {
    const modal = await this.modalController.create({
      component: AlertPage,
      componentProps: { message: msg },
      cssClass: 'alert-model'
    });
    await modal.present();
  }

  async showModal1(msg) {
    const modal = await this.modalController.create({
      component: Alert1Page,
      cssClass: 'alert-model'
    });
    await modal.present();
  }
  async showModal2(msg) {
    const modal = await this.modalController.create({
      component: Alert2Page,
      cssClass: 'alert-model'
    });
    await modal.present();
  }

  home() {
    this.router.navigate(['homepage']);
  }

  getPatientHospitalMap() {
    this.viewlinkedlist.patientId = sessionStorage.getItem('patientId');
    this.service.getPatientHospitalMap(this.viewlinkedlist).subscribe(response => {
      console.log('response: ', response);
      if (response.code === 400) {
        this.Record = false;
        console.log('entered in 400');;
      }
      else if (response.code === 200) {
        var res1 = response;
        if (res1.code === 200) {
          this.Record = true;
          this.res1 = JSON.parse(response.response);
          console.log("resOut.output:", this.res1);
        }
      }
    });
  }
}


