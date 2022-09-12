import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewhealthPage } from './viewhealth.page';

const routes: Routes = [
  {
    path: '',
    component: ViewhealthPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewhealthPageRoutingModule {}
