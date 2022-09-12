import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';

@Injectable({
  providedIn: 'root'
})
export class PersonServiceService {

  constructor(private http: HttpClient) { }


  public doRegistration(patientRequest: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/patient/initRegistration',patientRequest);
  }

  public verifyOTP(verify: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/patient/registerPatient', verify);
  }

  public doLogin(patientLogin: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/patient/login',patientLogin);
  }
  public checkId(idCheck: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/patient/find',idCheck);
  }

  public checklogin(loginCheck: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/patient/findlogin',loginCheck);
  }

  public findorg(orgName: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/search/'+orgName,orgName);
  }

  public getEHR(ehrContext: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/getEHRContextByHIP',ehrContext);
  }

  public initEhr(addEHR: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/initAddEHRContextByHIP',addEHR);
  }

  public add(initadd: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/addEHRContextByHIP',initadd);
  }

  public getPatientHospitalMap(viewHealth: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/getPatientHospitalMap',viewHealth);
  }

  public getConsentForPatient(viewHealth: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/consent/getConsentForPatient/active',viewHealth);
  }

  public getCompositionList(viewHealth: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/getCompositionList',viewHealth);
  }

  public view(viewHealth: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/viewHealthRecord',viewHealth);
  }

  public initConsent(consent: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/initConsent',consent);
  }

  public manageConsentRequest(consent: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/consent/manageConsentRequest/revoke',consent);
  }

  public updateConsentRequest(consent: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/consent/manageConsentRequest/update',consent);
  }

  public getAccessHistory(patientid: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/log/getAccessHistory', patientid);
  }
  

  public createConsent(consent: any): Observable<any>{
    return this.http.post('http://35.154.55.190:8080/MHMS_PATIENTAPP/createConsent',consent);
  }

}

