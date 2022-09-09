export class IPDISCHARGE {
   
    
    
    medicationDisplay: Med[];
    createdDate: any;
   
    
    patientID:any;
    docId:any;
    orgId:any;
    orgName:any;
    disDate: any;
    disTime1: any;
    dischargeType: any;
    dischargeCondition: any;
    conditionDescription:any;
    treatmentAdvice:any;
    followUp:any;
    docName:any;
    designation:any;
    consentID:any;
    
  
}



  export class Med {
        'medName': string;
        'medDose': string;
        'timingDescription': string;
        'directionDuration': string;
        'overallDirectionDescription': string;
        'createdTime': string;
  }