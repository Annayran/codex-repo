# Fabricante por ID conhecido da base instalada

Decisao funcional de 13/10/2026: o IDGENESIS do fabricante vinculado na base
instalada e referencia para localizar o mesmo cadastro remoto. Empresas legadas
podem ter CNPJ "0" e codigoExterno nulo; isso nao invalida o ID conhecido.

No fluxo ativo, um fabricante com ID local ou protocolo confirmado usa apenas
GET /empresas/{id}. HTTP deve ser sucesso, ID retornado positivo e igual ao
solicitado, inclusive o id no corpo JSON. Nao exige CNPJ/codigo externo nesse
caminho, nao executa PUT e nao recria fabricante em 404.

Sem ID conhecido, conciliacao por codigo externo e validacao documental
continuam como antes. POST novo continua exigindo CNPJ de 14 digitos. Clientes,
setores, materiais, modelos e itens-materiais nao recebem essa excecao.

Retorno validado segue o fluxo existente: confirmar controle, aplicar campos
remotos na IDBASE=1, registrar historico e permitir a proxima dependencia.

Risco: um ID local vinculado incorretamente nao e detectado por documento/nome
nesse caminho. A qualidade do vinculo importado/conciliado e premissa explicita.

Deploy: pausar job, substituir JAR consolidado, manter tabelas/campos exigidos
pelo pacote de rastreabilidade. Testar fabricante 54, retorno de outro ID e 404.
Nao ha migracao de dados ou DDL nesta correcao. Rollback: restaurar JAR anterior
com jobs pausados, preservando protocolos, historico e controles.
