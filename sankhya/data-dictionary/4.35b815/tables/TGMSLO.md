# TGMSLO — Solicitação Liberação Orçamentária

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SolicitacaoLiberacaoOrcamento | Solicitação Liberação Orçamentária | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAPROVADOR | Código aprovador | I |  |  | N |
| CODMETA | Código | I |  |  | N |
| CODSOLICITANTE | Código solicitante | I |  |  | N |
| DHAPROVACAO | Data da aprovação | H |  |  | N |
| DHSOLICITACAO | Data da solicitação | H |  |  | N |
| NUSOLICITACAO | Número solicitação | I |  |  | N |
| OBSERVACAO | Observação solicitante | S |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | SolicitacaoLiberacaoOrcamento / [TGMSLO](TGMSLO.md) | CODMETA->CODMETA; CODSOLICITANTE->CODSOLICITANTE; NUSOLICITACAO->NUSOLICITACAO | I |
| SolicitacaoLiberacaoOrcamento / [TGMSLO](TGMSLO.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| SolicitacaoLiberacaoOrcamento / [TGMSLO](TGMSLO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODSOLICITANTE->CODUSU | I |
| SolicitacaoLiberacaoOrcamento / [TGMSLO](TGMSLO.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODAPROVADOR->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
