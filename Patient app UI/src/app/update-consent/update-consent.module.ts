import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UpdateConsentPageRoutingModule } from './update-consent-routing.module';

import { UpdateConsentPage } from './update-consent.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    UpdateConsentPageRoutingModule
  ],
  declarations: [UpdateConsentPage]
})
export class UpdateConsentPageModule {}
