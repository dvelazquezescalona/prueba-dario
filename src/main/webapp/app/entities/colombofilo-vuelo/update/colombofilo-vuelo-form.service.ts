import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IColombofiloVuelo, NewColombofiloVuelo } from '../colombofilo-vuelo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IColombofiloVuelo for edit and NewColombofiloVueloFormGroupInput for create.
 */
type ColombofiloVueloFormGroupInput = IColombofiloVuelo | PartialWithRequiredKeyOf<NewColombofiloVuelo>;

type ColombofiloVueloFormDefaults = Pick<NewColombofiloVuelo, 'id'>;

type ColombofiloVueloFormGroupContent = {
  id: FormControl<IColombofiloVuelo['id'] | NewColombofiloVuelo['id']>;
  envidas: FormControl<IColombofiloVuelo['envidas']>;
  distancia: FormControl<IColombofiloVuelo['distancia']>;
  colombofilo: FormControl<IColombofiloVuelo['colombofilo']>;
  vuelo: FormControl<IColombofiloVuelo['vuelo']>;
};

export type ColombofiloVueloFormGroup = FormGroup<ColombofiloVueloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ColombofiloVueloFormService {
  createColombofiloVueloFormGroup(colombofiloVuelo: ColombofiloVueloFormGroupInput = { id: null }): ColombofiloVueloFormGroup {
    const colombofiloVueloRawValue = {
      ...this.getFormDefaults(),
      ...colombofiloVuelo,
    };
    return new FormGroup<ColombofiloVueloFormGroupContent>({
      id: new FormControl(
        { value: colombofiloVueloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      envidas: new FormControl(colombofiloVueloRawValue.envidas, {
        validators: [Validators.required],
      }),
      distancia: new FormControl(colombofiloVueloRawValue.distancia, {
        validators: [Validators.required],
      }),
      colombofilo: new FormControl(colombofiloVueloRawValue.colombofilo),
      vuelo: new FormControl(colombofiloVueloRawValue.vuelo),
    });
  }

  getColombofiloVuelo(form: ColombofiloVueloFormGroup): IColombofiloVuelo | NewColombofiloVuelo {
    return form.getRawValue() as IColombofiloVuelo | NewColombofiloVuelo;
  }

  resetForm(form: ColombofiloVueloFormGroup, colombofiloVuelo: ColombofiloVueloFormGroupInput): void {
    const colombofiloVueloRawValue = { ...this.getFormDefaults(), ...colombofiloVuelo };
    form.reset(
      {
        ...colombofiloVueloRawValue,
        id: { value: colombofiloVueloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ColombofiloVueloFormDefaults {
    return {
      id: null,
    };
  }
}
