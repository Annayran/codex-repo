# Genesis V2.1 - reconciliacao de identidade e reprocessamento

## Diagnostico do server.log de 14/09/2026

- Movimentos `93517` e `93525`: `GET /clientes/?codigoExterno=0081470002` retornou lista vazia, mas o `POST /clientes/` retornou HTTP 400 informando que o CNPJ ja existe. O cadastro remoto existe com outro codigo externo.
- Movimento `94251`: a API criou cliente `1369`, setor `1499` e material `233`. O fluxo parou antes do fabricante porque o produto `13482` nao forneceu CNPJ de fabricante.
- Os registros chegaram a quatro tentativas. O botao anterior alterava o status para `REPROCESSAR`, mas nao zerava `TENTATIVAS`; por isso o job continuava exibindo `candidatos=0`.
- O log mostra o job disparando aproximadamente a cada cinco segundos. Esse intervalo deve ser revisto antes do Go Live.

## Correcao

1. Cliente continua sendo pesquisado por `codigoExterno`.
2. Se nao for encontrado, o ID Genesis local e consultado e somente e reutilizado quando o CNPJ/CPF remoto for igual ao documento do Sankhya.
3. Se o ID local estiver incorreto, a busca alternativa e feita por CNPJ/CPF. Um unico registro e atualizado por `PUT` e seu ID substitui o vinculo local.
4. Nunca ha conciliacao por nome ou razao social.
5. Quando um modelo local e encontrado na Genesis pelo codigo externo, seu ID e reutilizado diretamente no item-material. Fabricante e material so sao criados quando o modelo nao existe.
6. O botao de reprocessamento zera `TENTATIVAS`, limpa HTTP/erro anteriores e grava `USU_REPROCESSAMENTO` e `DH_REPROCESSAMENTO`.

## Teste em HML

1. Desabilitar temporariamente a acao agendada.
2. Fazer backup das tabelas adicionais Genesis e guardar o JAR V2.0.
3. Publicar `ZAYA-MAH-GENESIS-V2.1.jar` e confirmar a recarga da classe.
4. Ajustar o intervalo do job para pelo menos dois minutos durante a homologacao.
5. Na fila, selecionar primeiro apenas o registro `387` ou `403` e executar a acao de reprocessamento.
6. Confirmar que `TENTATIVAS` ficou zero, o usuario foi preenchido e o status ficou `REPROCESSAR`.
7. Acompanhar o log ate `ITEM_MATERIAL` e validar o ID retornado.
8. Depois, repetir para o registro `404`.

Para `93517/93525`, o resultado esperado e localizar o cliente pelo ID/documento e fazer `PUT`, sem novo `POST`. Para `94251`, o resultado esperado e reutilizar o modelo existente do produto `13482`, sem bloquear pela ausencia de CNPJ do fabricante.

## Rollback

Desabilitar o job e restaurar o JAR V2.0. Nao excluir registros remotos automaticamente. As criacoes ja aceitas pela Genesis nao participam da transacao Oracle e devem permanecer rastreadas pelos IDs locais e pelo log.

## Limite de validacao

Nao foram feitas chamadas reais contra a HML nem alteracoes no banco durante a geracao deste pacote. A busca por CNPJ/CPF deve ser confirmada na primeira execucao pelo retorno registrado no server.log.
