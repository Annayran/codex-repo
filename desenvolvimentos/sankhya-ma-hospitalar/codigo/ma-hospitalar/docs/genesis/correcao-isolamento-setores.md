# Isolamento de setores por parceiro

Analise do JAR recebido em 18/09/2026: SHA-256
`2D76673E14BB739397158830F33B844C95F8AF5AE2C559D94332BD383BCCBDCA`.
O bytecode confirma o OR irrestrito entre CODPARC e IDCLIENTEGEN e a
atribuicao automatica PADRAO=S. O binario possui melhorias ausentes nos
fontes locais; nao reconstruir a entrega completa com os fontes antigos.

Correcao local: excluir parceiros diferentes da busca alternativa, permitir
alternativa por ID somente sem CODPARC, atualizar uma unica linha por IDREGSET
com guarda de proprietario, preservar PADRAO existente e criar novos setores
com PADRAO=N. Um unico setor continua selecionavel sem ser padrao. Multiplos
setores ambiguos continuam bloqueados; nao selecionar arbitrariamente.

Complemento: GET de setor por codigo externo ou por ID local somente permite
reutilizacao/PUT se idCliente corresponder ao cliente esperado. Cliente diferente
ou ausente gera CONFLITO (409), antes de POST/PUT de setor. Nenhum fluxo de
cliente, fabricante, material ou modelo foi alterado por este complemento.

Validacao local: classes alteradas compiladas com --release 8; 79 testes nao HTTP
aprovados, incluindo sete novos casos de setor. Demais classes da suite usam o
baseline manual v2.1 previamente compilado. A consulta Oracle tem verificacao
estatica de contrato, nao teste integrado no banco. Nao houve deploy ou chamada
a API Genesis. O fonte local ainda diverge do JAR; nao gerar entrega completa
que substitua melhorias do binario recebido.

Deploy: aplicar a mesma correcao aos fontes correspondentes ao JAR recebido,
compilar Java 8, desativar agenda e testar em HML com lote 1. Para nota 94251,
o setor de CODPARC=67803 nao pode entrar para CODPARC=136. Validar repeticao,
ausencia de alteracao do setor alheio e persistencia de usuario/IDs.

Nao altera schema nem saneia dados antigos. IDs importados ainda precisam
validacao por ambiente. A alternativa sem CODPARC exige confirmacao cadastral.
Rollback operacional: desativar agenda e restaurar JAR anterior. Nao apagar
cadastros remotos nem controles locais; efeitos HTTP nao sofrem rollback JDBC.
