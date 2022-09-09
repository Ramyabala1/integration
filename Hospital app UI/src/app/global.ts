

import { environment } from '../environments/environment';
export const baseUrl = environment.baseUrl;

const logopath = 'assets/images/karnatakalogo.png';

const getNamesCountUrl = '';

export const otpResendUrl = environment.loginUrl + '/resendOTP';

export const verifyOTPUrl = environment.loginUrl + '/verifyOTP';
export const verifyAuthOTPUrl = environment.loginUrl + '/verifyAuthOTPUrl';
export const otpUrl = environment.loginUrl + '/sendOTP';
export const getConfig = environment.emanasUrl + '/config/getMapContentByNames';

export const refreshToken = environment.loginUrl + '/updateToken';
export const logoutUrl = baseUrl + '/logout';