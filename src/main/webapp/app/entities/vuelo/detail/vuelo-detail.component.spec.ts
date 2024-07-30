import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VueloDetailComponent } from './vuelo-detail.component';

describe('Vuelo Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VueloDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: VueloDetailComponent,
              resolve: { vuelo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VueloDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load vuelo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VueloDetailComponent);

      // THEN
      expect(instance.vuelo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
