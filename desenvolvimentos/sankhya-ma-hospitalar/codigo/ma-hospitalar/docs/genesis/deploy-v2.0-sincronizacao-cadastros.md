# Genesis V2.0 - sincronizacao completa de cadastros

## Entrega

Esta versao altera somente a rotina Java. Nao ha mudanca de estrutura de banco, view ou dashboard.

Para cada equipamento elegivel (`TGFPRO.AD_TIPOPRODUTO = 'M1'`), a acao agendada executa, nesta ordem:

1. cliente;
2. setor padrao do cliente;
3. material;
4. fabricante;
5. modelo;
6. item-material.

Em cada etapa, a rotina consulta a Genesis pelo `codigoExterno`. Se nao encontrar, executa `POST`; se encontrar com divergencia, executa `PUT`; se estiver alinhado, nao altera. O ID retornado e conciliado imediatamente na tabela adicional correspondente antes da proxima dependencia.

O setor usa o nome/razao social do cliente. O fabricante usa `TGFPRO.CODFAB -> TGFPAR.CODPARC` e exige o CNPJ de `TGFPAR.CGC_CPF`, com fallback para `TGFPRO.CNPJFABRICANTE`. CNPJ ausente ou invalido bloqueia o envio antes do HTTP.

## Protecoes

- Somente produtos `M1` seguem para a API.
- Duplicidades locais interrompem o candidato sem escolher um registro arbitrariamente.
- Produto e serie identificam o item-material local, mesmo quando o antigo `IDGENESIS` estiver incorreto.
- Token, CPF, CNPJ, e-mail e telefone permanecem mascarados nos logs.
- Nao e executado `rollback()` manual dentro da transacao gerenciada pelo Sankhya.
- A repeticao e idempotente pelo `codigoExterno`: uma falha depois de um `POST` remoto e recuperada pelo `GET` da proxima execucao.

## Implantacao em HML

1. Desabilitar temporariamente a acao agendada.
2. Fazer backup restauravel das tabelas `AD_GENCLIENTE`, `AD_GENSECTOR`, `AD_GENMATERIAL`, `AD_GENEMPRESA`, `AD_GENMODELO`, `AD_GENITEMMAT` e `AD_GENMOVEST`.
3. Guardar o JAR V1.4 atualmente instalado.
4. Executar `sql/validacao-v2.0/01_pre_requisitos.sql` e corrigir qualquer equipamento sem fabricante/CNPJ.
5. Publicar `ZAYA-MAH-GENESIS-V2.0.jar`.
6. Manter lote igual a 1 na primeira execucao.
7. Habilitar a acao e acompanhar um candidato ate `ITEM_MATERIAL`.
8. Executar `sql/validacao-v2.0/02_cadastros_conciliados.sql` e `03_resultado_movimentos.sql`.

O resultado esperado no log apresenta GET e a decisao `CRIADO`, `ATUALIZADO` ou `SEM_ALTERACAO` para cada entidade, encerrando o candidato como `ENVIADO`.

## Rollback operacional

1. Desabilitar a acao agendada.
2. Restaurar o JAR V1.4.
3. Nao excluir automaticamente registros criados na Genesis: a API externa nao participa da transacao Oracle.
4. Comparar os IDs gravados nas tabelas adicionais com o backup e com os logs antes de qualquer reversao de dados.
5. Se houver necessidade de desfazer dados locais, restaurar o backup somente com aprovacao funcional e tecnica.

## Risco residual

Os contratos de cadastro foram validados por testes locais, mas a criacao real depende dos dados e das regras da base HML Genesis. A primeira execucao deve permanecer controlada com lote 1.
