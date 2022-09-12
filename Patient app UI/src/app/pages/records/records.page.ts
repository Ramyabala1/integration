/* eslint-disable @typescript-eslint/quotes */
/* eslint-disable @typescript-eslint/prefer-for-of */
/* eslint-disable no-var */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PersonServiceService } from 'src/app/person-service.service';

export class Treatments {
  output: string;
  template: string;
  hospitalName: string;
}

@Component({
  selector: 'app-records',
  templateUrl: './records.page.html',
  styleUrls: ['./records.page.scss'],
})
export class RecordsPage implements OnInit {

  compositionID: any;
  templateId: any;
  patientID: any;
  consentID: any;


  viewHealth = {
    compositions: [],
    patientId: '',
    episodeId: ''
  };
  res1: any;
  public resTreatment: Treatments[];
  output = {};
  illnessSummary: any;
  improvement: any;
  diff: any;
  clinical: any;
  follow: any;

  getCompdata = {
    patientId: '',
    virtualfolderId: ''
  };
  resnewArray: [];
  compArray: any[] = [];

  constructor(private router: Router, private service: PersonServiceService) { }

  ngOnInit() {
    this.getCompositionId();
  }

  getCompositionId() {
    this.getCompdata.patientId = sessionStorage.getItem('patientId');
    this.getCompdata.virtualfolderId = sessionStorage.getItem('episodeId');
    console.log("getcomp ", this.getCompdata);
    this.service.getCompositionList(this.getCompdata).subscribe(response => {
      console.log('response: ', response);
      if (response.code === 400) {
        console.log('entered in 400');;
      }
      else if (response.code === 200) {
        var res1 = response;
        if (res1.code === 200) {
          this.resnewArray = JSON.parse(response.response);
          console.log("resOut: ", this.resnewArray);
        }
      }
    });
  }

  viewDetails(list) {
    console.log("list: ", list.compositionId);
    sessionStorage.setItem("compositionId", list.compositionId);
    const facconst = {
      compositionId: list.compositionId
    };
    this.compArray.push(facconst);
    console.log("compArray: ", this.compArray);
    this.router.navigate(['viewhealth']);
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
  }

  home() {
    this.router.navigate(['homepage']);
  }
  
}

