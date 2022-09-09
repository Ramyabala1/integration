export class IPBMR {
   
    
    diagnosisDisplay: Diagnosis[];
    complaintDisplay: Complaint[];
    medicationDisplay: Med[];
    createdDate: any;
   
    improvementStatus:any;
    patientID:any;
    docId:any;
    orgId:any;
    orgName:any;
    capacity:any;
    past_prescription:any;
    course:any;
    vitals:any;
    physical_exam:any;
    mental_status:any;
    investigation:any;
    history:any;
    consentID:any;
    gname: any;
  gphone: any;
  
  gdmitDate: any;
  
}

export class Complaint {
    symptomName: string;
    pattern: string;
  }

  export class Diagnosis {
    diagnosticCertainity: string;
    problemCode: string;
    problemDiagnosis: string;
  }

  export class Med {
        'medName': string;
        'medDose': string;
        'timingDescription': string;
        'directionDuration': string;
        'overallDirectionDescription': string;
        'createdTime': string;
  }