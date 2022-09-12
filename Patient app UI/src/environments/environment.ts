// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,

  // baseUrl: 'http://35.154.55.190:8080/MHMS_MOSIP/',
  // projectUrl: 'http://13.127.15.245//mhms_mhd/#',
  // loginUrl: 'http://13.127.15.245//MHMS_DEV/user',
  // cors: 'http://35.154.55.190:8080',
  // systemIP: '3.111.8.53:8080',
  // mosipUrl: 'http://localhost:8089/mosip',
  // mhaUserName: 'mha.admin',
  // emanasUrl: 'http://13.127.15.245//MHMS_DEV/rest'

  baseUrl: 'http://35.154.55.190:8080/MHMS_PATIENTAPP/',
  projectUrl: 'http://3.111.8.53/mhms_mhd/#',
  loginUrl: 'http://3.111.8.53/MHMS_DEV/user',
  cors: 'http://3.111.8.53:8080',
  systemIP: '3.111.8.53:8080',
  // mosipUrl: 'http://localhost:8089/mosip',
  mhaUserName: 'mha.admin',
  emanasUrl: 'http://3.111.8.53/MHMS_DEV/rest'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
