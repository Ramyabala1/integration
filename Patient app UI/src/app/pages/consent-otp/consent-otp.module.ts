import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ConsentOtpPageRoutingModule } from './consent-otp-routing.module';

import { ConsentOtpPage } from './consent-otp.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ConsentOtpPageRoutingModule
  ],
  declarations: [ConsentOtpPage]
})
export class ConsentOtpPageModule {}
