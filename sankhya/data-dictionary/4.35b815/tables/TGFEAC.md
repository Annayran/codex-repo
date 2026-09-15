# TGFEAC — Acompanhamento de Etapas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcompanhamentoEtapa | Acompanhamento de Etapas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUALESTOQUE | Estoque Atual | I |  |  | N |
| CODETAPA | Etapa | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHACOMP | Data e hora do Acompanhamento | H |  |  | N |
| NUACOMP | Número do Acompanhamento | I |  |  | N |
| NUNOTA | Número da Nota | I |  |  | N |
| NUPROD | Número da Produção | I |  |  | N |
| QTD | Quantidade | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | EtapaProducao / [TGFETA](TGFETA.md) | CODETAPA->CODETAPA | I |
| AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EtapaProducao / [TGFETA](TGFETA.md) | AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | CODETAPA->CODETAPA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
