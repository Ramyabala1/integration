import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewhealthPageRoutingModule } from './viewhealth-routing.module';

import { ViewhealthPage } from './viewhealth.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewhealthPageRoutingModule
  ],
  declarations: [ViewhealthPage]
})
export class ViewhealthPageModule {}
