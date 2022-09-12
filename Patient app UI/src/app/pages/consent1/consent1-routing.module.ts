import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { Consent1Page } from './consent1.page';

const routes: Routes = [
  {
    path: '',
    component: Consent1Page
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class Consent1PageRoutingModule {}
