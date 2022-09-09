export class Restraint {
    
    nrName: any;
    mhe: any;
    icpsyc: any;
    setting: any;
    informedNR: any;
    psycDiagnosis: any;
    rType: any;
    duration: any;
    rStartDate: any;
    rEndDate: any;
    rStartTime: any;
    rEndTime: any;
    reason: any;
    injuries: any;
    limb: any;
    otherCompli: any;
    dt: any;
    pulse: any;
    temperature: any;
    rrate: any;
    injury: any;
    bloodS: any;
    breath: any;
    durationType: any;
    createdDate: any;
    periodicArray: Array<any> = [];
    LimbIsch: any;
    otherComp: any;
    patientID:any;
    docId:any;
    orgId:any;
    orgName:any;
    consentID:any;
    periodarray: period[];
    medicationArr :Array<any> = [];
}

export class period {
    dt: null;
    pulse: any;
    temperature: any;
    rrate: any;
    injury: any;
    bloodS: any;
    breath: any;
  }


  export class medication {
    medicine: any;
    dose: any;
    route: any;
    freq: any;
    totalDose: any;
    sideEffect: any;
  }


