# TIMCIM — Taxas

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimImovelConta | Taxas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CIMCODIGO | Código | I |  |  | N |
| CIMCONTRATO | Contrato | S |  |  | N |
| CIMDESCRICAO | Descrição | S |  |  | N |
| CIMDIAVENCTO | Dia de Vcto. | I |  |  | N |
| CIMDTULTAPRESENTACAO | Dt. Última Apresentação | D |  |  | N |
| CIMHISTORICO | Tipo de Detalhamento | I |  |  | N |
| CIMIMOVEL | Imóvel | I |  |  | N |
| CIMPARCDESP | Cód. Parc. Despesa | I |  |  | N |
| CIMRESPONSAVEL | Responsável quando ocupado | S |  |  | N |
| CIMTIPODECONTA | Tipo Conta | I |  |  | N |
| CIMVALORFIXO | Valor Fixo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimImovel / [TIMIMV](TIMIMV.md) | TimImovelConta / [TIMCIM](TIMCIM.md) | IMVCODIGO->CIMIMOVEL | I |
| TimImovelConta / [TIMCIM](TIMCIM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CIMPARCDESP->CODPARC | I |
| TimImovelConta / [TIMCIM](TIMCIM.md) | TimImovel / [TIMIMV](TIMIMV.md) | CIMIMOVEL->IMVCODIGO | I |
| TimImovelConta / [TIMCIM](TIMCIM.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | CIMHISTORICO->HCBCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
