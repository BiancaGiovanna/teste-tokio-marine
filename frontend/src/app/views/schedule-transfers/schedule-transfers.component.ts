import { Component } from '@angular/core';
import {
  AbstractControl,
  AbstractControlOptions,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-schedule-transfers',
  templateUrl: './schedule-transfers.component.html',
  styleUrls: ['./schedule-transfers.component.css'],
})
export class ScheduleTransfersComponent {
  transferForm: FormGroup;
  selectedDate: Date = new Date();

  constructor(private fb: FormBuilder) {
    const formOptions: AbstractControlOptions = {
      validators: [this.accountValidator, this.dateValidator],
    };
    this.transferForm = this.fb.group(
      {
        originAccount: [
          '',
          [Validators.required, Validators.pattern(/^\d{10}$/)],
        ],
        destinationAccount: [
          '',
          [Validators.required, Validators.pattern(/^\d{10}$/)],
        ],
        amount: [
          '',
          [Validators.required, Validators.min(0), Validators.pattern(/^\d+$/)],
        ],

        selectedDate: [new Date(), [Validators.required]],
      },
      formOptions
    );
  }

  accountValidator(
    control: AbstractControl
  ): { [key: string]: boolean } | null {
    const originAccount = control.get('originAccount')?.value;
    const destinationAccount = control.get('destinationAccount')?.value;

    if (
      originAccount &&
      destinationAccount &&
      originAccount === destinationAccount
    ) {
      return { sameAccount: true };
    }

    return null;
  }

  dateValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const selectedDate = new Date(control.value);
    const currentDate = new Date();
    const maxAllowedDate = new Date();
    maxAllowedDate.setDate(currentDate.getDate() + 49);

    if (selectedDate < currentDate || selectedDate > maxAllowedDate) {
      return { invalidDate: true };
    }

    return null;
  }

  scheduleTransfer(): void {
    if (this.transferForm.valid) {
      console.log('Dados válidos:', this.transferForm.value);
    } else {
      console.log(
        'Formulário inválido. Verifique os campos.',
        this.transferForm.value
      );
    }
  }
}
