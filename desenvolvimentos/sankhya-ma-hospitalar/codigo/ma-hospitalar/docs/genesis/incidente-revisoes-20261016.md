# Revisoes da exportacao de 16/10/2026

Fonte: Genesis_Movimento_Estoque (1).xlsx, emissao 13:58:19. Leitura de 145
registros: ENVIADO 90, REVISAO 51, API_SINCRONIZADO 3, PENDENTE 1.
Mensagens representam o ultimo estado exportado, nao comprovam falha atual da API.

Distribuicao das 51 revisoes:
- 22: vinculo local pertence a outro cadastro.
- 16: reserva ENVIO_INCERTO nao autoriza novo POST.
- 10: reconsulta com HTTP 401/token invalido.
- 1: identidade divergente do ID consultado.
- 1: identidade MATERIAL divergente.
- 1: multiplas correspondencias locais de produto/serie.

Codigo: CadastroGenesisRepository.carregar pode recuperar material pelo modelo
quando nao encontra material pelo grupo. BaseGenesisApiRepository.vincular tenta
depois associar CODGRUPOPROD, aceitando apenas nulo ou igual ao produto atual.
Se material herdado tem outro grupo, a atualizacao retorna zero. Esse caminho
explica a possibilidade do conflito; confirmar valores reais pelas consultas.
Nao alterar grupo sem confirmar se a relacao funcional admite muitos-para-um.

Correcao de observabilidade: falha de persistencia dos cadastros apos GET validado
registra DETALHE com FALHA_PERSISTENCIA_LOCAL em transacao independente, apos
rollback. Dashboard apresenta Falha local mantendo o estado bruto PENDENTE.
Nao exige novo dominio/DDL; protege historico ja CONFIRMADA. Diagnostico de vinculo
inclui entidade, PK, ID Genesis e chave Sankhya esperada, sem copiar resposta ou token.
Nao corrige retroativamente evidencias antigas nem muda IDs.

sql/genesis/diagnostico-revisoes-20261016.sql e somente leitura. Investigar vinculos,
reservas e respostas, duplicidade local e cronologia 401. Nao foi executado na base.
HTTP 401 exige conferir configuracao/validade no ambiente; nao comprova token
atualmente invalido. Nao reproduzir credenciais em consultas, logs ou documentos.
Envio incerto requer GET pelo codigo estavel e evidencia antes de novo POST.
Duplicidade local exige comparar IDs remotos e referencias filhas antes de sanear.

Homologacao: instalar JAR e ZIP juntos, reproduzir conflito controlado, confirmar
rollback do negocio e evidencia independente; GET sem erro continua normal.
Rollback: restaurar artefatos anteriores; DETALHE e apenas evidencia e pode permanecer.
Nao realizado deploy, DML, exclusao ou liberacao de reservas.

Decisao confirmada em 16/10: preservar material do modelo mesmo quando atende
varios grupos. Material recuperado pelo modelo passa a ter precedencia sobre
o material do grupo; referencia herdada nao atualiza CODGRUPOPROD. Antes da
persistencia, valida ID e relacionamento atual modelo/material, exigindo uma
correspondencia. Campos remotos continuam atualizados pela resposta validada.
Nao cria material adicional nem altera ID do modelo para contornar conflito.
Reprocessamento automatico existente permanece; nao limpar os controles enviados.
