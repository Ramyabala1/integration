import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AboutComponent } from './about/about.component';
import { HomepageComponent } from './homepage/homepage.component';
import { OPBMRComponent } from './OPBMR/OPBMR.component';
import { IPBMRComponent } from './IPBMR/IPBMR.component';
import { AdComponent } from './advance-directive/AD.component';
import { DashBoardComponent } from './dashboard/dashboard.component';
import { GetBmrComponent } from './get-bmr/getBMR.component';
import { OpFormComponent } from './opform-addBmr/opform.component';
import { IpFormComponent } from './ipform-addBmr/ipform.component';
import { CreateserverComponent } from './CreateServer/createserver.component';
import { ConsentRequestComponent } from './consentrequest/consentrequest.component';
import { VerifyConsentComponent } from './verifyconsent/verifyconsent.component';
import { RestraintMonitoring } from './restraintMonitoring/restraint.component';
import { AssessmentComponent } from './assessment/assessment.component';
import { TheraphyComponent } from './theraphy/theraphy.component';
import { IpFormDischargeComponent } from './ipform-discharge/ipform-discharge.component'
import { IpFormAddBMRComponent } from './ipform/ipform-addBmr.component';
import { AddPatient } from './add-Patient/add-patient.component';

import { CommonAuthGuard } from './auth/common-auth-guard';
import { AuthGuard } from './auth/auth.guard';
import { PassAuthGuardService } from './auth/pass-auth-guard.service';

const routes: Routes = [

  // { path: '', redirectTo: '/homepage', pathMatch: 'full' },
  // { path: '', component: HomepageComponent },
  // { path: 'about_mhms', component: AboutComponent},
  // { path: 'homepage', component: HomepageComponent},
  // { path: 'opbmr', component: OPBMRComponent},
  // { path: 'dashboard', component: DashBoardComponent , canDeactivate: [CommonAuthGuard] },
  // { path: 'ipbmr', component: IPBMRComponent},
  // { path: 'ad', component: AdComponent},
  // { path: 'get-BMR', component: GetBmrComponent},
  // { path: 'opForm', component: OpFormComponent},
  // { path: 'ipForm', component: IpFormComponent},
  // { path: 'createserver', component: CreateserverComponent},
  // { path: 'consentrequest', component: ConsentRequestComponent},
  // { path: 'verifyConsent', component: VerifyConsentComponent},
  // { path: 'restraint', component: RestraintMonitoring},
  // { path: 'assessment', component: AssessmentComponent},
  // { path: 'assessment', component: AssessmentComponent},
  // { path: 'therapy', component: TheraphyComponent},
  // { path: 'discharge', component: IpFormDischargeComponent},
  // { path: 'addIP', component: IpFormAddBMRComponent},
  // { path: 'addPatient', component: AddPatient}

  { path: '', component: HomepageComponent , canActivate: [CommonAuthGuard]},
  { path: 'about_mhms', component: AboutComponent },
  // { path: 'homepage', component: HomepageComponent, canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]},
  { path: 'opbmr', component: OPBMRComponent , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]},
  { path: 'dashboard', component: DashBoardComponent , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService], canDeactivate: [CommonAuthGuard] },
  { path: 'ipbmr', component: IPBMRComponent , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]},
  { path: 'ad', component: AdComponent , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]},
  { path: 'get-BMR', component: GetBmrComponent , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]},
  { path: 'opForm', component: OpFormComponent , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]},
  { path: 'ipForm', component: IpFormComponent , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]},
  { path: 'createserver', component: CreateserverComponent , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]},
  { path: 'consentrequest', component: ConsentRequestComponent , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]},
  { path: 'verifyConsent', component: VerifyConsentComponent , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]},
  { path: 'restraint', component: RestraintMonitoring },
  { path: 'assessment', component: AssessmentComponent },
  { path: 'assessment', component: AssessmentComponent },
  { path: 'therapy', component: TheraphyComponent },
  { path: 'discharge', component: IpFormDischargeComponent },
  { path: 'addIP', component: IpFormAddBMRComponent },
  { path: 'addPatient', component: AddPatient , canActivate: [AuthGuard, CommonAuthGuard, PassAuthGuardService]}
  
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const routingComponents = [
  HomepageComponent,
  AboutComponent,
  OPBMRComponent,
  IPBMRComponent,
  AdComponent,
  GetBmrComponent,
  DashBoardComponent,
  OpFormComponent,
  IpFormComponent,
  CreateserverComponent,
  ConsentRequestComponent,
  VerifyConsentComponent,
  RestraintMonitoring,
  IpFormDischargeComponent,
  IPBMRComponent,
  AddPatient
];

