# TIMDLV — TimRecisao

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 49
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRescisao | Rescisão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DLVAPLICARFRUICAO | Rescisão Conforme Lei do Distrato (nº 13.786/2018) | S |  |  | N |
| DLVAPROVEITHASALDO | Aproveita Saldo | S |  |  | N |
| DLVBENFEITORIAS | Descr. Benfeitorias | S |  |  | N |
| DLVCODIGO | Código | I |  |  | N |
| DLVCONTRATO | Contrato | I |  |  | N |
| DLVCORRECAO | Vlr. Correção | F |  |  | N |
| DLVDATA | Data | D |  |  | N |
| DLVDESCAP | Vlr. Descaptalização | F |  |  | N |
| DLVDESCRICAO | Descrição | S |  |  | N |
| DLVDHALTERACAO | Dh. Alteração | H |  |  | N |
| DLVDHEFETIVADA | Dh. Efetivada | H |  |  | N |
| DLVDHINCLUSAO | Dh. Inclusão | H |  |  | N |
| DLVESTAGIO | Estágio | S |  |  | N |
| DLVGEROUPARCS | Gerou Parcelas | S |  |  | N |
| DLVLRPARCELA | Vlr. Parcela | F |  |  | N |
| DLVNUACERTO | Nro. Acerto | I |  |  | N |
| DLVNURENEG | Nro. Negociação. Skw | I |  |  | N |
| DLVPARCELASEMATRASO | Qtd. Parc's. Atraso | I |  |  | N |
| DLVPRIMEIRAPARCELA | Dt. Primeira Parcela | D |  |  | N |
| DLVPROVISAO | Estágio Devolução | S |  |  | N |
| DLVQTDPARCABERTO | Qtd. Parc's. Aberto | I |  |  | N |
| DLVQTDPARCELAS | Qtd. Parcelas | I |  |  | N |
| DLVQTDPARPAGAS | Qtd. Parc's. Pagas | I |  |  | N |
| DLVSMJURMUL | Vlr. Soma Juros Multa | F |  |  | N |
| DLVTIPORESCISAO | Tipo | S |  |  | N |
| DLVTXADMRESCISAO | Vlr. Tx. Adm.  - Lei Distrato | F |  |  | N |
| DLVTXRECISAO | Vlr. Taxa Rescisão (-) | F |  |  | N |
| DLVUSUALTERACAO | Usuário. Alteração | I |  |  | N |
| DLVUSUARIO | Usuário Efetivou | I |  |  | N |
| DLVUSUINCLUSAO | Usuário Inclusão | I |  |  | N |
| DLVVALORAGUA | Vlr. Água (-) | F |  |  | N |
| DLVVALORATUALIZACAO | Vlr. Atualização | F |  |  | N |
| DLVVALORBENFEITORIA | Vlr. Benfeitorias (+) | F |  |  | N |
| DLVVALORCEDITAL | Vlr. Custas  Edital (-) | F |  |  | N |
| DLVVALORCEXTRA | Vlr. Custas Extra Jud (-) | F |  |  | N |
| DLVVALORCJUSTICA | Vlr. Custas Justiça Comum (-) | F |  |  | N |
| DLVVALORDESCONTADO | Vlr. Descontado | F |  |  | N |
| DLVVALOREMABERTO | Vlr. Em Aberto | F |  |  | N |
| DLVVALOREMATRASO | Vlr. Em Atraso | F |  |  | N |
| DLVVALORENERGIA | Vlr. Energia (-) | F |  |  | N |
| DLVVALORHONORARIOS | Vlr. Honorários (-) | F |  |  | N |
| DLVVALORIMPOSTOS | Vlr. Impostos (-) | F |  |  | N |
| DLVVALOROUTROS | Vlr. Outros Gastos (-) | F |  |  | N |
| DLVVALORPAGO | Vlr. Pago | F |  |  | N |
| DLVVALORTOTAL | Vlr. Total | F |  |  | N |
| DLVVLRPERCDESCONTADO | Vlr. Descontado % | F |  |  | N |
| DLVVLRRESTCONTRATO | Vlr. Contrato Atualizado | F |  |  | N |
| DLVVLRSALDO | Vlr. Saldo | F |  |  | S |
| DLVVLRTXFRUICAO | Vlr. Tx. Fruição - Lei Distrato | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimRescisao / [TIMDLV](TIMDLV.md) | TIMCONTRATOLTV->DLVCONTRATO; TIMRESCISAOLTV->DLVCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimRescisao / [TIMDLV](TIMDLV.md) | LTVCODIGO->DLVCONTRATO | I |
| TimRescisao / [TIMDLV](TIMDLV.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | DLVUSUALTERACAO->CODUSU | I |
| TimRescisao / [TIMDLV](TIMDLV.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | DLVUSUINCLUSAO->CODUSU | I |
| TimRescisao / [TIMDLV](TIMDLV.md) | Usuario / [TSIUSU](TSIUSU.md) | DLVUSUARIO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
