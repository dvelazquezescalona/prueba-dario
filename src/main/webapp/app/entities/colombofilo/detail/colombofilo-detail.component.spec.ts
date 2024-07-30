import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ColombofiloDetailComponent } from './colombofilo-detail.component';

describe('Colombofilo Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColombofiloDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ColombofiloDetailComponent,
              resolve: { colombofilo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ColombofiloDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load colombofilo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ColombofiloDetailComponent);

      // THEN
      expect(instance.colombofilo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
