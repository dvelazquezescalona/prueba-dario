import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPaloma, NewPaloma } from '../paloma.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaloma for edit and NewPalomaFormGroupInput for create.
 */
type PalomaFormGroupInput = IPaloma | PartialWithRequiredKeyOf<NewPaloma>;

type PalomaFormDefaults = Pick<NewPaloma, 'id' | 'sexo' | 'activo'>;

type PalomaFormGroupContent = {
  id: FormControl<IPaloma['id'] | NewPaloma['id']>;
  anilla: FormControl<IPaloma['anilla']>;
  anno: FormControl<IPaloma['anno']>;
  pais: FormControl<IPaloma['pais']>;
  sexo: FormControl<IPaloma['sexo']>;
  activo: FormControl<IPaloma['activo']>;
  color: FormControl<IPaloma['color']>;
  colombofilo: FormControl<IPaloma['colombofilo']>;
};

export type PalomaFormGroup = FormGroup<PalomaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PalomaFormService {
  createPalomaFormGroup(paloma: PalomaFormGroupInput = { id: null }): PalomaFormGroup {
    const palomaRawValue = {
      ...this.getFormDefaults(),
      ...paloma,
    };
    return new FormGroup<PalomaFormGroupContent>({
      id: new FormControl(
        { value: palomaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      anilla: new FormControl(palomaRawValue.anilla, {
        validators: [Validators.required],
      }),
      anno: new FormControl(palomaRawValue.anno, {
        validators: [Validators.required],
      }),
      pais: new FormControl(palomaRawValue.pais, {
        validators: [Validators.required],
      }),
      sexo: new FormControl(palomaRawValue.sexo, {
        validators: [Validators.required],
      }),
      activo: new FormControl(palomaRawValue.activo),
      color: new FormControl(palomaRawValue.color),
      colombofilo: new FormControl(palomaRawValue.colombofilo),
    });
  }

  getPaloma(form: PalomaFormGroup): IPaloma | NewPaloma {
    return form.getRawValue() as IPaloma | NewPaloma;
  }

  resetForm(form: PalomaFormGroup, paloma: PalomaFormGroupInput): void {
    const palomaRawValue = { ...this.getFormDefaults(), ...paloma };
    form.reset(
      {
        ...palomaRawValue,
        id: { value: palomaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PalomaFormDefaults {
    return {
      id: null,
      sexo: false,
      activo: false,
    };
  }
}
