import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { Http } from '@angular/http';
import { GeneralService } from 'src/app/general.service';
import { HttpClient } from "@angular/common/http";



export class Treatments {
  output: string;
  template: string;
  hospitalName:string;
}


@Component({
  selector: 'app-homepage',
  templateUrl: './getBMR.component.html',
  styleUrls: ['./getBMR.component.css']
})





export class GetBmrComponent implements OnInit {
  docName: string;
  toggleDurationName: any;
  toggleDiagnosisType: string;
  istriggered: boolean;
  validDuration: boolean;
  constructor(private http: Http, private _formBuilder: FormBuilder, private httpClient: HttpClient, private router: Router,
    private gs: GeneralService) {
  }
  patientData: any;
  complaints = [];
  dias = [];
  op_view: boolean;
  message: any;
  patientid: any;
  hiu: any;
  hip: any;
  hiTypes = ['InPatient Record', 'OutPatient Record', 'InPatient Discharge Record', 'Restraint Monitoring', 'Assessment', 'Therapy'];
  identifierValue: any;
  reqName: any;
  accessValue: "";
  fixmail = true;
  response: any;
  //consentrequest = new ConsentRequest();
  from: any;
  enableMessage: boolean = false;
  today = new Date();
  year = this.today.getFullYear();
  month = this.today.getMonth();
  day = this.today.getDay();
  //maxDate = new Date(this.year, this.month + 1, this.day);
  //minDate = new Date(this.year-100, this.month, this.day);
  minDate = new Date(2000, 0, 1);
  maxDate = new Date();
  to: any;
  hitype: any = [];

  consentID: any;
  consentrequest = {
    patientID: '',
    // timestamp: '',
    hiRequest: {
      consent: {
        id: '',
        purpose: {
          code: '',
        },
        permission: {
          accessMode: '',
        },
        hiTypes: []
      },
    }
  }
  newObject: any;
  newObjectd: any;
  stateData: any;
  showFhirResponse = true;
  showFhirRequest = false;

  request: any;
  adRes: any;
  approvedOn: any;
  public fieldArray: Array<any> = [];

  public resTreatment: Treatments[];
  public OPArray = [];
  orgName: any;
  mheName: any;
  MHEList: any;
  createdDate: any;
  treatedMhp: any;
  public IPArray = [];
  ipDisplayDataA: any;
  hospitalname:any;
  opDisplayData: any;
  public VFChildren: Array<any> = [];
  public ComplaintDisplay: Comps[] = [];
  public DiagnosisDisplay: Array<any> = [];
  public medicationDisplay: Array<any> = [];


  // getTreatmentForm: FormGroup;
  // psCheckboxGroup: any;

  ngOnInit() {
    var data = history.state;
    this.hitype = [];
    this.from = new Date();
    this.to = new Date();
    var historyData = history.state;
    this.stateData = historyData['consentID'];
    if (this.stateData) {
      this.consentID = this.stateData;
      this.patientid = historyData['patientID'];
    }
  }

  getBMR() {
    this.consentrequest.patientID = this.patientid;
    // this.consentrequest.patientID = "KA-02-2021-038452";
    this.consentrequest.hiRequest.consent.id = this.consentID;
    // this.consentrequest.hiRequest.consent.id = "b33fc050-7fff-47e0-a53a-474344e2323a";

    this.consentrequest.hiRequest.consent.purpose.code = 'CAREMGT'
    this.consentrequest.hiRequest.consent.permission.accessMode = 'VIEW'
    this.consentrequest.hiRequest.consent.hiTypes = this.hitype;
    this.gs.getBMRForPatient(this.consentrequest).subscribe((response: any) => {
      if (response.text() !== "[]") {
        try {
          this.adRes = JSON.parse(response.text());
          // console.log("response: ", this.adRes);
          // console.log("response: ", this.adRes.response);
          this.resTreatment = JSON.parse(this.adRes.response);
          for (let i = 0; i < this.resTreatment.length; i++) {
            var resOutt = this.resTreatment[i];
            this.opDisplayData = resOutt.output;
            console.log("opDisplayDataA:", this.opDisplayData);
            console.log("resOut.output:", resOutt.template);

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
                  // "specificDirectionDescription" : element.specificDirectionDescription,
                  directionDuration: dirDu + " " + DurType,
                  overallDirectionDescription: this.opDisplayData.resultSet[0].additionalInstructions,
                  createdTime: this.opDisplayData.resultSet[0].date_created,
                };
                this.medicationDisplay.push(med);
              }
              const obj = {
               
                Hospitalname:resOutt.hospitalName,
                diagnosticCertainity: this.opDisplayData.resultSet[0].diagnosticCertainity,
                problemCode: this.opDisplayData.resultSet[0].problemDiagnosisCode,
                problemDiagnosis: this.opDisplayData.resultSet[0].problemDiagnosis,

                symptomName: this.opDisplayData.resultSet[0].symptomSign,
                pattern: this.opDisplayData.resultSet[0].pattern,

                // ProblemDescription: this.opDisplayData.resultSet[0].ProblemDescription,
                // additionalInstructions: this.opDisplayData.resultSet[0].additionalInstructions,
                author: this.opDisplayData.resultSet[0].author,
                clinicalHistory: this.opDisplayData.resultSet[0].clinicalHistory,
                comorbidDiagnosis: this.opDisplayData.resultSet[0].comorbidDiagnosis,
                confidentialityLevel: i,
                date_created: this.opDisplayData.resultSet[0].date_created,
                // diagnosticCertainity: this.opDisplayData.resultSet[0].diagnosticCertainity,
                // directionDuration: this.opDisplayData.resultSet[0].directionDuration,
                followUpDate: this.opDisplayData.resultSet[0].followUpDate,
                illnessSummary: this.opDisplayData.resultSet[0].illnessSummary,
                improvementStatus: this.opDisplayData.resultSet[0].improvementStatus,
                // medicationItem: this.opDisplayData.resultSet[0].medicationItem,
                medicationNarrative: this.opDisplayData.resultSet[0].medicationNarrative,
                // pattern: this.opDisplayData.resultSet[0].pattern,
                // problemDiagnosis: this.opDisplayData.resultSet[0].problemDiagnosis,
                // problemDiagnosisCode: this.opDisplayData.resultSet[0].problemDiagnosisCode,
                problemDiagnosisTerminology: this.opDisplayData.resultSet[0].problemDiagnosisTerminology,
                reasonDescription: this.opDisplayData.resultSet[0].reasonDescription,
                reasonOfReferral: this.opDisplayData.resultSet[0].reasonOfReferral,
                referredDoctor: this.opDisplayData.resultSet[0].referredDoctor,
                referredOrganization: this.opDisplayData.resultSet[0].referredOrganization,
                serviceName: this.opDisplayData.resultSet[0].serviceName,
                serviceNarrative: this.opDisplayData.resultSet[0].serviceNarrative,
                // symptomSign: this.opDisplayData.resultSet[0].symptomSign,
                // timingDescription: this.opDisplayData.resultSet[0].timingDescription,
                treatmentInstructions: this.opDisplayData.resultSet[0].treatmentInstructions,
                uid: this.opDisplayData.resultSet[0].uid,
                DiagnosisDisplay: this.DiagnosisDisplay,
                ComplaintDisplay: this.ComplaintDisplay,
                medicationDisplay: this.medicationDisplay,
              }
              this.OPArray.push(obj);
              console.log("this.OPArray:", this.OPArray);

            } else if (resOutt.template === "MHMS - ip_bmr.v1" || resOutt.template === "MHMS - op_bmr.v1") {
              console.log("inside OP bmr");
              this.ipDisplayDataA = resOutt.output;
              console.log("ipDisplayDataA:", this.ipDisplayDataA);
              const ipobj = {
                Hospitalname:resOutt.hospitalName,
                Assessmentdate: this.ipDisplayDataA.resultSet[0].Assessmentdate,
                dischargeDate: '',
                Assessmentname: this.ipDisplayDataA.resultSet[0].Assessmentname,
                Complaints: this.ipDisplayDataA.resultSet[0].Complaints,
                CourseinHospital: this.ipDisplayDataA.resultSet[0].CourseinHospital,
                Diagnosis: this.ipDisplayDataA.resultSet[0].Diagnosis,
                Pattern: this.ipDisplayDataA.resultSet[0].Pattern,
                Sectionofadmission: this.ipDisplayDataA.resultSet[0].Sectionofadmission,
                author: this.ipDisplayDataA.resultSet[0].author,
                date_created: this.ipDisplayDataA.resultSet[0].date_created,
                uid: this.ipDisplayDataA.resultSet[0].uid,

                DiagnosisCertainity: this.ipDisplayDataA.resultSet[0].DiagnosisCertainity,
                DiagnosisCode: this.ipDisplayDataA.resultSet[0].DiagnosisCode,
                DiagnosisTerminology: this.ipDisplayDataA.resultSet[0].DiagnosisTerminology,
                Dosingtime: this.ipDisplayDataA.resultSet[0].Dosingtime,
                Duration: this.ipDisplayDataA.resultSet[0].Duration,
                HistoryofPresentIllness: this.ipDisplayDataA.resultSet[0].HistoryofPresentIllness,
                Medicinaname: this.ipDisplayDataA.resultSet[0].Medicinaname,
                Multimedianame: this.ipDisplayDataA.resultSet[0].Multimedianame,
                Pastprescription: this.ipDisplayDataA.resultSet[0].Pastprescription,
                Remarks: this.ipDisplayDataA.resultSet[0].Remarks,
                StatusofGeneralExam: this.ipDisplayDataA.resultSet[0].StatusofGeneralExam,
                SummaryofInvestigations: this.ipDisplayDataA.resultSet[0].SummaryofInvestigations,
                SummaryofMentalStatusExam: this.ipDisplayDataA.resultSet[0].SummaryofMentalStatusExam,
                SummaryofVitals: this.ipDisplayDataA.resultSet[0].SummaryofVitals,
                Uploadfile: this.ipDisplayDataA.resultSet[0].Uploadfile,
                confidentialityLevel: 0
              };
              this.IPArray.push(ipobj);
              console.log("this.IPArray:", this.IPArray);
            }



          }
        } catch (err) {
          alert(err);
        }
      } else {
        alert("No record found");
      }
    }, (error) => {
      alert('error occurred');
    });
  }

  toggleFocus() {
    this.istriggered = true;
  }

  getTreatmentSumm() {
    this.consentrequest.patientID = this.patientid.trim;
    // this.consentrequest.timestamp = new Date().toString();
    this.consentrequest.hiRequest.consent.id = this.consentID;
    this.consentrequest.hiRequest.consent.purpose.code = 'CAREMGT'
    // this.consentrequest.hiRequest.consent.patient.id = this.patientid;
    // this.consentrequest.hiRequest.consent.requester.identifier.value = this.identifierValue;
    // this.consentrequest.hiRequest.consent.requester.name = this.reqName;
    this.consentrequest.hiRequest.consent.permission.accessMode = 'VIEW'
    // this.consentrequest.hiRequest.dateRange.from = this.from;
    // this.consentrequest.hiRequest.dateRange.to = this.to;
    this.consentrequest.hiRequest.consent.hiTypes = this.hitype;
    this.gs.getBMRSummaryForPatient(this.consentrequest).subscribe(response => {
      var resList = [];
      var res = response.text();
      if (res !== null) {
        try {
          this.response = res;
        } catch (e) {
          alert(response.text()); // error in the above string (in this case, yes)!
        }
      }
    }, (error) => {
      alert('error occurred');
    });
  }

  fetchFhirRequest() {
    this.consentrequest.patientID = this.patientid.trim();
    // this.consentrequest.timestamp = new Date().toString();
    this.consentrequest.hiRequest.consent.id = this.consentID.trim();
    this.consentrequest.hiRequest.consent.purpose.code = 'CAREMGT'
    // this.consentrequest.hiRequest.consent.patient.id = this.patientid.trim();
    // this.consentrequest.hiRequest.consent.requester.identifier.value = this.identifierValue.trim();
    // this.consentrequest.hiRequest.consent.requester.name = this.reqName.trim();
    this.consentrequest.hiRequest.consent.permission.accessMode = 'VIEW'
    // this.consentrequest.hiRequest.dateRange.from = this.from;
    // this.consentrequest.hiRequest.dateRange.to = this.to;
    this.consentrequest.hiRequest.consent.hiTypes = this.hitype;
    this.showFhirRequest = true;
    this.showFhirResponse = false;
    this.request = JSON.stringify(this.consentrequest);
  }

  onChange(name: string, isChecked: boolean) {
    if (name === 'InPatient Record') {
      name = 'IPBMR';
    } else if (name === 'OutPatient Record') {
      name = 'OPBMR';
    } else if (name === 'Restraint Monitoring') {
      name = 'RESTRAINT';
    } else if (name === 'Assessment') {
      name = 'ASMENT';
    } else if (name === 'Therapy') {
      name = 'THERAPY';
    } else if (name === 'InPatient Discharge Record') {
      name = 'IPDISCHARGE';
    }
    if (isChecked) {
      this.hitype.push(name);
    } else {
      const index = this.hitype.findIndex(x => x === name);
      this.hitype.splice(index, 1)
    }
  }

  cancel() {
    if (window.confirm('Are you sure you want to leave this page?')) {
      sessionStorage.setItem('validRoute', 'true');
      this.router.navigate(['dashboard']);
    }
  }

  opensMenu(menuTrigger) {
    if (this.istriggered === true) {
      menuTrigger.openMenu();
    } else {
      menuTrigger.closeMenu();
    }
    this.istriggered = false;
  }

}

export class Comps {
  symptomName: string;
  pattern: string;
}


