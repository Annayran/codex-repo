# OS 8677 - Correcao integrada candidata

Base: DDL extraido do banco e fornecido pelo usuario. Nao representa recuperacao de versao historica anterior a intervencao.

## Alteracoes funcionais

1. ALTERACAO: exclui CODOCOR 14, mantendo o calculo dessa ocorrencia em REDUCAO.
2. GANHOS: remove LEFT JOIN TCSPRE PRE2 sem uso e o filtro ja comentado que o referenciava.
3. GANHOS: busca o preco anterior com P1.REFERENCIA < OCC.DTOCOR em vez de < PRE.REFERENCIA. Para ocorrencia posterior ao ultimo preco, o mesmo preco pode ser a base e a variacao resulta zero. Os demais criterios de elegibilidade do preco foram preservados.

Preservados: periodicidade mensal, colunas, seis blocos, regras de novos contratos e cancelamentos, inclusive filtros legados do cancelamento. Sem DISTINCT, sem divisao pela quantidade de precos. FORCE removido do deploy para nao aceitar criacao forcada de view com erros.

## Validacao e deploy

1. Executar teste-antes-depois.sql no schema SANKHYA, na mesma base dos arquivos de evidencia. E somente SELECT; comparar valores, datas, quantidades e classificacoes e exportar o resultado.
2. Expectativas para 328/1051: 31 linhas, soma VLR 2388,66 e perdas zero. Para 338/1048: aditivo 15/07/2022 com VLR zero; reducao 02/07/2026 com VLR_CAN 873,185; aditivo 03/07/2026 com VLR 540,38. Para 627/1048: reducao 01/09/2026 com VLR_CAN 1369,044; aditivo 02/09/2026 com VLR 258,95. Todos esses movimentos uma vez.
3. Conferir tambem 610, 978, 779 e 883. O saldo aritmetico nao reproduz automaticamente os filtros/classificacoes do dashboard. Nao afirmar validacao integral apenas pela ausencia de repeticoes.
4. Apos validacao em homologacao, salvar o DDL vigente imediatamente antes do deploy. Executar deploy.sql e conferir STATUS em ALL_OBJECTS e erros em ALL_ERRORS para SANKHYA.VW_ZAYA_EVOLUCAO_MRR. Atualizar grade e graficos e conferir o mesmo periodo e filtros da cliente.
5. Em regressao, executar preferencialmente o DDL salvo imediatamente antes do deploy. rollback.sql e copia do DDL anteriormente extraido e fornecido nesta conversa, acrescido do terminador SQL.

## Limites e evidencias

Arquivos (2) e (3): ocorrencias unicas na origem e ganhos multiplicados por 35 no contrato 328.
Arquivo (4): remocao do JOIN elimina repeticoes nos sete contratos; exclusao de 14 em ALTERACAO preserva reducoes e aditivos dos casos principais.
Arquivos (5) e (6): precos e ocorrencias confirmam aditivo sem preco proprio no contrato 338 em 15/07/2022, que repetia 1005,51.

A terceira alteracao e uma correcao proposta a partir dessas evidencias, ainda nao executada no Oracle. Nao houve nova validacao do dashboard, aceite funcional ou deploy nesta sessao. O teste de excluir uma ocorrencia em homologacao e atualizar o MRR permanece pendente; a investigacao confirmou duplicacao de calculo, nao uma falha de sincronizacao de exclusoes.
