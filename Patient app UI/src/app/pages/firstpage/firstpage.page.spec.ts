import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { AppRoutingModule } from 'src/app/app-routing.module';

import { FirstpagePage } from './firstpage.page';

describe('FirstpagePage', () => {
  let component: FirstpagePage;
  let fixture: ComponentFixture<FirstpagePage>;
  let router: Router;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ FirstpagePage ],
      imports: [IonicModule.forRoot(),AppRoutingModule,ReactiveFormsModule]
    }).compileComponents();

    fixture = TestBed.createComponent(FirstpagePage);
    component = fixture.componentInstance;
    router = TestBed.get(Router);
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
