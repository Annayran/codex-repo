# TGFHICM — Histórico de Alíquota ICMS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 124
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoAliquotaIcms | Histórico de Alíquota ICMS | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQADREMICMS | Alíquota ad rem ICMS | F |  |  | N |
| ALIQESTRANGEIRA | Alíquota de Origem Estrangeira | F |  |  | N |
| ALIQFRETE | Alíquota do Frete | F |  |  | N |
| ALIQICMSAT | Alíquota ICMS AT (%) | F |  |  | N |
| ALIQICMSATIMP | Alíquota ICMS AT produto importado (%) | F |  |  | N |
| ALIQICMSATINT | Alíquota de ICMS Interna (%) | F |  |  | N |
| ALIQICMSCARGTRIBRED | Alíquota da Carga Tributária Reduzida (ICMS) (%) | F |  |  | N |
| ALIQICMSEFET | Alíquota Interna (CST 60) | F |  |  | N |
| ALIQICMSESPST | Alíquota de ICMS Específico para o cálculo de ST | F |  |  | N |
| ALIQICMSLIMITECALCST | Alíq. Limite créd. ICMS p/ dedução no Valor ST | F |  |  | N |
| ALIQINTDEST | Alíq. Interna Destino | F |  |  | N |
| ALIQSTCARGTRIBRED | Alíquota da Carga Tributária Reduzida (ST) (%) | F |  |  | N |
| ALIQSUBTRIB | Aliquota Débito de ST | F |  |  | N |
| ALIQUFDEST | Alíquota Estado Destino | F |  |  | N |
| ALIQUOTA | Alíquota | F |  |  | N |
| BASEFCPINT | Base para cálculo do FCP Interno | S |  |  | N |
| BASESTRED | Base ST Redução | S |  |  | N |
| BASESTUFDEST | Base ST UF Destino por | S |  |  | N |
| BASICMMOD | Modalidade BC ICMS | I |  |  | N |
| BASICMSTMOD | Modalidade BC ICMS ST | I |  |  | N |
| CALCDIFICMSDENTRO | Cálculo Diferimento Por Dentro | S |  |  | N |
| CALCMVAAJUSTADO | Calcular MVA Ajustado | S |  |  | N |
| CALCREDPREICMCON | Calcular % de Crédito Presumido de ICMS ST Convênio ICMS 106/1996 | S |  |  | N |
| CALCREPREDDENTRO | Calculo Desoneração ICMS por dentro | S |  |  | N |
| CALCREPREDST | Cálculo de Desoneração ICMS/ST por Dentro, conforme Resolução SEFAZ nº 13/2019 | S |  |  | N |
| CALCSTCONSUTRI | Calcular ST Conforme Portaria Sutri860 | S |  |  | N |
| CALCSTDECPR | Calcular ST conforme Decreto 4.520/2020 - PR | S |  |  | N |
| CALCSTEXTRANOTA | Calcula ST extra nota com base em MVA na VENDA (Não utilizar em compras) | S |  |  | N |
| CALCSTLIVRECOM | Calcular ICMS ST Zona Franca/Ýrea de livre comércio | S |  |  | N |
| CALPERREDBASEICMS | Calcular % de Redução BC ICMS Lei 9.025/2020 | S |  |  | N |
| CALPERREDBASEST | Calcular % de Redução BC ICMS ST Lei 9.025/2020 | S |  |  | N |
| CARACTRIB | Característica tributável | S |  |  | N |
| CODANTECIPST | Cód.Antecipação ST | S |  |  | N |
| CODBEN | Código do Benefício | S |  |  | N |
| CODFORMBASDIFAL | Fórmula para Cálculo da Base do DIFAL e do FCP | I |  |  | N |
| CODFORMBASICM | Fórmula para cálculo da base de ICMS | I |  |  | N |
| CODFORMBCICMSAT | Fórmula para Cálculo da Base do ICMS AT | I |  |  | N |
| CODFORMCALCDIFAL | Fórmula para Cálculo do DIFAL | I |  |  | N |
| CODFORMVA | Fórmula para calcular MVA Ajustada | I |  |  | N |
| CODMOTDESONERAICMS | Cód.Mot.Desoneração ICMS | I |  |  | N |
| CODMOTDESONERAST | Cód. Mot. Desoneração do ICMS ST | I |  |  | N |
| CODOBSPADRAO | Observação Padrão | I |  |  | N |
| CODRESTRICAO | Cód. restrição | I |  |  | N |
| CODRESTRICAO2 | Cód. restrição 2 | I |  |  | N |
| CODTAB | Tabela | I |  |  | N |
| CODTABICMS | Tabela c/base p/ICMS | I |  |  | N |
| CODTABSTANT | Tabela c/Base ST Operação Anterior: | I |  |  | N |
| CODTABSTFARPOP | Tabela p/ Base ST - Farmácia Popular | I |  |  | N |
| CODTABSTPMPF | Tabela p/ Base ST - PMPF | I |  |  | N |
| CODTABSTUFDEST | Tabela c/Base ST em Favor da UF de Destino | I |  |  | N |
| CODTRIB | Tributação | I |  |  | N |
| CONSIDIPIVLROPPROP | Considerar IPI na comparação conforme portaria Sutri860 | S |  |  | N |
| CONSPMVABCICM | Considera Pauta + MVA para a base de cálculo do ICMS ST | S |  |  | N |
| CONVPRODUZ | Convênio Produzir | S |  |  | N |
| CREDPRESDECPR | % Crédito Presumido Decreto 4.520/2020 - PR | F |  |  | N |
| CSOSN | Cód. situação op. Simples nacional (CSON) | I |  |  | N |
| CUSCOMICMSBASEST | Usar último custo de entrada com ICMS como base p/ST | S |  |  | N |
| DESCONSIDERAREDBASE | Para redução da base de cálculo p/ produtos de origem estrangeira | S |  |  | N |
| DESCRRESTRICAO | Descrição Restrição | S |  |  | S |
| DESCRRESTRICAO2 | Descrição Restrição 2 | S |  |  | S |
| DESICMSSN | Desonera ICMS do Simples Nacional | S |  |  | N |
| DHALTER | Data Alteração | D |  |  | N |
| FINALIDADE | Destinação da mercadoria adquirida | S |  |  | N |
| FORMAREPICMS | FORMAREPICMS | S |  |  | N |
| FORMCALFCPDIFAL | Fórmula para o Cálculo do FCP do DIFAL | I |  |  | N |
| ICMSDIFPOSITIVA | Utilizar diferença positiva | S |  |  | N |
| ICMSUFORIGDIFEMIT | ICMS devido à UF de origem da prestação, quando diferente da UF do emitente | S |  |  | N |
| IDALIQ | Cód. Alíq. ICMS | I |  |  | N |
| MAIORBASEICMS | Usar a maior base para ICMS | S |  |  | N |
| MAIORBASEST | Maior Base ST | S |  |  | N |
| MARGLUCRO | Margem de Lucro | F |  |  | N |
| MARGLUCROEST | Margem Lucro (MVA) para origem estrangeira | F |  |  | N |
| MOTREDADREM | Motivo Redução do ad rem | I |  |  | N |
| MVAORIGINAL | Usar MVA original quando carga trib. inferior à alíq. interestadual? | S |  |  | N |
| MVASIMPLIFICADO | MVA Simplificado | S |  |  | N |
| MVASTUFDEST | Margem Lucro (MVA) UF Destino | F |  |  | N |
| NAOCONSIDMVA | Considera Pauta para a base de cálculo do ICMS ST | S |  |  | N |
| OUTORGA | Outorga | F |  |  | N |
| PAUTAVLRSTFIXO | Pauta de Valor Fixo do Item - CE | S |  |  | N |
| PERCALIQADREMICMS | % Redução Alíquota ad rem ICMS | F |  |  | N |
| PERCCARGATRIBMEDIA | Percentual Carga Trib. Média | F |  |  | N |
| PERCICMSFCP | % ICMS FCP | F |  |  | N |
| PERCICMSFCPINT | % ICMS FCP Interno | F |  |  | N |
| PERCICMSMONORET | % Destino ICMS Monofásico Retido | F |  |  | N |
| PERCMINSUBTRIB | Percentual Mínimo ST (RIOLOG) | F |  |  | N |
| PERCMULTCALCDP | % Diferença Positiva | F |  |  | N |
| PERCPMPF | Percentual PMPF | F |  |  | N |
| PERCREDBASEDEST | % Redução Base Destino | F |  |  | N |
| PERCREDBASEICMSEFET | Perc. Red. Base | F |  |  | N |
| PERCREDPR | Percentual do Crédito Presumido | F |  |  | N |
| PERCREDPREICMCON | Percentual de Crédito Presumido de ICMS ST Convênio ICMS 106/1996 | F |  |  | N |
| PERCSTFCPINT | % ST FCP Interno | F |  |  | N |
| PERCTRAVAMED | Porcentagem de trava para medicamentos | F |  |  | N |
| PERCUSAQUDECPE | Percentual/Coeficiente sobre Custo Aquisição - PE | F |  |  | N |
| PERCUSAQUDECPEEST | Percentual/Coeficiente sobre Custo Aquisição - PE - Origem Estrangeira | F |  |  | N |
| PROEMPREGO | Optante pelo Regime Especial do Pró-Emprego (Resolução n° 257/2008 - SEF) | S |  |  | N |
| REDBASE | Redução da Base | F |  |  | N |
| REDBASEESTRANGEIRA | Redução da Base para Origem Estrangeira | F |  |  | N |
| REDBASEFRETE | Redução da Base do Frete | F |  |  | N |
| REDBASEST | Redução de Base ST | F |  |  | N |
| REDICMS | Redução ICMS | S |  |  | N |
| REGRACALCBCICMSAT | Regra p/ cálculo da base ICMS AT | S |  |  | N |
| REGRADEDICMSAT | Deduzir do ICMS AT | S |  |  | N |
| REPDIFALFCP | ICMS DIFAL (Não Contribuinte) | S |  |  | N |
| REPICMS | Repassar ICMS | S |  |  | N |
| REPPERCBASEICMS | % da Base ICMS | F |  |  | N |
| REPREDBASE | Repassar Redução do Imposto para o cliente? | S |  |  | N |
| REPREDBASE2 | Redução da BASE | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| STCAT137SP | Calcular ST p/ Medicamentos - CAT-SP | S |  |  | N |
| STSUTRI4302014MG | Calcular ST conforme portaria Sutri 430/2014 - MG | S |  |  | N |
| TABCFOP | Tabela de CFOPs | S |  |  | N |
| TIPCALCDIFAL | Tipo de Cálculo do DIFAL | I |  |  | N |
| TIPCALCFCPSTESPEC | Tipo de Cálculo de FCP Específico | I |  |  | N |
| TIPCALCSTESPEC | Tipo de Cálculo de ST Específico | I |  |  | N |
| TIPCUSAQUDECPE | Tipo de custo Custo Decreto 38.296/PE | S |  |  | N |
| TIPRESTRICAO | Tipo de Restrição | S |  |  | N |
| TIPRESTRICAO2 | Tipo de Restrição 2 | S |  |  | N |
| UFDEST | UF destino | I |  |  | N |
| UFORIG | UF origem | I |  |  | N |
| USUARIO | Usuário | S |  |  | N |
| ZERAR | Usar Vlr.ICMS na ST, anotar Base/Vlr na Obs.item e zerar Base/Vlr do ICMS? | S |  |  | N |
| ZERARDIFALREM | Zerar Valor do ICMS DIFAL do Remetente | S |  |  | N |
| ZERARSTNEG | Zerar valor da substituição tributária quando negativo | S |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
