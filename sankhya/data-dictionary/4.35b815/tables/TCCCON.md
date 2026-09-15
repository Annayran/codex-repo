# TCCCON — Contrato Civil

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 16
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContratoCivil | Contrato civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODCC | Nro. Contrato | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODEMPREE | Empreendimento | I |  |  | N |
| CODFORM | Fórmula Padrão | I |  |  | N |
| CODMOEDA | Moeda do Contrato | I |  |  | N |
| CODPARCFIN | Cód. Parceiro Responsável Financeiro | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODUSUVAR | Usuário Responsável | I |  |  | N |
| DTASSINATURA | Data Assinatura | D |  |  | N |
| DTCONTRATO | Data Contrato | D |  |  | N |
| DTPREVISAO | Dt Previsão de Entrega das Chaves | D |  |  | N |
| DTULTVAR | Última Variação | D |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| TPCONTRATO | Tipo Contrato | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoCivil / [TCCCON](TCCCON.md) | AcabamentoCivil / [TCCACA](TCCACA.md) | CODCC->CODCC | I |
| ContratoCivil / [TCCCON](TCCCON.md) | CompradoresCivil / [TCCCOM](TCCCOM.md) | CODCC->CODCC | I |
| ContratoCivil / [TCCCON](TCCCON.md) | CorretorCivil / [TCCCOR](TCCCOR.md) | CODCC->CODCC | I |
| ContratoCivil / [TCCCON](TCCCON.md) | Empreendimento / [TCCEMP](TCCEMP.md) | CODEMPREE->CODEMPREE | I |
| ContratoCivil / [TCCCON](TCCCON.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContratoCivil / [TCCCON](TCCCON.md) | FormulaCivil / [TCCFOR](TCCFOR.md) | CODFORM->CODFORM | I |
| ContratoCivil / [TCCCON](TCCCON.md) | ItemContratoCivil / [TCCITE](TCCITE.md) | CODCC->CODCC | I |
| ContratoCivil / [TCCCON](TCCCON.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| ContratoCivil / [TCCCON](TCCCON.md) | NegociacaoCivil / [TCCNEG](TCCNEG.md) | CODCC->CODCC | I |
| ContratoCivil / [TCCCON](TCCCON.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFIN->CODPARC | I |
| ContratoCivil / [TCCCON](TCCCON.md) | ProtocoloCivil / [TCCPRO](TCCPRO.md) | CODCC->CODCC | I |
| ContratoCivil / [TCCCON](TCCCON.md) | TestemunhaCivil / [TCCTES](TCCTES.md) | CODCC->CODCC | I |
| ContratoCivil / [TCCCON](TCCCON.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| ContratoCivil / [TCCCON](TCCCON.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUVAR->CODUSU | I |
| HistoricoFormula / [TCCHTF](TCCHTF.md) | ContratoCivil / [TCCCON](TCCCON.md) | CODCC->CODCC | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | ContratoCivil / [TCCCON](TCCCON.md) | CODCC->CODCC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
