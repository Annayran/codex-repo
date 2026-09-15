# TGMCFG — Configurações de Metas

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 50
- Instâncias DWF: 1
- Relações DWF: 18
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoMeta | Configurações de Metas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APRESCOD | Ver código com a descrição no Planejamento | S |  |  | N |
| ARQIMPORTPO | Arq. de Importação de Planej. Orçamentário | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CAMPOSUBSTITUTO | Campos Substitutos | S |  |  | S |
| CENTRORESULTADO | Centro de resultado | I |  |  | N |
| CIDADE | Cidade | I |  |  | N |
| CODGER | Gerente | I |  |  | N |
| CODMETA | Código | I |  |  | N |
| CODMETAANT | Cód. Meta anterior | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODUSUAPROV | Cód. Usuário Aprovação | I |  |  | N |
| CONTROLE | Controle | I |  |  | N |
| DATA | Período | I |  |  | N |
| DESCRMETA | Descrição da Meta | S |  |  | N |
| DTAPROV | Data da Aprovação | H |  |  | N |
| DTINICORC | Data Início | I |  |  | N |
| EMPRESA | Empresa | I |  |  | N |
| EXECUTANTE | Executante | I |  |  | N |
| GRUPO | Grupo de Produtos | I |  |  | N |
| GRUPONAT | Grupo Natureza | I |  |  | N |
| IGNORCNAOPREV | Ignora orçamento não previsto | S |  |  | N |
| INCCOMP | Considerar Componentes | S |  |  | N |
| INVESTIMENTO | Investimento | S |  |  | N |
| LOCAL | Local | I |  |  | N |
| MARCA | Marca | I |  |  | N |
| METAPORQTD | Meta por quantidade | S |  |  | N |
| NATUREZA | Natureza | I |  |  | N |
| PAIS | País | I |  |  | N |
| PERCAVISO | % p/avisar antes de exceder a Despesa | F |  |  | N |
| PERFIL | Perfil principal | I |  |  | N |
| PERIODO | Periodicidade | I |  |  | N |
| PRODUTO | Produto | I |  |  | N |
| PROJETO | Projeto | I |  |  | N |
| REGIAO | Região | I |  |  | N |
| SIMPLIFICADA | Simplificada | S |  |  | N |
| SOMARIMP | Somar IMP | S |  |  | N |
| SOMARINSS | Somar INSS | S |  |  | N |
| SOMARIRRF | Somar IRRF | S |  |  | N |
| SOMARISSRET | Somar ISSRET | S |  |  | N |
| TIPMETA | Tipo de Meta | S |  |  | N |
| TIPODATA | Tipo de Data | S |  |  | N |
| TIPOMSG | Quando exceder a Despesa Prevista | S |  |  | N |
| TIPQTD | Produtos por | S |  |  | N |
| TIPVLR | Tipo do Valor | S |  |  | N |
| UF | Estado (UF) | I |  |  | N |
| VALORBRUTO | Usar valor Bruto | S |  |  | N |
| VALPREVIGOR | Valida alteração do período em vigor | S |  |  | N |
| VENDEDOR | Vendedor/Comprador | I |  |  | N |
| VENDITEM | Vendedor Item | I |  |  | N |
| VERSAO | Versão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CampoSubstituto / [TGMSUB](TGMSUB.md) | CODMETA->CODMETA | I |
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | LiberacaoLimite / [TSILIB](TSILIB.md) | CODMETA->CODMETA | I |
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | LimitacoesIntervaloUnico / [TGMCFGI](TGMCFGI.md) | CODMETA->CODMETA | I |
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | LogLimitacoesIntervaloUnico / [TGMCFGF](TGMCFGF.md) | CODMETA->CODMETA | I |
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | Mensagem / [TGMMSG](TGMMSG.md) | CODMETA->CODMETA | I |
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | MetaAtual / [TGMMET](TGMMET.md) | CODMETA->CODMETA | I |
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | TipoMeta / [TGMTME](TGMTME.md) | CODMETA->CODMETA | I |
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUAPROV->CODUSU | I |
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | UsuarioLiberadorMeta / [TGMUSLB](TGMUSLB.md) | CODMETA->CODMETA | I |
| DetailMetaForecast / [TGMDFM](TGMDFM.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| MetaAtual / [TGMMET](TGMMET.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| RegImportacaoPO / [TGFIPO](TGFIPO.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| SolicitacaoLiberacaoOrcamento / [TGMSLO](TGMSLO.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| Transferencia / [TGMTRA](TGMTRA.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
