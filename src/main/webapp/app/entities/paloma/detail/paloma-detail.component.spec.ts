import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PalomaDetailComponent } from './paloma-detail.component';

describe('Paloma Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PalomaDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PalomaDetailComponent,
              resolve: { paloma: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PalomaDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load paloma on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PalomaDetailComponent);

      // THEN
      expect(instance.paloma).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
