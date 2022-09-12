import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PersonServiceService } from 'src/app/person-service.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.page.html',
  styleUrls: ['./homepage.page.scss'],
})
export class HomepagePage implements OnInit {

  public showCard = false;
  getVfdata = {
    patientId: '',
  };

  res1: [];

  constructor(private service: PersonServiceService, private router: Router) { }

  ngOnInit() {
    this.getVFId();
  }

  menu() {
    this.router.navigate(['menupage']);
  }

  next() {
    this.router.navigate(['consent']);
  }

  accessHistory() {
    this.router.navigate(['accessHistory']);
  }

  clickedPlus(event: Event) {
    this.showCard = !this.showCard;
  }

  link() {
    this.router.navigate(['link']);
  }

  view(list) {
    sessionStorage.setItem('episodeId', list.episodeId);
    this.router.navigate(['records']);
  }

  getVFId() {
    this.getVfdata.patientId = sessionStorage.getItem('patientId');
    this.service.getPatientHospitalMap(this.getVfdata).subscribe(response => {
      console.log('response: ', response);
      if (response.code === 400) {
        console.log('entered in 400');;
      }
      else if (response.code === 200) {
        var res = response;
        if (res.code === 200) {
          this.res1 = JSON.parse(response.response);
          console.log("resOut: ", this.res1);
        }
      }
    });
  }

}
