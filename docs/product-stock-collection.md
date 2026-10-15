# Produto e estoque — coletor V1.5

## Dados coletados

NoteStockCollector executa duas consultas SELECT fixas no backend ERP, com NUNOTA por bind JDBC, sem SQL recebido do usuário ou receptor. A primeira lê TGFITE e LEFT JOIN TGFPRO: sequência, produto, empresa do item, local de origem, controle, unidade, quantidade negociada, indicador ATIVO e unidade padrão do produto. A segunda lê TGFEST somente para produto/empresa/local/controle presentes na nota, preservando parceiro, tipo, saldo físico e reservado em linhas separadas.

Os campos foram conferidos no catálogo local 4.35b815 e as APIs em SDK 4.35b99; nenhuma consulta foi executada pelo agente contra o banco do cliente. Homologar existência/semântica e permissões na instalação 4.36b159 antes de uso real. Restringir a ação aos operadores autorizados a consultar a nota e seu estoque: JDBC não deve ser interpretado como aplicação automática das permissões de tela.

## Limites e lacunas

- Até 50 itens, 500 linhas de estoque no total e 8 linhas por item. Truncamentos são declarados; ausência de linha não é estoque zero.
- PreparedStatement.setMaxRows usa uma linha extra para detectar truncamento. Timeout de 3 segundos por consulta. ResultSet, statements e sessão JDBC são fechados. Não há DML, alteração de sessão para escrita ou scripts de banco.
- TGFEST.ESTOQUE e RESERVADO permanecem separados. Não se calcula ESTOQUE - RESERVADO nem se aprova disponibilidade automaticamente.
- productActive só é true para ATIVO=S e false para ATIVO=N; valor desconhecido permanece null. Quantidade não positiva não é convertida em valor absoluto: required fica null e a quantidade original consta da evidência.
- Dimensões esperadas, controle de estoque de referência, propriedade do estoque, conversões, TOP histórica, eventos e causa no instante da falha continuam pendentes.
- Itens revertidos por rollback podem não existir na nota; o coletor declara a ausência e não inventa o produto mencionado no relato.
- O pacote mantém schemaVersion=1 com campos opcionais productActive e stockRows por item. Leitores antigos podem ignorar esses campos; usar receptor atualizado. Relatórios antigos continuam legíveis.

## Tratamento no receptor

PackageIntake valida o número de linhas, tamanho de campos, decimais e igualdade de empresa/local/controle com o item. Item.productActive=false gera achado PRODUTO_INATIVO; saldo físico negativo gera ESTOQUE_FISICO_NEGATIVO. Os achados são evidências reportadas no instante da coleta, sem confirmar causa raiz. A falta de política de disponibilidade preserva suas lacunas.

## Entrega e validação

Artefato comum: ZAYA-NEXO-V1.5.jar, sem credenciais. Pacote privado de homologação: ZAYA-NEXO-HOMOLOGACAO-V1.5.jar, provisionado na mesma sessão autorizada, com endpoint, credencial e expiração originais de 09/10/2026 16:27:14 (Brasília). Não criar parâmetros NEXO. Manter classe SendIncidentAction e relato MENSAGEM_ERRO.

Gradle passou com transporte, expiração, sinais de log e testes sintéticos de produto/estoque, separação de dimensões, ausência de linhas e limites. Os 17 testes Maven passaram; o primeiro empacotamento foi bloqueado pelo arquivo JAR aberto no receptor Windows. Após parar somente o receptor, o empacotamento passou e o receptor foi reiniciado na mesma sessão, com novo supervisor preservando a expiração. Um envio sintético autenticado local confirmou produto inativo, preservação de físico=3/reservado=2, disponibilidade=null e leitura de relatório antigo. Execução das consultas reais no ERP e regra de disponibilidade ainda não homologadas. IA permanece desligada.

## Rollback

Para reverter a coleta na mesma sessão, reinstalar o JAR privado V1.4 enquanto sua credencial estiver válida. O receptor novo aceita os pacotes anteriores. Nenhuma mudança de banco precisa ser revertida. Não reutilizar o pacote após a expiração da sessão.
