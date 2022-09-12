import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { AutocompleteComponent } from 'angular-ng-autocomplete';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AlertPage } from './alert.page';

describe('AlertPage', () => {
  let component: AlertPage;
  let fixture: ComponentFixture<AlertPage>;
  let router: Router;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AlertPage ],
      imports: [IonicModule.forRoot(),AppRoutingModule,ReactiveFormsModule,AutocompleteComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(AlertPage);
    component = fixture.componentInstance;
    router = TestBed.get(Router);
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
