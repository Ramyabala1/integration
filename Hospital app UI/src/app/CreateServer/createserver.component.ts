import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GeneralService } from '../general.service';
import { MatSnackBar, MatAutocompleteSelectedEvent} from '@angular/material';

export class HiuUser {
   
    
  userName:any;
  password:any;
  mheUserName:any;
  mhePassword:any;
  mheOrgname:any
 
  
}

@Component({
  selector: 'createserver-component',
  templateUrl: './createserver.component.html',
  styleUrls: ['./createserver.component.css']
})


export class CreateserverComponent implements OnInit {
  docName: string;
  toggleDurationName: any;
  toggleDiagnosisType: string;
  istriggered: boolean;
  validDuration: boolean;
  constructor(private router: Router, private generalService: GeneralService, private snackBar: MatSnackBar,
    ) {
  }
  color = 'accent';
  mode = 'indeterminate';
  value = 100;
  category: any;
  clinicName: any;
  recordFound = 'false';
  MHEseasons: string[] = ['FollowUp', 'Refer', 'Move to IP', 'Review SOS'];
  OPseasons: string[] = ['FollowUp'];
  Visit: string[] = ['Regular OPD Visit', 'Emergency'];
  improveArray: string[] = [];
   public diagnosisTypes = ['Differential Diagnosis'];
  public durationTypes = ['Days','Weeks','Month'];
  patientData: any;
  complaints = [];
  dias = [];
  op_view: boolean;
  comValid = false;
  tomorrow: any;
  puuid: any;
  today: Date;
  public birthdate: Date;
  public age: number;
  orgName: any;
  history: any;
  illness: any;
  addNote: any;
  improveStatus: any;
  followUp: any;
  
  
  
  
  hiuUser = new HiuUser();
  

  ngOnInit() {
    
    
   
    
    
  }

  cancel() {
    if (window.confirm('Are you sure you want to leave this page?')) {
      sessionStorage.setItem('validRoute', 'true');
      this.router.navigate(['']);
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
  

  
  this.generalService.CreateServer(this.hiuUser).subscribe(response => {
    var jsonResponse = response.json();
    if(jsonResponse.status===200)
      {
         if(jsonResponse !== null && jsonResponse.entity !== null){
          alert(jsonResponse.entity);     
         }
        this.router.navigate(['dashboard']);
      }
       else if(jsonResponse.status === 400) {
       
      
        if(jsonResponse !== null && jsonResponse.entity !== null){
          alert(jsonResponse.entity);  
        }
      }
    } , (error) => {
      
      alert('error occurred');

    });

}
   
 




}
