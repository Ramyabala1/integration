import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { environment } from 'src/environments/environment';
import { Http } from '@angular/http';
import { GeneralService } from 'src/app/general.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashBoardComponent implements OnInit {
  baseurl = environment.cors;
  reqName: string;

  openNav() {
    document.getElementById('mySidenav').style.width = '190px';
  }

  closeNav() {
    document.getElementById('mySidenav').style.width = '0';
  }

  constructor(private http: Http, private router: Router, private spinn: NgxSpinnerService
    , private as: AuthService) {
    // sessionStorage.setItem('validRoute', 'true');
  }

  ngOnInit() {
    sessionStorage.setItem("manualSpinn", "false");
    this.spinn.hide();
    // sessionStorage.setItem("validRoute", "true");
    sessionStorage.removeItem('opForm');
    this.reqName = sessionStorage.getItem('username');
  }

  addPatientButton() {
    sessionStorage.setItem("validRoute", "true");
    this.router.navigate(['/addPatient']);
  }

  addConsentReq() {
    sessionStorage.setItem("validRoute", "true");
    this.router.navigate(['/consentrequest']);
  }

  getBmrData() {
    sessionStorage.setItem("validRoute", "true");
    this.router.navigate(['/get-BMR']);
  }

  setFormName(data) {
    sessionStorage.setItem("validRoute", "true");
    this.router.navigate(['/consentrequest']);
  }

  logOut() {
    localStorage.clear();
    sessionStorage.clear();
    sessionStorage.setItem('validRoute', 'true');
    this.router.navigate(['/']);
  }
}
