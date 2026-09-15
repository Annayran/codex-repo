# TGFIXN — Importação de XML de Notas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 69
- Instâncias DWF: 1
- Relações DWF: 17
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImportacaoXMLNotas | Importação de XML de Notas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CFOPXML | CFOP's XML | S |  |  | N |
| CHAVEACESSO | Chave Acesso | S |  |  | N |
| CNPJDEST | Cnpj Destinatário | S |  |  | N |
| CNPJEXPED | Cnpj Expedidor | S |  |  | N |
| CNPJPARC | Cnpj Parceiro | S |  |  | N |
| CNPJRECEB | Cnpj Recebedor | S |  |  | N |
| CNPJREMET | Cnpj Remetente | S |  |  | N |
| CNPJTRANSP | Cnpj Transportadora | S |  |  | N |
| CODCENCUS | Código do Centro de Resultado | I |  |  | N |
| CODEMP | Código da Empresa | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPARCCOLETACTE | Parceiro Coleta CT-e | I |  |  | N |
| CODPARCDEST | Cód. Parceiro Destinatário | I |  |  | N |
| CODPARCENTREGACTE | Parceiro Entrega CT-e | I |  |  | N |
| CODPARCEXPED | Cód. Parceiro Expedidor | I |  |  | N |
| CODPARCRECEB | Cód. Parceiro Recebedor | I |  |  | N |
| CODPARCREMET | Cód. Parceiro Remetente | I |  |  | N |
| CODPARCTRANSP | Cód. Parceiro Transportadora | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODTIPVENDA | Tipo de Negociação | I |  |  | N |
| CODUSUIMP | Usuário Importação | I |  |  | N |
| CODUSUPROC | Usuário Processamento | I |  |  | N |
| CODVEND | Cód. Comprador | I |  |  | N |
| CONFIG | CONFIG | C |  |  | N |
| DETALHESIMPORTACAO | Detalhes | S |  |  | N |
| DHEMISS | Dh. Emissão | H |  |  | N |
| DHIMPORT | Dh. Importação | H |  |  | N |
| DHPROCAG | Dh. Proc Agenda | H |  |  | N |
| DHPROCESS | Dh. Processamento | H |  |  | N |
| DIASEMISSAOCALC | Dias Emissão Doc | S |  |  | S |
| DOCSREF | Docs Referenciados | C |  |  | N |
| DTAUTORIZACAO | Dh. Autorização | H |  |  | N |
| DTCONFIRMACAO | Dh. Confirmação | H |  |  | N |
| DTVENC | Dt. Vencimento | D |  |  | N |
| ENTSAINFE | Entrada/Saida NF-e | S |  |  | N |
| IMPORTADOMDE | Importado pelo DF-e | S |  |  | N |
| NATUREZAOPER | Descrição Nat. Operação | S |  |  | N |
| NOMEARQUIVO | Arquivo | S |  |  | N |
| NUARQUIVO | Código | I |  |  | N |
| NUFIN | Nro. Financeiro | I |  |  | N |
| NUMCOTACAO | Nro. da Cotação | I |  |  | N |
| NUMNOTA | Nro Nota | I |  |  | N |
| NUNOTA | Nro Único da Nota | I |  |  | N |
| OBSERVACAODFE | Justificativa DF-e | S |  |  | N |
| PAPELCTE | Papel no CT-e | S |  |  | N |
| PLACAVEIC | Placa Veículo | S |  |  | N |
| QTDPROCAG | Qtd. Proc Adenda | I |  |  | N |
| SERIEDOC | Série Doc | I |  |  | N |
| SITUACAOCTE | Situação CT-e | S |  |  | N |
| SITUACAOMDE | Situação  da manifestação | S |  |  | N |
| SITUACAONFE | Situação NF-e | S |  |  | N |
| STATUS | Status | I |  |  | N |
| STATUSWMS | Status WMS | I |  |  | S |
| TEMXML | Possui o XML | S |  |  | S |
| TIPIMPCTE | Tipo de Importação CT-e | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPOCTE | Tipo CTe | S |  |  | N |
| TIPOMANIFDFE | Tipo Manifestação DF-e | I |  |  | N |
| TIPONFE | Tipo NF-e | S |  |  | N |
| TOMADORCTE | Tomador CT-e | S |  |  | N |
| ULTEVEDFE | Último Evento DF-e | S |  |  | N |
| VLRNOTA | Valor da Nota | F |  |  | N |
| XML | XML | C |  |  | N |
| XNOMEDEST | Nome do Destinatário | S |  |  | N |
| XNOMEEMIT | Nome do Emitente | S |  |  | N |
| XNOMETOMA | Nome do Tomador | S |  |  | N |
| XNOMETRANSP | Nome da Transportadora | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroColeta / [TGFPAR](TGFPAR.md) | CODPARCCOLETACTE->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroDestinatario / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroEntrega / [TGFPAR](TGFPAR.md) | CODPARCENTREGACTE->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroExpedidor / [TGFPAR](TGFPAR.md) | CODPARCEXPED->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroRecebedor / [TGFPAR](TGFPAR.md) | CODPARCRECEB->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroRemetenteCte / [TGFPAR](TGFPAR.md) | CODPARCREMET->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroTransportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ProjecaoReceitaDespesa / [TGFPRJ](TGFPRJ.md) | CODPROJ->CODPROJ | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUIMP->CODUSU | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUPROC->CODUSU | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
