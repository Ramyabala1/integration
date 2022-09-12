import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PersonServiceService } from 'src/app/person-service.service';

export class Treatments {
  EhrAccessLog: string;
  id: any;
}

@Component({
  selector: 'app-access-history',
  templateUrl: './access-history.page.html',
  styleUrls: ['./access-history.page.scss'],
})
export class AccessHistoryPage implements OnInit {

  accessHistoryRequest = {
    api: 'mosip-emanas.access.history.view',
    version: '1.0',
    patientId: '',
    source: 'PatientApp',
    licenseKey: '123e4567-e89b-12d3-a456',
    timestamp: '',
  }

  adRes: any;
  activity: any;
  healthResource: any;
  hip: any;
  healthProfessionalName: any;
  hiu: any;
  createDate: any;
  statuscode: any;
  accessHistoryCard: boolean;

  constructor(private router: Router, private service: PersonServiceService) { }

  ngOnInit() {
    this.accessHistoryApiCall()
  }

  accessHistoryApiCall() {
    console.log("api called")
    this.accessHistoryRequest.patientId = sessionStorage.getItem('patientId');
    this.accessHistoryRequest.timestamp = new Date().toString();

    this.service.getAccessHistory(this.accessHistoryRequest).subscribe(response => {
      console.log("response", response.response);
      if (response.code === 400) {
        console.log('entered in 400');
        this.accessHistoryCard = false;
      }
      else if (response.code === 200) {
        console.log('entered in 200');
        this.accessHistoryCard = true;
        this.adRes = JSON.parse(response.response);
        for (let i = 0; i < this.adRes.length; i++) {
          var resOutt = this.adRes[i];
          console.log('output', resOutt);
          this.activity = resOutt.activity
          this.healthResource = resOutt.healthResource
          this.hip = resOutt.hip
          this.healthProfessionalName = resOutt.healthProfessionalName
          this.hiu = resOutt.hiu
          this.createDate = resOutt.createdDate
          this.statuscode = resOutt.statusCode
        }
      }
    });
  }

  back() {
    this.router.navigate(['homepage']);
  }

}
