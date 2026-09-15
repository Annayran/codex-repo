# TGFAVR — Registro de avaria

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegistroAvaria | Registro de Avaria | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Cód. Produto | I |  |  | N |
| CODUSU | Cód. Usuario | I |  |  | N |
| DTALTER | Data | H |  |  | N |
| NROSERIE | Nro de série | S |  |  | N |
| NUAVARIA | Nro da avaria | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PERCDESC | % Desconto | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FotoAvaria / [TGFFAV](TGFFAV.md) | RegistroAvaria / [TGFAVR](TGFAVR.md) | NUAVARIA->NUAVARIA | I |
| RegistroAvaria / [TGFAVR](TGFAVR.md) | FotoAvaria / [TGFFAV](TGFFAV.md) | NUAVARIA->NUAVARIA | I |
| RegistroAvaria / [TGFAVR](TGFAVR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RegistroAvaria / [TGFAVR](TGFAVR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
