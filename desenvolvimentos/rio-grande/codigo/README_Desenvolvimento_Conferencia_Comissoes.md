# Conferência de Comissões

## Objetivo

Permitir a conferência e a revisão das comissões disponíveis no Fechamento de
Comissão antes do fechamento e da geração do financeiro. Somente registros
revisados são enviados no relatório.

## Arquivos

- `Dashboard_Conferencia_Comissoes.sql`: consulta do componente de tabela.
- `Layout_Dashboard_Igual_Fechamento.md`: ordem, descrição e visibilidade dos
  campos para reproduzir a tela Fechamento de Comissões.
- `Marcar_Comissao_Revisada.js`: ação da entidade `ValorComissaoVendedor`.
- `Comissao_Conferencia.jrxml`: relatório independente, sem sub-relatório.
- `Agendamento_Relatorio_Comissoes.sql`: destinatários e parâmetros mensais.

## Regra de relacionamento confirmada

A `VGFCOM_FECH` é relacionada à `TGFCOM` por:

- `CODVEND`;
- `NUNOTAORIG`;
- `NUFINORIG`;
- `NUMOSORIG`;
- `NUMITEMORIG`;
- `TIPO`;
- `CODFORM`.

Não comparar `TGFCOM.NUFIN` com `VGFCOM_FECH.FINNUFIN`. No teste do vendedor 3,
abril/2026, a comparação eliminava todos os 14 registros. Sem ela, os 14
registros permaneceram e cada linha encontrou no máximo uma `TGFCOM`.

O campo `TGFCOM.NUFIN` continua no `SELECT` do Dashboard porque integra a chave
primária da entidade e é necessário para a ação localizar o registro.

## Comportamento da revisão

A ação grava na `TGFCOM`:

- `AD_REVISADO = 'S'`;
- `AD_CODUSUREV = getUsuarioLogado()`;
- `AD_DHREV = SYSDATE`.

Uma comissão já revisada preserva o primeiro usuário e a primeira data/hora.

Quando uma nota possui mais de um desdobramento na view, todas as parcelas podem
apontar para a mesma comissão da `TGFCOM`. Nesse caso, a revisão é feita no nível
da comissão/nota, não individualmente por parcela financeira.

## Regra do relatório

O relatório:

- consulta a `VGFCOM_FECH` para obter os valores calculados;
- utiliza a `TGFCOM` somente para identificar as linhas revisadas;
- não exige `NUFECHAMENTO`;
- não exige financeiro já gerado;
- agrupa os dados por parceiro e nota;
- usa `MAX(VLRBAIXA)` para não repetir o valor nas parcelas;
- usa `SUM(COMLIQ)` para recompor a comissão total da nota;
- para `TIPCALC = 'N'`, usa `TGFCAB.VLRNOTA` como valor-base e `DTNEG` como data
  de referência;
- não apresenta produtos e não utiliza sub-relatório.

## Implantação

1. No componente de tabela do Dashboard, substituir a consulta pelo conteúdo de
   `Dashboard_Conferencia_Comissoes.sql`.
2. Manter a entidade `ValorComissaoVendedor` e habilitar a seleção de múltiplas
   linhas.
3. Manter no resultado os oito campos da chave primária da `TGFCOM`, ainda que
   sejam ocultados visualmente.
4. Na ação `Marcar Comissão como Revisada`, substituir o script pelo conteúdo de
   `Marcar_Comissao_Revisada.js`.
5. Importar e compilar somente `Comissao_Conferencia.jrxml`.
6. Na consulta do agendamento, utilizar
   `Agendamento_Relatorio_Comissoes.sql`.

## Validação sugerida

### Dashboard

1. Informar vendedor 3 e período de 01/04/2026 a 30/04/2026.
2. Confirmar a apresentação dos 14 registros encontrados na view.
3. Confirmar que registros ainda não revisados aparecem como pendentes.
4. Selecionar uma comissão e executar a ação.
5. Atualizar o componente e confirmar situação, usuário e data/hora.
6. Para notas com dois desdobramentos, confirmar que ambos refletem a revisão da
   mesma comissão.

### Relatório

1. Executar o relatório com o mesmo vendedor e período.
2. Confirmar que somente notas revisadas aparecem.
3. Conferir parceiro, nota, data, valor-base e comissão.
4. Para a nota 91043, confirmar que a comissão resulta da soma dos valores de
   `COMLIQ` e que o valor-base não é duplicado pelos desdobramentos.
5. Confirmar o total de baixa e de comissão por parceiro.

### Agendamento

1. Executar a consulta do agendamento em modo de teste.
2. Confirmar o destinatário, e-mail, vendedor e período do mês anterior.
3. Confirmar que vendedores sem registro revisado no período não são retornados.

## Rollback

1. Restaurar no componente a consulta anterior do Dashboard.
2. Restaurar o JavaScript anterior da ação.
3. Reimportar a versão anterior do JRXML.
4. Restaurar a consulta anterior do agendamento.

O rollback não requer alteração de dados. As marcações de revisão já realizadas
permanecem na `TGFCOM`; caso seja necessária qualquer reversão desses dados, ela
deve ser autorizada e planejada separadamente.

## Pendência de homologação

A execução das consultas e a compilação do JRXML precisam ser realizadas no
ambiente Sankhya. A regra foi validada com os resultados fornecidos, mas não foi
executada diretamente contra o banco por este workspace.
