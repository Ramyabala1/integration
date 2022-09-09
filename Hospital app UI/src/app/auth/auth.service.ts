import { Injectable } from '@angular/core';
import * as GlobalVariables from '../global';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Http } from '@angular/http';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private snackBar: MatSnackBar, private httpC: HttpClient, private http: Http) { }

  // login(jsonObject) {
  //   return this.http.post(GlobalVariables.loginUrl, jsonObject);
  // }

  isLoggedIn() {
    const helper = new JwtHelperService();
      const token = sessionStorage.getItem('jwt');
      const isExpired = helper.isTokenExpired(token);
    if (!token) {
      return false;
    } else {
      if (isExpired) {
        this.snackBar.open('Session Expired Please Login to Continue', 'x', {
          duration: 6000,
      });
        return false;
      }
      return true;
    }

  }
  isPassUpdated() {
    if (sessionStorage.getItem('saveClicked') === 'true') {
      this.logout();
      return false;
    } else {
      return true;
    }
  }
  isValidRoute() {
    if (sessionStorage.getItem('validRoute') === 'true') {
      sessionStorage.setItem('validRoute', 'false');
      return true;
    } else {
      return false;
    }
  }
  isValidChildRoute() {
    if (sessionStorage.getItem('validChildRoute') === 'true') {
      sessionStorage.setItem('validChildRoute', 'false');
      return true;
    } else {
      return false;
    }
  }
  refreshToken(): Observable<any> {
    const obj = {
      'resetToken': sessionStorage.getItem('jwt')
    };
    return this.httpC.post(GlobalVariables.refreshToken, obj);
  }
  logout() {
    const userUuid = JSON.parse(sessionStorage.getItem('registryAdmin')).uuid;
    const userToken = JSON.parse(sessionStorage.getItem('registryAdmin')).userToken;
        const jsonObject = {
        'user_token' : userToken,
        'uuid' : userUuid
    };
    return this.httpC.post(GlobalVariables.logoutUrl, jsonObject);
  }

}
