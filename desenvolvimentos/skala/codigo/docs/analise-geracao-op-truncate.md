# Investigação — geração de OP e NPE em truncate

Data: 2026-11-04. Projeto: SKala. Fontes: três imagens fornecidas no chat e classes do 50_Skala.jar extraídas seletivamente do jares_tsijar.zip em Downloads. Não houve execução no Sankhya, acesso ao banco ou alteração de código.

A imagem identifica AD_PLTPRPRGP, ação 229, módulo 50, com classe Java iniciada por tech.skala.action.modules.manufatura.pcp e controle manual de transação. A classe completa está truncada na imagem; o caminho compatível no JAR é tech.skala.action.modules.manufatura.pcp.programacaosimplificada.GeraOrdemProducaoAction. A imagem de erro informa Registro não processado / Registro não encontrado / Erro interno (NPE) / tech.skala.core.util.number.SkalaBigDecimalUtils.truncate, linha 13.

No JAR analisado, GeraOrdemProducaoAction chama ProgramacaoProducaoSimplificada.loadByRecord antes de GeraOrdemProducaoProcessor.gerarOP. O carregamento instancia ProdutoVenda a partir de CODPRODPA e chama loadByPk. ProdutoVenda.loadByVO lê CODGPROD e chama truncate(codGProd,-2) e truncate(codGProd,-4), calculando seção e departamento. CODGPROD é distinto de CODGRUPOPROD, também lido pela classe. Não foi confirmado o rótulo de CODGPROD na tela.

SkalaBigDecimalUtils.truncate, linha 13, executa value.setScale(scale,RoundingMode.DOWN) sem proteção contra null. Um argumento value nulo causa exatamente NPE nessa linha. ProdutoVenda.loadByPk captura Exception e relança RecordNotFoundException: a mensagem de registro não encontrado pode encapsular o NPE e não prova ausência do produto.

Conclusão: mecanismo da falha comprovado no artefato; CODGPROD nulo no produto é a hipótese diretamente sustentada por esse fluxo. Falta conferir os valores da programação/produto e confirmar a versão implantada e o stack trace completo. Não é possível atribuir o erro a um produto específico só pela lista ao fundo da imagem.

Validação sugerida (somente leitura): SELECT CODPROD, CODGPROD, CODGRUPOPROD FROM TGFPRO WHERE CODPROD = :CODPROD; usar o CODPRODPA da programação que falhou. Conferir o mapeamento do campo no ambiente se a coluna não existir fisicamente. Se CODGPROD for nulo, determinar com o funcional a classificação correta; se não for, obter stack trace e JAR implantado. Não substituir null por zero indiscriminadamente: classificação também é utilizada posteriormente no fluxo.

A falha ocorre antes da chamada de geração para a linha afetada; isso não permite garantir rollback de outras linhas de uma seleção em lote, especialmente com transação manual. Antes de reexecutar, conferir OPs já vinculadas à programação.

Próxima ação: identificar NUPRGP/CODPRODPA afetados, conferir CODGPROD e obter stack completo se necessário. Nenhum patch, build, commit, deploy ou ação remota realizado.

## Confirmação pelo log recebido em 04/11/2026

Fonte: Downloads/server.log_20261104152709.zip, entrada server.log; leitura filtrada sem extrair configuração/conexão. Ocorrências do mesmo caminho em 04/11/2026 às 15:24:35, 15:25:41 e 15:26:45 (horários registrados no log, fuso não confirmado).

Stack confirmado: SkalaBigDecimalUtils.truncate:13 -> ProdutoVenda.loadByVO:76 -> ProdutoVenda.loadByPk:55 -> ProgramacaoProducaoSimplificada.loadByRecord:198 -> GeraOrdemProducaoAction.doAction:53. Também aparece ProdutoVenda.loadByPk:58, compatível com encapsulamento da exceção.

O stack do ambiente confirma o caminho identificado no artefato: a linha 76 da fonte analisada executa truncate(codGProd,-2), após leitura de CODGPROD. Falha no carregamento antes da geração de OP para a linha afetada. A filtragem não identificou CODPROD/NUPRGP do registro. Próxima ação permanece conferir CODGPROD no produto selecionado. Sem alteração de código ou banco.
