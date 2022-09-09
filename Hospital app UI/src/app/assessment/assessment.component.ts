import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GeneralService } from '../general.service';
import { MatSnackBar, MatAutocompleteSelectedEvent} from '@angular/material';
import {Location, DatePipe} from '@angular/common';

import {Assessment} from '../assessment';
@Component({
  selector: 'assessment-component',
  templateUrl: './assessment.component.html',
  styleUrls: ['./assessment.component.css'],
  providers: [DatePipe]
})
export class AssessmentComponent implements OnInit {
  docName: string;
  toggleDurationName: any;
  toggleDiagnosisType: string;
  istriggered: boolean;
  validDuration: boolean;
  constructor(private router: Router, private datePipe: DatePipe,private generalService: GeneralService, private snackBar: MatSnackBar,
    ) {
  }
  asModel = new Assessment();
  color = 'accent';
  mode = 'indeterminate';
  value = 100;
  userName: any;
  orgName: any;
  puuid: any;
  today: any;
  tomorrow: any;
  userUuid: any;
  orgUuid: any;
  languageArray: string[] = ['Kannada','Hindi','English'];
  referralReasonArray: string[] = ['Specific Learning Assessment',
  'Personality Assessment',
  'Neuro Psychological Assessment',
  'Psychopathology Assessment',
  'Others'];

  public newInformant: { informant: any, relationship: any};
  public informantArray: Array<any> = [];
  referral = {
    ReferredDoctor: '',
    ReferredHospital: '',
    ComorbidDiagnosis: '',
    TreatmentDetails: '',
    ReasonofReferral: ''
  };

  qualificationObject = {
    qualification: '',
    degreeName: '',
    specialization: '',
    institution: '',
    place : '',
    year : '',
    filePath: ''
  };
  public VFChildren: Array<any> = [];
  public AssessVF: Array<any> = [];
  public UIArray: Array<any> = [];
  recordFound = 'false';
  createdDate: any;
  showClose = true;
  deleteFileMessage: any;  
  endDate = new Date();
  sessionToken: any;
 
  opque: any;
  periodValid = false;
  rEndTime;
  rStartTime;
  monitoringVF = [];
 
  periodicArray = [];
  periodicDisplay = [];
  
  showFhirRequest:any;
  responseDetails:any;
  stateData:any;

  ngOnInit() {
    var historyData = history.state;
    this.newInformant = { informant: '', relationship: '' };
    this.stateData = historyData['consentID'];
    if(this.stateData)
    {
      this.asModel.consentID = this.stateData;
      this.asModel.patientID = historyData['patientID'];
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
  
  this.asModel.patientID=  this.asModel.patientID.trim();
  this.asModel.orgId=  this.asModel.orgId.trim();
  this.asModel.consentID=  this.asModel.consentID.trim();
  this.asModel.orgName= this.asModel.orgName.trim();
  this.asModel.docId= this.asModel.docId.trim();

this.asModel.informantArray=this.informantArray;

 
this.showFhirRequest=false




this.generalService.createAssessment(this.asModel).subscribe(response => {
  
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
   


addInformant() {
  if (this.newInformant.informant !== '' && this.newInformant.relationship !== '') {
     this.informantArray.push(this.newInformant);
    this.newInformant = { informant: '', relationship: '' };
  }
}


deleteInformant(index) {
  this.informantArray.splice(index, 1);
}
}







