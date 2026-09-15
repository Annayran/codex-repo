# TGFODP — Ordem de Despacho

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OrdemDespacho | Ordem de Despacho | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODPARCTRANSP | Transportadora | I |  |  | N |
| DTINCLUSAO | Dt. inclusão | H |  |  | N |
| NUODP | Nro. único | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | OrdemDespacho / [TGFODP](TGFODP.md) | NUODP->NUODP | I |
| OrdemDespacho / [TGFODP](TGFODP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUODP->NUODP | I |
| OrdemDespacho / [TGFODP](TGFODP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| OrdemDespacho / [TGFODP](TGFODP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
