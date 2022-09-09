import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { environment } from 'src/environments/environment';
import { Http } from '@angular/http';
import { GeneralService } from 'src/app/general.service';
import { HttpClient } from '@angular/common/http';
import { NgxSpinnerService } from 'ngx-spinner';
// import { rsapublicKey } from 'src/app/global';
// import { JSEncrypt } from 'jsencrypt';

export class UserDetails {
  hiu_username: string;
  hiu_password: string;
  hiu_ipaddress: string;
}

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})

export class HomepageComponent implements OnInit {
  baseurl = environment.cors;
  color = 'blue';
  mode = 'indeterminate';
  value = 100;
  loading = false;
  showMsg = false;
  orgID: any;
  ipAddress: any;
  username: any;
  hide = true;
  password: any;
  hideLogin = true;
  error_disp: any;
  public positionSelectOPS = [];
  public positionSelectOP: any;
  userOrgRole = [];
  showSelect = false;
  projectUrl: any;
  email: any;
  step1 = true;
  saltRes: any;
  captchaImage: any;
  captchaID: any;
  captcha: any;
  hash1: string;
  ophash1: string;
  version: any;
  lastLoggedOrg: any;
  lastLogedTime: any;
  lastLaggedOrgName: any;
  selected: any;
  opData1: any;
  opData: {
    'name': '',
    'uuid': '',
    'mheAppData': any
  };
  eusername: any;
  epassword: any;
  error: any;
  invalid = false;

  openNav() {
    document.getElementById('mySidenav').style.width = '190px';
  }

  closeNav() {
    document.getElementById('mySidenav').style.width = '0';
  }
  ngOnInit() {
    this.version = environment.version;
    sessionStorage.removeItem('opForm');
    //this.getIPAddress();

  }
  constructor(private http: HttpClient, private router: Router, private gs: GeneralService,
    private spinn: NgxSpinnerService,) {
    sessionStorage.setItem('validRoute', 'true');
  }
  getIPAddress() {
    this.gs.getClientIPAddress().subscribe((res: any) => {
      this.ipAddress = res.ip;
    });
  }

  login() {
    sessionStorage.setItem('manualSpinn', 'true');
    this.spinn.show();
    let object = new UserDetails();
    object.hiu_username = this.username;
    object.hiu_password = this.password;
    object.hiu_ipaddress = this.ipAddress;
    this.gs.postAuthenticate(object).subscribe(response => {
      var jsonResponse = response.json();
      if (jsonResponse.status === 200) {
        if (jsonResponse !== null && jsonResponse.response !== null) {
          sessionStorage.setItem("jwt", jsonResponse.response);
          sessionStorage.setItem("username", this.username);
        }
        sessionStorage.setItem('validRoute', 'true');
        this.router.navigate(['dashboard']);
      }
      else if (jsonResponse.status === 404) {
        if (jsonResponse !== null && jsonResponse.errors !== null) {
          this.error = jsonResponse.errors;
        }
        this.invalid = true;
      }
      sessionStorage.setItem('manualSpinn', 'false');
      this.spinn.hide();
    }, (error) => {
      this.invalid = true;
      alert('error occurred');
      sessionStorage.setItem('manualSpinn', 'false');
      this.spinn.hide();
    });
  }
}
