import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AccessHistoryPageRoutingModule } from './access-history-routing.module';

import { AccessHistoryPage } from './access-history.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AccessHistoryPageRoutingModule
  ],
  declarations: [AccessHistoryPage]
})
export class AccessHistoryPageModule {}
