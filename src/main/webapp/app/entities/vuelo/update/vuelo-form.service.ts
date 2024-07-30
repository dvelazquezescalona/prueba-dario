import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVuelo, NewVuelo } from '../vuelo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVuelo for edit and NewVueloFormGroupInput for create.
 */
type VueloFormGroupInput = IVuelo | PartialWithRequiredKeyOf<NewVuelo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVuelo | NewVuelo> = Omit<T, 'fecha'> & {
  fecha?: string | null;
};

type VueloFormRawValue = FormValueOf<IVuelo>;

type NewVueloFormRawValue = FormValueOf<NewVuelo>;

type VueloFormDefaults = Pick<NewVuelo, 'id' | 'fecha'>;

type VueloFormGroupContent = {
  id: FormControl<VueloFormRawValue['id'] | NewVuelo['id']>;
  fecha: FormControl<VueloFormRawValue['fecha']>;
  descripcion: FormControl<VueloFormRawValue['descripcion']>;
  competencia: FormControl<VueloFormRawValue['competencia']>;
  campeonato: FormControl<VueloFormRawValue['campeonato']>;
  paradero: FormControl<VueloFormRawValue['paradero']>;
};

export type VueloFormGroup = FormGroup<VueloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VueloFormService {
  createVueloFormGroup(vuelo: VueloFormGroupInput = { id: null }): VueloFormGroup {
    const vueloRawValue = this.convertVueloToVueloRawValue({
      ...this.getFormDefaults(),
      ...vuelo,
    });
    return new FormGroup<VueloFormGroupContent>({
      id: new FormControl(
        { value: vueloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fecha: new FormControl(vueloRawValue.fecha, {
        validators: [Validators.required],
      }),
      descripcion: new FormControl(vueloRawValue.descripcion, {
        validators: [Validators.required],
      }),
      competencia: new FormControl(vueloRawValue.competencia, {
        validators: [Validators.required],
      }),
      campeonato: new FormControl(vueloRawValue.campeonato, {
        validators: [Validators.required],
      }),
      paradero: new FormControl(vueloRawValue.paradero),
    });
  }

  getVuelo(form: VueloFormGroup): IVuelo | NewVuelo {
    return this.convertVueloRawValueToVuelo(form.getRawValue() as VueloFormRawValue | NewVueloFormRawValue);
  }

  resetForm(form: VueloFormGroup, vuelo: VueloFormGroupInput): void {
    const vueloRawValue = this.convertVueloToVueloRawValue({ ...this.getFormDefaults(), ...vuelo });
    form.reset(
      {
        ...vueloRawValue,
        id: { value: vueloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VueloFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fecha: currentTime,
    };
  }

  private convertVueloRawValueToVuelo(rawVuelo: VueloFormRawValue | NewVueloFormRawValue): IVuelo | NewVuelo {
    return {
      ...rawVuelo,
      fecha: dayjs(rawVuelo.fecha, DATE_TIME_FORMAT),
    };
  }

  private convertVueloToVueloRawValue(
    vuelo: IVuelo | (Partial<NewVuelo> & VueloFormDefaults),
  ): VueloFormRawValue | PartialWithRequiredKeyOf<NewVueloFormRawValue> {
    return {
      ...vuelo,
      fecha: vuelo.fecha ? vuelo.fecha.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
