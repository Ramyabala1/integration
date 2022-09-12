import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { Consent1PageRoutingModule } from './consent1-routing.module';

import { Consent1Page } from './consent1.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    Consent1PageRoutingModule
  ],
  declarations: [Consent1Page]
})
export class Consent1PageModule {}
