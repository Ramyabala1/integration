import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { Alert1Page } from './alert1.page';

const routes: Routes = [
  {
    path: '',
    component: Alert1Page
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class Alert1PageRoutingModule {}
