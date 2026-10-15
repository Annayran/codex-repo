# Problemas Conhecidos

## Bugs

- Nenhum bug confirmado no build atual. `gradlew.bat clean test jar --stacktrace` passou na ultima validacao.

## Debitos Tecnicos

- `MovimentoEstoqueRepository.lerParametros()` ainda precisa ser implementado conforme o local definitivo dos parametros seguros no Sankhya.
- `IDINTGENESIS` esta sendo gerado por `MAX(IDINTGENESIS) + 1`; ideal trocar para sequence Oracle antes de execucoes concorrentes.
- `JsonUtil` e manual; funciona para o DTO atual, mas pode ser substituido por Jackson/Gson se o contrato crescer.
- A `GenesisPostLinhaViewMain` contem uma linha fixa usada apenas para teste manual; nao deve ser tratada como fluxo oficial.

## Pontos de Atencao

- Token/API Genesis nao deve ser versionado nem exibido em logs.
- A busca atual pode processar todos os registros elegiveis da view com `SERIE IS NOT NULL`; monitorar volume antes de deixar rodar sem supervisao.
- Registros com `STATUS_INTEGRACAO = 'ENVIADO'` sao ignorados; `PENDENTE` e `REPROCESSAR` podem ser reenviados.
- O POST real ja retornou HTTP 201 em smoke/main, mas a execucao completa dentro do Sankhya ainda depende de liberar a acao agendada.
- Se a Genesis exigir IDs internos (`idCategoria`, `idFabricante`, `idFornecedor`, `idSubCategoria`) em novos cenarios, a view ou parametros auxiliares precisarao ser complementados.

## Riscos Operacionais

- Execucao concorrente pode duplicar `IDINTGENESIS` enquanto nao houver sequence.
- Token expirado/incorreto pode gerar 401 em lote.
- Falha de API 500/timeout pode acumular registros `REPROCESSAR`.
- Se `CHAVE_MOVIMENTO` nao for unica por empresa/nota/sequencia/produto/serie, pode haver controle incorreto na `AD_GENMOVEST`.
- Se a view retornar linhas com campos obrigatorios vazios, o Java marcara erro funcional na tabela de controle.

## A Confirmar

- Liberacao/configuracao da acao agendada pela Hiaria no ambiente MA Hospitalar.
- Local definitivo de armazenamento de URL/token Genesis no Sankhya.
- Criacao de sequence para `AD_GENMOVEST`.
- Periodicidade final da acao agendada.
- Obrigatoriedade real dos IDs internos da Genesis em todos os cenarios.