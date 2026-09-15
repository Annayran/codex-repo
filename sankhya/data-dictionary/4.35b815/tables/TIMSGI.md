# TIMSGI — TABLE TIMSGI

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 12
- Instâncias DWF: 2
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLocacaoSeguroIncendio | Seguro Incêndio | S | N |
| TimUltimoLocacaoSeguroIncendio | Último Seguro Incêndio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| SGIAPOLICE | Apólice | S |  |  | N |
| SGICODIGO | Código | I |  |  | N |
| SGICONTRATOLOC | Cod. Contrato | I |  |  | N |
| SGICORRETORA | Corretora | I |  |  | N |
| SGIDTCANCELAMENTO | Dt. Cancelamento | D |  |  | N |
| SGIDTFIM | Dt. Término Seguro | D |  |  | N |
| SGIDTINICIO | Dt. Início Seguro | D |  |  | N |
| SGIFEITOADM | Feito com a administradora | S |  |  | N |
| SGIPERCCOMISSAO | Comisssão % | F |  |  | N |
| SGISEGURADORA | Seguradora | I |  |  | N |
| SGITIPOSEGURO | Tipo de seguro | S |  |  | N |
| SGIVLRSEGURO | Vlr. Seguro | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoSeguroIncendio / [TIMSGI](TIMSGI.md) | LOCCODIGO->SGICONTRATOLOC | I |
| TimLocacaoSeguroIncendio / [TIMSGI](TIMSGI.md) | Parceiro / [TGFPAR](TGFPAR.md) | SGISEGURADORA->CODPARC | I |
| TimLocacaoSeguroIncendio / [TIMSGI](TIMSGI.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | SGICORRETORA->CODPARC | I |
| TimLocacaoSeguroIncendio / [TIMSGI](TIMSGI.md) | TimLocacao / [TIMLOC](TIMLOC.md) | SGICONTRATOLOC->LOCCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
