# TGMTRA — Transferências

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 47
- Instâncias DWF: 1
- Relações DWF: 22
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Transferencia | Transferências | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODCENCUSLIB | CR Liberador | I |  |  | N |
| CODCID | Cidade | I |  |  | N |
| CODCTACTB | Conta Bancária | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODGER | Gerente | I |  |  | N |
| CODGRUPONAT | Grupo da Natureza | I |  |  | N |
| CODGRUPOPROD | Grupo de Produtos | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODMETA | Meta | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODPAIS | País | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODREG | Região | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CODTIPPARC | Tipo de Parceiro | I |  |  | N |
| CODUF | Unidade Federativa | I |  |  | N |
| CODUSU | Usuário Solicitante | I |  |  | N |
| CODUSULIB | Usuário Liberação | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DIA | Dia | I |  |  | N |
| DTALTER | Data da Alteração | H |  |  | N |
| DTREF | Período | D |  |  | N |
| EXCLUIDO | Excluido | I |  |  | S |
| GRAU | Grau | I |  |  | N |
| MARCA | Marca | S |  |  | N |
| NUAUTINV | Nro Autorização | I |  |  | N |
| NUFIN | Nro Único Financeiro | I |  |  | N |
| NUMTRANSF | Número da Transferência | I |  |  | N |
| NUNOTA | Nro Único Nota | I |  |  | N |
| NUTVO | Transferência de Verba | I |  |  | N |
| OBSERVACAO | Observação Solicitante | S |  |  | N |
| OBSLIB | Observação Liberador #4 | S |  |  | N |
| OBSLIB1 | Observação Liberador #1 | S |  |  | N |
| OBSLIB2 | Observação Liberador #2 | S |  |  | N |
| OBSLIB3 | Observação Liberador #3 | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SEQUENCIAITE | Sequência Item | I |  |  | N |
| SINAL | Sinal | I |  |  | N |
| STATUS | Status | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VALOR | Valor Solicitado | F |  |  | N |
| VALOR_ORIG | Valor Origem | F |  |  | N |
| VLRDESDOB | Vlr do Desdobramento | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Transferencia / [TGMTRA](TGMTRA.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Transferencia / [TGMTRA](TGMTRA.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| Transferencia / [TGMTRA](TGMTRA.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTACTB->CODCTACTB | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Gerente / [TGFVEN](TGFVEN.md) | CODGER->CODVEND | I |
| Transferencia / [TGMTRA](TGMTRA.md) | GrupoNaturezas / [TGFGNT](TGFGNT.md) | CODGRUPONAT->CODGRUPONAT | I |
| Transferencia / [TGMTRA](TGMTRA.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| Transferencia / [TGMTRA](TGMTRA.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Transferencia / [TGMTRA](TGMTRA.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| Transferencia / [TGMTRA](TGMTRA.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| TransferenciaVerbaOrcamento / [TGMTVO](TGMTVO.md) | Transferencia / [TGMTRA](TGMTRA.md) | NUTVO->NUTVO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
