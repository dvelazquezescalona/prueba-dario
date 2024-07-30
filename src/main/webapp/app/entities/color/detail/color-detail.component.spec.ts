import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ColorDetailComponent } from './color-detail.component';

describe('Color Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColorDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ColorDetailComponent,
              resolve: { color: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ColorDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load color on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ColorDetailComponent);

      // THEN
      expect(instance.color).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
