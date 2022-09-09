import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, filter, switchMap, take, finalize } from 'rxjs/operators';

import { AuthService } from '../app/auth/auth.service';
import { MatSnackBar } from '@angular/material';
import { NgxSpinnerService } from 'ngx-spinner';


@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    private refreshingInProgress: boolean;
    private accessTokenSubject: BehaviorSubject<string> = new BehaviorSubject<string>(null);

    constructor(private authService: AuthService,
        private router: Router, private snackBar: MatSnackBar, private spinnService: NgxSpinnerService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.spinnService.show();
        const accessToken = sessionStorage.getItem('token');
        return next.handle(this.addAuthorizationHeader(req, accessToken)).pipe(
            finalize(() => {
                if (sessionStorage.getItem('manualSpinn') !== 'true') {
                    this.spinnService.hide();
                }
            }),
            catchError(err => {
                sessionStorage.setItem('manualSpinn', 'false');
                this.spinnService.hide();

                if (err instanceof HttpErrorResponse && err.error.status === 401) {
                    const refreshToken = sessionStorage.getItem('token');
                    this.logoutAndRedirect(err.error);
                    if (refreshToken && accessToken) {
                        return this.refreshToken(req, next);
                    }
                } else if (err instanceof HttpErrorResponse && err.error.status === 403) {
                    return this.logoutAndRedirect(err.error);
                } else if (err.error.message && err.error.status !== 404 && err.error.status !== 400) {
                    this.snackBar.open(err.error.message, 'x', {
                        duration: 5000,
                    });
                }
                return throwError(err.error);
            })
        );
    }

    private addAuthorizationHeader(request: HttpRequest<any>, token: string): HttpRequest<any> {
        if (token) {
            return request.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
        }

        return request;
    }

    private logoutAndRedirect(err): Observable<HttpEvent<any>> {
        const obj = {
            userId: sessionStorage.getItem('userID'),
            sessionToken: JSON.parse(sessionStorage.getItem('registryAdmin')).userToken,
        };
        this.authService.logout();
        this.snackBar.open('Session Expired, Please login again', 'x', {
            duration: 5000,
        });
        sessionStorage.clear();
        sessionStorage.setItem('validRoute', 'true');
        this.refreshingInProgress = false;
        this.router.navigate(['']);
        return throwError(err);
    }

    private refreshToken(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (!this.refreshingInProgress) {
            this.refreshingInProgress = true;
            this.accessTokenSubject.next(null);
            return this.authService.refreshToken().pipe(
                switchMap((res) => {
                    const nToken = res[`token`];
                    sessionStorage.setItem('token', nToken);
                    this.refreshingInProgress = false;
                    this.accessTokenSubject.next(nToken);
                    // repeat failed request with new token
                    return next.handle(this.addAuthorizationHeader(request, nToken));
                })
            );
        } else {
            // wait while getting new token
            return this.accessTokenSubject.pipe(
                filter(token => token !== null),
                take(1),
                switchMap(token => {
                    // repeat failed request with new token
                    return next.handle(this.addAuthorizationHeader(request, token));
                }));
        }
    }
}