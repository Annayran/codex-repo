# VIMDER — VIMDER

- Módulo técnico: [VIM](../modules/VIM.md)
- Campos: 27
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimDespesaSinteticaLoc | Despesas Locação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHBAIXA | Data Baixa | D |  |  | N |
| DTLCODIGO | Código | I |  |  | N |
| DTLCODIPTU | Cod. IPTU | I |  |  | N |
| DTLCOMISSAOIPTU | Comissão Iptu | F |  |  | N |
| DTLCOMPLEMENTO | Complemento | S |  |  | N |
| DTLDHGERREPASSE | Dh. Ger. Repasse | H |  |  | N |
| DTLDTFIM | Dt. Fim Período | D |  |  | N |
| DTLDTINIC | Dt. Início Período | D |  |  | N |
| DTLGARANTIDO | Garantido | S |  |  | N |
| DTLHISTORICO | Tipo do Detalhamento | I |  |  | N |
| DTLPARCELA | ID Parcela | I |  |  | N |
| DTLPARCELAIPTU | Cod. Parcela IPTU | I |  |  | N |
| DTVENC | Dt. Vencimento | D |  |  | N |
| DTVENCINIC | Dt. Venc. Inicial | H |  |  | N |
| NUFIN | Nro Único | I |  |  | N |
| RECDESP | Receita/Despesa | I |  |  | N |
| TIMORIGEM | Tipo Parcela | S |  |  | N |
| TIMPARCELA | Parcela | I |  |  | N |
| TIPJURO | Tipo Juro | S |  |  | N |
| TIPMULTA | Tipo Multa | S |  |  | N |
| VLRDESC | Vlr Desconto | F |  |  | N |
| VLRDESCEMBUT | Vlr Desc. Embutido | F |  |  | N |
| VLRDESDOB | Vlr do Desdobramento | F |  |  | N |
| VLRJURO | Vlr Juros | F |  |  | N |
| VLRJUROEMBUT | Vlr Juros Embutidos | F |  |  | N |
| VLRMULTA | Vlr Multa | F |  |  | N |
| VLRMULTAEMBUT | Vlr Multa Embutida | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimDespesaSinteticaLoc / [VIMDER](VIMDER.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | DTLHISTORICO->HCBCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
