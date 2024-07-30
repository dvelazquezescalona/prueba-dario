import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MunicipioDetailComponent } from './municipio-detail.component';

describe('Municipio Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MunicipioDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MunicipioDetailComponent,
              resolve: { municipio: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MunicipioDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load municipio on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MunicipioDetailComponent);

      // THEN
      expect(instance.municipio).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
