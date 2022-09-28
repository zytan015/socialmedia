import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogBoxPostComponent } from './dialog-box-post.component';

describe('DialogBoxPostComponent', () => {
  let component: DialogBoxPostComponent;
  let fixture: ComponentFixture<DialogBoxPostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogBoxPostComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogBoxPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
