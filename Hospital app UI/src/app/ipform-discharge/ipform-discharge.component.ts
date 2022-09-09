import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GeneralService } from '../general.service';
import { MatSnackBar, MatAutocompleteSelectedEvent} from '@angular/material';




import {IPDISCHARGE} from '../ipDischargeModel';
@Component({
  selector: 'ipform-discharge.component',
  templateUrl: './ipform-discharge.component.html',
  styleUrls: ['./ipform-discharge.component.css']
})


export class IpFormDischargeComponent implements OnInit {

  toggleDurationName: any;
  toggleDiagnosisType: string;
  istriggered: boolean;
  validDuration: boolean;
  constructor(private router: Router, private generalService: GeneralService, private snackBar: MatSnackBar,
    ) {
  }
  color = 'accent';
  mode = 'indeterminate';
  value = 100;
  category: any;
  clinicName: any;
  recordFound = 'false';
  MHEseasons: string[] = ['FollowUp', 'Refer', 'Move to IP', 'Review SOS'];
  OPseasons: string[] = ['FollowUp'];
  Visit: string[] = ['Regular OPD Visit', 'Emergency'];
  improveArray: string[] = [];
   public diagnosisTypes = ['Differential Diagnosis' , 'Provisional Diagnosis'];
  public durationTypes = ['Days','Weeks','Month'];
  patientData: any;
  complaints = [];
  dias = [];
  op_view: boolean;
  comValid = false;
  tomorrow: any;
  puuid: any;
  today: Date;
  public birthdate: Date;
  public age: number;
  orgName: any;
  history: any;
  illness: any;
  addNote: any;
  improveStatus: any;
 
  public OPVF = [];
  public IPVF = [];
  complaintTemplate: any;
  diagnosisTemplate: any;
  historyTemplate: any;
  illnessTemplate: any;
  addNoteTemplate: any;
  improveStatusTemplate: any;
  prescriptionTemplate: any;
  followUpTemplate: any;
  referralTemplate: any;
  capacityTemplate: any;
  public VFChildren = [];
  comp: any;
  public newComplaint: { complaint: any, duration: any };
  public complaintArray = [];
  public newDiagnosis: { diagnosisType: any, description: any, icdCode: any };
  public newPrescription: { medName: any, medTime: any, medDuration1: any, medDuration2: any, medDosage: any, medRemarks: any };
  public diagnosisArray = [];
  public prescriptionArray = [];
  userName: any;
  icdCodes = [];
  icdDesc = [];
  drugNames;
  dosage = [];
  sessionToken: any;
  userUuid: any;
  orgUuid: any;
  seasons: any;
  orgType: any;
  mhmsID: any;
  PatientAD = 'No';
  createdDate: any;
  treatedMhp: any;
  error_value = false;
  invalidEntry:any;
  visitType = 'Regular OPD Visit';
  deleteFileMessage: any;
  ipDisModel = new IPDISCHARGE();
  stateData:any;
  improvementStatus:['','','']
 
  showFhirRequest=false;
  gname:any;
  gphone:any;
  admitDate:any;
  adTime1:any;
  response:any;

  disDate: any;
  disTime1: any;
  dischargeType: any;
  dischargeCondition: any;
  conditionDescription:any;
  treatmentAdvice:any;
  followUp:any;
  docName:any;
  designation:any;
  validDisDate:any;

  ngOnInit() {
    
    this.orgType = sessionStorage.getItem('orgType');
   
    this.istriggered = true;
   
    this.toggleDurationName = 'Duration Type';
    this.toggleDiagnosisType = 'Diagnosis Type';
    this.newDiagnosis = { diagnosisType: '', description: '', icdCode: '' };
    this.newComplaint = { complaint: '', duration: '' };
    this.newPrescription = { medName: '', medTime: '', medDuration1: '', medDuration2: '', medDosage: '', medRemarks: '-' };
   
    if (this.orgType === 'MHE') {
      this.seasons = this.MHEseasons;
    } else {
      this.seasons = this.OPseasons;
    }

    var historyData = history.state;
    this.stateData = historyData['consentID'];

    if(this.stateData)
    {
      this.ipDisModel.consentID = this.stateData;
      this.ipDisModel.patientID = historyData['patientID'];
    }

   
    this.puuid = sessionStorage.getItem('patientUuid');
    // this.getVF();
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

  opensMenu(menuTrigger) {
    if (this.istriggered === true ) {
      menuTrigger.openMenu();
    } else {
      menuTrigger.closeMenu();
    }
    this.istriggered = false;
  }
  toggleFocus() {
    this.istriggered = true;
  }

  addDiagnosisType(diagType) {
    this.toggleDiagnosisType = diagType;
    this.newDiagnosis.diagnosisType = diagType;
    diagType = '';
    this.addDiagnosis();
  }

  addDiagnosis() {
    const diagnosis = this.newDiagnosis.diagnosisType !== '' && this.newDiagnosis.description !== '' && this.newDiagnosis.icdCode !== '';
    if (diagnosis) {
      this.diagnosisArray.push(this.newDiagnosis);
      this.toggleDiagnosisType = 'Diagnosis Type';
      this.newDiagnosis = { diagnosisType: '', description: '', icdCode: '' };
      this.icdDesc = [];
      this.icdCodes = [];
    }
  }
  addDurationType(duration) {
    this.toggleDurationName = duration;
    this.newPrescription.medDuration2 = duration;
    duration = '';
    this.addPrescription();
  }
  onSelectionChanged(event: MatAutocompleteSelectedEvent) {
    const opt = event.option.value;
    this.newDiagnosis.icdCode = opt.code;
    this.newDiagnosis.description = opt.description;
    this.addDiagnosis();
    this.icdCodes = [];
    this.icdDesc = [];

  }
  addPrescription() {
    if (!this.isEmptyOrSpaces(this.newPrescription.medName)
     && !this.isEmptyOrSpaces(this.newPrescription.medTime)
      && !this.isEmptyOrSpaces(this.newPrescription.medDuration1)
       && !this.isEmptyOrSpaces(this.newPrescription.medDuration2)
       && this.newPrescription.medDosage !== ''
       && this.newPrescription.medRemarks !== ''
       && this.validDuration) {
      this.prescriptionArray.push(this.newPrescription);
      this.toggleDurationName = 'Duration Type';
      this.newPrescription = { medName: '', medTime: '', medDuration1: '', medDuration2: '', medDosage: '', medRemarks: '-' };
    }
  }
  onSelectMedicine(event: MatAutocompleteSelectedEvent) {
    const opt = event.option.value;
    this.newPrescription.medName = opt.moleculeName;
    this.dosage = opt.dosages.split(',');
    this.addPrescription();
  }

  manageDuration(dur) {
    if(dur.invalid){
      this.validDuration = false;
    } else {
      this.validDuration = true;
    }
    
  }


 isEmptyOrSpaces(str) {
    return str === null || str.match(/^ *$/) !== null;
}

deletecomplaintValue(index) {
    this.complaintArray.splice(index, 1);
    if (this.complaintArray.length <= 0) {
      this.comValid = false;
    }
}
deleteDiagnosisValue(index) {
  this.diagnosisArray.splice(index, 1);
}
deletePrescription(index) {
  this.prescriptionArray.splice(index, 1);
}



save() {
  this.ipDisModel.medicationDisplay = this.prescriptionArray;
  
  this.generalService.createIPDischarge(this.ipDisModel).subscribe(response => {    
    let resp = response.text();
    alert(resp);
    this.router.navigate(['dashboard']);
  } , (error) => {
    
    alert('error occurred');
  });

}
   
fetchFhirRequest()
{
  this.ipDisModel.medicationDisplay = this.prescriptionArray;
  
 
  this.generalService.createIPDishargeFhirRequest(this.ipDisModel).subscribe(response => {
    
    let resp = response.text();
    this.showFhirRequest = true;
    this.response = resp;
   
  } , (error) => {
    
    alert('error occurred');
  });
}




addComplaints() {
  if (this.newComplaint.complaint !== ''
    && this.newComplaint.duration !== ''
    && this.complaintArray.length < 5
    && this.validDuration) {
     this.complaintArray.push(this.newComplaint);
    this.newComplaint = { complaint: '', duration: '' };
     if (this.complaintArray.length > 0) {
       this.comValid = true;
     }
  }
}

mergeDischargeDate() {
  if (this.ipDisModel.disDate && this.ipDisModel.disTime1) {
    const yy = new Date(this.ipDisModel.disDate).getFullYear();
    const mm = new Date(this.ipDisModel.disDate).getMonth();
    const dd = new Date(this.ipDisModel.disDate).getDate();
    const times = this.ipDisModel.disTime1.split(' ')[0].split(':');
    if (this.ipDisModel.disTime1.split(' ')[1] === 'PM') {
      times[0] = <any>(Number(times[0]) + 12);
    }
    this.ipDisModel.disDate = new Date(yy, mm, dd, Number(times[0]), Number(times[1]));
   
  }
}


}

export class Comps {
  symptomName: string;
  pattern: string;
}