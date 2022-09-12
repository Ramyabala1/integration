/* eslint-disable no-var */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PersonServiceService } from 'src/app/person-service.service';

@Component({
  selector: 'app-viewhealth',
  templateUrl: './viewhealth.page.html',
  styleUrls: ['./viewhealth.page.scss'],
})
export class ViewhealthPage implements OnInit {

  viewHealth = {
    api: 'mosip-emanas.ehr.view',
    version: '1.0',
    compositions: [],
    patientId: '',
    episodeId: ''
  };

  adRes: any;
  compArray: any[] = [];
  opDisplayData: any;
  public VFChildren: Array<any> = [];
  public ComplaintDisplay: Comps[] = [];
  public DiagnosisDisplay: Array<any> = [];
  public medicationDisplay: Array<any> = [];
  public OPArray = [];
  public IPArray = [];
  public AssArray = [];
  public RestraintArray = [];
  public DischargeArray = [];
  public TherapyArray = [];
  ipDisplayDataA: any;
  restDisplayData: any;
  dischargeDisplayData: any;
  therapyDisplayData: any;
  assessementDisplayData: any;
  opBmr: any;

  constructor(private router: Router, private service: PersonServiceService) { }

  ngOnInit() {
    this.viewDetails();
  }

  viewDetails() {
    const facconst = {
      compositionId: sessionStorage.getItem('compositionId')
    };
    this.compArray.push(facconst);
    console.log("compArray: ", this.compArray);
    this.viewHealth.compositions = this.compArray;
    this.viewHealth.patientId = sessionStorage.getItem('patientId');
    this.viewHealth.episodeId = sessionStorage.getItem('episodeId');
    console.log("request: ", this.viewHealth);
    this.service.view(this.viewHealth).subscribe((response: any) => {
      if (response.code === 400) {
        console.log('entered in 400');;
      }
      else if (response.code === 200) {
        console.log('resOut.output:', response.response);
        this.adRes = JSON.parse(response.response);
        console.log(this.adRes);
        for (let i = 0; i < this.adRes.length; i++) {
          var resOutt = this.adRes[i];
          console.log("resOut.output:", resOutt);
          this.opDisplayData = resOutt.output;

          if (resOutt.template === "MHMS - op_bmr.v2") {
            if (this.opDisplayData.resultSet[0].medicationItem != null) {
              const nameDose = this.opDisplayData.resultSet[0].medicationItem;
              const nameArray = nameDose.split("_");
              let DurType = "";
              let dirDu = "";
              if (this.opDisplayData.resultSet[0].directionDuration != null) {
                if (
                  this.opDisplayData.resultSet[0].directionDuration.substring(
                    this.opDisplayData.resultSet[0].directionDuration.length - 1
                  ) === "W"
                ) {
                  DurType = "Weeks";
                } else if (
                  this.opDisplayData.resultSet[0].directionDuration.substring(
                    this.opDisplayData.resultSet[0].directionDuration.length - 1
                  ) === "D"
                ) {
                  DurType = "Days";
                } else if (
                  this.opDisplayData.resultSet[0].directionDuration.substring(
                    this.opDisplayData.resultSet[0].directionDuration.length - 1
                  ) === "M"
                ) {
                  DurType = "Months";
                }
                dirDu = this.opDisplayData.resultSet[0].directionDuration.substring(
                  1,
                  this.opDisplayData.resultSet[0].directionDuration.length - 1
                );
              }
              const med = {
                medName: nameArray[0],
                medDose: nameArray[1],
                timingDescription: this.opDisplayData.resultSet[0].timingDescription,
                directionDuration: dirDu + " " + DurType,
                overallDirectionDescription: this.opDisplayData.resultSet[0].additionalInstructions,
                createdTime: this.opDisplayData.resultSet[0].date_created,
              };
              this.medicationDisplay.push(med);
            }
            const obj = {
              Hospitalname: resOutt.hospitalName,
              diagnosticCertainity: this.opDisplayData.resultSet[0].diagnosticCertainity,
              problemCode: this.opDisplayData.resultSet[0].problemDiagnosisCode,
              problemDiagnosis: this.opDisplayData.resultSet[0].problemDiagnosis,
              symptomName: this.opDisplayData.resultSet[0].symptomSign,
              pattern: this.opDisplayData.resultSet[0].pattern,
              author: this.opDisplayData.resultSet[0].author,
              clinicalHistory: this.opDisplayData.resultSet[0].clinicalHistory,
              comorbidDiagnosis: this.opDisplayData.resultSet[0].comorbidDiagnosis,
              confidentialityLevel: i,
              date_created: this.opDisplayData.resultSet[0].date_created,
              followUpDate: this.opDisplayData.resultSet[0].followUpDate,
              illnessSummary: this.opDisplayData.resultSet[0].illnessSummary,
              improvementStatus: this.opDisplayData.resultSet[0].improvementStatus,
              medicationNarrative: this.opDisplayData.resultSet[0].medicationNarrative,
              problemDiagnosisTerminology: this.opDisplayData.resultSet[0].problemDiagnosisTerminology,
              reasonDescription: this.opDisplayData.resultSet[0].reasonDescription,
              reasonOfReferral: this.opDisplayData.resultSet[0].reasonOfReferral,
              referredDoctor: this.opDisplayData.resultSet[0].referredDoctor,
              referredOrganization: this.opDisplayData.resultSet[0].referredOrganization,
              serviceName: this.opDisplayData.resultSet[0].serviceName,
              serviceNarrative: this.opDisplayData.resultSet[0].serviceNarrative,
              treatmentInstructions: this.opDisplayData.resultSet[0].treatmentInstructions,
              uid: this.opDisplayData.resultSet[0].uid,
              DiagnosisDisplay: this.DiagnosisDisplay,
              ComplaintDisplay: this.ComplaintDisplay,
              medicationDisplay: this.medicationDisplay,
            }
            this.OPArray.push(obj);
            sessionStorage.setItem('OPArray', JSON.stringify(this.OPArray));
            console.log("this.OPArray:", this.OPArray);
          } else if (resOutt.template === "MHMS - Therapy reporting.v0") {
            console.log("Inside therapy");
            this.therapyDisplayData = resOutt.output;
            console.log("therapy:", this.therapyDisplayData);
            const therapyObj = {
              author: this.therapyDisplayData.resultSet[0].author,
              education: this.therapyDisplayData.resultSet[0].education,
              occupation: this.therapyDisplayData.resultSet[0].occupation,
              createdDate: this.therapyDisplayData.resultSet[0].date_created,
              sessionNumber: this.therapyDisplayData.resultSet[0].session,
              therapyType: this.therapyDisplayData.resultSet[0].therapyType,
              therapyMethod: this.therapyDisplayData.resultSet[0].therapyMethod,
              objective: this.therapyDisplayData.resultSet[0].objectiveSession,
              therapyTechnique: this.therapyDisplayData.resultSet[0].therapyTechnique,
              keyIssue: this.therapyDisplayData.resultSet[0].keyIssue,
              therapistName: this.therapyDisplayData.resultSet[0].therapistName,
              therapistQualification: this.therapyDisplayData.resultSet[0].therapistQualification,
              observationReflection: this.therapyDisplayData.resultSet[0].therapyObservation,
              supervisorName: this.therapyDisplayData.resultSet[0].supervisorName,
              supervisorQualification: this.therapyDisplayData.resultSet[0].supervisorQualification,
              nextSessionDate: this.therapyDisplayData.resultSet[0].nextSessionDate,
              nextSession: this.therapyDisplayData.resultSet[0].nextSession,
              psychiatricDiagnosis: this.therapyDisplayData.resultSet[0].psychiatricDiagnosis,
            };
            this.TherapyArray.push(therapyObj);
            sessionStorage.setItem('TherapyArray', JSON.stringify(this.TherapyArray));
            console.log("TherapyArray:", this.TherapyArray);
          }
        }
      }
      this.router.navigate(['viewhealth']);
    });
  }

  link() {
    this.router.navigate(['homepage']);
  }

  get1() {
    var x = sessionStorage.getItem('mhpname');
    document.getElementById('demo').innerHTML = x;
    var x = sessionStorage.getItem('illnessSummary');
    document.getElementById('demo1').innerHTML = x;
    var x = sessionStorage.getItem('improvement');
    document.getElementById('demo2').innerHTML = x;
    var x = sessionStorage.getItem('diff');
    document.getElementById('demo3').innerHTML = x;
    var x = sessionStorage.getItem('clinical');
    document.getElementById('demo4').innerHTML = x;
    var x = sessionStorage.getItem('follow');
    document.getElementById('demo5').innerHTML = x;
  }

}

export class Comps {
  symptomName: string;
  pattern: string;
}


