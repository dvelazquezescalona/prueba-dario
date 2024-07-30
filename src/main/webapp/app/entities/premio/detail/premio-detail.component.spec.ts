import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PremioDetailComponent } from './premio-detail.component';

describe('Premio Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PremioDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PremioDetailComponent,
              resolve: { premio: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PremioDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load premio on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PremioDetailComponent);

      // THEN
      expect(instance.premio).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
