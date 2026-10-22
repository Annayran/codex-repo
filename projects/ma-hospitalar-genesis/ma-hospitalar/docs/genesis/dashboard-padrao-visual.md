# Dashboard: padrao visual e filtros

Entrega de 16/10/2026 restrita ao JSP/CSS/JavaScript. Nao altera regras de envio,
estados persistidos, agendamentos ou contratos da API.

- Equipamentos: numero de serie junto aos filtros existentes. Envios filtram o
  conjunto carregado; base instalada filtra dados e total no Oracle antes da pagina.
- Estoques: pesquisa produto, descricao, ID Genesis ou estado.
- Cadastros/reconsultas: pesquisa entidade, chave ou estado.
- Cancelamentos/diagnostico: pesquisa nota, chave (incluindo serie) ou estado.
- Requisicoes: preserva pesquisa de movimento/produto pelo contexto.
- Pesquisa reinicia paginacao; navegacao preserva parametros do Sankhya.
- Evidencias e mensagens abrem em dialog amplo, com JSON como texto escapado,
  sem expandir as linhas da grade. Escape fecha; foco e gerido pelo dialog nativo.
- Tema claro/escuro salvo no localStorage do navegador, sem alterar preferencia
  de outros componentes Sankhya. Falha de storage nao impede abertura.

BuildGenesisHomologacao.ps1 gera ZIP com index.jsp e CSS/JS embutidos, seguindo
o empacotamento existente. Substituir somente o recurso JSP do componente BI.
Nao exige novo JAR nem alteracao de banco nesta entrega visual.

Validacao local: tools/TestGenesisDashboard.cjs usa fixture sintetica, Playwright
e Edge headless. Verifica modal, Escape, temas, persistencia da preferencia e
filtro de serie. Capturas desktop/mobile em build/dashboard-qa.
Nao substitui compilacao JSP e execucao Oracle no ambiente Sankhya.

Homologar todas as abas, pesquisa/limpeza, paginacao filtrada e contraste dos
estados reais. Backup do ZIP anterior permite rollback sem migracao de dados.
PENDENTE no historico permanece o valor real: esta entrega nao mascara nem
reclassifica identidade/persistencia sem evidencia do processamento.

Refinamento visual de 16/10: referencia em video analisada; preservadas cores
MA Hospitalar e navegacao operacional. Indicadores principais em faixa com
acentos azul/verde/vermelho, barras proporcionais aos dados existentes e tokens
semanticos para marca, rotulos, erros, sucesso e revisao nos dois temas.
Contraste local medido (>=4,5:1) para faixa superior, navegacao ativa, rotulos,
ordenacao e badges de erro/sucesso. Transicoes respeitam reduced-motion.
Nao foram adicionados dados ficticios, metricas por refresh ou novas chamadas API.


---
Copia de consulta para entrega; fonte canonica local: `ma-hospitalar/docs/genesis/dashboard-padrao-visual.md`. Sanitizacao de caminhos e enderecos aplicada; nao representa homologacao.
