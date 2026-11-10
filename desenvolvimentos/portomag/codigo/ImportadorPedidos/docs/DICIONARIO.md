# Dicionario proposto — V1.1

Metadados novos. O XML fonte nao contem conexao, identificadores internos, acoes ou possiveis credenciais dos exports de referencia. O ZIP de homologacao recebe somente o exportInfo do export nativo 4.35b811 e permanece ignorado pelo Git.

O detalhe usa chave composta `IDPED + SEQEXT`; o mestre possui a ligacao para o detalhe. Esse desenho segue a topologia comprovada no export legado 4.36b126, mas a importacao na versao PortoMag continua pendente de homologacao.

Os campos tecnicos ID, IDPED, CHAVEIMP, SITUACAO, CODUSU, DHIMPORT, ARQUIVO, SHA256, PAGINAS, LAYOUT e paginas de evidencia sao definidos pela solucao. Os demais representam informacoes observadas no PDF.

## ZPM_PEDITE

| Campo | Tipo | Descricao |
|---|---|---|
| IDPED | I | Pedido recebido |
| SEQEXT | S | Sequencia no PDF |
| TIPO | S | Material ou servico |
| CODEXT | S | Codigo externo Claro |
| DESCRICAO | S | Descricao e execucao no PDF |
| CENTROEXT | S | Centro externo Claro |
| DTENTREGA | D | Entrega prevista |
| QTDE | F | Quantidade |
| UNEXT | S | Unidade no PDF |
| VLRUNIT | F | Preco unitario no PDF |
| ALIQICMS | F | Aliquota ICMS ou ISS informada |
| VLRICMS | F | Valor ICMS ou ISS informado |
| ALIQST | F | Aliquota ST informada |
| VLRST | F | Valor ST informado |
| ALIQIPI | F | Aliquota IPI informada |
| VLRIPI | F | Valor IPI informado |
| TOTALITEM | F | Total impresso do item |
| SITE | S | Identificadores de site no PDF |
| MUNICIPIO | S | Municipio de execucao |
| INICIO | S | Mes e ano inicial |
| TERMINO | S | Mes e ano final |
| PAGINA | I | Pagina do item |
| CNPJCLI | S | CNPJ de faturamento do item |
| FATURAMENTO | S | Bloco de faturamento do anexo |
| LOCENTREGA | S | Bloco de entrega do anexo |
| PAGANEXO | I | Pagina do anexo |

## ZPM_PEDREC

| Campo | Tipo | Descricao |
|---|---|---|
| ID | I | Identificador |
| NUMPED | S | Numero do pedido externo |
| DTEMISSAO | D | Data de emissao |
| CNPJFORN | S | CNPJ fornecedor no PDF |
| FORNECEDOR | S | Fornecedor no PDF |
| COMPRADOR | S | Comprador no PDF |
| CONDPAG | S | Condicao de pagamento no PDF |
| TOTALPDF | F | Total impresso no PDF |
| LAYOUT | S | Versao do leitor |
| CNPJCLI | S | CNPJ de faturamento |
| ARQUIVO | S | Nome do arquivo de origem |
| SHA256 | S | SHA256 informado pelo leitor |
| PAGINAS | I | Quantidade de paginas |
| CHAVEIMP | S | Chave unica de recebimento |
| SITUACAO | S | Situacao do recebimento |
| CODUSU | I | Usuario da importacao |
| DHIMPORT | D | Data e hora da importacao - validar precisao no ambiente |
