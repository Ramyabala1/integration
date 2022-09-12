/* eslint-disable no-var */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { PersonServiceService } from 'src/app/person-service.service';

@Component({
  selector: 'app-consent1',
  templateUrl: './consent1.page.html',
  styleUrls: ['./consent1.page.scss'],
})
export class Consent1Page implements OnInit {

  authService: any;
  value: any;
  emanasId: any;
  authType: any;
  uin: any;
  transactionId: any;
  otpChannel: any;

  consent = {
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
      aeId: '',
      permission: '',
      purpose: [],
      hresource: {
        resourceType: '',
        episodeId: '',
        compositions: [{
          compositionId: "65ca5381-2cf9-4e11-883f-6d266b64bad6::local.ethercis.com::1",
          templateId: ''
        }],
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

  estName: any;
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
  establishment: string;
  personType: string;

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

  res1: any[];
  compIdArray: [];
  compid: any;

  resnewArray: [];
  verifyArray: any[] = [];

  ngOnInit() {
    this.getVFId();
    this.emanasId = sessionStorage.getItem('patientId');
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
          sessionStorage.setItem('episodeId', this.res1[0].episodeId);
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
    console.log("ischecked, ", isItemChecked.detail.checked);
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


  create() {
    this.consent.person.serviceParameters.individualId = this.uin;
    this.consent.timestamp = new Date().toString();

    this.personType = sessionStorage.getItem('personType');
    if (this.personType === 'authorized_entity') {
      this.consent.person.personType = 'AUTHORIZEDENTITY';
      this.consent.person.personId = sessionStorage.getItem('aeId');
      this.consent.consentParameters.aeId = sessionStorage.getItem('aeId');
    } else {
      this.consent.person.personType = 'PATIENT';
      this.consent.person.personId = sessionStorage.getItem('patientId');
    }
    this.consent.consentParameters.patientId = this.emanasId;
    this.consent.person.authService = this.authService;
    this.consent.person.serviceParameters.individualIdType = 'VID|UIN';
    this.authArray = [];
    this.authArray.push('Demo');
    this.authArray.push('OTP');
    this.authArray.push('Bio');
    this.newArray[0] = this.authArray;
    this.consent.person.serviceParameters.authType = this.newArray[0];
    this.consent.consentParameters.permission = this.permission;
    this.consent.consentParameters.purpose = this.hitype;

    this.consent.consentParameters.hip.estName = this.estName;
    this.consent.consentParameters.hip.estId = this.establishment;
    this.consent.consentParameters.hiu.individualId = this.individualid;
    this.consent.consentParameters.hiu.hiuType = this.hiu;
    this.consent.consentParameters.hiu.estName = this.estName;
    this.consent.consentParameters.hiu.individualName = this.name;
    this.consent.consentParameters.hiu.individualRole = this.role;
    this.consent.consentParameters.hiu.estId = this.establishment;
    this.consent.consentParameters.validity.validityType = this.validity;
    this.consent.consentParameters.validity.createDate = "2022-06-22T02:50:12.208Z";
    this.consent.consentParameters.validity.validityEndDate = "2023-08-22T02:50:12.208Z";

    if (this.otpChannel == 'PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.consent.person.serviceParameters.otpChannel = this.newArray[0];
    } else if (this.otpChannel == 'EMAIL') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.newArray[0] = this.verifyArray;
      this.consent.person.serviceParameters.otpChannel = this.newArray[0];
    } else if (this.otpChannel == 'EMAIL,PHONE') {
      this.verifyArray = [];
      this.verifyArray.push('EMAIL');
      this.verifyArray.push('PHONE');
      this.newArray[0] = this.verifyArray;
      this.consent.person.serviceParameters.otpChannel = this.newArray[0];
    }

    this.consent.consentParameters.hresource.resourceType = "name";
    this.consent.consentParameters.hresource.episodeId = sessionStorage.getItem('episodeId');
    this.consent.consentParameters.hresource.compositions[0].compositionId = '225f187d-c1ff-4531-b972-c354670ac26d::local.ethercis.com::1';
    this.consent.consentParameters.hresource.compositions[0].templateId = 'MHMS - Therapy reporting.v0';
    console.log("json: ", this.consent);

    this.service.initConsent(this.consent).subscribe(response => {
      if (response.code === 400) {
        console.log('entered in 400');
      }
      else if (response.code === 200) {
        var transaction = response;
        this.transactionId = transaction.transactionId;
        console.log('entered in 200');

      }
      this.router.navigate(['consent-otp']);
      sessionStorage.setItem('consent_transaction', this.transactionId);
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
