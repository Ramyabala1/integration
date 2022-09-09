import { Injectable } from '@angular/core';
import {Http, Headers, ResponseContentType} from '@angular/http';

import { HttpClient,HttpHeaders } from '@angular/common/http';
import * as GlobalVariables from '../app/global';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})

export class GeneralService {

  options;
  queryUrl = '?search=';
  headers = new Headers ({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': environment.cors});

  constructor(private http: Http, private httpc:HttpClient) { }
  
  createAuthorizationHeader(headers: Headers) {
    headers.append('Content-Type', 'application/json');
    headers.append('Access-Control-Allow-Origin', environment.cors);
     headers.append('Access-Control-Allow-Methods','GET, POST, OPTIONS, PUT,  DELETE');
     headers.append('Access-Control-Allow-Headers','Access-Control-Allow-Origin,Content-Type, Authorization,Access-Control-Allow-Headers,Access-Control-Allow-Methods');
       }


  

  postAuthenticate(object)
  {
    return this.http.post(environment.baseUrl + 'fhir/hiu/authenticate', object);
    
  }

  postOPBMRData(object)
  {
     
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/addOPBMRTreatment', object,{ headers: headers });
    
  }


  getAd(consent)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/getAD',consent, { headers: headers });
    
  }

  ViewAd(consent)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/getADDetails',consent, { headers: headers });
    
  }

  createComposition(opmodel)
  {
   
    
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/CreateOPBMRTreatment',  opmodel,{ headers: headers });
  }

  createRestraintComposition(rModel)
  {
   
    
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/createRestraintTreatment',  rModel,{ headers: headers });
  }

  createTheraphyComposition(tModel)
  {
   
    
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/createTheraphyTreatment',  tModel,{ headers: headers });
  }
  
  createAssessment(tModel)
  {
   
    
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/CreateAssessment',  tModel,{ headers: headers });
  }

  createCompositionIPBMR(ipmodel)
  {
   
    
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/CreateIPBMRTreatment',  ipmodel,{ headers: headers });
  }

  createCompositionAddIPBMR(ipmodel)
  {
   
    
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/createAddIPBMRTreatment',  ipmodel,{ headers: headers });
  }
 
  postIPBMRData(object)
  {
     
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/addIPBMRTreatment', object,{ headers: headers });
    
  }

  getBMRForPatient(object)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + 'ehr/viewTreatments', object,{ headers: headers });
    
  }

  getBMRSummaryForPatient(object)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/ViewTreatmentSummary', object,{ headers: headers });
    
  }

  CreateServer(server)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/CreateServer', server,{ headers: headers });
  }

  // consentRequest(server)
  // {
  //   const headers = new Headers();
  //   this.createAuthorizationHeader(headers);
  //   headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
  //   headers.append('X-Forwarded-Host', environment.systemIP);
  //   return this.http.post(environment.baseUrl + '/consentRequest', server,{ headers: headers });
  // }

  initConsent(server)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + 'consent/initConsent', server,{ headers: headers });
  }

  verifyConsent(server)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/verifyAndCreateConsent', server,{ headers: headers });
  }

  createConsent(server)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + 'consent/createConsent', server,{ headers: headers });
  }

  

  getPatient(puuid,hiu,hip)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.get(environment.baseUrl + '/getPatient/'+puuid+'/'+hiu+'/'+hip, { headers: headers  });
  }

  getUploadedFile(fileUuid,type,filetype)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.get(environment.baseUrl + '/downloadFile/'+fileUuid+'/'+type+'/'+filetype
    , { headers: headers});
 
  }

  getUploadedPDFFile(fileUuid,type,filetype): Observable<Response>
  {
    var url = environment.baseUrl + '/downloadPdfFile/'+fileUuid+'/'+type+'/'+filetype;
    // const headers = new Headers();
    // this.createAuthorizationHeader(headers);
    // headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    // headers.append('X-Forwarded-Host', environment.systemIP);
   // return this.http.get(url, { headers: headers, responseType: ResponseContentType.Blob});
   
    const headers = new HttpHeaders({ 'Content-Type': 'application/json',
         "Authorization": 'Bearer ' + sessionStorage.getItem("jwt"), "X-Forwarded-Host": environment.systemIP,responseType : 'blob'});
    return this.httpc.get<Response>(url, { headers: headers, responseType:'blob' as 'json'});
 
  }

  CreateFhirRequest(opmodel)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
  
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/createOPFhirRequest', opmodel,{ headers: headers });
    
  }

  CreateIPFhirRequest(opmodel)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/createAddIPFhirRequest', opmodel,{ headers: headers });
    
  }

  createIPDischarge(opmodel)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/createIPDischarge', opmodel,{ headers: headers });
    
  }
  
  // downloadFile(file){
  //   const headers = new Headers();
  //   headers.append('Accept', 'application/pdf');

  //   return this.http.get(file, { headers: headers, responseType: 'blob' as 'json'  });
//}

  createIPDishargeFhirRequest(opmodel)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + '/createIPDishargeFhirRequest', opmodel,{ headers: headers });
    
  }

  public getClientIPAddress()  
  {  
    return this.httpc.get("http://api.ipify.org/?format=json");  
  } 
  
  // otpResendService(jsonObject) {
  //   const url = GlobalVariables.otpResendUrl;
  //   jsonObject.otp = '';
  //   return this.httpc.post(url, jsonObject);
  // }

  verifyOtpService(obj) {
    const url = GlobalVariables.verifyOTPUrl;
    return this.httpc.post(url, obj);
  }

  getAllConfigs(param) {
    return this.httpc.post(GlobalVariables.getConfig, param);
  }


  // mobOtpService(contact, type) {
  //   const url = GlobalVariables.otpUrl;
  //   const jsonObject = {
  //     'contactType': 'mobile',
  //     'contactVal': contact,
  //     'purpose': type
  //   };
  //   return this.httpc.post(url, jsonObject);
  // }

  getMosipDetails(obj)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
   
    return this.http.post(environment.mosipUrl + '/sendOTPekycRequest' ,obj, { headers: headers });
    
  }

  verifyDetails(obj)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
   
    return this.http.post(environment.baseUrl + 'patient/verifyAndGetDemogDetails' ,obj, { headers: headers });
    
  }

  getMosipAuth(obj)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    
    return this.http.post(environment.mosipUrl + '/sendOTPAuthRequest' ,obj, { headers: headers });
    
  }


  mosipSendOTP(mobile)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    
    return this.http.post(environment.mosipUrl + '/sendOTP?mosipID='+mobile , { headers: headers });
    
  }

  initAuth(auth)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + 'patient/initAuth', auth,{ headers: headers });
    
  }

  createPatient(jsonObject)
  {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
    headers.append('X-Forwarded-Host', environment.systemIP);
    return this.http.post(environment.baseUrl + 'patient/createPatient', jsonObject,{ headers: headers });
  
  }

  getDistrictList(statename) {
    return this.httpc.get(environment.emanasUrl + '/config/getDistrictList/stateName/' + statename);
  }
  


  // downloadFile(index): Observable<Blob>{

  //   const headers = new Headers();
  //   this.createAuthorizationHeader(headers);
  //   headers.append('Accept', 'application/pdf');   
  //   return this.http.get(index, { headers: headers, responseType: 'blob' as 'json' });
  // }
 

 
  

 }
