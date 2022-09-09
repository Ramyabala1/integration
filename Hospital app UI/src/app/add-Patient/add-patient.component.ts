import { Component, OnInit, Pipe } from '@angular/core';
import { Location, DatePipe, TitleCasePipe } from '@angular/common';
import { Router } from '@angular/router';
import { MatSnackBar, MatSelectChange, MatAutocompleteSelectedEvent } from '@angular/material';
import { GeneralService } from 'src/app/general.service';
import * as messages from '../general-messages';
import { NgxSpinnerService } from 'ngx-spinner';

export class idType {
  key: string;
  value: string;
  order: string;
  status: string;
}


@Component({
  selector: 'app-add-patient.component',
  templateUrl: './add-patient.component.html',
  styleUrls: ['./add-patient.component.css'],
  providers: [DatePipe, TitleCasePipe]

})




export class AddPatient implements OnInit {
  today: Date;
  orgName: any;
  idType = 'MOSIP ID';
  validMobile = false;
  public showMob = false;
  mobButtonName = 'Send OTP';
  public buttonName2: any = 'Verify OTP';
  public otpmatch: any;
  error_value = false;
  email_error_value = false;
  showMsg = false;
  public validOTPMobile = false;
  userName: any;
  getlogOutResponse: any;
  id: any;
  abarkData: any;
  ids = ['ID Proof', 'None'];
  // idTypes = ['AB-ARK ID', 'Ration Card', 'Driving Licence'];
  idTypes: any[] = [];
  salutationArray: any[] = [];
  genderArray: any[] = [];

  mobileVerify = false;

  mobileNum: any;
  showMosipOTPField = false;
  validMosipOTP = false;
  showOTPField = false;
  salutation: any;
  firstname: any;
  lastname: any;
  guardianName: any;
  guardianSal: any;
  dob: any;
  gender: any;
  abId: any;
  state: any;
  address1: any;
  address2: any;
  city: any;
  district: any;
  pincode: any;
  email: any;
  timeOut: boolean;
  otpResponseMessage: any;
  mobilerefID = '';
  otpExpireTime: any;
  otpResentOtp: any;
  mobileOtp: any;
  mosipOtp: any;
  idEntry = new idType();
  otpObj: any;
  mobToken: any;
  districtsList: Array<any> = [];
  resdistrictList: Array<any> = [];
  pincodeData: any;
  mosipDetails: any;
  showMsgpincode = false;
  idNumber: any;
  verifyType = 'Mobile';
  verifyArray: any[] = [];
  newArray = [];
  auth = {
    timestamp: '',
    id: '',
    mobile: '',
    service: '',
    otpChannel: ''
  }
  NoneAuth = {
    timestamp: '',
    mobile: '',
    service: '',
  }
  postData: any;
  mobileNumNone: any;
  otpVerified: boolean;
  verified = false;
  otpInvalid = false;
  constructor(private _location: Location, private router: Router, private generalService: GeneralService,
    private snackBar: MatSnackBar, private datePipe: DatePipe, private titlecasePipe: TitleCasePipe,
    private spinn: NgxSpinnerService) {
    this.userName = sessionStorage.getItem('userName');
  }
  stateList = [];

  ngOnInit() {
    this.otpExpireTime = messages.otpExpireTime;
    this.otpResentOtp = messages.otpResentOtp;
    this.getAllConfigs();
    this.id = 'ID Proof';
    this.today = new Date();
    this.orgName = sessionStorage.getItem('orgName');
    this.state = 'Karnataka';
    this.loadDistrictList();
    this.idEntry.key = 'MOSIP ID';
    this.idEntry.value = 'MOSIP ID';
    this.idEntry.order = '1';
    this.idEntry.status = '1';
    this.idTypes.push(this.idEntry);
  }

  // getABARK(idNumber) {
  //   this.generalService.getABARK(idNumber.value).subscribe(response => {
  //     this.abarkData = response;
  //     if (this.abarkData == null) {
  //       this.showMsg = true;
  //       this.firstname = '';
  //       this.address1 = '';
  //       this.guardianName = '';
  //       this.dob = '';
  //       this.salutation = '';
  //       this.gender = '';
  //       this.city = '';
  //       this.district = '';
  //       this.pincode = '';
  //       this.email = '';
  //       this.state = '';
  //       setTimeout(() => {
  //         this.showMsg = false;
  //       }, 10000);
  //     } else {
  //       this.abId = this.abarkData.abid;
  //       this.firstname = this.abarkData.ename;
  //       this.address1 = this.abarkData.eaddress;
  //       // this.guardianName = this.abarkData.ecareoff;
  //       const careTaker = this.abarkData.ecareoff.split(': ');
  //       if (careTaker[0].substring(0, 3) === 'S/O' || careTaker[0].substring(0, 3) === 'D/O' || careTaker[0].substring(0, 3) === 'W/O') {
  //         this.guardianName = careTaker[1];
  //       }
  //       if (careTaker[0].substring(0, 3) === 'W/O') {
  //         this.guardianName = '';
  //       }
  //       const dt = this.abarkData.dob;
  //       const parts = dt.split('-');
  //       const mydate = new Date(parts[2], parts[1] - 1, parts[0]);
  //       this.dob = new Date(mydate);
  //       if (this.abarkData.gender === 'M') {
  //         this.salutation = 'Mr.';
  //         this.gender = 'Male';
  //       } else if (this.abarkData.gender === 'F') {
  //         this.salutation = 'Ms.';
  //         this.gender = 'Female';
  //       }
  //       this.city = this.abarkData.evillage;
  //       this.state = this.abarkData.estate;
  //       this.district = this.titlecasePipe.transform(this.abarkData.edistrict);
  //       this.pincode = this.abarkData.epincode;
  //       this.email = this.abarkData.email;
  //     }
  //   });
  // }

  // getPincode(pincodeF) {
  //   this.showMsgpincode = false;
  //   if (pincodeF.value.length === 6) {
  //     this.generalService.getPincode(pincodeF.value).subscribe(response => {
  //       this.pincodeData = response;
  //       let res = this.pincodeData[0];
  //       this.city = res.cityName;
  //       this.state = this.titlecasePipe.transform(res.stateName);
  //       this.district = this.titlecasePipe.transform(res.district);
  //     }, (error) => {
  //       this.showMsgpincode = true;
  //       setTimeout(() => {
  //         this.showMsgpincode = false;
  //       }, 5000);
  //       this.city = '';
  //       this.district = '';
  //       this.state = '';
  //     });
  //   }
  // }

  cancel() {
    sessionStorage.setItem("validRoute", "true");
    this.router.navigate(['dashboard']);
  }

  checkMosipOtp(mosipOtp) {
    if (mosipOtp.length === 6) {
      this.validMosipOTP = true;
    } else {
      this.validMosipOTP = false;
    }
  }

  getServDet() {
    sessionStorage.setItem("manualSpinn", "true");
    this.spinn.show();
    if (this.idType === 'None') {
      const constserviceDetails = {
        'mobile': this.mobileNum,
        'otp': this.mosipOtp,
        'transactionId': sessionStorage.getItem("transactionID"),
        'service': sessionStorage.getItem("service"),
        'timestamp': new Date().toString(),
      }
      this.postData = constserviceDetails;
    } else {
      const serviceDetails = {
        'idnumber': this.idNumber,
        'otp': this.mosipOtp,
        'transactionId': sessionStorage.getItem("transactionID"),
        'service': sessionStorage.getItem("service"),
        'timestamp': new Date().toString()
      }
      this.postData = serviceDetails;
    }
    console.log("final data: ", this.postData);
    this.generalService.verifyDetails(this.postData).subscribe((response: any) => {
      this.mosipDetails = response.text();
      var jsonResponse = response.json();

      var json = JSON.parse(this.mosipDetails);
      if (json.status === 200 && json.errors === undefined) {
        this.otpVerified = true;
        // this.verified = true;
        var res = JSON.parse(json.response);
        this.otpResponseMessage = res.status;
        if (res.status == 'OTP expired or does not match') {
          console.log("entered in if");
          this.otpVerified = false;
        }
        this.mobileVerify = true;
        this.otpInvalid = false;
        this.mosipDetails = res.demodetail;
        this.firstname = this.mosipDetails.givenName;
        this.lastname = this.mosipDetails.middleName;
        this.address1 = this.mosipDetails.address1;
        this.city = this.mosipDetails.city;
        this.pincode = this.mosipDetails.pinCode;
        this.state = this.mosipDetails.state;
        this.mobileNum = this.mosipDetails.phoneNumber;
        this.email = this.mosipDetails.email;
        this.district = this.mosipDetails.district.toUpperCase();
        const dt = this.mosipDetails.dateOfBirth;
        var date1 = dt.split('/')
        var newDate = date1[1] + '-' + date1[2] + '-' + date1[0];
        this.dob = new Date(newDate);
        sessionStorage.setItem("MosipAuthToken", this.mosipDetails.authToken)
        if (this.mosipDetails.genderName === 'MLE' || this.mosipDetails.genderName === 'Male') {
          for (let i = 0; i < this.genderArray.length; i++) {
            if (this.genderArray[i].value === 'Male') {
              this.gender = this.genderArray[i].key;
              for (let i = 0; i < this.salutationArray.length; i++) {
                if (this.salutationArray[i].value === 'Mr.') {
                  this.salutation = this.salutationArray[i].key;
                }
              }
            }
          }
        }
        if (this.mosipDetails.genderName === 'Female' || this.mosipDetails.genderName === 'FLE') {
          for (let i = 0; i < this.genderArray.length; i++) {
            if (this.genderArray[i].value === 'Female') {
              this.gender = this.genderArray[i].key;
              for (let i = 0; i < this.salutationArray.length; i++) {
                if (this.salutationArray[i].value === 'Ms.') {
                  this.salutation = this.salutationArray[i].key;
                }
              }
            }
          }
        }
      } else if (json.errors !== undefined && json.status == 500) {
        this.mobileVerify = false;
        this.otpInvalid = true;
        var err = JSON.parse(json.errors);
        alert(err);
        sessionStorage.setItem("manualSpinn", "true");
        this.spinn.hide();
      } else if (json.status == 400) {
        this.mobileVerify = false;
        this.otpInvalid = true;
        var err = JSON.parse(json.errors);
        alert(err);
        sessionStorage.setItem("manualSpinn", "true");
        this.spinn.hide();
      }
      sessionStorage.setItem("manualSpinn", "false");
      this.spinn.hide();
    });
    sessionStorage.setItem("manualSpinn", "false");
    this.spinn.hide();
  }

  register(patientForm, submitBtn: HTMLButtonElement) {
    console.log("patientform: ", patientForm);
    submitBtn.disabled = true;
    const jsonObject = this.getPostObject(patientForm);
    console.log("response : ", jsonObject);
    this.generalService.createPatient(jsonObject).subscribe(res => {
      var jsonResponse = res.json();
      if (jsonResponse.status === 201) {
        if (jsonResponse !== null && jsonResponse.response !== null) {
          alert(jsonResponse.message);
        }
        sessionStorage.setItem("validRoute", "true");
        this.router.navigate(['dashboard']);
      }
      else if (jsonResponse.status !== 201) {
        submitBtn.disabled = false;
        this.snackBar.open(jsonResponse.message, 'X', {
          duration: 6000,
        });
      }
    }, (error) => {
        alert('error occurred');
        submitBtn.disabled = false;
      });
  }


  getPostObject(patientDetails) {
    debugger
    console.log("patientdetails: ", patientDetails);
    let gc = '';
    if (this.gender === 'Male') {
      gc = 'M';
    } else if (this.gender === 'Female') {
      gc = 'F';
    } else {
      gc = 'TRA';
    }
    this.checkDistrictCase(this.district);

    const orgObject = {
      'userToken': '',
      'userUuid': '',
      'orgUuid': '',
      'idType': this.idType,
      'idNumber': patientDetails.value.idNumber,
      'prefix': this.salutation,
      'givenName': this.firstname,
      'middleName': this.lastname,
      'gender': {
        'genderCode': gc,
        'genderName': this.gender
      },
      'emergencyContact': {
        'contactName': this.guardianSal + this.guardianName,
        'relation': patientDetails.value.relation,
        'telephone': '',
        'mobile': patientDetails.value.guardianPhone,
        'email': patientDetails.value.guardianEmail
      },
      'phoneNumber':  this.mobileNum,
      'email': this.email,
      'dateOfBirth': this.dob,
      'status': 'Visiting',
      'address': [{
        'district': this.district,
        'city': this.city,
        'state': this.state,
        'pinCode': this.pincode,
        'postalCode': this.pincode,
        'address1': this.address1,
        'address2': this.address2,
      }],
      'mobToken': this.mobToken
    };

    if (sessionStorage.getItem("service")) {
      orgObject.idNumber = sessionStorage.getItem("MosipAuthToken");
    }
    return orgObject;
  }

  getMosipOtp(idNum) {
    sessionStorage.removeItem("transactionID");
    sessionStorage.setItem("manualSpinn", "true");
    this.spinn.show();
    this.showMsg = false;
    this.mosipOtp = '';
    this.otpResponseMessage = '';
    this.mobButtonName = 'Resend OTP';
    this.timeOut = true;
    this.showMosipOTPField = true;
    this.validMosipOTP = false;
    setTimeout(() => {
      if (this.mobileVerify === false) {
        this.timeOut = false;
      }
    }, 45000);
    if (this.idType === 'AB-ARK ID') {
      this.auth.service = 'ABARK';
      this.auth.mobile = this.mobileNum;
    } else if (this.idType === 'MOSIP ID') {
      this.auth.service = 'MOSIP';
      this.auth.id = this.idNumber;
    } else if (this.idType === 'eHospital UHID') {
      this.auth.service = 'EHOSPITAL';
      this.auth.mobile = this.mobileNum;
    } else {
      this.auth.service = 'DEFAULT';
      this.auth.mobile = this.mobileNum;
    }
    this.auth.timestamp = new Date().toString();
    sessionStorage.setItem("service", this.auth.service);

    if (this.verifyType == 'PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.auth.otpChannel = this.newArray[0];
    } else if (this.verifyType == 'EMAIL') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.newArray[0] = this.verifyArray;
      this.auth.otpChannel = this.newArray[0];
    } else if (this.verifyType == 'EMAIL,PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.auth.otpChannel = this.newArray[0];
    }
    console.log(this.auth);
    // this.validMosipOTP = true;
    this.generalService.initAuth(this.auth).subscribe((response: any) => {
      this.otpObj = response.json();
      console.log("resp: ", this.otpObj);
      if (this.otpObj.status === 200) {
        sessionStorage.setItem("transactionID", this.otpObj.response)
        this.mobilerefID = response.refID;
        this.showMosipOTPField = true;
        this.validMosipOTP = true;
        this.showMsg = false;
      } else {
        this.showMsg = true;
        alert("Please verify mosip ID or service down");
      }
    });
    sessionStorage.setItem("manualSpinn", "false");
    this.spinn.hide();
  }

  getMosipNoneOtp(mobileNone) {
    sessionStorage.setItem("manualSpinn", "true");
    this.spinn.show();
    this.showMsg = false;
    this.mobButtonName = 'Resend OTP';
    this.timeOut = true;
    this.showMosipOTPField = true;
    this.validMosipOTP = false;
    setTimeout(() => {
      if (this.mobileVerify === false) {
        this.timeOut = false;
      }
    }, 10000);
    this.NoneAuth.service = 'DEFAULT';
    this.NoneAuth.mobile = this.mobileNum;
    this.NoneAuth.timestamp = new Date().toString();
    sessionStorage.setItem("service", this.NoneAuth.service);

    // this.validMosipOTP = true;
    this.generalService.initAuth(this.NoneAuth).subscribe((response: any) => {
      this.otpObj = response.json();
      if (this.otpObj.status === 200) {
        sessionStorage.setItem("transactionID", this.otpObj.response)
        this.mobilerefID = response.refID;
        this.showMosipOTPField = true;
        this.validMosipOTP = true;
      } else {
        this.showMsg = true;
        alert("Please verify mosip ID or service down");
      }
    });
    sessionStorage.setItem("manualSpinn", "false");
    this.spinn.hide();
  }

  // verifyMobOTP() {
  //   this.otpObj.otp = this.mobileOtp;
  //   this.generalService.verifyOtpService(this.otpObj).subscribe(response => {
  //     const res = <any>response;
  //     this.otpResponseMessage = res.message;
  //     if (res.message === 'OTP Verified') {
  //       this.timeOut = true;
  //       this.mobileVerify = true;
  //       this.validOTPMobile = true;
  //     } else {
  //       this.mobileVerify = false;
  //       this.validOTPMobile = false;
  //     }
  //   });
  //   this.otpResponseMessage = '';
  // }

  checkMobile(contact) {
    if (contact.value.length === 10) {
      this.mobButtonName = 'Send OTP';
      this.validMobile = true;
      this.validOTPMobile = true;
    } else {
      this.validMobile = false;
      this.validOTPMobile = false;
    }
  }

  checkIdMobile() {
    this.mobButtonName = 'Send OTP';
    this.validMobile = true;
  }

  checkIDNumber(contact) {
    if (contact.value !== this.idNumber) {
      this.mobButtonName = 'Send OTP';
      this.validMobile = true;
      this.validOTPMobile = true;
    } else {
      this.validMobile = false;
      this.validOTPMobile = false;
    }
  }

  getAllConfigs() {
    sessionStorage.setItem("manualSpinn", "true");
    this.spinn.show();
    const param = ['ID_Types', 'Salutation', 'Gender'];
    this.generalService.getAllConfigs(param)
      .subscribe(response => {
        const result = response;
        let arr = result['ID_Types'].mhmsconfigArray;
        for (let i = 0; i < arr.length; i++) {
          if (arr[i].status === '1') {
            this.idTypes.push(arr[i]);
          }
        }
        arr = result['Salutation'].mhmsconfigArray;
        for (let i = 0; i < arr.length; i++) {
          if (arr[i].status === '1') {
            this.salutationArray.push(arr[i]);
          }
        }
        arr = result['Gender'].mhmsconfigArray;
        for (let i = 0; i < arr.length; i++) {
          if (arr[i].status === '1') {
            this.genderArray.push(arr[i]);
          }
        }
      });
    sessionStorage.setItem("manualSpinn", "false");
    this.spinn.hide();
  }

  loadDistrictList() {
    this.generalService.getDistrictList("Karnataka").subscribe((response) => {
     this.resdistrictList = <any>response;
      for (let i = 0; i < this.resdistrictList.length; i++) {
        let district = this.resdistrictList[i];
        if (district.status === "1") {
          this.districtsList.push(district.districtName.toUpperCase());
        }
      }
    });
  }

  checkDistrictCase(district)
  {
    for (let i = 0; i < this.resdistrictList.length; i++) {
    let dis = this.resdistrictList[i];
    if (dis.districtName.toUpperCase()===district) {
      this.district =dis.districtName;
    }
  }
  }
}
