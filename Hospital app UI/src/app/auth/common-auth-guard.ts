import { Injectable } from '@angular/core';
import { CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot, UrlTree, CanActivateChild, CanLoad } from '@angular/router';
import { AuthService } from './auth.service';
import { CanDeactivate } from '@angular/router';
import { DashBoardComponent } from '../dashboard/dashboard.component';



@Injectable({
    providedIn: 'root'
})
export class CommonAuthGuard implements CanDeactivate<DashBoardComponent>, CanActivate, CanActivateChild {

    constructor( private authService: AuthService) { }

    canDeactivate(component: DashBoardComponent) {
        if (sessionStorage.getItem('validRoute') === 'true') {
            return true;
        } else if (sessionStorage.getItem('validRoute') === 'false') {
            return false;
        } else {
            return true;
        }
    }

    canActivate(route, state: RouterStateSnapshot) {
        if (this.authService.isValidRoute()) {
            return true;
        } else {
            return false;
        }
    }

    canActivateChild(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
      ) {
        if (this.authService.isValidChildRoute()) {
            return true;
        } else {
            return false;
        }
      }



}
