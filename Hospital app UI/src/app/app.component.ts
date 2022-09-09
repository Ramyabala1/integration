import { Component } from '@angular/core';
import { ConnectionService } from 'ng-connection-service';
import { MatSnackBar } from '@angular/material';

import { Router } from '@angular/router';

import { GeneralService } from './general.service';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'mhms_admin';
  status = 'ONLINE';
  isConnected = true;

  constructor(private connectionService: ConnectionService, private snackBar: MatSnackBar,
     private as: AuthService, private router: Router) {

    sessionStorage.setItem('validRoute', 'true');
    sessionStorage.setItem('validChildRoute', 'true');
  }
}