import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GeneralService } from 'src/app/general.service';
import { MatSnackBar } from '@angular/material';
import { DatePipe } from '@angular/common';
// import { Console } from 'console';

@Component({
  selector: 'consentrequest.component-component',
  templateUrl: './consentrequest.component.html',
  styleUrls: ['./consentrequest.component.css'],
  providers: [DatePipe]
})

export class ConsentRequestComponent implements OnInit {
  docName: string;
  toggleDurationName: any;
  toggleDiagnosisType: string;
  istriggered: boolean;
  validDuration: boolean;
  constructor(private router: Router, private generalService: GeneralService, private snackBar: MatSnackBar, private datePipe: DatePipe
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
  public diagnosisTypes = ['Differential Diagnosis'];
  public durationTypes = ['Days', 'Weeks', 'Month'];
  patientData: any;
  complaints = [];
  dias = [];
  op_view: boolean;
  message: any;
  patientid: any;
  hiu: any;
  hip: any;
  accessmode = ['STORE', 'VIEW'];
  identifierValue: any;
  reqName: any;
  accessValue: "";
  fixmail = true;
  accept = false;
  mob: any;
  timeOut: boolean;
  timeOutMHMSID: boolean;
  tempArray: any;
  requestconsent: boolean;
  //consentrequest = new ConsentRequest();
  norecordsFound: any;
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
  mosipID: any;
  type: any;
  showDec: boolean;
  to: any;
  otpObj: any;
  otpResponseMessage: any;
  mobToken: any;
  mobileVerify = false;
  showOTPField = false;
  mobilerefID = '';
  showMobileOTPField = false;
  otpExpireTime: any;
  otpResentOtp: any;
  enableDateRange: boolean;
  public otpbtns = true;
  otpVerified: any;
  otpNotMatch: any;
  public disp = false;
  public disp1 = false;
  public msg = false;
  otpcard = false;
  verifyType = 'eManas';
  otpType = 'Mobile'
  otpArray: any[] = [];
  newArray = [];
  accessType = 'VIEW';
  consentRequestID: any;
  transactionId: any;
  formName: any;
  // consentVerify = {
  //   otp: '',
  //   consentRequestID: '',
  //   patientId: '',
  //   orgmapping: false,
  //   mhpRegId: '',
  //   type: '',
  //   mosId: ''
  // }
  consentVerify = {
    otp: '',
    transactionId: '',
    // consentRequestID: '',
    timestamp: '',
  }
  idNumber: '7620149124';
  consentrequest = {
    timestamp: '',
    AuthorizationType: '',
    consent: {
      requester: {
        name: '',
        identifier: {
          type: '',
          value: '',
          system: '',
        }
      },
      permission: {
        accessMode: "VIEW",
        dateRange: {
          from: '',
          to: '',
        }
      },
      hiTypes: ["IPBMR"],
      hiu: {
        id: '',
      },
      hip: {
        id: '',
      },
      patient: {
        id: '',
        authService: "MOSIP",
        serviceParameters: {
          vid: '',
          otpChannel: '',
        }
      },
      purpose: {
        code: '',
      }
    }
  }

  ngOnInit() {

  
  this.hiu='KA-11-MHE-006790' ;
  this.hip= 'KA-04-MHE-007141';
  this.identifierValue='KA-11-MHP-01-000130';
  this.reqName = 'Dr Bhat';
    this.enableDateRange = false;
    var data = history.state;
    if (data['name'] !== null) {
      this.formName = data['name'];
      this.accessValue = data['permission'];
      if (data['permission'] === 'VIEW' && data['name'] !== 'AD') {
        this.enableDateRange = true;
      }
      if (data['permission'] === 'STORE') {
        if (data['name'] === 'OPFORM') {
          this.consentrequest.consent.hiTypes.push('OPBMR');
        }
        if (data['name'] === 'IPFORM') {
          this.consentrequest.consent.hiTypes.push('IPBMR');
        }
        if (data['name'] === 'DISCHARGE') {
          this.consentrequest.consent.hiTypes.push('IPDISCHARGE');
        }
        if (data['name'] === 'RESTRAINT') {
          this.consentrequest.consent.hiTypes.push('RM');
        }
        if (data['name'] === 'ASSESSMENT') {
          this.consentrequest.consent.hiTypes.push('ASMENT');
        }
        if (data['name'] === 'THERAPY') {
          this.consentrequest.consent.hiTypes.push('THERAPY');
        }
      }
      if (data['name'] === 'AD') {
        this.consentrequest.consent.hiTypes.push('Advance Directive');
      }
    }
    this.from = new Date();
    this.to = new Date();
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
  toggleFocus() {
    this.istriggered = true;
  }

  save() {
    this.consentrequest.AuthorizationType = 'SMSOTP';
    this.consentrequest.timestamp = new Date().toString();
    this.consentrequest.consent.hiu.id = this.hiu;
    this.consentrequest.consent.hip.id = this.hip;
    this.consentrequest.consent.purpose.code = 'CAREMGT'
    this.consentrequest.consent.patient.id = this.patientid;
    this.consentrequest.consent.requester.identifier.value = this.identifierValue;
    this.consentrequest.consent.requester.identifier.system = "https://e-manas.karnataka.gov.in/";
    this.consentrequest.consent.requester.name = this.reqName;
    this.consentrequest.consent.permission.accessMode = 'VIEW';
    this.consentrequest.consent.permission.dateRange.from = this.from;
    this.consentrequest.consent.permission.dateRange.to = this.to;
    this.consentrequest.consent.patient.authService = this.verifyType;

    if (this.otpType == 'PHONE') {
      this.otpArray = [];
      this.otpArray.push('PHONE');
      this.newArray[0] = this.otpArray;
      this.consentrequest.consent.patient.serviceParameters.otpChannel = this.newArray[0];
      this.consentrequest.consent.patient.serviceParameters.vid = this.idNumber;
    } else if (this.otpType == 'EMAIL') {
      this.otpArray = [];
      this.otpArray.push('EMAIL');
      this.newArray[0] = this.otpArray;
      this.consentrequest.consent.patient.serviceParameters.otpChannel = this.newArray[0];
      this.consentrequest.consent.patient.serviceParameters.vid = this.idNumber;
    } else if (this.otpType == 'EMAIL,PHONE') {
      this.otpArray = [];
      this.otpArray.push('EMAIL');
      this.otpArray.push('PHONE');
      this.newArray[0] = this.otpArray;
      this.consentrequest.consent.patient.serviceParameters.otpChannel = this.newArray[0];
      this.consentrequest.consent.patient.serviceParameters.vid = this.idNumber;
    }
    console.log(this.consentrequest);
    // this.generalService.consentRequest(this.consentrequest).subscribe(response => {
    this.generalService.initConsent(this.consentrequest).subscribe(response => {
      if (response.status === 200) {
        let remarkList = response.json();
        var jsonResponse = response.json();
        var result = jsonResponse.entity;
        console.log("tran Id jsonResponse: ", jsonResponse);
        console.log("tran Id result: ", result);
        if (jsonResponse.code === 200) {
          console.log("entered in 200");
          this.transactionId = jsonResponse.transactionId;
          this.enableMessage = true;
        } else if (jsonResponse.code === 400) {
          console.log("entered in 400");
          this.enableMessage = false;
          alert(jsonResponse.errors);
        }
        // if (result['message'] !== undefined) {
        //   this.enableMessage = true;
        //   alert(result['message']);
        //   this.consentRequestID = result['ConsentRequestID'];
        //   this.transactionId = jsonResponse.transactionId;
        //   this.message = "ConsentRequestID: " + result['ConsentRequestID'];
        //   if (result['type'] !== undefined && result['type'] === 'Mosip') {
        //     this.type = result['type'];
        //     this.mosipID = result['mosipID'];
        //   }
        // }
        // else if (result['error'] !== undefined) {
        //   var error = result['error'];
        //   alert(error["message"]);
        //   this.message = error['message'];
        // } else {
        //   alert(result);
        //   this.timeOutMHMSID = true;
        // }
      }
    }, (error) => {
      alert('error occurred');
    });
  }

  enableDate(data: any) {
    this.accessValue = data.value;
    if (data.value === 'VIEW') {
      this.enableDateRange = true;
    } else {
      this.enableDateRange = false;
    }
  }

  // searchMHMSID() {
  //   this.showDec = true;
  //   this.enableMessage = false;
  //   this.requestconsent = true;
  //   if (this.patientid.length === 17) {
  //     this.save('emanas');
  //     this.generalService.getPatient(this.patientid, this.hiu, this.hip).subscribe((response: any) => {
  //       if (response.status === 200) {
  //         var jsonResponse = response.json();
  //         var result = jsonResponse.entity;
  //         if (result === 'patient found') {
  //           this.save('emanas');
  //         }
  //         else if (result === 'patient not under org') {
  //           this.showDec = true;
  //         } else {
  //           this.norecordsFound = result;
  //           this.timeOutMHMSID = true;
  //           setTimeout(() => {
  //             this.timeOutMHMSID = false;
  //           }, 6000);
  //         }
  //       } else if (response.status === 400) {
  //         alert("Invalid data");
  //       }
  //     }, (error) => {
  //       this.norecordsFound = 'Invalid Error';
  //       this.timeOutMHMSID = true;
  //       setTimeout(() => {
  //         this.timeOutMHMSID = false;
  //       }, 6000);
  //     });
  //   }
  // }

  verifyOTP(otpMobile) {
    // this.consentVerify.consentRequestID = this.consentRequestID;
    this.consentVerify.transactionId = this.transactionId;
    this.consentVerify.otp = otpMobile.value;
    // this.consentVerify.patientId = this.patientid;
    // this.consentVerify.orgmapping = this.showDec;
    // this.consentVerify.mhpRegId = this.identifierValue;
    this.consentVerify.timestamp = new Date().toString();
    // if (this.type !== undefined && this.mosipID !== undefined) {
    //   this.consentVerify.mosId = this.mosipID;
    //   this.consentVerify.type = this.type;
    // }
    if (otpMobile.value.length > 5) {
      // this.generalService.verifyConsent(this.consentVerify).subscribe(response => {
      this.generalService.createConsent(this.consentVerify).subscribe(response => {
        const res = response as any;
        console.log("res: ", res);
        var jsonresponse = response.json();
        console.log("jsonresponse: ", jsonresponse);
        if (jsonresponse.status === 200) {
          var result = jsonresponse.message;
          console.log("result: ", result);
          this.enableMessage = true;
          // if (result['message'] != null) {
          if (jsonresponse.message != null) {
            this.otpVerified = jsonresponse.message;
            this.otpNotMatch = '';
            alert('Consent created successfully with ID --> ' + jsonresponse.consentID);
            if (this.formName === "OPFORM") {
              this.router.navigate(['/opForm'], {
                state: {
                  consentID: result['consentID'], patientID: this.patientid
                }
              });
            }
            if (this.formName === "IPFORM") {
              this.router.navigate(['/addIP'], {
                state: {
                  consentID: result['consentID'], patientID: this.patientid
                }
              });
            }
            if (this.formName === "DISCHARGE") {
              this.router.navigate(['/discharge'], {
                state: {
                  consentID: result['consentID'], patientID: this.patientid
                }
              });
            }
            if (this.formName === "AD") {
              this.router.navigate(['/ad'], {
                state: {
                  consentID: result['consentID'], patientID: this.patientid
                }
              });
            }
            if (this.formName === "RESTRAINT") {
              this.router.navigate(['/restraint'], {
                state: {
                  consentID: result['consentID'], patientID: this.patientid
                }
              });
            }
            if (this.formName === "ASSESSMENT") {
              this.router.navigate(['/assessment'], {
                state: {
                  consentID: result['consentID'], patientID: this.patientid
                }
              });
            }
            if (this.formName === "THERAPY") {
              this.router.navigate(['/therapy'], {
                state: {
                  consentID: result['consentID'], patientID: this.patientid
                }
              });
            }
            if (this.formName === "GETBMR") {
              this.router.navigate(['/get-BMR'], {
                state: {
                  consentID: result['consentID'], patientID: this.patientid
                }
              });
            }
          }
        } else {
          var result = jsonresponse.entity.error;
          this.enableMessage = true;
          this.otpVerified = '';
          if (result['message'] != null) {
            alert(result['message']);
            this.otpNotMatch = result['message'];
          }
        }
      });
    }
  }

  onChange(event) {
    if (event.checked) {
      this.requestconsent = true;
    }
  }

  hide() {
    this.showDec = false;
  }

}
