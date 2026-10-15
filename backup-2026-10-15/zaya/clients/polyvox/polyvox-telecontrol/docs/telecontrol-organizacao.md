# Organizacao do pacote Telecontrol

Este documento descreve a organizacao incremental (sem quebra) aplicada no projeto `br.zaya.telecontrol`.

## Objetivo

- Melhorar separacao de responsabilidades (SOLID).
- Aproximar o fluxo de um desenho MVC:
  - Controllers: `actions/*`
  - Application/Use Cases: `osintegracao/application/*`
  - Infra/Integracao legado: `services/*`, `adapters/*`, `interfaces/*`
- Manter compatibilidade com classes e bindings do Sankhya.

## Estrutura atual recomendada

```text
br.zaya.telecontrol
  actions/                 # bridges de compatibilidade para metadata Sankhya
  pedidos/actions/         # implementacoes reais do feature pedidos
  pedidos/application/     # fachadas de caso de uso para pedidos
  pedidos/infrastructure/   # mappers/adapters de pedidos
  faturamento/actions/     # implementacoes reais do feature faturamento
  faturamento/application/ # fachadas de caso de uso para faturamento
  faturamento/infrastructure/ # mappers/adapters de faturamento
  serie/actions/          # action/scheduler consolidado de envio de series
  serie/application/      # caso de uso unico de envio de series
  serie/infrastructure/   # mapper de serie
  preco/actions/          # action de envio de precos
  preco/application/      # caso de uso unico de envio de precos
  produto/actions/         # implementacoes reais do feature produto
  produto/application/     # fachadas de caso de uso para produto
  produto/infrastructure/  # mappers/adapters de produto
  ordemservico/actions/    # implementacoes reais do feature ordem de servico
  ordemservico/application/ # fachadas de caso de uso para ordem de servico
  ordemservico/infrastructure/ # mappers/adapters de ordem de servico
  osintegracao/actions/    # implementacoes reais do feature integracao de OS
  osintegracao/
    application/           # contratos de caso de uso e DTOs de saida
  adapters/                # adaptadores para legado (Api/Service/DAO)
  interfaces/              # contratos de infraestrutura
  services/                # implementacao legado (nao removida)
  models/
  parsers/
  utils/
```

## O que foi aplicado

1. Criado boundary de aplicacao para OS:
   - `OsIntegracaoUseCase`
   - `SincronizacaoResultado`
   - `LegacyOsIntegracaoUseCase`
2. `SincronizarOsAction` e `OsIntegracaoScheduler` agora dependem de `OsIntegracaoUseCase`.
3. Mantida compatibilidade total com o legado atraves de `LegacyOsIntegracaoUseCase`, que delega para `OsIntegracaoServiceAdapter` e `OsIntegracaoService`.
4. Actions movidas para pacotes por feature e classes em `actions/*` mantidas como bridges `@Deprecated`.
5. Fachadas de `application` criadas para `pedidos`, `faturamento`, `produto` e `ordemservico`, preservando o legado interno.
6. Rotina de series consolidada em `serie/application/EnviaSerieService`, com bridges para `br.zaya.telecontrol.services.EnviaSerie`, `br.zaya.telecontrol.parsers.ParseSerie`, `br.zaya.telecontrol.actions.PostSerie`, `br.com.zaya.telecontrol.*` e `ProdutoSerie.*`.
7. Rotina de precos consolidada em `preco/application/EnvioPrecoService`, preservando as assinaturas publicas `postPrecoProduto`, `postPrecoPeca` e `postPreco`.
8. Actions legadas de pedido exportado/preco (`EnviaPedidoExportadoBt`, `MarcarExportadoBt`, `EnvioPrecosBt`) foram disponibilizadas como bridges para compatibilidade com metadata Sankhya.

## Proximos passos (incrementais)

1. Mover regras de parse/mapeamento para pacote `osintegracao/application` e `osintegracao/domain`.
2. Separar persistencia de OS e pecas em repositrios dedicados.
3. Migrar chamadas diretas de `Api` para `ITelecontrolApiClient` no legado interno.
4. Evoluir lock em memoria para lock transacional em banco (evita concorrencia entre nos).
5. Remover bridges antigas de `actions/*` somente apos atualizar metadata do Sankhya para os novos FQCNs.
6. Homologar a view `AD_TCLSER`, a fila `AD_TCLLOGSER` e `EP_SERIE` antes de ativar o scheduler de series. A rotina Java atual nao depende mais da STP `AD_STP_INC_AD_TCLLOGSER`.
7. Homologar `EP_PRECOS`, `SNK_PRECO`, `PRECOVEN` e `PRECOGAR` antes de ativar envio de precos em massa.

## Regra de seguranca de refatoracao

- Nunca remover entradas usadas por metadata do Sankhya sem migracao validada.
- Troca de dependencia sempre por construtor com default backward-compatible.
- Refatorar em lotes pequenos com validacao de compilacao entre lotes.

