# TCSALT — Controle de Alterações nos bancos

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleAlteracaoBanco | Controle de Alterações nos bancos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODSCRIPT | Script | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Data da Alteração | H |  |  | N |
| STATUS | Status da Alteração | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleAlteracaoBanco / [TCSALT](TCSALT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ControleAlteracaoBanco / [TCSALT](TCSALT.md) | Script / [TCSSPT](TCSSPT.md) | CODSCRIPT->CODSCRIPT | I |
| ControleAlteracaoBanco / [TCSALT](TCSALT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Script / [TCSSPT](TCSSPT.md) | ControleAlteracaoBanco / [TCSALT](TCSALT.md) | CODSCRIPT->CODSCRIPT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
