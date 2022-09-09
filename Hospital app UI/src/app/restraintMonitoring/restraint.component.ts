import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GeneralService } from '../general.service';
import { MatSnackBar, MatAutocompleteSelectedEvent} from '@angular/material';
import {Location, DatePipe} from '@angular/common';

import {Restraint} from '../restraint';
@Component({
  selector: 'restraint-component',
  templateUrl: './restraint.component.html',
  styleUrls: ['./restraint.component.css'],
  providers: [DatePipe]
})
export class RestraintMonitoring implements OnInit {

  responseDetails:any;

  docName: string;
  toggleDurationName: any;
  toggleDiagnosisType: string;
  istriggered: boolean;
  validDuration: boolean;
  constructor(private router: Router, private datePipe: DatePipe,private generalService: GeneralService, private snackBar: MatSnackBar,
    ) {
  }
  rModel = new Restraint();
  rStartDate: any;
  rEndDate: any;
  invalidPeriod: boolean;
  invalidTime: boolean;
  loading = false;
  orgName: any;
  puuid: any;
  today: any;
  tomorrow: any;
  endDate = new Date();
  sessionToken: any;
  userUuid: any;
  orgUuid: any;
  userName: any;
  opque: any;
  periodValid = false;
  rEndTime;
  rStartTime;
  VFChildren = [];
  monitoringVF = [];
  UIArray = [];
  periodicArray = [];
  periodicDisplay = [];
  newPeriod = {
    dt: null,
    pulse: '',
    temperature: '',
    rrate: '',
    injury: '',
    bloodS: '',
    breath: '',
  };
  showFhirRequest:any;
  settingArray = ['In-Patient','Out-Patient'];
  durationArray: any[] = [];

  periodicTime;
  recordFound = 'false';
  createdDate: any;
  
  rateControl: any;
  addCircleMessage: any;
  deleteFileMessage: any;
  stateData:any;

  medication = {
    medicine: '',
    dose: '',
    route: '',
    freq: '',
    totalDose: '',
    sideEffect: ''
  }
  medicationErrMsg = '';
  medicationArr = [];

  


  ngOnInit() {
    var historyData = history.state;
    this.stateData = historyData['consentID'];
    if(this.stateData)
    {
      this.rModel.consentID = this.stateData;
      this.rModel.patientID = historyData['patientID'];
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

  this.rModel.patientID=  this.rModel.patientID.trim();
  this.rModel.orgId=  this.rModel.orgId.trim();
  this.rModel.consentID=  this.rModel.consentID.trim();
  this.rModel.orgName= this.rModel.orgName.trim();
  this.rModel.docId= this.rModel.docId.trim();
  
  this.rModel.periodicArray=this.periodicArray;
  this.rModel.medicationArr= this.medicationArr;

 
this.showFhirRequest=false




if(this.rModel.medicationArr.length>0 &&this.rModel.periodicArray.length>0 ){


this.generalService.createRestraintComposition(this.rModel).subscribe(response => {
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
}else
{
  alert('Atleast one medication and periodic monitoring must be added');
}

}
  
//   let resp = response.text();
//   alert(resp);
//   this.router.navigate(['dashboard']);
// } , (error) => {
  
//   alert('error occurred');
// });

  
 

// }
   
addPeriod() {
  if (this.newPeriod.dt !== null && this.newPeriod.pulse !== '' && this.newPeriod.temperature !== ''
     && this.newPeriod.rrate !== '' && this.newPeriod.injury !== '' && this.newPeriod.bloodS !== '' && this.newPeriod.breath !== ''
    && this.periodicTime !== null && this.invalidPeriod === false) {
    
      this.periodicArray.push(this.newPeriod);
      this.periodicTime = null;
      this.newPeriod = {
      dt: null,
      pulse: '',
      temperature: '',
      rrate: '',
      injury: '',
      bloodS: '',
      breath: '',
    };
      if (this.periodicArray.length > 0) {
        this.periodValid = true;
      } else {
        this.periodValid = false;
      }
  }
}

addMedications() {
  if (this.medication.medicine && this.medication.dose && this.medication.freq && this.medication.route && this.medication.sideEffect && this.medication.totalDose) {
    this.medicationErrMsg = '';
    this.medicationArr.push(this.medication);
    this.medication = {
      medicine: '',
      dose: '',
      route: '',
      freq: '',
      totalDose: '',
      sideEffect: ''
    }
  } else {
    this.medicationErrMsg = 'All fields are required!'
    setTimeout(() => {
      this.medicationErrMsg = '';
    }, 3000);
  }
}
delMedication(index) {
  this.medicationArr.splice(index, 1);
}

deletePeriodicValue(index) {
  this.periodicArray.splice(index, 1);
  if (this.periodicArray.length > 0) {
    this.periodValid = true;
  } else {
    this.periodValid = false;
  }
}

mergeStartDate() {
  this.comparetimes();

  if (this.rStartDate && this.rStartTime) {
    this.rModel.rStartDate = this.rStartDate;
    this.rModel.rStartTime = this.rStartTime;
    sessionStorage.setItem('restraint Start date', this.rModel.rStartDate);
    const yy = new Date(this.rModel.rStartDate).getFullYear();
    const mm = new Date(this.rModel.rStartDate).getMonth();
    const dd = new Date(this.rModel.rStartDate).getDate();
    const times = this.rStartTime.split(' ')[0].split(':');
    if (this.rStartTime.split(' ')[1] === 'PM') {
      times[0] = <any>(Number(times[0]) + 12);
    }
    this.rModel.rStartDate = new Date(yy, mm, dd, Number(times[0]), Number(times[1]));
    this.compareTime();
  }
}
comparetimes() {
  if ( this.rStartDate && this.rEndDate) {
    const startdate = this.rStartDate;
    const enddate = this.rEndDate;
    sessionStorage.setItem('restraint End date', enddate);
    if (startdate.getTime() === enddate.getTime()) {
      if (this.rStartTime !== null && this.rEndTime !== null && this.rStartTime > this.rEndTime) {
        this.invalidTime = true;
      } else {
        this.invalidTime = false;
      }
    } else {
      this.invalidTime = false;
    }
  }

}
mergeEndDate() {
  this.comparetimes();
  if (this.rEndDate && this.rEndTime) {
    this.rModel.rEndDate = this.rEndDate;
    this.rModel.rEndTime = this.rEndTime;
    const yy = new Date(this.rModel.rEndDate).getFullYear();
    const mm = new Date(this.rModel.rEndDate).getMonth();
    const dd = new Date(this.rModel.rEndDate).getDate();
    const times = this.rEndTime.split(' ')[0].split(':');
    if (this.rEndTime.split(' ')[1] === 'PM') {
      times[0] = <any>(Number(times[0]) + 12);
    }
    this.rModel.rEndDate = new Date(yy, mm, dd, Number(times[0]), Number(times[1]));
    this.compareTime();
  }
}
mergePeriodicDt() {
  const yy = new Date(this.newPeriod.dt).getFullYear();
  const mm = new Date(this.newPeriod.dt).getMonth();
  const dd = new Date(this.newPeriod.dt).getDate();
  const times = this.periodicTime.split(' ')[0].split(':');
  if (this.periodicTime.split(' ')[1] === 'PM') {
    times[0] = <any>(Number(times[0]) + 12);
  }
  this.newPeriod.dt = new Date(yy, mm, dd, Number(times[0]), Number(times[1]));
}


compareTime() {
  if (this.newPeriod.dt && this.periodicTime && this.rStartDate && this.rEndDate && this.rStartTime && this.rEndTime) {
    this.mergePeriodicDt();
   if (sessionStorage.getItem('rMForm') !== null) {
      const start = new Date(this.datePipe.transform(this.rModel.rStartDate, 'shortDate') + ' ' + this.datePipe.transform(this.rModel.rStartDate, 'shortTime'));
      const end = new Date(this.datePipe.transform(this.rModel.rEndDate, 'shortDate') + ' ' + this.datePipe.transform(this.rModel.rEndDate, 'shortTime'));
      if (this.newPeriod.dt >= start && this.newPeriod.dt <= end) {
        this.invalidPeriod = false;
      } else {
        this.invalidPeriod = true;
      }
     } else {
      if (this.newPeriod.dt >= this.rModel.rStartDate && this.newPeriod.dt <= this.rModel.rEndDate) {
        this.invalidPeriod = false;
      } else {
        this.invalidPeriod = true;
      }
     }
}
}


fetchFhirRequest()
{

}


}

