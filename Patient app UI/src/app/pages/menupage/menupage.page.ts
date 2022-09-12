import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menupage',
  templateUrl: './menupage.page.html',
  styleUrls: ['./menupage.page.scss'],
})
export class MenupagePage implements OnInit {

  form: FormBuilder;
  constructor(private router: Router, private formbuilder: FormBuilder) { }

  emanasId: any;

  ngOnInit() {
    this.emanasId = sessionStorage.getItem('patientId');
  }

  menu(){
    this.router.navigate(['homepage']);
  }

  logout(){
    sessionStorage.clear();
    localStorage.clear();
    this.router.navigate(['login']);

  }
}
