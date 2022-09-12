import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MenupagePage } from './menupage.page';

const routes: Routes = [
  {
    path: '',
    component: MenupagePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MenupagePageRoutingModule {}
