import { Component, OnInit, SecurityContext } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { environment } from 'src/environments/environment';
import { Http } from '@angular/http';
import { GeneralService } from 'src/app/general.service';
import { HttpClient } from "@angular/common/http";
import { DomSanitizer } from '@angular/platform-browser';
import { getAllDebugNodes } from '@angular/core/src/debug/debug_node';
import { forkJoin } from 'rxjs';
import { analyzeAndValidateNgModules } from '@angular/compiler';
import { saveAs } from 'file-saver';



@Component({
  selector: 'app-homepage',
  templateUrl: './AD.component.html',
  styleUrls: ['./AD.component.css']
})




export class AdComponent implements OnInit {

  baseurl = environment.cors;
  improveArray: any;
 // detailsArray: Array<any> = [];
 detailsText:any;
  resourceJsonString: any;
  imageArray: any = [];
  request: any;
  jsonRequest: any;
  error: any;
  adID: any;
  message: any;
  patientId: any;
  showResponse: boolean;
  pdf: any;
  image: any;
  showDetails: boolean;
  consentId: any;
  stateData: any;
  today: any;
  comments: any;
  public fieldArray: Array<any> = [];
  showAD: any;
  timeOutMHMSID: any;
  hitype = [];
  accept: boolean;
  adRes: any;
  status: any;
  approvedOn: any;
  checkStatus = false;

  verifiedAD: boolean;
  dataSource2: any;
  fileObject: { 'fileUuid': string; 'fileType': string; 'fileCategory': string; 'fileName': string; };
  public files: Array<any> = [];
  public filesNew: Array<any> = [];
  fileLen: number;
  contentArray: any = [];
  showFhirRequest = false;
  fhirrequest:any;
  consentrequest = {


    patientID: '',
    timestamp: '',
    hiRequest: {
      consent: {
        id: '',
        requester: {
          name: '',
          identifier: {
            type: '',
            value: '',
            system: '',
          }
        },
        permission: {
          accessMode: '',


        },
        hiTypes: [],

        patient: {
          id: '',
        },
        purpose: {
          code: '',

        }

      },
      dateRange: {
        from: new Date(),
        to: new Date(),

      }

    }
  }

  constructor(private sanitizer: DomSanitizer, private http: Http, private httpClient: HttpClient, private router: Router,
    private gs:GeneralService) {

  }

  openNav() {
    document.getElementById('mySidenav').style.width = '190px';
  }

  closeNav() {
    document.getElementById('mySidenav').style.width = '0';
  }
  ngOnInit() {

    this.accept = false;
    this.showAD = false;
    this.showDetails = false;
    var historyData = history.state;
    this.stateData = historyData['consentID'];
    this.timeOutMHMSID = false;
    if (this.stateData) {
      this.consentId = this.stateData;
      this.patientId = historyData['patientID'];
    }
    this.today = new Date();
    this.detailsText = '';
  }

  getAD() {
    this.showAD = false;
    this.showDetails = false;
    this.hitype = ["Advance Directive"];
    this.consentrequest.patientID = this.patientId.trim();
    this.consentrequest.timestamp = new Date().toString();
    this.consentrequest.hiRequest.consent.id = this.consentId.trim();
    this.consentrequest.hiRequest.consent.purpose.code = 'CAREMGT'
    this.consentrequest.hiRequest.consent.patient.id = this.consentId.trim();


    this.consentrequest.hiRequest.consent.permission.accessMode = 'VIEW'

    this.consentrequest.hiRequest.consent.hiTypes = this.hitype;
    this.showDetails = false;
    this.showResponse = true;
    this.gs.getAd(this.consentrequest).subscribe(response => {

      var resList = [];
      var res = response.text();
      if (res !== null && res.length > 70) {
        resList = JSON.parse(res);
        if (resList !== null) {
          this.message = res;
        } else {
          alert(response.text());
        }
      } else {
        alert(response.text());
      }
    }, (error) => {
      this.message = 'error occurred';
      alert('error occurred');
    });

  }

  back() {
    sessionStorage.setItem("validRoute", "true");
    this.router.navigate(['dashboard']);
  }

  ViewAD() {

    this.hitype = ["Advance Directive"];
    this.consentrequest.patientID = this.patientId.trim();
    this.consentrequest.timestamp = new Date().toString();
    this.consentrequest.hiRequest.consent.id = this.consentId.trim();
    this.consentrequest.hiRequest.consent.purpose.code = 'CAREMGT'
    this.consentrequest.hiRequest.consent.patient.id = this.patientId.trim();


    this.consentrequest.hiRequest.consent.permission.accessMode = 'VIEW'

    this.consentrequest.hiRequest.consent.hiTypes = this.hitype;


    this.gs.ViewAd(this.consentrequest).subscribe(response => {

      if (response.text() !== "[]") {

     try {
            
      this.showDetails = true;
      this.showResponse = false;
        this.adRes = JSON.parse(response.text());
        this.approvedOn = this.adRes.approvedOn;
        this.adID = this.adRes.advanceDirectiveID;
        this.status = this.adRes.status;
        this.comments = this.adRes.comments;
        if (this.status === 'Approved') {
          this.checkStatus = true;
        }
        this.fieldArray = this.adRes.fileArray;
        for (let i = 0; i < this.fieldArray.length; i++) {
          const fileObj = {
            fileType: '',
            fileContent: ''
          };
          const imgObj = {
            fileType: '',
            fileContent: ''
          };
          if (this.fieldArray[i].fileUuid) {
            const fileUuid = this.fieldArray[i].fileUuid;
            const content = this.fieldArray[i].filePath;
            const file_value = content.split('.');
            const fileType = file_value[file_value.length - 1];
            if (fileType === 'jpg' || fileType === 'jpeg' || fileType === 'png' || fileType === 'PNG') {
              if (this.fieldArray[i].fileType === 'NAME') {
                imgObj.fileType = 'Proof of Name';
              } else if (this.fieldArray[i].fileType === 'ADDRESS') {
                imgObj.fileType = 'Proof of Address';
              } else if (this.fieldArray[i].fileType === 'AGE') {
                imgObj.fileType = 'Proof of Age';
              } else if (this.fieldArray[i].fileType === 'AD') {
                imgObj.fileType = 'AD Form';
              } else if (this.fieldArray[i].fileType === 'COMMON') {
                imgObj.fileType = 'Patient ID Document';
              }
              this.gs.getUploadedFile(fileUuid, "view",fileType).subscribe((response2:any )=> {

                imgObj.fileContent = response2.text();
              });

              this.imageArray.push(imgObj);
            } else {
              this.gs.getUploadedPDFFile(fileUuid, "view",fileType).subscribe((response2) => {
                 
               
                let fileName = i+'.pdf';
                   
                    saveAs.saveAs(response2, fileName); 
                    
                      
                    // var fileURL = URL.createObjectURL(response2);
                    //  window.open(fileURL);

                    //  this.fileName= 'D:\\temp\\Final_output.xlsx';  
                    //  var data = new Blob([this.fileName], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    // FileSaver.saveAs(data, this.fileName); 

                    // saveAs.saveAs(blob, fileName);

                    // const data = window.URL.createObjectURL(blob);
                    // const link = document.createElement("a");
                    // link.href = data;
                    // link.download = fileName;
                    // link.dispatchEvent(
                    //   new MouseEvent("click", {
                    //     bubbles: true,
                    //     cancelable: true,
                    //     view: window,
                    //   })
                    // );

                    // setTimeout(function () {
                    //   window.URL.revokeObjectURL(data);
                    //   link.remove();
                    // }, 100);

                    this.filesNew.push(fileObj);
              });
            }
          }
        }
     }catch(err)
     {
      alert(response.text());
     }

      } else {
        alert("No AD found");
      }
    });

  }

  ViewADDetails() {
    this.showDetails = false;
    this.showAD = true;
    this.timeOutMHMSID = false;
    this.accept = false;
    this.showResponse = false;
  }

  getADDetails() {
    this.showAD = false;
    this.showDetails = false;
    this.hitype = ["Advance Directive"];
    this.consentrequest.patientID = this.patientId.trim();
    this.consentrequest.timestamp = new Date().toString();
    this.consentrequest.hiRequest.consent.id = this.consentId.trim();
    this.consentrequest.hiRequest.consent.purpose.code = 'CAREMGT'
    this.consentrequest.hiRequest.consent.patient.id = this.patientId.trim();


    this.consentrequest.hiRequest.consent.permission.accessMode = 'VIEW'

    this.consentrequest.hiRequest.consent.hiTypes = this.hitype;

    this.gs.getAd(this.consentrequest).subscribe(response => {

      if (response.text().length > 70) {
        this.showResponse = true;


        this.adRes = JSON.parse(response.text());
        this.approvedOn = this.adRes.approvedOn;
        this.adID = this.adRes.advanceDirectiveID;
        this.status = this.adRes.status;
        this.comments = this.adRes.comments;
        if (this.status === 'Approved') {
          this.checkStatus = true;
        }
        this.fieldArray = this.adRes.fileArray;
       
        for (let i = 0; i < this.fieldArray.length; i++) {
          const fileObj = {
            fileType: '',
            fileContent: ''
          };
          const imgObj = {
            fileType: '',
            fileContent: ''
          };
          if (this.fieldArray[i].fileUuid) {
            const fileUuid = this.fieldArray[i].fileUuid;
            const content = this.fieldArray[i].filePath;
            const file_value = content.split('.');
            const fileType = file_value[file_value.length - 1];
            if (fileType === 'jpg' || fileType === 'jpeg' || fileType === 'png' || fileType === 'PNG') {
              if (this.fieldArray[i].fileType === 'NAME') {
                imgObj.fileType = 'Proof of Name';
              } else if (this.fieldArray[i].fileType === 'ADDRESS') {
                imgObj.fileType = 'Proof of Address';
              } else if (this.fieldArray[i].fileType === 'AGE') {
                imgObj.fileType = 'Proof of Age';
              } else if (this.fieldArray[i].fileType === 'AD') {
                imgObj.fileType = 'AD Form';
              } else if (this.fieldArray[i].fileType === 'COMMON') {
                imgObj.fileType = 'Patient ID Document';
              }
              this.gs.getUploadedFile(fileUuid, "get",fileType ).subscribe((response2:any) => {
                  if (response.text() !== "Could not download file") {
                    imgObj.fileContent = response2.text();
                    this.imageArray.push(imgObj);
                   // this.detailsArray.push(response2.text());
                   this.detailsText += response2.text() + "\n";
                  }
                });
            } else {
             this.gs.getUploadedFile(fileUuid, "get", "PDF").subscribe((response2:any) => {
                  if (this.fieldArray[i].fileType === 'NAME') {
                    fileObj.fileType = 'Proof of Name';
                  } else if (this.fieldArray[i].fileType === 'ADDRESS') {
                    fileObj.fileType = 'Proof of Address';
                  } else if (this.fieldArray[i].fileType === 'AGE') {
                    fileObj.fileType = 'Proof of Age';
                  } else if (this.fieldArray[i].fileType === 'AD') {
                    fileObj.fileType = 'AD Form';
                  } else if (this.fieldArray[i].fileType === 'COMMON') {
                    fileObj.fileType = 'Patient ID Document';
                  }

                  if (response.text() !== "Could not download file") {
                    fileObj.fileContent = response2.text();
                    this.filesNew.push(fileObj);
                    this.detailsText += response2.text() + "\n";
                   
                  }
                });

              

            }


          }
        }
        

      }
      else {
        alert(response.text());
      }
     
    }
      , (error) => {
        this.message = 'error occurred';
        alert('error occurred');
      });
  }

  // download(index){
    

  //   this.gs.downloadFile(index)
  //   .subscribe(
  //       data => saveAs(data, filename),
  //   );
  // }


  fetchFhirRequest()
  {
    this.hitype = ["Advance Directive"];
    this.consentrequest.patientID = this.patientId.trim();
    this.consentrequest.timestamp = new Date().toString();
    this.consentrequest.hiRequest.consent.id = this.consentId.trim();
    this.consentrequest.hiRequest.consent.purpose.code = 'CAREMGT'
    this.consentrequest.hiRequest.consent.patient.id =this.patientId.trim();


    this.consentrequest.hiRequest.consent.permission.accessMode = 'VIEW'

    this.consentrequest.hiRequest.consent.hiTypes = this.hitype;
    this.showFhirRequest = true;
  this.fhirrequest =  JSON.stringify(this.consentrequest);

  }
}