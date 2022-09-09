export class OPBMR {
    clinicalHistory: any;
    illnessSummary: any;
    treatmentInstructions: any;
    improvementStatus: any;
    followUpDate: any;
    diagnosisDisplay: Diagnosis[];
    complaintDisplay: Complaint[];
    medicationDisplay: Med[];
    referredOrganization: any;
    referredDoctor: any;
    reasonOfReferral: any;
    comorbidDiagnosis: any;
    serviceName: any;
    reasonDescription: any;
    createdDate: any;
    visitType: any;
    addNote: any;
    category: any;
    TreatmentDetails: any;
    patientID:any;
    docId:any;
    orgId:any;
    orgName:any;
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