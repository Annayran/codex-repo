# TGACUL — Culturas

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 22
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Cultura | Culturas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANOPREVCOLHEITA | Ano Previsto Colheita | I |  |  | N |
| AREAPLANTIO | Area de Plantio | I |  |  | N |
| CODAREA | Cód.Área | I |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODPARCAGRON | Cód. Parceiro Agronomo | I |  |  | N |
| CODPROD | Cód.Produto | I |  |  | N |
| CODPROJ | CODPROJ | I |  |  | N |
| CODSAF | Cód. Safra | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DTALTER | DTALTER | H |  |  | N |
| FINANCIADA | Financiada | S |  |  | N |
| MESPREVCOLHEITA | Mês Previsto Colheita | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PRODUTIVIDADE | Produtividade | F |  |  | N |
| SACASPREV | Sacas Previstas | F |  |  | N |
| SACASREAL | Sacas Real | F |  |  | N |
| SAFRA | Safra | S |  |  | N |
| SEQPLANEJ | Sequência Planejamento | I |  |  | N |
| STATUS | Status | S |  |  | N |
| VARIEDADE | Variedade | S |  |  | N |
| VLRSACA | Valor saca | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Cultura / [TGACUL](TGACUL.md) | AreaPlantio / [TGAARE](TGAARE.md) | CODAREA->CODAREA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
