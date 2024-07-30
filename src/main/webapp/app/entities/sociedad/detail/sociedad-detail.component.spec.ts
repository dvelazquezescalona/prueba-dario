import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SociedadDetailComponent } from './sociedad-detail.component';

describe('Sociedad Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SociedadDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SociedadDetailComponent,
              resolve: { sociedad: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SociedadDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load sociedad on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SociedadDetailComponent);

      // THEN
      expect(instance.sociedad).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
