# TCSSPT — Scripts

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Script | Scripts | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSCRIPT | Script | I |  |  | N |
| CONTEUDO | Conteúdo | S |  |  | N |
| DECRSCRIPT | Decrição | S |  |  | N |
| DHCRIACAO | Data e Hota de Criação | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleAlteracaoBanco / [TCSALT](TCSALT.md) | Script / [TCSSPT](TCSSPT.md) | CODSCRIPT->CODSCRIPT | I |
| Script / [TCSSPT](TCSSPT.md) | ControleAlteracaoBanco / [TCSALT](TCSALT.md) | CODSCRIPT->CODSCRIPT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
