import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ParaderoDetailComponent } from './paradero-detail.component';

describe('Paradero Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParaderoDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ParaderoDetailComponent,
              resolve: { paradero: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ParaderoDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load paradero on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ParaderoDetailComponent);

      // THEN
      expect(instance.paradero).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
