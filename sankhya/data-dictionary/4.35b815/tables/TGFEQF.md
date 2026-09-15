# TGFEQF — Cadastro Equipamento Fiscal

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CadastroEquipamentoFiscal | Cadastro Equipamento Fiscal | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODEQUIP | Cod. Equipamento | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| TIPOEQUIP | Tipo equipamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroEquipamentoFiscal / [TGFEQF](TGFEQF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CadastroMaquina / [TGFMAQ](TGFMAQ.md) | CadastroEquipamentoFiscal / [TGFEQF](TGFEQF.md) | CODEQUIP->CODEQUIP | I |
| CadastroSat / [TGFSAT](TGFSAT.md) | CadastroEquipamentoFiscal / [TGFEQF](TGFEQF.md) | CODEQUIP->CODEQUIP | I |
| Usuario / [TSIUSU](TSIUSU.md) | CadastroEquipamentoFiscal / [TGFEQF](TGFEQF.md) | CODEQUIP->CODEQUIP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
