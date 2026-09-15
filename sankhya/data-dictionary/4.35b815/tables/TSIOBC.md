# TSIOBC — Ocorrências Bancárias

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 30
- Instâncias DWF: 1
- Relações DWF: 12
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OcorrenciaBancariaRetorno | Ocorrências Bancárias | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALTERAR | Alterar | S |  |  | N |
| ATUALIZACAOREMESSA | Atualização Ocorrência Remessa | S |  |  | N |
| BAIXAPARCIAL | Baixa Parcial | S |  |  | N |
| BAIXAR | Baixar | S |  |  | N |
| CAMPOSAJUSTAR | Campos a serem ajustados | C |  |  | N |
| CODBCO | Banco | I |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODIGO | Codigo | I |  |  | N |
| CODMODELO | Código Modelo | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODOCOREMESSA | Cód.Ocorrência Remessa | S |  |  | N |
| CODOCORRENCIA | Cód.Ocorrência | S |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODTIPTIT | Tipo de título | I |  |  | N |
| CONCILIAR | Conciliar | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| ENVPIXEMAIL | Enviar E-mail PIX | S |  |  | N |
| INSERIR | Inserir | S |  |  | N |
| INTERROMPER | Interromper | S |  |  | N |
| NURFE | Número | I |  |  | N |
| REGISTRARLOG | Registrar Log | S |  |  | N |
| REGISTRARNOSSONUM | Registrar Nosso Número | S |  |  | N |
| REGISTRARQRCODE | Gravar QR Code Pix | S |  |  | N |
| REMOVERMONCOB | Remover título do monitoramento de cobrança | S |  |  | N |
| REPORTAR | Reportar | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TRATAROCORRENCIA | Tratar Ocorrência | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheRetornoMovimentoBco / [TSIIRT](TSIIRT.md) | OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | CODIGO->CODIGO; SEQUENCIA->SEQUENCIA | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | ModeloEmail / [TSIMEM](TSIMEM.md) | CODMODELO->CODMODELO | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Rejeicao / [TGFREJ](TGFREJ.md) | CODIGO->CODIGO; CODOCORRENCIA->CODOCORRENCIA; SEQUENCIA->SEQUENCIA | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
