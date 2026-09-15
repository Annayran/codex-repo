# TGFMEPI — Movimento de EPI

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MovimentoEpi | Movimento de EPI | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODFUNC | Funcionário | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| DTVALIDADE | Data de Validade | H |  |  | N |
| NUNOTA | Número Único da Nota | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MovimentoEpi / [TGFMEPI](TGFMEPI.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| MovimentoEpi / [TGFMEPI](TGFMEPI.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| MovimentoEpi / [TGFMEPI](TGFMEPI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
