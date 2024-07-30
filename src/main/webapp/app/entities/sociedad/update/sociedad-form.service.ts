import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISociedad, NewSociedad } from '../sociedad.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISociedad for edit and NewSociedadFormGroupInput for create.
 */
type SociedadFormGroupInput = ISociedad | PartialWithRequiredKeyOf<NewSociedad>;

type SociedadFormDefaults = Pick<NewSociedad, 'id'>;

type SociedadFormGroupContent = {
  id: FormControl<ISociedad['id'] | NewSociedad['id']>;
  nombreSociedad: FormControl<ISociedad['nombreSociedad']>;
  latitud: FormControl<ISociedad['latitud']>;
  longitud: FormControl<ISociedad['longitud']>;
  municipio: FormControl<ISociedad['municipio']>;
};

export type SociedadFormGroup = FormGroup<SociedadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SociedadFormService {
  createSociedadFormGroup(sociedad: SociedadFormGroupInput = { id: null }): SociedadFormGroup {
    const sociedadRawValue = {
      ...this.getFormDefaults(),
      ...sociedad,
    };
    return new FormGroup<SociedadFormGroupContent>({
      id: new FormControl(
        { value: sociedadRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombreSociedad: new FormControl(sociedadRawValue.nombreSociedad, {
        validators: [Validators.required],
      }),
      latitud: new FormControl(sociedadRawValue.latitud, {
        validators: [Validators.required],
      }),
      longitud: new FormControl(sociedadRawValue.longitud, {
        validators: [Validators.required],
      }),
      municipio: new FormControl(sociedadRawValue.municipio),
    });
  }

  getSociedad(form: SociedadFormGroup): ISociedad | NewSociedad {
    return form.getRawValue() as ISociedad | NewSociedad;
  }

  resetForm(form: SociedadFormGroup, sociedad: SociedadFormGroupInput): void {
    const sociedadRawValue = { ...this.getFormDefaults(), ...sociedad };
    form.reset(
      {
        ...sociedadRawValue,
        id: { value: sociedadRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SociedadFormDefaults {
    return {
      id: null,
    };
  }
}
