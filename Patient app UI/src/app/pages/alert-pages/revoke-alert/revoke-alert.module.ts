import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RevokeAlertPageRoutingModule } from './revoke-alert-routing.module';

import { RevokeAlertPage } from './revoke-alert.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RevokeAlertPageRoutingModule
  ],
  declarations: [RevokeAlertPage]
})
export class RevokeAlertPageModule {}
