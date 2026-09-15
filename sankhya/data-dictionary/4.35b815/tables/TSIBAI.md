# TSIBAI — Bairros

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 5
- Relações DWF: 23
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Bairro | Bairro | S | S |
| BairroRecebimento | Bairro para recebimento | S | N |
| BairroTrabalho | Bairro Trabalho | S | N |
| TimBairroImovel | Bairros dos imóveis | S | N |
| TimBairroImovel2 | Bairros com Imóveis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUNUVERSAO | Atualizar versão | S |  |  | N |
| CODBAI | Código do Bairro | I |  |  | N |
| CODREG | Região | I |  |  | N |
| DESCRICAOCORREIO | Nome do Correio | S |  |  | N |
| DTALTER | Data de alteração | H |  |  | N |
| NOMEBAI | Nome | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgenciaBancaria / [TSIAGE](TSIAGE.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| Bairro / [TSIBAI](TSIBAI.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| CEP / [TSICEP](TSICEP.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAIENTREGA->CODBAI | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | BairroRecebimento / [TSIBAI](TSIBAI.md) | CODBAIRECEB->CODBAI | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | BairroTrabalho / [TSIBAI](TSIBAI.md) | CODBAITRAB->CODBAI | I |
| Contato / [TGFCTT](TGFCTT.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| Empreendimento / [TCCEMP](TCCEMP.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| Empresa / [TSIEMP](TSIEMP.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| Parceiro / [TGFPAR](TGFPAR.md) | BairroRecebimento / [TSIBAI](TSIBAI.md) | TIMBAIRROCOMPRA->CODBAI | I |
| Regiao / [TSIREG](TSIREG.md) | Bairro / [TSIBAI](TSIBAI.md) | CODREG->CODREG | I |
| TimCartorio / [TIMCAR](TIMCAR.md) | Bairro / [TSIBAI](TSIBAI.md) | CARBAIRRO->CODBAI | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | Bairro / [TSIBAI](TSIBAI.md) | EDGBAIRRO->CODBAI | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Bairro / [TSIBAI](TSIBAI.md) | EPRBAIRRO->CODBAI | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | Bairro / [TSIBAI](TSIBAI.md) | FACBAIRROIMOVEL->CODBAI | I |
| TimImovel / [TIMIMV](TIMIMV.md) | Bairro / [TSIBAI](TSIBAI.md) | IMVBAIRRO->CODBAI | I |
| TimLote / [TIMLTE](TIMLTE.md) | Bairro / [TSIBAI](TSIBAI.md) | LTEBAIRRO->CODBAI | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Bairro / [TSIBAI](TSIBAI.md) | LOTBAIRRO->CODBAI | I |
| TimProspect / [TIMPPR](TIMPPR.md) | Bairro / [TSIBAI](TSIBAI.md) | PPRBAIRRO->CODBAI | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimBairroImovel / [TSIBAI](TSIBAI.md) | FACBAIRROIMOVEL->CODBAI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
