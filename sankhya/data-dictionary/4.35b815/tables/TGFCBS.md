# TGFCBS — CEPs do Banco Safra

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CEPsBancoSafra | CEPs do Banco Safra | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CEPFIM | CEP Final | S |  |  | N |
| CEPINI | CEP Inicial | S |  |  | N |
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Data Alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContaBancaria / [TSICTA](TSICTA.md) | CEPsBancoSafra / [TGFCBS](TGFCBS.md) | CODCTABCOINT->CODCTABCOINT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
