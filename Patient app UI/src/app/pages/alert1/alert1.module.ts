import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { Alert1PageRoutingModule } from './alert1-routing.module';

import { Alert1Page } from './alert1.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    Alert1PageRoutingModule
  ],
  declarations: [Alert1Page]
})
export class Alert1PageModule {}
