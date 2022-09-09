import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GeneralService } from '../general.service';
import { MatSnackBar, MatAutocompleteSelectedEvent} from '@angular/material';


import { DatePipe } from '@angular/common';


@Component({
  selector: 'verifyconsent.component-component',
  templateUrl: './verifyconsent.component.html',
  styleUrls: ['./verifyconsent.component.css'],
  providers: [DatePipe]
})


export class VerifyConsentComponent implements OnInit {
  docName: string;
  toggleDurationName: any;
  toggleDiagnosisType: string;
  istriggered: boolean;
  validDuration: boolean;
  constructor(private router: Router, private generalService: GeneralService, private snackBar: MatSnackBar,private datePipe: DatePipe
    ) {
  }
  color = 'accent';
  mode = 'indeterminate';
  value = 100;
  
  
 
  
 
  
 message:any;
  otp:any;
  consentRequestId:any;

  consent={
    otp:'',
    consentRequestID:''
  }




  ngOnInit() {
    
  
    
  }

  cancel() {
    if (window.confirm('Are you sure you want to leave this page?')) {
      sessionStorage.setItem('validRoute', 'true');
      this.router.navigate(['dashboard']);
    }

  }

  opensMenu(menuTrigger) {
    if (this.istriggered === true ) {
      menuTrigger.openMenu();
    } else {
      menuTrigger.closeMenu();
    }
    this.istriggered = false;
  }
  toggleFocus() {
    this.istriggered = true;
  }

  
 
  
  

  




save() {
  

 this.consent.otp = this.otp;
 this.consent.consentRequestID=this.consentRequestId;
  

  this.generalService.verifyConsent(this.consent).subscribe(response => {
    
    if(response.status===200)
      {
        let remarkList = response.json();
      
         var jsonResponse = response.json();
         var result = jsonResponse.entity;

         var entity = result.output;
        
        
        
         if(jsonResponse !== null && jsonResponse.entity !== null){
        //       
        this.message = entity['message'];
         }
      //  this.router.navigate(['dashboard']);
      }
       else if(response.status === 400) {
        var jsonResponse = response.json();
        var result = jsonResponse.entity;

        var entity = result.error;
        if(jsonResponse !== null && jsonResponse.entity !== null){
         // alert(entity);  

          this.message = entity;
        }
       
       
       
        
      }
    } , (error) => {
      
      alert('error occurred');

    });

}
   
 



}
