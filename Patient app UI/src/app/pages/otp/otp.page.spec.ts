import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { IonicModule} from '@ionic/angular';
import { AppRoutingModule } from 'src/app/app-routing.module';

import { OtpPage } from './otp.page';

describe('OtpPage', () => {
  let component: OtpPage;
  let fixture: ComponentFixture<OtpPage>;
  let router: Router;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OtpPage ],
      imports: [IonicModule.forRoot(),AppRoutingModule,ReactiveFormsModule]
    }).compileComponents();

    fixture = TestBed.createComponent(OtpPage);
    router=TestBed.get(Router);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
