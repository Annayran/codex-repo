# TIMEPR — Empreendimentos

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 50
- Instâncias DWF: 1
- Relações DWF: 22
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimEmpreendimento | Mod. Imob. Empreendimentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| EPRANDARES | Andares | I |  |  | N |
| EPRAPTOPORANDAR | Apartamentos por andar | I |  |  | N |
| EPRAREA | Área | F |  |  | N |
| EPRBAIRRO | Bairro | I |  |  | N |
| EPRCEP | C.E.P. | S |  |  | N |
| EPRCIDADE | Cidade | I |  |  | N |
| EPRCODEND | Endereço | I |  |  | N |
| EPRCODIGO | Código | I |  |  | N |
| EPRCOMISSAOCAPTACAO | Comissão de captação | F |  |  | N |
| EPRCOMISSAOGERENTE | Comissão do gerente | F |  |  | N |
| EPRCOMISSAOSUPERVIS | Comissão do supervisor | F |  |  | N |
| EPRCOMISSAOVENDA | Comissão de venda | F |  |  | N |
| EPRCOMPLEMENTO | Complemento | S |  |  | N |
| EPRCONTACORRENTE | Conta corrente | I |  |  | N |
| EPRDATADEENTREGA | Dt. Entrega | D |  |  | N |
| EPRDATADELANCAMENTO | Dt. Lançamento | D |  |  | N |
| EPREDIFICIO | Edifício | I |  |  | N |
| EPRELEVADORES | Elevadores por torre | I |  |  | N |
| EPREMPREENDIMENTO | Empreendimento | S |  |  | N |
| EPRENDERECO | Endereço | S |  |  | S |
| EPRESTAGIO | Estágio | S |  |  | N |
| EPRGERENTEEMP | Gerente do empreendimento | I |  |  | N |
| EPRINDICEAPOSCHAVES | Índice após as chaves | I |  |  | N |
| EPRINDICEATECHAVES | Índice até as chaves | I |  |  | N |
| EPRIVV | Índice de Velocidade de Vendas | I |  |  | N |
| EPRJUROSAPOSCHAVES | Juros após as chaves | I |  |  | N |
| EPRJUROSATECHAVES | Juros até as chaves | I |  |  | N |
| EPRLANCAMENTO | Lançamento | S |  |  | N |
| EPRMAXPARCELAS | Máximo de parcelas | I |  |  | N |
| EPRNUFINMODAP | Financeiro Mod. AP | I |  |  | N |
| EPRNUFINMODCCF | Financeiro Mod. CCF | I |  |  | N |
| EPRNUFINMODCCV | Financeiro Mod. CCV | I |  |  | N |
| EPRNUFINMODCDU | Financeiro Mod. CDU | I |  |  | N |
| EPRNUFINMODCE | Financeiro Mod. CE | I |  |  | N |
| EPRNUFINMODFPPF | Financeiro Mod. FPPF | I |  |  | N |
| EPRNUFINMODFPPP | Financeiro Mod. FPPP | I |  |  | N |
| EPRNUMERO | Numero | S |  |  | N |
| EPRPAVIMENTOS | Pavimentos por torre | I |  |  | N |
| EPRPERIODICIDADEAPOS | Periodicidade após as chaves | I |  |  | N |
| EPRPERIODICIDADEATE | Periodicidade até as chaves | I |  |  | N |
| EPRPROJCORBANCARIO | Projeto Correspondente Bancário | I |  |  | N |
| EPRPROJETO | Projeto | I |  |  | N |
| EPRRESUMIDO | Nome resumido | S |  |  | N |
| EPRSUBSOLO | Sub-solo | I |  |  | N |
| EPRTIPO | Tipo do empreendimento | S |  |  | N |
| EPRTIPOCOMISSAO | Tipo de comissão | I |  |  | N |
| EPRTORRES | Torres | I |  |  | N |
| EPRUNIDADES | Unidades | I |  |  | N |
| EPRUSUARIO | Usuário Inclusão | I |  |  | N |
| EPRUSUARIOALT | Usuário Alteração | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | SACEMPREENDIMENTO->EPRCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | CLAEMPREENDIMENTO->EPRCODIGO | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | EDGEMPREENDIMENTO->EPRCODIGO | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Bairro / [TSIBAI](TSIBAI.md) | EPRBAIRRO->CODBAI | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Cidade / [TSICID](TSICID.md) | EPRCIDADE->CODCID | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | ContaBancaria / [TSICTA](TSICTA.md) | EPRCONTACORRENTE->CODCTABCOINT | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Endereco / [TSIEND](TSIEND.md) | EPRCODEND->CODEND | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Moeda / [TSIMOE](TSIMOE.md) | EPRINDICEAPOSCHAVES->CODMOEDA | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Moeda2 / [TSIMOE](TSIMOE.md) | EPRINDICEATECHAVES->CODMOEDA | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Projeto / [TCSPRJ](TCSPRJ.md) | EPRPROJCORBANCARIO->CODPROJ | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Projeto2 / [TCSPRJ](TCSPRJ.md) | EPRPROJETO->CODPROJ | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | TimCorretor / [TIMCOR](TIMCOR.md) | EPRGERENTEEMP->CORCODIGO | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | TimCustosCond / [TIMECR](TIMECR.md) | EPRCODIGO->ECREMPREENDIMENTO | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | EPREDIFICIO->EDGCODIGO | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Usuario / [TSIUSU](TSIUSU.md) | EPRUSUARIOALT->CODUSU | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | EPRUSUARIO->CODUSU | I |
| TimFechamento / [TIMFEC](TIMFEC.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | FECEMPREENDIMENTO->EPRCODIGO | I |
| TimFechamentoAluguel / [TIMFEA](TIMFEA.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | FEAEMPREENDIMENTO->EPRCODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | FACEMPREENDIMENTO->EPRCODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | IMVEMPREENDIMENTO->EPRCODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | FACEMPREENDIMENTO->EPRCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | VDOEMPREENDIMENTO->EPRCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
