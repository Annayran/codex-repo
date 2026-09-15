# TIMEDG — Edifícios e Galerias

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 36
- Instâncias DWF: 1
- Relações DWF: 13
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimEdificioGaleria | Edifícios e Galerias | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| EDGADMEMAIL | E-Mail | S |  |  | N |
| EDGADMFONE | Telefone | S |  |  | N |
| EDGADMNOME | Nome | S |  |  | N |
| EDGANDARES | Andares | I |  |  | N |
| EDGANOCONSTRUCAO | Ano Construção | I |  |  | N |
| EDGAPTOPORANDAR | Aptos por Andar | I |  |  | N |
| EDGBAIRRO | Bairro | I |  |  | N |
| EDGCEP | CEP | S |  |  | N |
| EDGCIDADE | Cidade | I |  |  | N |
| EDGCODEND | Endereço | I |  |  | N |
| EDGCODIGO | Código | I |  |  | N |
| EDGCODPARCEIRO | Parceiro | I |  |  | N |
| EDGCOMPL | Complemento | S |  |  | N |
| EDGCONSTRUTORA | Construtora | I |  |  | N |
| EDGCTBEMAIL | E-mail | S |  |  | N |
| EDGCTBFONE | Telefone | S |  |  | N |
| EDGCTBNOME | Nome | S |  |  | N |
| EDGEDIFICIO | Edifício / Galeria | S |  |  | N |
| EDGELEVADORES | Elevadores | I |  |  | N |
| EDGEMAIL | E-Mail | S |  |  | N |
| EDGEMPREENDIMENTO | Empreendimento | I |  |  | N |
| EDGENDERECO | Endereço | S |  |  | N |
| EDGENDFINAL | Endereço | S |  |  | S |
| EDGGASEMAIL | E-Mail | S |  |  | N |
| EDGGASFONE | Telefone | S |  |  | N |
| EDGGASNOME | Fornecedor | S |  |  | N |
| EDGIFEAREACONSTRUIDA | Área Construída Total (m²) | F |  |  | S |
| EDGNUMERO | Numero | I |  |  | N |
| EDGPAVIMENTOS | Pavimentos | I |  |  | N |
| EDGPODEANIMAIS | Pode animais | S |  |  | N |
| EDGRATEARPARA | Ratear para | I |  |  | N |
| EDGSEGINCTPCOB | Tipo de cobrança do Seguro Incêndio | I |  |  | N |
| EDGSINDICO | Nome | S |  |  | N |
| EDGSUBSOLO | Sub-solos | I |  |  | N |
| EDGTELEFONES | Telefone | S |  |  | N |
| EDGTORRES | Torres | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | IEOEDIFICIO->EDGCODIGO | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | Bairro / [TSIBAI](TSIBAI.md) | EDGBAIRRO->CODBAI | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | Cidade / [TSICID](TSICID.md) | EDGCIDADE->CODCID | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | Endereco / [TSIEND](TSIEND.md) | EDGCODEND->CODEND | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | Parceiro / [TGFPAR](TGFPAR.md) | EDGCONSTRUTORA->CODPARC | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | EDGCODIGO->IEOEDIFICIO | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | EDGEMPREENDIMENTO->EPRCODIGO | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | TimImovel / [TIMIMV](TIMIMV.md) | EDGCODIGO->IMVEDIFICIO | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | EPREDIFICIO->EDGCODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | FACEDIFICIO->EDGCODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | IMVEDIFICIO->EDGCODIGO | I |
| TimIptu / [TIMIPT](TIMIPT.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | IPTEDIFICIO->EDGCODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | FACEDIFICIO->EDGCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
