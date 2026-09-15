# TCIBEM — Imobilizados

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 75
- Instâncias DWF: 1
- Relações DWF: 32
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Imobilizado | Imobilizados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQCOFINS | Alíquota COFINS | F |  |  | N |
| ALIQPIS | Alíquota PIS | F |  |  | N |
| CODBEM | Código do bem | S |  |  | N |
| CODBEMATUAL | Bem atual | S |  |  | N |
| CODBEMORIG | Bem origem | S |  |  | N |
| CODDEP | Departamento | I |  |  | S |
| CODEMP | Empresa | I |  |  | N |
| CODLOCAL | Local | I |  |  | S |
| CODPOSSUIDOR | Cód. possuidor | I |  |  | S |
| CODPROD | Produto | I |  |  | N |
| CODPRODORIG | Produto de origem desmembramento | I |  |  | N |
| CODUSUCOMPRA | Cód. Usuário Manut. Compra | I |  |  | N |
| CODUSUDESMEMBRAMENTO | Código Usuário Desmembramento | I |  |  | N |
| CREDPISCOFINSAQUIPAR | Parcelas Créd. PIS/COFINS sobre Aquisição | I |  |  | N |
| CSTCOFINS | Cód.Sit.Tributária COFINS | I |  |  | N |
| CSTPIS | Cód.Sit.Tributária PIS | I |  |  | N |
| DESCRABREV | Descrição abreviada | S |  |  | N |
| DESCRBEM | Descrição do bem | S |  |  | N |
| DESCRLOCAL | Descrição Local | S |  |  | S |
| DESCRUTILBEM | Descrição Utilização do Bem | S |  |  | N |
| DHALTERCOMPRA | Data Manut. Compra | H |  |  | N |
| DHDESMEMBRAMENTO | Data do desmembramento | D |  |  | N |
| DIGITADOCOMPRA | Digitado na compra | S |  |  | N |
| DTBAIXA | Data da baixa | D |  |  | N |
| DTCOMPRA | Data da compra | D |  |  | N |
| DTFIMAJ | Data final | D |  |  | N |
| DTFIMDEP | Data final | D |  |  | N |
| DTFIMREFCIAP | Data final | D |  |  | N |
| DTINICIOAJ | Data inicial | D |  |  | N |
| DTINICIODEP | Data inicial | D |  |  | N |
| DTINIREFCIAP | Data inicial | D |  |  | N |
| DTRETORNO | Data retorno | D |  |  | S |
| NFDEVVENDA | Nf. devolução venda | I |  |  | S |
| NFRETORNO | Nf. retorno | I |  |  | S |
| NFSAIDA | Nf. saída | I |  |  | S |
| NOMEPOSSUIDOR | Nome possuidor | S |  |  | S |
| NPARCPISCOF | Nro. Parcelas Apr. Cred. PIS/COFINS | I |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |
| NUMNOTA | Nro. Nota | I |  |  | N |
| NUMNOTABAIXA | Nf. baixa | I |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| NUNOTABAIXA | Nro. único baixa | I |  |  | N |
| NUNOTADEV | Nro. único nota de devolução | I |  |  | N |
| NUNOTADEVVENDA | Nro. único dev.venda de bens | I |  |  | N |
| NUNOTAORIGDESMEMB | Nro. Único nota origem desmembramento | I |  |  | N |
| NUNOTASAIDA | Nro. único nota de saída | I |  |  | N |
| PISCOFMP540 | Participa da MP540 PIS/COFINS | S |  |  | N |
| QTDMESESCIAP | Quantidade de meses | I |  |  | N |
| SALDO | Saldo | F |  |  | S |
| TEMAJ | Tem depreciação | S |  |  | N |
| TEMCREDPISCOFINSAQUI | Tem Créd. PIS/COFINS sobre Aquisição? | S |  |  | N |
| TEMCREDPISCOFINSDEPR | Tem Créd.PIS/COFINS sobre Dep.mensal | S |  |  | N |
| TEMDEPRECIACAO | Tem depreciação | S |  |  | N |
| TIPOENTCIAP | Tipo de Movimentação do Bem ou Componente | S |  |  | N |
| UTILIMOB | Util.Imobilizado | I |  |  | N |
| VALORPRESENTE | Valor Presente | F |  |  | N |
| VIDAUTIL | Vida Útil do Bem (meses) | I |  |  | N |
| VLRAQUISICAO | Valor aquisição | F |  |  | N |
| VLRCOFINSCOMPRA | Valor da COFINS na compra | F |  |  | N |
| VLRCOMPRAAQUISICAO | Valor total dos Bens | F |  |  | N |
| VLRCOMPRAAQUISICAOCALC | Valor total dos Bens | F |  |  | S |
| VLRDEP | Valor atual do bem | F |  |  | N |
| VLRDEPAJ | Valor atual do bem | F |  |  | N |
| VLRDEPORIG | Valor do Bem a ser Depreciado | F |  |  | N |
| VLREXCBASECRED | Vlr. Parcela Exclusão BC Crédito | F |  |  | N |
| VLRICMSCIAP | Valor do ICMS | F |  |  | N |
| VLRICMSCIAPCALC | Valor do ICMS | F |  |  | S |
| VLRICMSDIFCIAP | Valor crédito de ICMS dif.alíquota | F |  |  | N |
| VLRICMSFRTCIAP | Valor crédito ICMS sobre frete | F |  |  | N |
| VLRICMSSTCIAP | Valor crédito ICMS por subst.tributária | F |  |  | N |
| VLRICMSSTCIAPCALC | Valor crédito ICMS por subst.tributária | F |  |  | S |
| VLRPISCOMPRA | Valor do PIS na compra | F |  |  | N |
| VLRSALDO | Valor depreciado inicial | F |  |  | N |
| VLRSALDOAJ | Valor depreciado inicial | F |  |  | N |
| VLRTOTDESPESABEM | Valor total das despesas | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BemNotafiscal / [TCIIBE](TCIIBE.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Conta / [TCICTA](TCICTA.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| DemonstrativoTcidem / [TCIDEM](TCIDEM.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| EquipamentoApontamento / [TAPEQT](TAPEQT.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| EquipamentoProjeto / [TAPEPJ](TAPEPJ.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CabecalhoNotaBaixa / [TGFCAB](TGFCAB.md) | NUNOTABAIXA->NUNOTA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CabecalhoNotaDevVenda / [TGFCAB](TGFCAB.md) | NUNOTADEVVENDA->NUNOTA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CabecalhoNotaRetorno / [TGFCAB](TGFCAB.md) | NUNOTADEV->NUNOTA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CabecalhoNotaSaida / [TGFCAB](TGFCAB.md) | NUNOTASAIDA->NUNOTA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CredExtemp / [TCICEX](TCICEX.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | Departamento /  | CODDEP->CODDEP | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | DespesasVinculadasAoBem / [VGFCABBEM](VGFCABBEM.md) | CODBEM->CODBEM | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | Local / [TCILOC](TCILOC.md) | CODBEM->CODBEM; CODDEP->CODDEPTO; CODPROD->CODPROD | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | LocalEstoque / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | saldoImobilizado / [TCISAL](TCISAL.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | SaldoImobilizadoAtual / [TCISAL_ATUAL](TCISAL_ATUAL.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | TaxaDepreciacao / [TCITAX](TCITAX.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | TaxaDepreciacaoAjuste / [TCITAXAJ](TCITAXAJ.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| InventarioBem / [TCICTE](TCICTE.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM | I |
| ItemDespesaInstalacaoBem / [TCIDIBI](TCIDIBI.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Local / [TCILOC](TCILOC.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| MovimentacaoImobilizado / [TCIMOV](TCIMOV.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODPROD->CODPROD | I |
| saldoImobilizado / [TCISAL](TCISAL.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| TaxaDepreciacao / [TCITAX](TCITAX.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| TaxaDepreciacaoAjuste / [TCITAXAJ](TCITAXAJ.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
