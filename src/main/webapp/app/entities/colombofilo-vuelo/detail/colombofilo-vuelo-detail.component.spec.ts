import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ColombofiloVueloDetailComponent } from './colombofilo-vuelo-detail.component';

describe('ColombofiloVuelo Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColombofiloVueloDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ColombofiloVueloDetailComponent,
              resolve: { colombofiloVuelo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ColombofiloVueloDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load colombofiloVuelo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ColombofiloVueloDetailComponent);

      // THEN
      expect(instance.colombofiloVuelo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
