import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";
import { MaskUtils } from "./utils";

export function currencyValueValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const currencyStr = control.value
    const isInvalid = MaskUtils.parseCurrencyToDecimal(currencyStr) <= 0
    return isInvalid ?{ minValueCurrency: {value: control.value}} : null;
  };
}
