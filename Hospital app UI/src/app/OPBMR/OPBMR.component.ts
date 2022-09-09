import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { environment } from 'src/environments/environment';
import { Http } from '@angular/http';
import { GeneralService } from 'src/app/general.service';
import { HttpClient } from "@angular/common/http";





@Component({
  selector: 'app-homepage',
  templateUrl: './OPBMR.component.html',
  styleUrls: ['./OPBMR.component.css']
})




export class OPBMRComponent implements OnInit {

  baseurl = environment.cors;
  improveArray :any;

  request:any;
  jsonRequest:any;
  error:any;
  message:any;
  constructor(private http: Http, private httpClient: HttpClient,private router: Router,private gs:GeneralService) {

  }

     openNav() {
      document.getElementById('mySidenav').style.width = '190px';
    }

     closeNav() {
      document.getElementById('mySidenav').style.width = '0';
    }
  ngOnInit() {
    this.httpClient.get("./assets/Request/opbmr_add.json").subscribe(data =>{
     this.jsonRequest = JSON.stringify(data);
     
     this.request= data;
    
  })

 

}

  
save()
{
  this.gs.postOPBMRData(this.jsonRequest).subscribe(response => {
          
    
    this.message = response.text();
     
      

  } , (error) => {
    this.message = 'error occurred';
    alert('error occurred');
  });

}
  



back()
{
  sessionStorage.setItem("validRoute", "true");
  this.router.navigate(['dashboard']);
}

 


}
