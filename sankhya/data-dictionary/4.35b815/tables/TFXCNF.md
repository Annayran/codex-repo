# TFXCNF — FOX-TFXCNF

- Módulo técnico: [TFX](../modules/TFX.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CadastroContingenciaPdv | CadastroContingenciaPdv | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APLICARPARATODOS | Aplicar para Todos | S |  |  | N |
| CODCONTINGENCIA | Cód.Contingência | I |  |  | N |
| CODEMPRESA | Cód.Empresa | I |  |  | N |
| CODPDV | Cód.Pdv | I |  |  | N |
| DATAENTRADA | Dt.Entrada | H |  |  | N |
| DATASAIDA | Dt.Saída | H |  |  | N |
| HORAENTRADA | Hora Entrada | I |  |  | N |
| HORASAIDA | Hora Saída | I |  |  | N |
| JUSTIFICATIVA | Justificativa | S |  |  | N |
| NUVERSAO | NUVERSAO | I |  |  | N |
| TIPOCONTINGENCIA | Tipo de Contingência | I |  |  | N |
| TIPOENTRADA | Tipo de Entrada | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroContingenciaPdv / [TFXCNF](TFXCNF.md) | CadastroDePdv / [TFXPDV](TFXPDV.md) | CODPDV->CODPDV | I |
| CadastroContingenciaPdv / [TFXCNF](TFXCNF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPRESA->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
