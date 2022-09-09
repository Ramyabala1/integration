import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthService) {

  }
  // next: ActivatedRouteSnapshot,
  canActivate(
    route,
    state: RouterStateSnapshot) {
    if (this.authService.isLoggedIn()) {
      return true;
    } else {
      sessionStorage.setItem('validRoute', 'true');
      this.router.navigate([''], { queryParams: { returnUrl: state.url } });
      return false;
    }
  }
}
