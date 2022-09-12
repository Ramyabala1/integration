import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ConsentOtpPage } from './consent-otp.page';

const routes: Routes = [
  {
    path: '',
    component: ConsentOtpPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ConsentOtpPageRoutingModule {}
