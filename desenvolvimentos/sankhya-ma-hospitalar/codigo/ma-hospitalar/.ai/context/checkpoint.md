# Checkpoint Tecnico

## Data

2026-08-27

## Estado Atual

O projeto Java 8 da integracao Genesis possui agendador, cliente HTTP, controle
`AD_GENMOVEST`, dashboard analitico, importador e modelagem da Base Instalada.

O importador de `MA_Data.xlsx` le as oito abas, envia lotes para
`AcaoImportarBaseGenesis` e preserva os vinculos locais nas reimportacoes.

A acao agendada foi redirecionada para usar a Base Instalada conciliada. Para
cada candidato da view, ela localiza o modelo pelo `TGFPRO.AD_IDEXTERNO`, resolve
os IDs Genesis de cliente e setor e envia somente `/itens-materiais/`. Item ja
importado usa `PUT` pelo `IDGENESIS`; item novo usa GET/POST e tem o retorno
gravado em `AD_GENITEMMAT`. O resultado operacional continua em `AD_GENMOVEST`.

O dashboard foi revisado para consolidar uma linha por `NUNOTA + SERIE`, usar
somente o historico mais recente da `AD_GENMOVEST`, paginar a grade e permitir
ordenacao por coluna. O rotulo tecnico `A_CONFIRMAR` e exibido como
`Nao classificado`. A mesma protecao contra duplicidade foi aplicada na leitura
de candidatos da acao agendada.

A coluna de erro exibe somente o botao `Ver detalhes`. A janela de diagnostico
separa mensagem funcional, entidade, operacao, HTTP, classificacao, orientacao
de correcao e detalhe tecnico. Ha tratamento para validacao, autenticacao,
permissao, duplicidade, timeout, limite de chamadas e indisponibilidade da API.

Os indicadores do dashboard foram separados em situacao atual e historico. A
situacao atual usa os equipamentos vigentes da view e o ultimo registro de cada
`CHAVE_MOVIMENTO`; o historico mantem o acumulado da `AD_GENMOVEST` para
auditoria. Nenhum registro foi excluido ou arquivado. Uma manutencao futura
depende de backup, homologacao, conciliacao de chaves, aprovacao e rollback.

## Artefatos

- `build/libs/MAHospitalar.jar`
- `build/genesis-importer-package/MAHospitalar-Genesis-Importador-JSP.zip`
- `sql/genesis/conciliacao-base-instalada/01_conciliar_todas_abas.sql`
- `docs/genesis/arquitetura.md`
- `docs/genesis/retencao-historico-integracao.md`

## Validacao

- Compilacao concluida com bytecode Java 8, major version 52.
- Oito testes do payload e da sincronizacao exclusiva de item-material aprovados.
- Sete testes de contrato do dashboard e dos artefatos aprovados.
- JavaScript validado sintaticamente e ZIP JSP autocontido conferido.
- Leitura real de `MA_Data.xlsx` aprovada: oito abas e 120.241 registros.
- O Gradle continua impedido neste host por bloqueio de conexao loopback; a
  compilacao e os testes alterados foram executados diretamente com JDK/JUnit.

## Proxima Melhor Acao

Executar a conciliacao da Base Instalada, confirmar um setor padrao quando o
cliente possuir varios setores, publicar o JAR atualizado e homologar com lote
`1`. Validar POST para item novo, PUT para item existente, `AD_GENITEMMAT` e
`AD_GENMOVEST` antes de ampliar o lote.

## Atualizacao 2026-08-27

A ação manual de reprocessamento foi corrigida para respeitar a transação
gerenciada pelo Sankhya. Ela não altera `autoCommit` nem executa `commit` ou
`rollback` diretamente; o ambiente passa a decidir a confirmação ou reversão.

O dashboard passou a consumir `TIPO_ESTOQUE`, mantendo `TIPO` como movimento.
A DDL foi corrigida para que a comparação de CNPJ altere somente
`TIPO_ESTOQUE`. `TIPO` continua derivado da `TGFESE` e `PODER` continua
derivado de `TGFEST.CODPARC`.
