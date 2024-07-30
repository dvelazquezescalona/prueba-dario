import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IParadero, NewParadero } from '../paradero.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IParadero for edit and NewParaderoFormGroupInput for create.
 */
type ParaderoFormGroupInput = IParadero | PartialWithRequiredKeyOf<NewParadero>;

type ParaderoFormDefaults = Pick<NewParadero, 'id'>;

type ParaderoFormGroupContent = {
  id: FormControl<IParadero['id'] | NewParadero['id']>;
  nombreParadero: FormControl<IParadero['nombreParadero']>;
  distanciaMedia: FormControl<IParadero['distanciaMedia']>;
  latitud: FormControl<IParadero['latitud']>;
  longitud: FormControl<IParadero['longitud']>;
  sociedad: FormControl<IParadero['sociedad']>;
};

export type ParaderoFormGroup = FormGroup<ParaderoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ParaderoFormService {
  createParaderoFormGroup(paradero: ParaderoFormGroupInput = { id: null }): ParaderoFormGroup {
    const paraderoRawValue = {
      ...this.getFormDefaults(),
      ...paradero,
    };
    return new FormGroup<ParaderoFormGroupContent>({
      id: new FormControl(
        { value: paraderoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombreParadero: new FormControl(paraderoRawValue.nombreParadero, {
        validators: [Validators.required],
      }),
      distanciaMedia: new FormControl(paraderoRawValue.distanciaMedia, {
        validators: [Validators.required],
      }),
      latitud: new FormControl(paraderoRawValue.latitud, {
        validators: [Validators.required],
      }),
      longitud: new FormControl(paraderoRawValue.longitud, {
        validators: [Validators.required],
      }),
      sociedad: new FormControl(paraderoRawValue.sociedad),
    });
  }

  getParadero(form: ParaderoFormGroup): IParadero | NewParadero {
    return form.getRawValue() as IParadero | NewParadero;
  }

  resetForm(form: ParaderoFormGroup, paradero: ParaderoFormGroupInput): void {
    const paraderoRawValue = { ...this.getFormDefaults(), ...paradero };
    form.reset(
      {
        ...paraderoRawValue,
        id: { value: paraderoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ParaderoFormDefaults {
    return {
      id: null,
    };
  }
}
