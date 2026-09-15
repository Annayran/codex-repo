# TGFNCB — Natureza / Conta Bancária

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NaturezaContaBancaria | Natureza / Conta Bancária | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Conta Banco Interação | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CONVENIO | Convênio | F |  |  | N |
| REMBCO | REMBCO | I |  |  | N |
| REMFINAL | REMFINAL | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NaturezaContaBancaria / [TGFNCB](TGFNCB.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| NaturezaContaBancaria / [TGFNCB](TGFNCB.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
