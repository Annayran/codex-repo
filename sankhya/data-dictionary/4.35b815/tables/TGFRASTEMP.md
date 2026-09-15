# TGFRASTEMP — Rastreamento Por Empresa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RastreamentoEmpresa | Rastreamento Por Empresa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód. Empresa | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| TIPORASTR | Tipo de Rastreamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | RastreamentoEmpresa / [TGFRASTEMP](TGFRASTEMP.md) | CODPROD->CODPROD | I |
| RastreamentoEmpresa / [TGFRASTEMP](TGFRASTEMP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
