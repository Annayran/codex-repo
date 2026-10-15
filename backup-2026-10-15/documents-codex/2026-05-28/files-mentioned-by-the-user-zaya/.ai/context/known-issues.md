# Known Issues - Particionamento Skala

Atualizado em: 2026-06-25

## Riscos e pontos de atencao

- `CODTIPOPER_OE` esta hard-coded como `3108` em `OEGenerator` e `RastreabilidadeRepository`. Se o ambiente exigir `3508`, atualizar ambos.
- `TGFCAB.AD_DHAGENDAMENTO` precisa existir como campo data/hora.
- Produtos precisam ter registro em `TGFVOA` para o mesmo `CODVOL` usado no item do pedido.
- Produtos com lote/controle obrigatorio continuam sujeitos as triggers nativas do Sankhya.
- A criacao da OEP e copia controlada dos principais campos, nao clone nativo completo. Campos obrigatorios podem variar por ambiente/TOP.
- Triggers de estoque/entrega do ambiente podem bloquear criacao ou desfazer mesmo quando a TOP aparenta nao validar estoque.
- `TGFVAR` foi implementada com campos essenciais validados no ambiente. Se outra base exigir campos opcionais por constraint/trigger, ajustar o insert.
- O desfazer depende de `VARIAVEIS_PKG.V_ADIARATUALIZACAOORIGEM`; nao usar `STP_SET_ATUALIZANDO`.
- Rastreabilidade usa `MAX(ID) + 1` com lock exclusivo. Funciona para o escopo atual, mas sequencias Oracle seriam melhores em alta concorrencia.
- JSP/ZIP fica em workspace externo (`2026-06-05`). Ideal trazer o JSP para este repo para evitar perda de contexto.

## Validacoes recomendadas

- Pedido nao liberado: confirmar bloqueio por `STATUSNOTA`.
- Pedido ja particionado: confirmar alerta/bloqueio por `AD_PARTICIONADO`.
- Produto sem paletizacao para o volume do item: confirmar mensagem amigavel.
- Produto com lote/controle: testar com e sem `CONTROLE` preenchido.
- Mix com muitos SKUs/OEPs: validar performance e arredondamentos.
- Agendamento ausente: confirmar bloqueio no JSP e no backend.
- `TGFVAR`: validar vinculos e soma de `QTDATENDIDA`.
- Desfazer: testar a partir da origem e a partir de uma OEP.
- ZIP HTML5: confirmar que contem somente `modalParticionamentoPedidos.jsp` na raiz.
