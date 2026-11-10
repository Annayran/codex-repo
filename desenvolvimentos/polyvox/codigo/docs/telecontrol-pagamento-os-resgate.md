# Telecontrol - Resgate Pagamento de OS

## Data

2026-06-26

## Objetivo

Registrar o resgate dos ultimos artefatos locais relacionados ao pagamento de OS Telecontrol:

- dashboard `dashboard_os_tlc`;
- JAR final `TelecontrolPolyvox.jar`.

## Artefatos resgatados

| Artefato | Destino no workspace | Origem local | SHA-256 |
| --- | --- | --- | --- |
| Dashboard JSP | `jsp/dashboard_os_tlc/dashboard_os_tlc.jsp` | `C:\zy-tools\workspace\Polyvox\dashboard_os_tlc\dashboard_os_tlc.jsp` | `DC16639947166D6F16D59D45879A49346E74D552B41576CFD55F06AC9CE34676` |
| CSS do dashboard | `jsp/dashboard_os_tlc/css/dashboardOsTlc.css` | `C:\zy-tools\workspace\Polyvox\dashboard_os_tlc\css\dashboardOsTlc.css` | `1208524FE70E86015369FA6F1C9E3B2A5B8A2CFFCE2B6D772C7BECF1B9644A3A` |
| ZIP operacional | `jsp/dashboard_os_tlc/dashboard_os_tlc.zip` | `C:\zy-tools\workspace\Polyvox\dashboard_os_tlc\dashboard_os_tlc.zip` | `321BB592024C94DCC02BE4AE5E1B41AE6D0B9CCB37AEFC73A6FF39598AB45003` |
| JAR final | `artifacts/telecontrol/TelecontrolPolyvox.jar` | `C:\zy-tools\workspace\Polyvox\PolyvoxTelecontrol\build\libs\TelecontrolPolyvox.jar` | `2915838AB0CC60A63E3286C97B7173882A27D1C569710E9AA216B696DD08512E` |

## Evidencias confirmadas

- As copias encontradas em `C:\zy-tools\workspace\Polyvox\dashboard_os_tlc` e `C:\zy-tools\workspace\Polyvox\Dashboards\dashboard_os_tlc` possuem o mesmo hash para JSP e CSS.
- O JAR resgatado foi compilado em `C:\zy-tools\workspace\Polyvox\PolyvoxTelecontrol\build\libs\TelecontrolPolyvox.jar`.
- O JAR contem classes ligadas ao pagamento/documento de OS:
  - `br.zaya.telecontrol.osintegracao.actions.GerarDocumentoOsAction`
  - `br.zaya.telecontrol.osintegracao.actions.DesfazerPagamentoOsAction`
  - `br.zaya.telecontrol.osintegracao.actions.GerarDocumentoOsScheduler`
  - `br.zaya.telecontrol.osintegracao.application.OsIntegracaoDocumentoService`
- O dashboard consulta dados da `AD_OSTLC`, `AD_OSPECA` e tabela de pecas Telecontrol, usando marcadores como `OSPAGA`, `NUFINGER` e `NUNOTAGER`.

## Regras Sankhya impactadas

Confirmado pelo codigo:

- OS finalizadas sao consideradas para processamento documental.
- OS com peca Telecontrol podem gerar nota de compra.
- OS sem peca podem gerar financeiro direto/clonado.
- `AD_OSTLC.OSPAGA`, `AD_OSTLC.NUFINGER` e `AD_OSTLC.NUNOTAGER` sao usados como marcadores de pagamento/documento gerado.
- A action de desfazer pagamento limpa marcadores em `AD_OSTLC`.

Inferido por padrao Sankhya:

- A geracao de nota/financeiro precisa ser homologada com foco em TOP, financeiro, confirmacao, transacao, origem/destino, estoque/fiscal quando aplicavel e integracoes dependentes.
- O dashboard depende dos IDs de acoes Java cadastradas no Sankhya para chamar as rotinas corretas.

## A confirmar

- TOP/modelo oficial usado na nota de compra gerada para OS com peca.
- Parametro `TLC_GERA_NOTA_COMPRA_OS` no ambiente.
- Parametro `AD_NUFINCLONETC` no ambiente.
- IDs reais das acoes Java configuradas na tela/dashboard.
- Se a nota de compra gerada exige vinculo em `TGFVAR`.
- Se desfazer pagamento deve tambem cancelar/estornar documentos ou financeiros gerados, alem de limpar `AD_OSTLC`.

## Testes recomendados

1. Publicar `artifacts/telecontrol/TelecontrolPolyvox.jar` em homologacao.
2. Publicar `jsp/dashboard_os_tlc/dashboard_os_tlc.zip` ou o JSP/CSS conforme o mecanismo HTML5/JSP do Sankhya.
3. Validar carregamento do dashboard com dados reais de `AD_OSTLC`.
4. Confirmar se os IDs das rotinas Java no dashboard apontam para:
   - gerar documento/pagamento de OS;
   - sincronizar OS;
   - desfazer pagamento de OS.
5. Executar um caso controlado de OS finalizada com peca e conferir `NUNOTAGER`, nota gerada e financeiro.
6. Executar um caso controlado de OS finalizada sem peca e conferir `NUFINGER` e financeiro gerado.
7. Testar rollback com erro provocado em homologacao.
8. Testar desfazer pagamento e confirmar se o comportamento esperado e apenas limpar marcadores ou tambem tratar documento/financeiro.

## Atualizacao 2026-06-29 - Selecao parcial de OS

Foi implementada a selecao parcial de OS no pagamento:

- O dashboard permite marcar OS pendentes na tabela detalhada.
- O botao `Gerar selecionadas` envia `P_NROOS_LIST` apenas com as OS marcadas.
- O botao `Gerar` da linha detalhada envia somente o `NROOS` daquela linha.
- A action `GerarDocumentoOsAction` passou a priorizar `NROOS` como filtro principal.
- O fallback por CNPJ foi preservado para compatibilidade.

Comportamento preservado:

- O service continua agrupando as OS recebidas por CNPJ/parceiro.
- OS com peca continuam gerando nota de compra consolidada.
- OS sem peca continuam gerando financeiro consolidado.

Regra confirmada em 2026-06-29:

- Uma selecao mista deve ser separada automaticamente por `CNPJ + tipo de geracao`.
- OS com peca geram nota consolidada somente com as OS com peca daquele CNPJ.
- OS sem peca geram financeiro direto consolidado somente com as OS sem peca daquele CNPJ.
- OS com peca e OS sem peca nao podem ser mescladas em um unico financeiro ou documento.
- O menor trabalho para o usuario deve ser selecionar as OS; a classificacao e separacao ficam a cargo do Java.

## Atualizacao 2026-06-30 - VLRDESDOB no financeiro direto

O financeiro direto de OS sem peca deve preencher `TGFFIN.VLRDESDOB` com base em `TGFPAR.AD_TAXAMANUTENCAO`, que e o mesmo campo usado pelo dashboard para calcular o valor a pagar.

Regra:

- 1 OS sem peca selecionada: `VLRDESDOB = AD_TAXAMANUTENCAO`.
- N OS sem peca selecionadas no mesmo CNPJ: `VLRDESDOB = AD_TAXAMANUTENCAO * N`.
- Em selecao mista, somente as OS sem peca entram nesse financeiro; OS com peca seguem para a nota consolidada.
- `AD_VLRFIXSER` permanece apenas como fallback no Java para compatibilidade.

## Atualizacao 2026-06-30 - ORIGEM F no financeiro direto

O financeiro direto de OS sem peca deve ser criado com:

```txt
TGFFIN.ORIGEM = 'F'
```

O codigo anterior podia sobrescrever a origem com o valor clonado do financeiro de referencia. Se a referencia estivesse como `E`, o financeiro novo tambem podia ficar como `E`.

Regra:

- Financeiro direto de OS sem peca sempre deve sair com `ORIGEM = 'F'`.
- O objetivo e manter liberado na tela Sankhya o campo de numero da nota.
- A confirmar: se `NUMNOTA` deve ser gravado automaticamente com `AD_OSTLC.NOTAFISCAL` ou apenas ficar disponivel para lancamento manual.
