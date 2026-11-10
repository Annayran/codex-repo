# Problemas Conhecidos

## Colunas sem cabecalho - 2026-10-07

- __EMPTY de SheetJS provocava UnrecognizedPropertyException antes de gerar nota.
- Corrigido no parser e JSP; status=0 agora exibido como falha. Validacao ERP pendente.

## Importador PVX - 2026-10-07

- Nota 278608: 149 registros e 148 itens; codigo 180 ausente; VLRTOT zero em todos os itens.
- Arquivo possui 58 totais incompatíveis com quantidade x unitario; regra a validar com cliente.
- Projeto recuperado compila e tem testes decimais, mas nao foi homologado no ERP.
- Conferir escala de QTY/UNITPRICE/TOTALPRICE e cotacao do modelo antes do deploy.
- Validar rollback de falha no segundo item e efeitos da totalizacao fiscal/financeira da TOP.

## Bugs Confirmados

- Nenhum bug confirmado por execucao em homologacao nesta sessao.

## Debitos Tecnicos

- Telecontrol ainda nao possui testes automatizados; a validacao conhecida foi compilacao Gradle.
- Existem bridges legadas para compatibilidade com metadata Sankhya; remover somente apos mapear FQCNs reais.
- Parte dos services legados ainda mistura regra, SQL/Jape, parse JSON e chamada HTTP.
- Scripts de compensacao nao possuem rollback versionado.
- JSP de compensacao contem SQL embutido em JavaScript; manter sincronizado com a query SQL versionada.

## Riscos Operacionais

- Series dependem da view `AD_TCLSER`, da fila `AD_TCLLOGSER`, de `TGFPRO.REFERENCIA`, `EP_SERIE` e comportamento da API para duplicidades.
- A origem das duplicidades em `AD_TCLLOGSER` ainda precisa ser saneada; marcar processado evita reenvio, mas nao corrige a causa.
- A sincronizacao Java `AD_TCLSER` -> `AD_TCLLOGSER` replica a numeracao manual por `MAX(ID)`; ha risco de concorrencia se dois schedulers rodarem ao mesmo tempo.
- Precos dependem de `EP_PRECOS`, `SNK_PRECO`, `PRECOVEN`, `PRECOGAR` e tabelas locais.
- GET de lista basica depende do formato real do JSON da API e da existencia das pecas em `AD_TCLPCA.REFERENCIA`.
- DELETE de lista basica por produto remove na API, mas nao apaga automaticamente os registros locais em `AD_TCLLTB`.
- `ApagarLOGBt` executa limpeza de `AD_TCLLGT`.
- `ACTION_NOTIF_COMP_DEV` usa numeracao manual baseada em `MAX(NULOG) + 1`; validar concorrencia.
- Alguns arquivos SQL podem ter diferenca de encoding/acentuacao; confirmar antes de deploy.
- Scripts auxiliares `.ai/scripts/ctx-*.ps1` dependem do comando `codex` disponivel no PATH.

## A Confirmar

- Resultado de homologacao do JAR final.
- Resultado de homologacao do `ImportadorPVX.jar` apos normalizacao de `Unit Price (USD)` com 6 casas decimais.
- Escala real dos campos `AD_IMPREGPED.UNITPRICE`, `AD_IMPREGPED.TOTALPRICE`, `TGFITE.VLRUNIT`, `TGFITE.VLRUNITMOE` e `TGFITE.VLRTOTMOE`.
- Se a geracao do item PVX deve preencher tambem `VLRTOT`, alem de `VLRUNIT`, `VLRUNITMOE` e `VLRTOTMOE`.
- Se tratar duplicidade da API como `PROCESSADO = 'S'` e suficiente para a regra de negocio.
- Se `TGFPRO.REFERENCIA` deve sempre substituir `AD_TCLLOGSER.REFERENCIA`.
- Se a view `AD_TCLSER` sempre entrega os campos e filtros esperados para formar a fila.
- Periodicidade oficial das rotinas Telecontrol e compensacao.
- Disponibilidade do `codex exec` no ambiente operacional local.
- Se o DELETE de lista basica tambem deve limpar, marcar ou reintegrar `AD_TCLLTB` local.

## Atualizacao 2026-06-26 - Achados da Auditoria Local

### Confirmado por busca no workspace

- Nao foram encontrados marcadores `TODO` ou `FIXME` relevantes no codigo/documentacao versionada.
- O dashboard OS possui IDs padrao de actions indicados na propria tela: `83`, `134` e `136`; esses IDs precisam ser confirmados no ambiente.
- O manifest do JAR local nao informa versao, origem do build ou `Main-Class`.

### Debitos tecnicos adicionais

- Fonte Java do JAR nao esta neste workspace, o que limita alteracoes diretas e revisoes por codigo-fonte local.
- JAR compilado confirma classes, mas nao permite validar todas as regras internas com a mesma seguranca de fonte Java versionado.
- A pasta `.tmp` e usada como apoio local e pode conter residuos de inspecoes; nao deve ser tratada como artefato funcional.

### Riscos operacionais adicionais

- O dashboard permite configurar IDs de rotinas Java; IDs incorretos podem acionar rotina errada ou falhar silenciosamente.
- Desfazer pagamento de OS limpa marcadores segundo a documentacao, mas o tratamento de nota/financeiro gerado permanece A confirmar.
- O fluxo de OS com peca pode gerar documento Sankhya; validar TOP, financeiro, estoque, fiscal, confirmacao, origem/destino e integracoes antes de producao.
- No pagamento de OS selecionadas, a regra confirmada e separar por `CNPJ + tipo de geracao`; homologar selecao mista para garantir que OS com peca gerem nota e OS sem peca gerem financeiro, sem mesclar os grupos.
