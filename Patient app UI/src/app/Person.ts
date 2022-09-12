

export class Person {
  login:    string;
  password: string;
  patient:  Patient;
}

export class Patient {
  emanasId:          string;
  authService:       string;
  serviceParameters: ServiceParameters;
}

export class ServiceParameters {
  individualId:              string;
  individualIdType: string;
  otpChannel:       string[];
  authType:         string[];
}

export class Otp1 {
  transactionId: string;
  otp:           Otp;
  service:       string;
  timestamp:     Date;
}

export class Otp {
  value: string;
}

export class Login{
  login: string;
  password: string;
}

export interface Root {
  orgUUID: string;
  patientId: string;
}

export interface Root {
  transactionId: string;
  patient: Patient;
  consentParameters: ConsentParameters;
}

export interface Patient {
  patientId: string;
  authService: string;
  serviceParameters: ServiceParameters;
}

export interface ServiceParameters {
  uin: string;
  individualIdType: string;
  otpChannel: string[];
  authType: string[];
}

export interface ConsentParameters {
  permission: string;
  purpose: string[];
  hresource: string;
  hip: string;
  hiu: Hiu;
  validity: Validity;
}

export interface Hiu {
  type: string;
  role: string;
  establishment: string;
}

export interface Validity {
  type: string;
  createdate: string;
}


export interface Root {
  transactionId: string;
  otp: string;
}




