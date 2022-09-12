import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { PersonServiceService } from 'src/app/person-service.service';

@Component({
  selector: 'app-update-consent',
  templateUrl: './update-consent.page.html',
  styleUrls: ['./update-consent.page.scss'],
})
export class UpdateConsentPage implements OnInit {

  authService: any;
  value: any;
  emanasId: any;
  authType: any;
  uin: any;
  transactionId: any;
  otpChannel: any;

  consentrequestnew = {
    api: 'mosip-emanas.consent.init',
    version: '1.0',
    source: 'PatientApp',
    licenseKey: '123e4567-e89b-12d3-a4',

    timestamp: '',
    person: {
      personType: 'PATIENT',
      personId: '',
      authService: '',
      serviceParameters: {
        individualId: '',
        individualIdType: '',
        otpChannel: [],
        authType: '["Demo","OTP","Bio"]'
      }
    },
    consentParameters: {
      patientId: '',
      consentId: '',
      permission: '',
      purpose: [],
      hresource: {
        type: '',
        episodeId: '',
        compositionId: ''
      },
      hip: {
        estId: '',
        estName: ''
      },
      hiu: {
        estId: '',
        estName: '',
        individualId: '',
        individualName: '',
        individualRole: '',
        hiuType: '',
      },
      validity: {
        validityType: '',
        createDate: '',
        validityEndDate: ''
      }
    }
  }

  compositionId: any;
  hiu: string;
  value1: string;
  value2: string;
  value3: string;
  permission: string;
  validity: string;
  value4: string;
  value5: string;
  name: string;
  role: string;
  individualid: string;
  virtual: string;
  value8: string;
  otpArray: any[] = [];
  authArray: any[] = [];
  newArray = [];
  otpType = 'Mobile';
  createdate: string;
  validityenddate: string;
  public isChecked = false;

  indeterminateState: boolean;
  checkParent: boolean;
  Checkboxes: any;
  hiTypes = ['InPatient Record', 'OutPatient Record', 'Restraint Monitoring', 'Assessment', 'Therapy', 'Advance directive'];
  hitype: any = [];

  constructor(private router: Router, private service: PersonServiceService,
    private modalController: ModalController,
  ) {
    this.Checkboxes = [
      {
        value: "InPatient Record",
        isItemChecked: false
      }, {
        value: "OutPatient Record",
        isItemChecked: false
      }, {
        value: "Restraint Monitoring",
        isItemChecked: false
      }, {
        value: "Assessment",
        isItemChecked: false
      }, {
        value: "Therapy",
        isItemChecked: false
      }, {
        value: "Advance directive",
        isItemChecked: false
      }
    ];
  }

  getVfdata = {
    patientId: '',
  };

  getCompdata = {
    patientId: '',
    virtualfolderId: ''
  };

  estName: any;
  res1: [];
  compIdArray: [];
  compid: any;
  resnewArray: [];
  purposeArray: any[] = [];
  from: any;
  to: any;
  validityfrom: any;
  validityto: any;
  patientid: any;
  hip: any;
  individualname: any;
  establishment: any;
  verifyType = 'eManas';
  idType = 'Individual';
  validityType = 'Duration';
  idNumber: '';
  enableMessage: boolean = false;
  displayString: any;

  consentVerify = {
    api: 'mosip-emanas.consent.update',
    version: '1.0',
    source: 'PatientApp',
    otp: '',
    transactionId: '',
    timestamp: '',
    authType: ['DEMO', 'OTP', 'BIO'],
  }

  otpVerified: any;
  otpNotMatch: any;
  public otpbtns = true;

  facilitiesArray: any[] = [];
  public saveUsername: boolean;
  timeOutMHMSID: boolean;
  otp: any;
  selectedItemsList = [];
  checkedIDs = [];
  additionalCoverArray = [
    { key: "1", displayValue: "InPatient Record", responseCode: "IPBMR", selected: false },
    { key: "2", displayValue: "OutPatient Record", responseCode: "OPBMR", selected: false },
    { key: "3", displayValue: "Restraint Monitoring", responseCode: "RESTRAINT", selected: false },
    { key: "4", displayValue: "Assessment", responseCode: "ASMENT", selected: false },
    { key: "5", displayValue: "Therapy", responseCode: "THERAPY", selected: false },
    { key: "5", displayValue: "Advance directive", responseCode: "ADVANCEDIRECTIVE", selected: false }]

  otpBox: boolean;
  verifyArray: any[] = [];

  ngOnInit() {
    this.getVFId();
    this.emanasId = sessionStorage.getItem('patientId');
    this.displayString = JSON.parse(sessionStorage.getItem('artifact'));
    console.log("this.displayString: ", this.displayString);
    this.validityType = this.displayString.consentParameters.validity.validityType;
    this.permission = this.displayString.consentParameters.permission;
    this.estName = this.displayString.consentParameters.hiu.estName;
    this.establishment = this.displayString.consentParameters.hiu.estId;
    this.hiu = this.displayString.consentParameters.hiu.hiuType;
    this.individualid = this.displayString.consentParameters.hiu.individualId;
    this.individualname = this.displayString.consentParameters.hiu.individualName;
    this.role = this.displayString.consentParameters.hiu.individualRole;
    this.validityfrom = this.displayString.consentParameters.validity.createdate;
    this.validityto = this.displayString.consentParameters.validity.validityenddate;
    this.virtual = this.displayString.consentParameters.hresource.episodeId;
    console.log("purpose: ", this.displayString.consentParameters.purpose);
    this.getarraydata(this.displayString.consentParameters.purpose);
    this.getcheckBoxData(this.displayString.consentParameters.purpose);
    this.facilitiesArray = this.displayString.consentParameters.purpose;
  }

  getcheckBoxData(value: any) {
    console.log("value: ", value);
    console.log("value: ", value[0]);
    for (let i = 0; i < value.length; i++) {
      this.hitype.push(value[i]);
      console.log("resOutt: ", this.hitype);
    }
  }

  getarraydata(value: any) {
    let fieldsEdited = [];
    value.forEach(idproof => {
      this.additionalCoverArray.forEach((element) => {
        if (element.responseCode == idproof) {
          element.selected = true;
          fieldsEdited.push(element);
          console.log("fieldsEdited ", fieldsEdited);
        }
      });
    });
  }

  getVFId() {
    this.getVfdata.patientId = sessionStorage.getItem('patientId');
    this.service.getPatientHospitalMap(this.getVfdata).subscribe(response => {
      console.log('response: ', response);
      if (response.code === 400) {
        console.log('entered in 400');;
      }
      else if (response.code === 200) {
        var res1 = response;
        if (res1.code === 200) {
          this.res1 = JSON.parse(response.response);
          console.log("resOut: ", this.res1);
        }
      }
    });
  }

  getCompositionId() {
    this.getCompdata.patientId = sessionStorage.getItem('patientId');
    this.getCompdata.virtualfolderId = sessionStorage.getItem('episodeId');
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

  checkCheckbox() {
    setTimeout(() => {
      this.Checkboxes.forEach(item => {
        item.isItemChecked = this.checkParent;
      });
    });
  }

  verifyEvent(name: string, isItemChecked: any) {
    console.log("ischecjed, ", isItemChecked.detail.checked);
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
    } else if (name === 'Advance directive') {
      name = 'ADVANCEDIRECTIVE';
    }
    console.log("name: ", name);

    if (isItemChecked.detail.checked === true) {
      console.log("inside checked");
      this.hitype.push(name);
      console.log("in: ", this.hitype);
    } else {
      console.log("else checked ", name);
      console.log("index value: ", this.hitype.findIndex(x => x.value === name));
      const index = this.hitype.findIndex(x => x.value === name);
      this.hitype.splice(index);
      console.log("out: ", this.hitype);
    }
  }


  save() {
    this.displayString = JSON.parse(sessionStorage.getItem('artifact'));
    console.log("displaystring: ", this.displayString);
    this.consentrequestnew.timestamp = new Date().toString();
    this.consentrequestnew.person.personId = this.emanasId;
    this.consentrequestnew.consentParameters.patientId = this.emanasId;
    this.consentrequestnew.person.authService = this.authService;
    this.consentrequestnew.person.serviceParameters.individualIdType = 'VID|UIN';
    this.authArray = [];
    this.authArray.push('Demo');
    this.authArray.push('OTP');
    this.authArray.push('Bio');
    this.newArray[0] = this.authArray;
    this.consentrequestnew.person.serviceParameters.authType = this.newArray[0];
    this.consentrequestnew.person.serviceParameters.individualId = this.uin;
    this.consentrequestnew.consentParameters.permission = this.permission;
    this.consentrequestnew.consentParameters.consentId = sessionStorage.getItem('consentID');
    this.consentrequestnew.consentParameters.purpose = this.hitype;

    this.consentrequestnew.consentParameters.hip.estName = this.estName;
    this.consentrequestnew.consentParameters.hip.estId = this.establishment;
    this.consentrequestnew.consentParameters.hiu.individualId = this.individualid;
    this.consentrequestnew.consentParameters.hiu.hiuType = this.hiu;
    this.consentrequestnew.consentParameters.hiu.estName = this.estName;
    this.consentrequestnew.consentParameters.hiu.individualName = this.individualname;
    this.consentrequestnew.consentParameters.hiu.individualRole = this.role;
    this.consentrequestnew.consentParameters.hiu.estId = this.establishment;
    this.consentrequestnew.consentParameters.validity.validityType = this.validityType;
    this.consentrequestnew.consentParameters.validity.createDate = this.validityfrom;
    this.consentrequestnew.consentParameters.validity.validityEndDate = this.validityto;

    this.consentrequestnew.consentParameters.hresource.episodeId = sessionStorage.getItem('episodeId');
    this.consentrequestnew.consentParameters.hresource.compositionId = this.compid;

    if (this.otpChannel == 'PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.consentrequestnew.person.serviceParameters.otpChannel = this.newArray[0];
    } else if (this.otpChannel == 'EMAIL') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.newArray[0] = this.verifyArray;
      this.consentrequestnew.person.serviceParameters.otpChannel = this.newArray[0];
    } else if (this.otpChannel == 'EMAIL,PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.consentrequestnew.person.serviceParameters.otpChannel = this.newArray[0];
    }
    console.log(this.consentrequestnew);
    this.service.initConsent(this.consentrequestnew).subscribe(response => {
      if (response.code === 400) {
        this.otpBox = false;
        console.log('entered in 400');
      }
      else if (response.code === 200) {
        this.otpBox = true;
        var transaction = response;
        this.transactionId = transaction.transactionId;
        console.log('entered in 200');
      }
      sessionStorage.setItem('consent_transaction', this.transactionId);
    }, (error) => {
      alert('error occurred');
    });
  }

  verifyOtp() {
    this.consentVerify.transactionId = this.transactionId;
    this.consentVerify.otp = this.otp;
    this.consentVerify.timestamp = new Date().toString();
    this.service.updateConsentRequest(this.consentVerify).subscribe(response => {
      if (response.code === 400) {
        console.log('entered in 400');
      }
      else if (response.code === 200) {
        console.log('response received');
        if (window.confirm('Consent created successfully with ID --> ' + response.response)) {
          this.modalController.dismiss();
          this.router.navigate(['consent']);
        }
      }
    });
  }


  back() {
    this.router.navigate(['consent']);
  }

  onChange() {
    if (this.authService === 'mosip') {
      this.value = 'mosip';
    }
    else if (this.authService === 'emanas') {
      this.value = '';
    }
  }

  onChange1() {
    if (this.hiu === 'Individual') {
      this.value1 = 'mosip';
    }
    else if (this.hiu === 'Role') {
      this.value1 = '';
    }
    else if (this.hiu === 'Establishment') {
      this.value1 = '';
    }
    if (this.hiu === 'Role') {
      this.value2 = 'Role';
    }
    else if (this.hiu === 'Individual') {
      this.value2 = '';
    }
    else if (this.hiu === 'Establishment') {
      this.value2 = '';
    }
    if (this.hiu === 'Establishment') {
      this.value3 = 'Establishment';
    }
    else if (this.hiu === 'Individual') {
      this.value3 = '';
    }
    else if (this.hiu === 'Role') {
      this.value3 = '';
    }
  }

  onChange2() {
    if (this.validity === 'Duration') {
      this.value4 = 'Duration';
    }
    else if (this.validity === 'Event') {
      this.value4 = '';
    }
    if (this.validity === 'Event') {
      this.value5 = 'Event';
    }
    else if (this.validity === 'Duration') {
      this.value5 = '';
    }
  }

  get1() {
    var x = sessionStorage.getItem('item1');
    document.getElementById('comp1').innerHTML = x;
    var x = sessionStorage.getItem('item2');
    document.getElementById('comp2').innerHTML = x;
    var x = sessionStorage.getItem('item3');
    document.getElementById('comp3').innerHTML = x;
    var x = sessionStorage.getItem('item4');
    document.getElementById('comp4').innerHTML = x;
    var x = sessionStorage.getItem('item5');
    document.getElementById('comp5').innerHTML = x;
    var x = sessionStorage.getItem('item6');
    document.getElementById('comp6').innerHTML = x;
    var x = sessionStorage.getItem('item7');
    document.getElementById('comp7').innerHTML = x;
    var x = sessionStorage.getItem('item8');
    document.getElementById('comp8').innerHTML = x;
  }

  get2() {
    var x = sessionStorage.getItem('adRes3');
    document.getElementById('uuid').innerHTML = x;
  }

}
