# TCIDIBI — Item Despesa Instalação Bem

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemDespesaInstalacaoBem | Item Despesa Instalação Bem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Cód. Bem | S |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| NURATEIO | Nº Rateio | I |  |  | N |
| VLRICMSRATEIO | Valor ICMS Rateado | F |  |  | N |
| VLRRATEIO | Valor Rateio | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DespesaInstalacaoBem / [TCIDIB](TCIDIB.md) | ItemDespesaInstalacaoBem / [TCIDIBI](TCIDIBI.md) | NURATEIO->NURATEIO | I |
| ItemDespesaInstalacaoBem / [TCIDIBI](TCIDIBI.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| ItemDespesaInstalacaoBem / [TCIDIBI](TCIDIBI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
