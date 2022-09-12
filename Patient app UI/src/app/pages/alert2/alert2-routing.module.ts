import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { Alert2Page } from './alert2.page';

const routes: Routes = [
  {
    path: '',
    component: Alert2Page
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class Alert2PageRoutingModule {}
