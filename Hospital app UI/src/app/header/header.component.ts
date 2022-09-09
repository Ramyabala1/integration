import { Component, OnInit, ViewChild } from '@angular/core';
import { MatMenuTrigger, MatDialog } from '@angular/material';
import { Router } from '@angular/router';


export interface DialogData {
  name: string;
}

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  name: string;

  constructor(private router: Router,public dialog: MatDialog,) {
   }

  userName: any;
    @ViewChild(MatMenuTrigger) trigger: MatMenuTrigger;

  ngOnInit() {

  }
  openNav() {
    document.getElementById('mySidenav').style.width = '50%';
  }

  closeNav() {
    document.getElementById('mySidenav').style.width = '0';
  }
    someMethod() {
      this.trigger.openMenu();
    }
    settings() {}
    logOut() {
      sessionStorage.setItem('validRoute', 'true');
      localStorage.clear();
      sessionStorage.clear();
      this.router.navigate(['']);
    }

    onClickHome() {}



  }

  


