import { Injectable } from '@angular/core';
import { CanActivate, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
    providedIn: 'root'
})
export class PassAuthGuardService implements CanActivate {

    constructor(private router: Router, private authService: AuthService) {

    }
    canActivate(route, state: RouterStateSnapshot) {
        if (this.authService.isPassUpdated()) {
            return true;
        } else {
            return false;
        }
    }
}
