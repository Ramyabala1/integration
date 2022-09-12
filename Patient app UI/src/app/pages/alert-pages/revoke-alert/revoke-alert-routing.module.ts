import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RevokeAlertPage } from './revoke-alert.page';

const routes: Routes = [
  {
    path: '',
    component: RevokeAlertPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RevokeAlertPageRoutingModule {}
