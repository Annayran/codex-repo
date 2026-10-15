# Current Sprint - Particionamento Skala

Atualizado em: 2026-06-25

## Objetivo corrente

Concluir validacao funcional/UAT do particionamento com Skala, usando JAR e JSP HTML5 atualizados.

## Escopo pronto para teste

- Particionamento automatico por capacidade de paletes.
- Mix de SKUs com prioridade e grupos.
- Calculo por embalagem do item (`TGFITE.CODVOL` + `TGFVOA`).
- Agendamento obrigatorio por OEP.
- Bloqueio para pedido nao liberado (`STATUSNOTA <> 'L'`).
- Bloqueio para pedido ja particionado (`AD_PARTICIONADO = 'S'`).
- Rastreabilidade customizada.
- Gravacao de vinculo nativo em `TGFVAR`.
- Desfazer particionamento pelo botao customizado.
- Bloqueio de exclusao nativa de OEP particionada.
- JSP com nomenclatura amigavel para usuario final.

## Fluxo principal de teste

1. Selecionar pedido/OE liberado no Sankhya.
2. Abrir o JSP de particionamento.
3. Conferir itens distintos do pedido e calculo por embalagem.
4. Ajustar capacidade, grupos e prioridades se necessario.
5. Simular particionamento.
6. Preencher agendamento de cada OEP.
7. Enviar para particionar.
8. Validar OEPs criadas, rastro (`AD_PART*`) e vinculos (`TGFVAR`).
9. Executar desfazer pela acao customizada.
10. Confirmar remocao das OEPs, rastro como `DESFEITO` e origem com `AD_PARTICIONADO = 'N'`.

## Responsavel pela proxima validacao

Pendencia atual com Andre Siqueira: realizar os testes finais no ambiente Sankhya/Skala.

## Pontos de acompanhamento

- Confirmar se a TOP de OEP permanece `3108` ou muda para `3508` em producao.
- Validar o desfazer com OEPs que tenham `TGFVAR`.
- Validar cenarios com produtos controlados por lote.
- Validar performance com muitos SKUs/OEPs.
