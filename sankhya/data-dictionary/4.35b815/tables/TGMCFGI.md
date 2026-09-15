# TGMCFGI — Limitações de Intervalo Único

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LimitacoesIntervaloUnico | Limitações de Intervalo Único | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODCENCUSFIM | Centro Resultado Fim | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODMETA | CODMETA | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DTALTER | Data alteração | H |  |  | N |
| DTFIM | Data Final | D |  |  | N |
| DTINIC | Data inicial | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | LimitacoesIntervaloUnico / [TGMCFGI](TGMCFGI.md) | CODMETA->CODMETA | I |
| LimitacoesIntervaloUnico / [TGMCFGI](TGMCFGI.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| LimitacoesIntervaloUnico / [TGMCFGI](TGMCFGI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| LimitacoesIntervaloUnico / [TGMCFGI](TGMCFGI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LimitacoesIntervaloUnico / [TGMCFGI](TGMCFGI.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
