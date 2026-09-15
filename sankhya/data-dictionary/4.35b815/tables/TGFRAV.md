# TGFRAV — Rateio por Veículo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RateioVeiculo | Rateio por Veículo | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AFERICAO | Aferição Veic. | F |  |  | N |
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODCTACTB | Conta Contábil | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODSITE | Site | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEICULO | Cód. Veículo | I |  |  | N |
| DIGITADO | Digitado | S |  |  | N |
| DTALTER | DTALTER | H |  |  | N |
| NUFIN | Num. Financeiro | I |  |  | N |
| NUMCONTRATO | Num. Contrato | I |  |  | N |
| ORIGEM | Origem | S |  |  | N |
| PERCRATEIO | % Rateio | F |  |  | N |
| TIPOAFERICAO | Tipo de aferição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | RateioVeiculo / [TGFRAV](TGFRAV.md) | NUFIN->NUFIN | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | PlanoContaEmpresa /  | CODCTACTB->CODCTACTB | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Site / [TGFSIT](TGFSIT.md) | CODSITE->CODSITE | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
