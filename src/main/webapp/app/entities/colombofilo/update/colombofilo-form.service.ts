import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IColombofilo, NewColombofilo } from '../colombofilo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IColombofilo for edit and NewColombofiloFormGroupInput for create.
 */
type ColombofiloFormGroupInput = IColombofilo | PartialWithRequiredKeyOf<NewColombofilo>;

type ColombofiloFormDefaults = Pick<NewColombofilo, 'id'>;

type ColombofiloFormGroupContent = {
  id: FormControl<IColombofilo['id'] | NewColombofilo['id']>;
  nombre: FormControl<IColombofilo['nombre']>;
  primerApellido: FormControl<IColombofilo['primerApellido']>;
  segindoApellido: FormControl<IColombofilo['segindoApellido']>;
  ci: FormControl<IColombofilo['ci']>;
  latitud: FormControl<IColombofilo['latitud']>;
  longitud: FormControl<IColombofilo['longitud']>;
  direccion: FormControl<IColombofilo['direccion']>;
  categoria: FormControl<IColombofilo['categoria']>;
  telefono: FormControl<IColombofilo['telefono']>;
  zona: FormControl<IColombofilo['zona']>;
  sociedad: FormControl<IColombofilo['sociedad']>;
};

export type ColombofiloFormGroup = FormGroup<ColombofiloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ColombofiloFormService {
  createColombofiloFormGroup(colombofilo: ColombofiloFormGroupInput = { id: null }): ColombofiloFormGroup {
    const colombofiloRawValue = {
      ...this.getFormDefaults(),
      ...colombofilo,
    };
    return new FormGroup<ColombofiloFormGroupContent>({
      id: new FormControl(
        { value: colombofiloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(colombofiloRawValue.nombre, {
        validators: [Validators.required],
      }),
      primerApellido: new FormControl(colombofiloRawValue.primerApellido, {
        validators: [Validators.required],
      }),
      segindoApellido: new FormControl(colombofiloRawValue.segindoApellido, {
        validators: [Validators.required],
      }),
      ci: new FormControl(colombofiloRawValue.ci, {
        validators: [Validators.required],
      }),
      latitud: new FormControl(colombofiloRawValue.latitud, {
        validators: [Validators.required],
      }),
      longitud: new FormControl(colombofiloRawValue.longitud, {
        validators: [Validators.required],
      }),
      direccion: new FormControl(colombofiloRawValue.direccion, {
        validators: [Validators.required],
      }),
      categoria: new FormControl(colombofiloRawValue.categoria, {
        validators: [Validators.required],
      }),
      telefono: new FormControl(colombofiloRawValue.telefono),
      zona: new FormControl(colombofiloRawValue.zona),
      sociedad: new FormControl(colombofiloRawValue.sociedad),
    });
  }

  getColombofilo(form: ColombofiloFormGroup): IColombofilo | NewColombofilo {
    return form.getRawValue() as IColombofilo | NewColombofilo;
  }

  resetForm(form: ColombofiloFormGroup, colombofilo: ColombofiloFormGroupInput): void {
    const colombofiloRawValue = { ...this.getFormDefaults(), ...colombofilo };
    form.reset(
      {
        ...colombofiloRawValue,
        id: { value: colombofiloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ColombofiloFormDefaults {
    return {
      id: null,
    };
  }
}
