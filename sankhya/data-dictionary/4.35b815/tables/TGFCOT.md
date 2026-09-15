# TGFCOT — Cabeçalho Cotação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 31
- Instâncias DWF: 1
- Relações DWF: 13
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoCotacao | Cabeçalho Cotação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Centro de Resultado | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODMOTCAN | Código Motivo de Cancelamento | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODTIPVENDA | Forma de Pagamento | I |  |  | N |
| CODUSU | Usuário Lançamento | I |  |  | N |
| CODUSUREQ | Usuário Comprador | I |  |  | N |
| CODUSURESP | Usuário Responsável | I |  |  | N |
| DHFINAL | Data final da cotação | D |  |  | N |
| DHINIC | Data inicial da cotação | D |  |  | N |
| DTALTER | Data de alteração | H |  |  | N |
| GERPEDREAL | Gera Pedido Real | S |  |  | N |
| MODFRETE | Modalidade de Frete | S |  |  | N |
| NUMCOTACAO | Nro. da Cotação | I |  |  | N |
| NUMNOTAORIG | Nro do Pedido de Origem | I |  |  | N |
| NUNOTAORIG | Nro Único do Pedido Origem | I |  |  | N |
| OBSERVACAO | Observações | S |  |  | N |
| OBSMOTCANC | Observação Motivo de Cancelamento | S |  |  | N |
| PESOCONDPAG | Condições de pagamento | I |  |  | N |
| PESOCONFIABFORN | Confiabilidade do fornecedor | I |  |  | N |
| PESOGARANTIA | Garantia | I |  |  | N |
| PESOPRAZOENTREG | Prazo de entrega | I |  |  | N |
| PESOPRAZOMED | Prazo Médio | I |  |  | N |
| PESOPRECO | Preço | I |  |  | N |
| PESOQUALATEND | Qualidade Atendimento | I |  |  | N |
| PESOQUALPROD | Qualidade do Produto | I |  |  | N |
| PESOTAXAJURO | Taxa de Juro | I |  |  | N |
| PRAZOENTREGA | Prazo de Entrega | I |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| VALPROPOSTA | Validade Proposta | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUMCOTACAO->NUMCOTACAO | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | ItemCotacao / [TGFITC](TGFITC.md) | NUMCOTACAO->NUMCOTACAO | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | MotivoCancCotacao / [TGFMTC](TGFMTC.md) | CODMOTCAN->CODIGO | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | UsuarioComprador / [TSIUSU](TSIUSU.md) | CODUSUREQ->CODUSU | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | UsuarioResponsavel / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| ItemCotacao / [TGFITC](TGFITC.md) | CabecalhoCotacao / [TGFCOT](TGFCOT.md) | NUMCOTACAO->NUMCOTACAO | I |
| ItemCotacaoParceiro / [TGFITC_COT](TGFITC_COT.md) | CabecalhoCotacao / [TGFCOT](TGFCOT.md) | NUMCOTACAO->NUMCOTACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
