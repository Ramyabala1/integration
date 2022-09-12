import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { Alert2PageRoutingModule } from './alert2-routing.module';

import { Alert2Page } from './alert2.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    Alert2PageRoutingModule
  ],
  declarations: [Alert2Page]
})
export class Alert2PageModule {}
