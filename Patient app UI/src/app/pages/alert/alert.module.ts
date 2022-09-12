import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';
import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';
import { AlertPageRoutingModule } from './alert-routing.module';
import { AlertPage } from './alert.page';
import { AutocompleteLibModule } from 'angular-ng-autocomplete';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule  } from '@angular/material/input';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AlertPageRoutingModule,
    AutocompleteLibModule,
    HttpClientModule,
    HttpClientJsonpModule,
    MatInputModule,
    MatAutocompleteModule
  ],
  declarations: [AlertPage]
})
export class AlertPageModule {}
