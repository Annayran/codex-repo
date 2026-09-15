# TSICID — Cidades

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 63
- Instâncias DWF: 12
- Relações DWF: 62
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Cidade | Cidade | S | S |
| CidadeDestino | Cidade de Destino | S | N |
| CidadeFatoGerador | Município do Fato Gerador | S | N |
| CidadeMunicipioFinal | Municipio de Descarregamento | S | N |
| CidadeMunicipioInicial | Municipio de Coleta | S | N |
| CidadeOrigem | Cidade de Origem | S | N |
| CidadeRecebimento | Cidade para Recebimento | S | N |
| CidadeTrabalho | Cidade Trabalho | S | N |
| FuncionarioCidadeNascimento | Funcionário Cidade de Nascimento | S | N |
| FuncionarioNaturalidadeMae | Naturalidade da Mãe do Funcionário | S | N |
| NaturalidadeParceiro | Naturalidade do Parceiro | S | N |
| TimCidadeImovel | Cidades dos imóveis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACTCANEXNT | Permite cancelamento extemporâneo? | S |  |  | N |
| ATUNUVERSAO | Atualizar versão | S |  |  | N |
| CNAEFULLNFSE | Usar CNAE completo | S |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODMUNDMS | Cód. município DMS | I |  |  | N |
| CODMUNFIS | Mun. domicílio fiscal | I |  |  | N |
| CODMUNSIAFI | Cód. município SIAFI | I |  |  | N |
| CODPARCNFSE | Parceiro Padrão NFSe | I |  |  | N |
| CODREG | Região | I |  |  | N |
| DDD | DDD | S |  |  | N |
| DESCONVERSAONAT | Desabilitar conversão de natureza de operação? | S |  |  | N |
| DESCRICAOCORREIO | Nome do correio | S |  |  | N |
| DISTANCIA | Distância | I |  |  | N |
| DTALTER | Data de alteração | H |  |  | N |
| ENVCODIGONBSJSON | Enviar o Código NBS no JSON | S |  |  | N |
| ENVFPJSON | Enviar forma de pagamento no Json | S |  |  | N |
| ENVITENSSEPJSON | Enviar itens de NFS-e separados no Json | S |  |  | N |
| ENVMULEMAILJSON | Envia múltiplos e-mails no Json | S |  |  | N |
| ENVTAGDESCONJSON | Enviar tag descontoCondicionado no metadados do Json? | S |  |  | N |
| ENVTPSERVJSON | Enviar Tipo de Serviço no Json? | S |  |  | N |
| GERCNAEMULTJSON | Gerar CNAE para Múltiplos Itens no JSON | S |  |  | N |
| GERCODNATISSJSON | Gerar Código Natureza Operação ISS no Json | S |  |  | N |
| GERNUNFSEINFCPM | Gerar o nº da NFSe nas Inf. Complementares da Substituição | S |  |  | N |
| INFQTDVLRUNIJSON | Enviar quantidade e valor unitário no Json | S |  |  | N |
| JSONSEMALIDENMUN | Envia alíquota no Json apenas quando o ISS for devido a outro município | S |  |  | N |
| LATITUDE | Latitude | S |  |  | N |
| LINKAGUA | Link conta água | S |  |  | N |
| LINKENERGIA | Link conta energia | S |  |  | N |
| LINKIPTU | Link conta IPTU | S |  |  | N |
| LONGITUDE | Longitude | S |  |  | N |
| MASCARALC116 | Máscara LC116 | S |  |  | N |
| MASCARANBS | Máscara NBS | S |  |  | N |
| MAXNOTALOTENFSE | Qtd. máxima de notas em um lote NFS-e | I |  |  | N |
| METARREDVLRISS | Método de arredondamento do valor ISS | S |  |  | N |
| MOTCANCSUBNFSE | Motivo de cancelamento para substituição de NFS-e | S |  |  | N |
| NFSETEMPLATESUBST | Utiliza processo específico para substituição? | S |  |  | N |
| NOFORMATLC116 | Não formatar LC116 | S |  |  | N |
| NOINSCMUNPAR | Não enviar IM quando ISS incidir no município do parceiro tomador | S |  |  | N |
| NOMECID | Nome | S |  |  | N |
| OBSJSONENOTAS | Observação do Json | S |  |  | N |
| PERMCANCNFSESUBSTIT | Permite cancelamento de NFS-e substituta? | S |  |  | N |
| POPULACAO | População | I |  |  | N |
| QTDDIASSUB | Prazo para substituição de NFS-e | I |  |  | N |
| QTDMAXENVITENSJSON | Quantidade Máxima p/ enviar itens NFS-e no Json | I |  |  | N |
| QTDSUB | Quantidade de substituições permitidas | I |  |  | N |
| REGESPTRIB | Enviar o Regime Especial de Tributação no json? | S |  |  | N |
| REMZEROESQUERDLC116 | Remover zero a esquerda LC116 | S |  |  | N |
| SEQENTREGA | Sequência de entrega | I |  |  | N |
| TEMSUBSTITNFSE | Tem substituição NFS-e | S |  |  | N |
| TIMPARCPREFEITURA | Parceiro Prefeitura | I |  |  | N |
| TIPCANCNFSE | Tipo de cancelamento para NFS-e | S |  |  | N |
| TIPOCNAE | Tipo de envio do CNAE | I |  |  | N |
| TIPOFRETE | Tipo de frete | S |  |  | N |
| UF | Cód. UF | I |  |  | N |
| UFNOMECID | Cidade - UF | S |  |  | S |
| UTDEDALZERO | Utiliza Dedução de Base Alíquota Zero? | S |  |  | N |
| VENDAMIN | Venda Mínima | F |  |  | N |
| VLRFRETEKM | Valor de frete por KM | F |  |  | N |
| VLRFRETEMIN | Valor de frete mínimo | F |  |  | N |
| VLRFRETETON | Valor de frete por tonelada | F |  |  | N |
| VLRLIMCANCNFSE | Valor limite para cancelamento NFS-e | F |  |  | N |
| VLRTAXAENT | Valor taxa de entrada | F |  |  | N |
| VMINRETENCAOISS | Valor mínimo para retenção | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgenciaBancaria / [TSIAGE](TSIAGE.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| AliquotaISS / [TGFISS](TGFISS.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDFIMCTE->CODCID | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CidadeFatoGerador / [TSICID](TSICID.md) | CMUNFATGER->CODCID | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CidadeOrigem / [TSICID](TSICID.md) | CODCIDINICTE->CODCID | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CidadeTrabalho / [TSICID](TSICID.md) | CODCIDPREST->CODCID | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDFIMCTE->CODCID | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CidadeOrigem / [TSICID](TSICID.md) | CODCIDINICTE->CODCID | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CidadeTrabalho / [TSICID](TSICID.md) | CODCIDPREST->CODCID | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| CEP / [TSICEP](TSICEP.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Cidade / [TSICID](TSICID.md) | AliquotaISS / [TGFISS](TGFISS.md) | CODCID->CODCID | I |
| Cidade / [TSICID](TSICID.md) | CancelamentoNFSe / [TSICAN](TSICAN.md) | CODCID->CODCID | I |
| Cidade / [TSICID](TSICID.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCNFSE->CODPARC | I |
| Cidade / [TSICID](TSICID.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Cidade / [TSICID](TSICID.md) | SequenciaEntrega / [TGFRCD](TGFRCD.md) | CODCID->CODCID | I |
| Cidade / [TSICID](TSICID.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | UF->CODUF | I |
| CidadeRegiaoFrete / [TGFCFC](TGFCFC.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| ClienteCrediario / [TGFCREDCLI](TGFCREDCLI.md) | Cidade / [TSICID](TSICID.md) | NATURALIDADE->CODCID | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | Cidade / [TSICID](TSICID.md) | CODCIDENTREGA->CODCID | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | CidadeRecebimento / [TSICID](TSICID.md) | CODCIDRECEB->CODCID | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | CidadeTrabalho / [TSICID](TSICID.md) | CODCIDTRAB->CODCID | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | NaturalidadeParceiro / [TSICID](TSICID.md) | NATURALIDADE->CODCID | I |
| Contato / [TGFCTT](TGFCTT.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Contrato / [TCSCON](TCSCON.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| ControleMultimodal / [TGFCMN](TGFCMN.md) | Cidade / [TSICID](TSICID.md) | CODCIDINICIO->CODCID | I |
| ControleMultimodal / [TGFCMN](TGFCMN.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDTERMINO->CODCID | I |
| Empreendimento / [TCCEMP](TCCEMP.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Empresa / [TSIEMP](TSIEMP.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| EmpresaSoma / [TSIEMP](TSIEMP.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| EventoMDF / [TGFEMDF](TGFEMDF.md) | Cidade / [TSICID](TSICID.md) | CODCIDEVE->CODCID | I |
| Feriado / [TSIFER](TSIFER.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDFIMCTE->CODCID | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CidadeOrigem / [TSICID](TSICID.md) | CODCIDINICTE->CODCID | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| ImpostoNota / [TGFIMN](TGFIMN.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | Cidade / [TSICID](TSICID.md) | CODCIDENCERRAMENTO->CODCID | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | CidadeMunicipioFinal / [TSICID](TSICID.md) | MUNFINAL->CODCID | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | CidadeMunicipioInicial / [TSICID](TSICID.md) | MUNINICIAL->CODCID | I |
| MetaAtual / [TGMMET](TGMMET.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | Cidade / [TSICID](TSICID.md) | NATURALIDADE->CODCID | I |
| Regiao / [TSIREG](TSIREG.md) | Cidade / [TSICID](TSICID.md) | CODREG->CODREG | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDDEST->CODCID | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | CidadeOrigem / [TSICID](TSICID.md) | CODCIDORIG->CODCID | I |
| RotaCarga / [TSIDIS](TSIDIS.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDDEST->CODCID | I |
| RotaCarga / [TSIDIS](TSIDIS.md) | CidadeOrigem / [TSICID](TSICID.md) | CODCIDORIG->CODCID | I |
| SequenciaEntrega / [TGFRCD](TGFRCD.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| TimCartorio / [TIMCAR](TIMCAR.md) | Cidade / [TSICID](TSICID.md) | CARCIDADE->CODCID | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | Cidade / [TSICID](TSICID.md) | EDGCIDADE->CODCID | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Cidade / [TSICID](TSICID.md) | EPRCIDADE->CODCID | I |
| TimImovel / [TIMIMV](TIMIMV.md) | Cidade / [TSICID](TSICID.md) | IMVCIDADE->CODCID | I |
| TimLote / [TIMLTE](TIMLTE.md) | Cidade / [TSICID](TSICID.md) | LTECIDADE->CODCID | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Cidade / [TSICID](TSICID.md) | LOTCIDADE->CODCID | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | Cidade / [TSICID](TSICID.md) | PPVCIDADE->CODCID | I |
| TimProspect / [TIMPPR](TIMPPR.md) | Cidade / [TSICID](TSICID.md) | PPRCIDADE->CODCID | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
