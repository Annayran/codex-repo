# Hotfix de abertura do dashboard - 03/11/2026

Usuario reportou espera indefinida apos atualizar o painel. Comparacao com a
copia local anterior revelou duas novas consultas correlacionadas a view
AD_VW_GENESIS_ESTOQUE na abertura: por movimento e no resumo global.
O JSP executa as consultas antes de entregar bootstrap ao React; a espera no
banco pode impedir a abertura inteira. Hipotese de regressao sustentada pelo
diff, ainda nao confirmada por plano Oracle, logs ou teste no ambiente real.

Correcao: restaurado exatamente o template JSP anterior, SHA256
5DFC07D2AC5791E02A7E0DDDFF52E01DA617C4D343DE72D7BF2EC562449D96CE.
Removida a coluna de consulta de origem da tabela. Os calculos de fora da
origem ficam nas consultas de diagnostico manuais, nao na abertura.
Visual, filtros, abas, temas e mensagens amigaveis preservados.
Teste de regressao proibe referencia a view de origem nas 15 queries do JSP.

Substituir SOMENTE o recurso do dashboard pelo ZIP hotfix, apos guardar backup
do recurso atual. Nao trocar JAR, jobs, configuracoes ou dados para esse reparo.
Reabrir o dashboard; se continuar aguardando, coletar erro/log e tempo das
queries no servidor. A aplicacao real continua sem validacao remota.
Pacote anterior MAHospitalar-Genesis-Entrega-20261103.zip contem o painel
reportado e nao deve ser usado para atualizar o dashboard; usar o hotfix.
Nenhuma instalacao remota foi executada nesta correcao.
