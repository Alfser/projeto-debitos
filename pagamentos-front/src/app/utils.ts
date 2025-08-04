export class ObjectUtil{
    static cleanParams<T extends Object>(searchParams: T){
        Object.keys(searchParams).forEach(key => {
            if (searchParams[key as keyof T] == null) {
                delete searchParams[key as keyof T];
            }
        });
    }
}

export class MaskUtils{
  static maskCpfCnpj(value: string): string {
    let valueCleaned = value.replace(/\D/g, '');

    if (valueCleaned.length <= 11) {
      // Aplica máscara de CPF (000.000.000-00)
      valueCleaned = valueCleaned.replace(/(\d{3})(\d)/, '$1.$2');
      valueCleaned = valueCleaned.replace(/(\d{3})(\d)/, '$1.$2');
      valueCleaned = valueCleaned.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    } else {
      // Aplica máscara de CNPJ (00.000.000/0000-00)
      valueCleaned = valueCleaned.replace(/^(\d{2})(\d)/, '$1.$2');
      valueCleaned = valueCleaned.replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3');
      valueCleaned = valueCleaned.replace(/\.(\d{3})(\d)/, '.$1/$2');
      valueCleaned = valueCleaned.replace(/(\d{4})(\d)/, '$1-$2');
    }

    return valueCleaned;
  }

  static maskCard(value: string): string {

    let valueCleaned = value.replace(/\D/g, '');
    // Aplica máscara (0000 0000 0000 0000)
    valueCleaned = valueCleaned.replace(/(\d{4})(?=\d)/g, '$1 ');

    return valueCleaned;
  }

  static maskCurrency(value: string): string {
    let valueCleaned = value.replace(/\D/g, '');

    // Garante que temos pelo menos '00' (centavos)
    valueCleaned = valueCleaned.padStart(3, '0');

    // Insere a vírgula dos centavos
    const reais = valueCleaned.slice(0, -2);
    const centavos = valueCleaned.slice(-2);
    valueCleaned = reais + ',' + centavos;

    // Adiciona pontos como separadores de milhar
    valueCleaned = valueCleaned.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');

    // Remove zeros à esquerda não significativos
    valueCleaned = valueCleaned.replace(/^0+(?=\d)/, '');

    // Se estiver vazio ou só com zeros, mantém '0,00'
    if (valueCleaned === '' || valueCleaned === ',') {
      valueCleaned = '0.00';
    }
    return valueCleaned;
  }

  static onlyDigits(value: string | undefined): string| undefined{
    if(value){
      return value.replace(/\D/g, '');
    }else{
      return undefined
    }
  }

  static parseCurrencyToDecimal(currencyString: string): number {
    if (!currencyString) return 0.0;
    let cleanValue = currencyString.replace(/\./g, '').replace('.', '');
    cleanValue = cleanValue.replace(/\./g, '').replace(',', '.');
    return parseFloat(cleanValue);
  }
}
