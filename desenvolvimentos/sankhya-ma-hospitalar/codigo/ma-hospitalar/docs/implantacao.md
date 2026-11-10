# Implantacao - Preco Dinamico MA Hospitalar

## 1. Preparacao

1. Fazer backup dos metadados e objetos adicionais do ambiente de teste.
2. Confirmar que a base e Oracle e que os JARs Sankhya correspondem a versao 4.35 ou compativel.
3. Identificar no dicionario do cliente o nome fisico do campo `% MC Minima` da `TGFTPV`.
4. Validar custo gerencial, regras fiscais, modelo de nota, PTAX, TOPs de venda e liberadores do evento 48.

## 2. Instalacao tecnica

Executar, nesta ordem:

1. `sql/00_create_objects.sql`.
2. Importar `metadata/metadata_mah_preco_dinamico.xml` ou cadastrar as telas/campos equivalentes.
3. `sql/01_price_engine.sql`.
4. Publicar `java/MAHospitalar.jar`.
5. Cadastrar `AuditaPrecoDinamicoItemEvent` na entidade ItemNota, eventos `afterInsert` e `afterUpdate`.
6. Cadastrar `ValidaMargemContribuicaoEvent` na entidade CabecalhoNota, evento `beforeUpdate`.
7. Cadastrar uma linha ativa em `AD_MAH_PD_CONFIG` por empresa, informando o campo fisico da MC minima.
8. Cadastrar regras vigentes em `AD_MAH_PD_REGRA`; nao copiar automaticamente valores da planilha para producao.
9. Classificar os produtos em `TGFPRO.AD_MAH_GRUPOPRECO` como PR1, PR2, PR3 ou PR4.

Classes:

- `br.com.zaya.mahospitalar.preco.event.AuditaPrecoDinamicoItemEvent`
- `br.com.zaya.mahospitalar.preco.event.ValidaMargemContribuicaoEvent`

## 3. Parametrizacao nativa

Revisar `sql/02_sankhya_parameters.sql`. A equipe funcional deve configurar:

- `NOMPROCCALCPRE = STP_MAH_PRECO_DINAMICO`;
- `MODCALCPRECDIN` com modelo ativo e parceiro valido;
- `USAIMPPRECODIN = S`;
- `RECPRECOTPV = Sempre`;
- campos de cabecalho/item que disparam recálculo;
- TOPs de Pedido/Venda com `Usar como preco = Preco de Venda`.

O JSON retornado injeta campos adicionais apenas no salvamento do item. Esses campos
devem constar no dicionario e, quando exigido pela versao, em `CAMITEMPRECODIN`.

## 4. Regras

Bases de credito:

- `N`: nao aplicar;
- `V`: percentual sobre a receita bruta;
- `C`: percentual sobre o custo gerencial.

Prioridade de selecao: UF especifica, escopo interna/interestadual e, por ultimo,
`QUALQUER`. Dentro do mesmo nivel vence a maior prioridade e a vigencia mais recente.
O trigger impede duas regras ativas com a mesma chave e vigencias sobrepostas.

Os debitos simulados de ICMS, DIFAL, FCP, PIS, COFINS, IPI e ST sao convertidos em
aliquotas efetivas sobre o preco-base. Creditos, fundos e despesas nao gerados pela
simulacao vem da regra adicional vigente.

## 5. Homologacao

1. Executar `sql/03_homologacao_memoria.sql` e obter a mensagem de 16 cenarios aprovados.
2. Validar Consulta de Produtos, Carrinho e Central de Vendas.
3. Alterar parceiro, empresa, TOP, negociacao, vendedor, quantidade, frete e moeda.
4. Aplicar desconto e confirmar que o preco nao e recalculado pelo desconto.
5. Confirmar pedido acima e abaixo da MC minima; aprovar/rejeitar a liberacao.
6. Alterar o pedido depois da aprovacao e confirmar nova memoria/solicitacao.
7. Testar pedido com pelo menos 100 itens e analisar tempo e plano das consultas.

## 6. Rollback

1. Limpar `NOMPROCCALCPRE` ou apontar para a procedure anterior.
2. Desativar os dois eventos Java.
3. Marcar `AD_MAH_PD_CONFIG.ATIVO = 'N'`.

Nao excluir regras, auditoria ou campos durante o rollback operacional.

## 7. Módulo Genesis

O módulo Genesis usa o mesmo `MAHospitalar.jar`, mas tem implantação e ativação
independentes. Seguir `docs/genesis/configuracao.md` e
`docs/genesis/homologacao-cutover.md`. A ordem técnica é:

1. `sql/genesis/00_central_parametros.sql`;
2. `sql/genesis/00_schema.sql`;
3. `sql/genesis/01_views.sql`;
4. importar `metadata/metadata_mah_genesis.xml`;
5. publicar o JAR e o conteúdo de `webapp/genesis`;
6. cadastrar a ação agendada e a ação manual indicadas na documentação;
7. manter `GEN_ATIVO='N'` e `GEN_SIMULACAO='S'` até cumprir todos os gates.

Nenhum script Genesis altera `TGFEST`. O rollback operacional desativa a agenda
sem remover `AD_MAH_GEN_MAP`, `AD_MAH_GEN_FILA` ou `AD_MAH_GEN_BASE`.
