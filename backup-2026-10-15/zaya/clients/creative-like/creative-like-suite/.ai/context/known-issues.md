# Problemas Conhecidos

## Pendencias Tecnicas

- `nuGdg` ainda esta como placeholder em `momentoNegociacaoModalButton.js`.
- O JSP do modulo `momento-negociacao` e painel de apoio; a execucao real ocorre via botao Java, nao por clique direto no HTML5.
- A Action real foi implementada, mas ainda precisa teste em homologacao antes de qualquer uso produtivo.
- O Pedido de Venda no JSP agora depende da consulta real na `AD_VGLDOC`; se a view nao retornar registro, o card mostra `nao encontrado`.
- Validar em homologacao se a ordenacao `CAB.NUNOTA DESC` e suficiente para exibir os pedidos na ordem esperada pelo usuario.
- Validar em homologacao se cancelar item do Pedido de Venda deve usar exatamente a mesma mensagem de `OCORRENCIAS` hoje usada para Pedido de Compra.
- Confirmar os nomes finais dos parametros da Action pontual de Pedido de Venda no cadastro Sankhya: hoje aceita `P_NUNOTA_PEDVENDA`/`P_NUNOTA` e `P_SEQUENCIA`/`P_SEQ`.
- Nao ha testes automatizados.
- O zip precisa ser recriado sempre que o JSP muda.

## Pontos de Atencao

- O JSP foi reforcado com UTF-8, mas o comportamento final depende do deploy/servidor Sankhya.
- A view `AD_VGLDOC` esta sendo usada no backend real para documentos relacionados, filtrando Pedido de Venda por `TIPMOV = 'P'`, sem limitar TOP.
- A regra real de `REFERENCIA`, `DIGITADO` e `AD_ITEMCANCELADO` precisa ser validada em homologacao.
- O workspace organizado em `C:\zy-tools\workspace\Creative Like` contem tambem o clone `PersonalizacoesCompraLike`; evitar misturar commits/artefatos entre projetos.

## Riscos Operacionais

- Tratar a simulacao como funcionalidade produtiva.
- Rodar testes em componente HTML5 produtivo em vez de homologacao.
- Executar a Action real sem validar antes quais itens seriam cancelados.

## A Confirmar

- Mensagem exata de erro caso o JSP ainda falhe no Sankhya.
- Versao do Sankhya/HTML5.
- `nuGdg` do componente.
- Campos definitivos para gravacao real em `TGFACT`.
