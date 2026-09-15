# TSIEND — Endereços

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 4
- Relações DWF: 18
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Endereco | Endereço | S | S |
| EnderecoRecebimento | Endereço de Recebimento | S | N |
| EnderecoTrabalho | Endereço de Trabalho | S | N |
| TimEndereco | Endereços com Imóveis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUNUVERSAO | Atualizar versão | S |  |  | N |
| CODEND | Cód.Endereço | I |  |  | N |
| CODLOGRADOURO | Cód. Logradouro p/ E-social | S |  |  | N |
| DESCRICAOCORREIO | Nome do correio | S |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| NOMEEND | Nome | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPOENDERECO | Endereço | S |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgenciaBancaria / [TSIAGE](TSIAGE.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| CEP / [TSICEP](TSICEP.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | Endereco / [TSIEND](TSIEND.md) | CODENDENTREGA->CODEND | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | EnderecoRecebimento / [TSIEND](TSIEND.md) | CODENDRECEB->CODEND | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | EnderecoTrabalho / [TSIEND](TSIEND.md) | CODENDTRAB->CODEND | I |
| Contato / [TGFCTT](TGFCTT.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| Empreendimento / [TCCEMP](TCCEMP.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| Empresa / [TSIEMP](TSIEMP.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| Endereco / [TSIEND](TSIEND.md) | Logradouro /  | CODLOGRADOURO->CODLOGRADOURO | I |
| Endereco / [TSIEND](TSIEND.md) | TipoEndereco / [TSITEND](TSITEND.md) | TIPO->TIPO | I |
| LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | Endereco / [TSIEND](TSIEND.md) | EDGCODEND->CODEND | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Endereco / [TSIEND](TSIEND.md) | EPRCODEND->CODEND | I |
| TimImovel / [TIMIMV](TIMIMV.md) | Endereco / [TSIEND](TSIEND.md) | IMVCODEND->CODEND | I |
| TimLote / [TIMLTE](TIMLTE.md) | Endereco / [TSIEND](TSIEND.md) | LTECODEND->CODEND | I |
| TimProspect / [TIMPPR](TIMPPR.md) | Endereco / [TSIEND](TSIEND.md) | PPRCODEND->CODEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
