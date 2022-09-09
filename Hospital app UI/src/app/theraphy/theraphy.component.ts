import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GeneralService } from '../general.service';
import { MatSnackBar, MatAutocompleteSelectedEvent} from '@angular/material';
import {Location, DatePipe} from '@angular/common';

import {Therapy} from '../Theraphy';
@Component({
  selector: 'theraphy-component',
  templateUrl: './theraphy.component.html',
  styleUrls: ['./theraphy.component.css'],
  providers: [DatePipe]
})
export class TheraphyComponent implements OnInit {
  docName: string;
  toggleDurationName: any;
  toggleDiagnosisType: string;
  istriggered: boolean;
  validDuration: boolean;
  responseDetails:any;
  constructor(private router: Router, private datePipe: DatePipe,private generalService: GeneralService, private snackBar: MatSnackBar,
    ) {
  }

  
  stateData:any;

  showFhirRequest= false;

  loading = false;
  orgName: any;
  puuid: any;
  today: any;
  tomorrow: any;
  sessionToken: any;
  userUuid: any;
  orgUuid: any;
  userName: any;
  opque: any;

  therapyMethodArray: any[] = [];

  public VFChildren: Array<any> = [];
  public TherapyVF: Array<any> = [];
  public UIArray: Array<any> = [];
  recordFound = 'false';
  createdDate: any;
  therapyModel = new Therapy();
  public newComposition: any = {};
  public allCompositionArray: Array<any> = [];
 
  followup: string[] = ['Yes', 'No'];
  id: any;

  ngOnInit() {
    var historyData = history.state;
    this.stateData = historyData['consentID'];
    if(this.stateData)
    {
      this.therapyModel.consentID = this.stateData;
      this.therapyModel.patientID = historyData['patientID'];
    }
   this.istriggered = true;
    this.puuid = sessionStorage.getItem('patientUuid');
    this.today = new Date();
    this.tomorrow = new Date();
    this.tomorrow.setDate(this.today.getDate() + 1);
    
  }

  cancel() {
    if (window.confirm('Are you sure you want to leave this page?')) {
      sessionStorage.setItem('validRoute', 'true');
      this.router.navigate(['dashboard']);
    }

  }


save() {
  this.therapyModel.patientID=  this.therapyModel.patientID.trim();
  this.therapyModel.orgId=  this.therapyModel.orgId.trim();
  this.therapyModel.consentID=  this.therapyModel.consentID.trim();
  this.therapyModel.orgName= this.therapyModel.orgName.trim();
  this.therapyModel.docId= this.therapyModel.docId.trim();

 
this.showFhirRequest=false;




this.generalService.createTheraphyComposition(this.therapyModel).subscribe(response => {
  
  let resp = response.text();
    

      
     
    var jsonResponse = response.json();
    if(jsonResponse.status===200)
    {
      
        this.responseDetails = jsonResponse;
       
        alert(this.responseDetails.response);
        this.router.navigate(['dashboard']); 
     
       }else{
        if(jsonResponse !== null && jsonResponse.entity !== null){
          this.responseDetails = jsonResponse;
          alert(this.responseDetails.errors);
        
         }
       }

  } , (error) => {
    
    alert('error occurred');
  });

}

}

