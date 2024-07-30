import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProvinciaDetailComponent } from './provincia-detail.component';

describe('Provincia Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProvinciaDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProvinciaDetailComponent,
              resolve: { provincia: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProvinciaDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load provincia on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProvinciaDetailComponent);

      // THEN
      expect(instance.provincia).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
