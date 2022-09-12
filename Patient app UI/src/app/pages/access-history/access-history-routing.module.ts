import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AccessHistoryPage } from './access-history.page';

const routes: Routes = [
  {
    path: '',
    component: AccessHistoryPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AccessHistoryPageRoutingModule {}
