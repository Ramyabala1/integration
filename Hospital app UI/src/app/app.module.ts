import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule, routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { GeneralService } from './general.service';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { DragDropModule } from '@angular/cdk/drag-drop';

import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CommonAuthGuard } from './auth/common-auth-guard';
import { TokenInterceptor } from '../app/token.interceptor';
import { MatProgressSpinnerModule } from '@angular/material'


import {
  MatAutocompleteModule,
  MatInputModule,
  MatBadgeModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatListModule,
  MatMenuModule,
  MatPaginatorModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatStepperModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatTreeModule,
  MatNativeDateModule,
  MAT_DATE_LOCALE
} from '@angular/material';

import { HomepageComponent } from './homepage/homepage.component';
import { TestComponent } from './test/test.component';
import { HeaderComponent } from './header/header.component';
import { OPBMRComponent } from './OPBMR/OPBMR.component';
import { IPBMRComponent } from './IPBMR/IPBMR.component';
import { AdComponent } from './advance-directive/AD.component';
import { GetBmrComponent } from './get-bmr/getBMR.component';

import { DashBoardComponent } from './dashboard/dashboard.component';
import { OpFormComponent } from './opform-addBmr/opform.component';
import { IpFormComponent } from './ipform-addBmr/ipform.component';
import { CreateserverComponent } from './CreateServer/createserver.component';
import { ConsentRequestComponent } from './consentrequest/consentrequest.component';
import { VerifyConsentComponent } from './verifyconsent/verifyconsent.component'
import { AngularMultiSelectModule } from 'angular2-multiselect-dropdown';
import { RestraintMonitoring } from './restraintMonitoring/restraint.component';
import { AssessmentComponent } from './assessment/assessment.component';
import { TheraphyComponent } from './theraphy/theraphy.component';

import { IpFormDischargeComponent } from './ipform-discharge/ipform-discharge.component'

import { IpFormAddBMRComponent } from './ipform/ipform-addBmr.component';
import { AuthComponent } from './auth/auth.component'



@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    HomepageComponent,
    HeaderComponent,
    TestComponent,
    TheraphyComponent,
    OPBMRComponent,
    IPBMRComponent,
    GetBmrComponent,
    DashBoardComponent,
    AdComponent,
    OpFormComponent,
    IpFormComponent,
    CreateserverComponent,
    ConsentRequestComponent,
    VerifyConsentComponent,
    RestraintMonitoring,
    AssessmentComponent,
    IpFormDischargeComponent,
    IPBMRComponent,
    IpFormAddBMRComponent,
    AuthComponent

  ],
  imports: [
    HttpModule,
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    MatIconModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    RouterModule,
    MatIconModule,
    DragDropModule,
    AngularMultiSelectModule,
    NgxSpinnerModule,
    MatProgressSpinnerModule

  ],


  schemas: [CUSTOM_ELEMENTS_SCHEMA],

  providers: [GeneralService,
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' },
    NgxSpinnerService,
    CommonAuthGuard,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    // {provide: ErrorHandler, useClass: AppErrorHandler}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
