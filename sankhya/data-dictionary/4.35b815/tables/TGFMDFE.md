# TGFMDFE — MDF-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 42
- Instâncias DWF: 1
- Relações DWF: 27
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Manifesto | Manifesto Fiscal | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CEPCAR | CEP | S |  |  | N |
| CEPDESCAR | CEP | S |  |  | N |
| CHAVEMDFE | Chave MDF-e | S |  |  | N |
| CODCIDENCERRAMENTO | Cód. Cidade Encerramento | I |  |  | N |
| CODPORTODESTINO | Cód. Porto Destino | S |  |  | N |
| CODPORTOEMBARQUE | Cód. Porto Embarque | S |  |  | N |
| CODPORTOTRANSBORDO | Cód. Porto Transbordo | S |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DESCPROD | Descrição do Produto | S |  |  | N |
| DHALTER | Data de Alteração | H |  |  | N |
| DHEMISS | Dt. e Hora da emissão | H |  |  | N |
| DHRECIBO | Dt. e Hora do Recbto. | H |  |  | N |
| EAN | EAN | S |  |  | N |
| INDCARREGPOST | Indicativo de Carregamento Posterior | S |  |  | N |
| IRINNAVIO | Nº IRIN | S |  |  | N |
| LATCAR | Latitude | F |  |  | N |
| LATDESCAR | Latitude | F |  |  | N |
| LONDESGCAR | Longitude | F |  |  | N |
| LONGCAR | Longitude | F |  |  | N |
| MMSI | Identidade do Serviço Móvel Marítimo | I |  |  | N |
| MUNFINAL | Municipio de Descarregamento | I |  |  | N |
| MUNINICIAL | Municipio de Coleta | I |  |  | N |
| NCM | NCM | S |  |  | N |
| NRORECIBO | Nro. Recibo | S |  |  | N |
| NULOTEMDFE | Nro. Lote | I |  |  | N |
| NUMALEATORIO | Nro. Aleatório | I |  |  | N |
| NUMMDFE | Nro. MDF-e | I |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| PESOBRUTOTOT | Peso Bruto Total | F |  |  | N |
| QRCODE | QRCODE | S |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |
| STATUSMDFE | Status MDF-e | S |  |  | N |
| TIPEMISS | Tipo de emissão | S |  |  | N |
| TIPONAVEGACAO | Tipo Navegação | I |  |  | N |
| TPCARGA | Tipo de Carga | S |  |  | N |
| UFFINAL | UF de Descarregamento | I |  |  | N |
| UFINICIAL | UF de Coleta | I |  |  | N |
| UNMED | Unidade de Medida | S |  |  | N |
| USAPESOBRUTONFE | Considerar Peso Bruto Total para NF-e | S |  |  | N |
| XML | XML | C |  |  | N |
| XMLENVCLI | XMLENVCLI | C |  |  | N |
| XMLPROTAUT | XMLPROTAUT | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoMDF / [TGFEMDF](TGFEMDF.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | Cidade / [TSICID](TSICID.md) | CODCIDENCERRAMENTO->CODCID | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | CidadeMunicipioFinal / [TSICID](TSICID.md) | MUNFINAL->CODCID | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | CidadeMunicipioInicial / [TSICID](TSICID.md) | MUNINICIAL->CODCID | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | CodigoIdentOperacaoTransporte / [TGFCIOT](TGFCIOT.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | ContratanteMDFE / [TGFCONTR](TGFCONTR.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | EmbarcacoesComboio / [TGFCOMB](TGFCOMB.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | EventoMDF / [TGFEMDF](TGFEMDF.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | LacreMDFE / [TGFMDFELAC](TGFMDFELAC.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | NotasMDF / [TGFNMDFE](TGFNMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | SeguroMDFE / [TGFMDFESEG](TGFMDFESEG.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | TerminaisCarregamento / [TGFTERCAR](TGFTERCAR.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | TerminaisDescarregamento / [TGFTERDESC](TGFTERDESC.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | UFsPercurso / [TGFUFP](TGFUFP.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | UFFINAL->CODUF | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | UnidadeFederativaOrigem / [TSIUFS](TSIUFS.md) | UFINICIAL->CODUF | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | UnidadesCargaVazia / [TGFUCV](TGFUCV.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | UnidadesTransporteVazia / [TGFUTV](TGFUTV.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | ValePedagioMDFE / [TGFMDFEVALP](TGFMDFEVALP.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | Viagem / [TGFVIAG](TGFVIAG.md) | NUVIAG->NUVIAG | I |
| ManifestoCompPgtoFrete / [TGFMDFCOMPGFRE](TGFMDFCOMPGFRE.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| ManifestoPrazoPgtoFrete / [TGFMDFPRAPGFRE](TGFMDFPRAPGFRE.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| NotasMDF / [TGFNMDFE](TGFNMDFE.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
