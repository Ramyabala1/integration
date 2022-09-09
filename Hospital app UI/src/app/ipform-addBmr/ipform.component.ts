import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GeneralService } from '../general.service';
import { MatSnackBar, MatAutocompleteSelectedEvent} from '@angular/material';




import {IPBMR} from '../ipModel';
@Component({
  selector: 'ipform-component',
  templateUrl: './ipform.component.html',
  styleUrls: ['./ipform.component.css']
})


export class IpFormComponent implements OnInit {
  docName: string;
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
  followUp: any;
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
  ipModel = new IPBMR();
  stateData:any;
  improvementStatus:['','','']
  responseDetails:any;
  showFhirRequest=false;
  gname:any;
  gphone:any;
  admitDate:any;
  adTime1:any;
  response:any;
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
      this.ipModel.consentID = this.stateData;
      this.ipModel.patientID = historyData['patientID'];
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

mergeAdmitDate() {
  if (this.admitDate && this.adTime1) {
    const yy = new Date(this.admitDate).getFullYear();
    const mm = new Date(this.admitDate).getMonth();
    const dd = new Date(this.admitDate).getDate();
    const times = this.adTime1.split(' ')[0].split(':');
    if (this.adTime1.split(' ')[1] === 'PM') {
      times[0] = <any>(Number(times[0]) + 12);
    }
    this.admitDate = new Date(yy, mm, dd, Number(times[0]), Number(times[1]));
   
  }
}

save() {

  this.ipModel.patientID=  this.ipModel.patientID.trim();
  this.ipModel.orgId=  this.ipModel.orgId.trim();
  this.ipModel.consentID=  this.ipModel.consentID.trim();
  this.ipModel.orgName= this.ipModel.orgName.trim();
  this.ipModel.docId= this.ipModel.docId.trim();


  this.ipModel.medicationDisplay = this.prescriptionArray;
  this.ipModel.complaintDisplay = this.complaintArray;
  this.ipModel.diagnosisDisplay = this.diagnosisArray;
  this.ipModel.gname = this.gname;
  this.ipModel.gphone = this.gphone;
  this.ipModel.gdmitDate = this.admitDate.toJSON();
  this.generalService.createCompositionIPBMR(this.ipModel).subscribe(response => {    
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
   
fetchFhirRequest()
{
  this.ipModel.medicationDisplay = this.prescriptionArray;
  this.ipModel.complaintDisplay = this.complaintArray;
  this.ipModel.diagnosisDisplay = this.diagnosisArray;
  this.ipModel.gname = this.gname;
  this.ipModel.gphone = this.gphone;
  this.ipModel.gdmitDate = this.admitDate.toJSON();
  this.generalService.CreateIPFhirRequest(this.ipModel).subscribe(response => {
    
    let resp = response.text();
    this.showFhirRequest = true;
    this.response = resp;
   
  } , (error) => {
    
    alert('error occurred');
  });
}



// getVF() {
//   this.generalService.getPersonVF(this.puuid).subscribe(response => {
//     const VFData = <any>response;
//     this.spinn.hide();
//     if (VFData) {
//       this.VFChildren = VFData.children;
//     } else {
//       sessionStorage.setItem('manualSpinn', 'false');
//       this.spinn.hide();
//     }
//     if (this.VFChildren != null) {
//     for (let l = 0; l < this.VFChildren.length; l++) {
//       if (this.VFChildren[l].name.substring(0, 2) === 'OP') {
//           this.OPVF.push(this.VFChildren[l]);
//       // } else if (this.VFChildren[l].name.substring(0, 2) === 'IP') {
//       //   this.IPVF.push(this.VFChildren[l]);
//       }
//     }
//     if (this.OPVF.length > 0) {
//      this.recordFound = 'true';
//    }
  
//   }
//   });
// }

// getPLVData() {
//   sessionStorage.setItem('manualSpinn', 'true');
 
//   this.generalService.getPatientLVD(this.puuid, 'OP').subscribe(res => {
//     const response = <any>res;
//     const VFData = response.item;
//     const ComplaintDisplay: Array<any> = [];
//     const DiagnosisDisplay: Array<any> = [];
//     const medicationDisplay: Array<any> = [];
//     this.recordFound = 'true';
//     VFData[0]['name'] = 'op_bmr_v2_matches_compositionIDs';
//     this.generalService.getopComposition(this.puuid, VFData[0]).subscribe(res2 => {
//       const response2 = <any>res2;
//       const re1 = response2.resultSet;
//       const re = response2.resultSet[0];
//       re1.forEach(element => {
//         if (element.symptomSign != null) {
//           const com = {
//                     complaint : element.symptomSign,
//                     duration : element.pattern
//                   };
//                   ComplaintDisplay.push(com);
//               }
//         if (element.clinicalHistory != null) {
//           this.ipModel.clinicalHistory = element.clinicalHistory;
//           this.ipModel.treatmentInstructions = element.treatmentInstructions;
//           // this.improveStatus = element.improvementStatus;
//          }
         
//         if (element.illnessSummary != null) {
//           this.ipModel.illnessSummary = element.illnessSummary;
//         }

//         if (element.serviceName === 'FollowUp') {
//           this.ipModel.followUpDate = element.followUpDate;
//           this.ipModel.category = element.serviceName
//         }
//         if (element.diagnosticCertainity != null) {
//           const diag = {
//             diagnosisType :  element.diagnosticCertainity,
//             icdCode : element.problemDiagnosisCode,
//             description : element.problemDiagnosis
//           };
//           DiagnosisDisplay.push(diag);
//         }
//          if (element.medicationItem != null) {
//           const nameDose = element.medicationItem;
//           const nameArray = nameDose.split('_');
//           let DurType = '';
//           if (element.directionDuration.substring(element.directionDuration.length - 1) === 'W') {
//                 DurType = 'Weeks';
//           } else if (element.directionDuration.substring(element.directionDuration.length - 1) === 'D') {
//               DurType = 'Days';
//             } else if (element.directionDuration.substring(element.directionDuration.length - 1) === 'M') {
//               DurType = 'Months';
//             }
//           const med = {
//             'medName': nameArray[0],
//             'medDosage' : nameArray[1],
//             'medTime' : element.timingDescription,
//             'medDuration2': DurType,
//             'medDuration1' : element.directionDuration.substring(1, element.directionDuration.length - 1),
//             'medRemarks' : element.additionalInstructions
//           };
//           medicationDisplay.push(med);
//         }
        
//       });
//       this.diagnosisArray = DiagnosisDisplay;
//       this.complaintArray  = ComplaintDisplay;
//       this.comValid = true;
//       this.prescriptionArray = medicationDisplay;
//       if (medicationDisplay.length > 0) {
//       }
//       sessionStorage.setItem('manualSpinn', 'false');
//       this.spinn.hide();
//       });
// });
// }

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



// getAllConfigs() {
//   const param = ['Improvement_Status', 'Diagnosis_Type', 'Duration_Type'];
//   this.generalService.getAllConfigs(param)
//       .subscribe(response => {
//         const result = response;
//         let arr = result['Improvement_Status'].mhmsconfigArray;
//         for (let i = 0; i < arr.length; i++) {
//           if (arr[i].status === '1') {
//             this.improveArray.push(arr[i].value);
//           }
//         }
//         arr = result['Diagnosis_Type'].mhmsconfigArray;
//         for (let i = 0; i < arr.length; i++) {
//           if (arr[i].status === '1') {
//             this.diagnosisTypes.push(arr[i].value);
//           }
//         }
//         arr = result['Duration_Type'].mhmsconfigArray;
//         for (let i = 0; i < arr.length; i++) {
//           if (arr[i].status === '1') {
//             this.durationTypes.push(arr[i].value);
//           }
//         }
//       });
// }


}

export class Comps {
  symptomName: string;
  pattern: string;
}