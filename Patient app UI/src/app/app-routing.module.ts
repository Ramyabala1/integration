import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'firstpage',
    pathMatch: 'full'
  },
  {
    path: 'registration',
    loadChildren: () => import('./pages/registration/registration.module').then( m => m.RegistrationPageModule)
  },
  {
    path: 'login',
    loadChildren: () => import('./pages/login/login.module').then( m => m.LoginPageModule)
  },
  {
    path: 'homepage',
    loadChildren: () => import('./pages/homepage/homepage.module').then( m => m.HomepagePageModule)
  },
  {
    path: 'firstpage',
    loadChildren: () => import('./pages/firstpage/firstpage.module').then( m => m.FirstpagePageModule)
  },
  {
    path: 'otp',
    loadChildren: () => import('./pages/otp/otp.module').then( m => m.OtpPageModule)
  },
  {
    path: 'menupage',
    loadChildren: () => import('./pages/menupage/menupage.module').then( m => m.MenupagePageModule)
  },
  {
    path: 'link',
    loadChildren: () => import('./pages/link/link.module').then( m => m.LinkPageModule)
  },
  {
    path: 'alert',
    loadChildren: () => import('./pages/alert/alert.module').then( m => m.AlertPageModule)
  },
  {
    path: 'alert1',
    loadChildren: () => import('./pages/alert1/alert1.module').then( m => m.Alert1PageModule)
  },
  {
    path: 'alert2',
    loadChildren: () => import('./pages/alert2/alert2.module').then( m => m.Alert2PageModule)
  },
  {
    path: 'viewhealth',
    loadChildren: () => import('./pages/viewhealth/viewhealth.module').then( m => m.ViewhealthPageModule)
  },
  {
    path: 'consent',
    loadChildren: () => import('./pages/consent/consent.module').then( m => m.ConsentPageModule)
  },
  {
    path: 'consent1',
    loadChildren: () => import('./pages/consent1/consent1.module').then( m => m.Consent1PageModule)
  },
  {
    path: 'consent-otp',
    loadChildren: () => import('./pages/consent-otp/consent-otp.module').then( m => m.ConsentOtpPageModule)
  },
  {
    path: 'records',
    loadChildren: () => import('./pages/records/records.module').then( m => m.RecordsPageModule)
  },
  {
    path: 'accessHistory',
    loadChildren: () => import('./pages/access-history/access-history.module').then( m => m.AccessHistoryPageModule)
  },  {
    path: 'revoke-alert',
    loadChildren: () => import('./pages/alert-pages/revoke-alert/revoke-alert.module').then( m => m.RevokeAlertPageModule)
  },
  {
    path: 'update-consent',
    loadChildren: () => import('./update-consent/update-consent.module').then( m => m.UpdateConsentPageModule)
  }


];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
