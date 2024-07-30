import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMunicipio, NewMunicipio } from '../municipio.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMunicipio for edit and NewMunicipioFormGroupInput for create.
 */
type MunicipioFormGroupInput = IMunicipio | PartialWithRequiredKeyOf<NewMunicipio>;

type MunicipioFormDefaults = Pick<NewMunicipio, 'id'>;

type MunicipioFormGroupContent = {
  id: FormControl<IMunicipio['id'] | NewMunicipio['id']>;
  nombreMunicipio: FormControl<IMunicipio['nombreMunicipio']>;
  provincia: FormControl<IMunicipio['provincia']>;
};

export type MunicipioFormGroup = FormGroup<MunicipioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MunicipioFormService {
  createMunicipioFormGroup(municipio: MunicipioFormGroupInput = { id: null }): MunicipioFormGroup {
    const municipioRawValue = {
      ...this.getFormDefaults(),
      ...municipio,
    };
    return new FormGroup<MunicipioFormGroupContent>({
      id: new FormControl(
        { value: municipioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombreMunicipio: new FormControl(municipioRawValue.nombreMunicipio, {
        validators: [Validators.required],
      }),
      provincia: new FormControl(municipioRawValue.provincia),
    });
  }

  getMunicipio(form: MunicipioFormGroup): IMunicipio | NewMunicipio {
    return form.getRawValue() as IMunicipio | NewMunicipio;
  }

  resetForm(form: MunicipioFormGroup, municipio: MunicipioFormGroupInput): void {
    const municipioRawValue = { ...this.getFormDefaults(), ...municipio };
    form.reset(
      {
        ...municipioRawValue,
        id: { value: municipioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MunicipioFormDefaults {
    return {
      id: null,
    };
  }
}
