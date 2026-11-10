# Sprint / Ciclo Atual

## Objetivo Atual

Homologar a integracao de itens-materiais usando os IDs conciliados da Base
Instalada Genesis.

## Concluido

- Modelagem mestre-detalhe das oito entidades adicionais.
- Importador da planilha em JSP/Java com reimportacao idempotente.
- Conciliacao das abas da Base Instalada com os cadastros Sankhya preparada.
- Resolucao de modelo por `TGFPRO.AD_IDEXTERNO` e `AD_GENMODELO.CODIGOEXTERNO`.
- Acao agendada ajustada para GET/POST/PUT somente de `/itens-materiais/`.
- Persistencia do ID retornado em `AD_GENITEMMAT` e resultado em `AD_GENMOVEST`.
- Testes unitarios do fluxo alterado aprovados.
- Dashboard com ordenacao, paginacao, rolagem controlada e rotulos amigaveis.
- Erros do dashboard apresentados em janela de diagnostico com orientacao
  funcional e detalhe tecnico recolhivel.
- Dashboard e agendador protegidos contra repeticao de `NUNOTA + SERIE` e contra
  multiplos historicos da mesma `CHAVE_MOVIMENTO`.
- Ação manual de reprocessamento ajustada para usar a transação gerenciada pelo
  Sankhya, sem commit ou rollback próprio.
- Dashboard preparado para exibir `TIPO_ESTOQUE` separadamente de movimento e
  poder.
- Cards operacionais limitados ao ultimo estado dos equipamentos atuais, com o
  acumulado antigo identificado como `Historico de integracao`.
- Politica de retencao da `AD_GENMOVEST` documentada sem executar limpeza.

## Proximas Acoes

- Executar e validar a conciliacao da Base Instalada.
- Definir `PADRAO='S'` quando um cliente possuir varios setores elegiveis.
- Publicar o JAR atualizado da acao agendada.
- Testar com lote `1` um item existente e um item novo.
- Conferir API, `AD_GENITEMMAT`, `AD_GENMOVEST` e dashboard.
- Publicar o JAR corrigido e validar o botão em um registro `ERRO`.
- Publicar a DDL corrigida da `AD_VW_GENESIS_ESTOQUE`, executar as validações de
  CNPJ, movimento e poder e então publicar o ZIP atualizado do dashboard.
- Avaliar arquivamento de testes antigos somente depois de backup restauravel,
  homologacao e confirmacao de que nenhuma chave sera reenviada.

## Bloqueios

- A conciliacao e a carga integral precisam estar executadas no ambiente.
- Clientes com varios setores precisam de um unico setor padrao.
