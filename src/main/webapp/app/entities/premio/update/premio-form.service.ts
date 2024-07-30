import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPremio, NewPremio } from '../premio.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPremio for edit and NewPremioFormGroupInput for create.
 */
type PremioFormGroupInput = IPremio | PartialWithRequiredKeyOf<NewPremio>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPremio | NewPremio> = Omit<T, 'fechaArribo'> & {
  fechaArribo?: string | null;
};

type PremioFormRawValue = FormValueOf<IPremio>;

type NewPremioFormRawValue = FormValueOf<NewPremio>;

type PremioFormDefaults = Pick<NewPremio, 'id' | 'designada' | 'fechaArribo'>;

type PremioFormGroupContent = {
  id: FormControl<PremioFormRawValue['id'] | NewPremio['id']>;
  designada: FormControl<PremioFormRawValue['designada']>;
  fechaArribo: FormControl<PremioFormRawValue['fechaArribo']>;
  tiempoVuelo: FormControl<PremioFormRawValue['tiempoVuelo']>;
  velocidad: FormControl<PremioFormRawValue['velocidad']>;
  lugar: FormControl<PremioFormRawValue['lugar']>;
  puntos: FormControl<PremioFormRawValue['puntos']>;
  paloma: FormControl<PremioFormRawValue['paloma']>;
  vuelo: FormControl<PremioFormRawValue['vuelo']>;
};

export type PremioFormGroup = FormGroup<PremioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PremioFormService {
  createPremioFormGroup(premio: PremioFormGroupInput = { id: null }): PremioFormGroup {
    const premioRawValue = this.convertPremioToPremioRawValue({
      ...this.getFormDefaults(),
      ...premio,
    });
    return new FormGroup<PremioFormGroupContent>({
      id: new FormControl(
        { value: premioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      designada: new FormControl(premioRawValue.designada, {
        validators: [Validators.required],
      }),
      fechaArribo: new FormControl(premioRawValue.fechaArribo, {
        validators: [Validators.required],
      }),
      tiempoVuelo: new FormControl(premioRawValue.tiempoVuelo),
      velocidad: new FormControl(premioRawValue.velocidad),
      lugar: new FormControl(premioRawValue.lugar),
      puntos: new FormControl(premioRawValue.puntos),
      paloma: new FormControl(premioRawValue.paloma),
      vuelo: new FormControl(premioRawValue.vuelo),
    });
  }

  getPremio(form: PremioFormGroup): IPremio | NewPremio {
    return this.convertPremioRawValueToPremio(form.getRawValue() as PremioFormRawValue | NewPremioFormRawValue);
  }

  resetForm(form: PremioFormGroup, premio: PremioFormGroupInput): void {
    const premioRawValue = this.convertPremioToPremioRawValue({ ...this.getFormDefaults(), ...premio });
    form.reset(
      {
        ...premioRawValue,
        id: { value: premioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PremioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      designada: false,
      fechaArribo: currentTime,
    };
  }

  private convertPremioRawValueToPremio(rawPremio: PremioFormRawValue | NewPremioFormRawValue): IPremio | NewPremio {
    return {
      ...rawPremio,
      fechaArribo: dayjs(rawPremio.fechaArribo, DATE_TIME_FORMAT),
    };
  }

  private convertPremioToPremioRawValue(
    premio: IPremio | (Partial<NewPremio> & PremioFormDefaults),
  ): PremioFormRawValue | PartialWithRequiredKeyOf<NewPremioFormRawValue> {
    return {
      ...premio,
      fechaArribo: premio.fechaArribo ? premio.fechaArribo.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
