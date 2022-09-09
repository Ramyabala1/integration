// import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
// import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
// import { HomepageComponent } from './homepage.component';
// import { Router } from '@angular/router';
// import { HttpRequest } from '@angular/common/http';
// import { environment } from 'src/environments/environment';
// import {validUser , blankUser} from 'src/mock/value';
// import { MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';
// import { ReactiveFormsModule, FormBuilder } from '@angular/forms';

// const routerSpy = jasmine.createSpyObj('Router', ['navigateByUrl']);

// describe('Home Component Integrated Test', () => {
//     let fixture: ComponentFixture<HomepageComponent>;
//     let loginSpy;
//     function updateForm(userEmail, userPassword) {
//       fixture.componentInstance.username.setValue(userEmail);
//       fixture.componentInstance.password.setValue(userPassword);
//     }
  
//     beforeEach(async(() => {
//       TestBed.configureTestingModule({
//         imports: [
           
//             ReactiveFormsModule,
//             MatFormFieldModule,
//             MatIconModule,
//             MatInputModule
//           ],
//         providers: [
         
//           { provide: Router, useValue: routerSpy }
//         ],
//         declarations: [HomepageComponent],
//       }).compileComponents();
  
//       fixture = TestBed.createComponent(HomepageComponent);
//       // router = TestBed.get(Router);
  
     
//     }));

   

//       it('Display Username Error Msg when Username is blank', () => {
//         updateForm(blankUser.username, validUser.password);
//         fixture.detectChanges();
    
//         const button = fixture.debugElement.nativeElement.querySelector('button');
//         button.click();
//         fixture.detectChanges();
    
//         const usernameErrorMsg = fixture.debugElement.nativeElement.querySelector('#username-error-msg');
       
//         expect(usernameErrorMsg.innerHTML).toContain('Please enter username');
//       });
  
//     it('loginService login() should called ', fakeAsync(() => {
//       updateForm(validUser.username, validUser.password);
//       fixture.detectChanges();
      
//       const button = fixture.debugElement.nativeElement.querySelector('button');
//       button.click();
//       fixture.detectChanges();
  
      
//     }));
  
//     it('should route to home if login successfully', fakeAsync(() => {
//       updateForm(validUser.username, validUser.password);
//       fixture.detectChanges();
//       const button = fixture.debugElement.nativeElement.querySelector('button');
//       button.click();
//       advance(fixture);
//       expect(routerSpy.navigateByUrl).toHaveBeenCalled();
//       const navArgs = routerSpy.navigateByUrl.calls.first().args[0];
//       // expecting to navigate to id of the component's first hero
//       expect(navArgs).toBe('/configDashboard', 'should nav to Home Page');
//     }));
//     function advance(f: ComponentFixture<any>) {
//       tick();
//       f.detectChanges();
//     }
// });