# Layout do Dashboard — Conferência de Comissões

## Objetivo

Apresentar o Dashboard na mesma ordem da tela **Fechamento de Comissões**, com o
controle de revisão acrescentado no início da grade.

## Colunas de revisão

Estas colunas devem aparecer antes das colunas do Fechamento:

| Ordem | Campo da consulta | Descrição na grade | Configuração |
|---:|---|---|---|
| 1 | `FAROL_REVISAO` | Revisado? | Tipo `Farol(P)` |
| 2 | `STATUS_REVISAO` | Situação | Texto |
| 3 | `NOME_REVISOR` | Nome Revisor | Texto |
| 4 | `AD_DHREV` | Data e Hora da Revisão | Data e hora |

O campo `AD_REVISADO` deve permanecer disponível para a ação, mas pode ficar
oculto porque o farol e a situação já apresentam seu conteúdo.

### Configuração obrigatória do farol

O retorno `#28A745` ou `#DC3545` da consulta é intencional. Para que esses
valores sejam desenhados como indicadores, `FAROL_REVISAO` não pode estar
cadastrado como campo do tipo Texto.

No Construtor de Componentes de BI:

1. Edite o componente do tipo Tabela.
2. Acesse a aba **Expressão** e a configuração dos campos retornados.
3. Localize `FAROL_REVISAO`.
4. Defina seu tipo como **Farol(P)**.
5. Informe a descrição **Revisado?**.
6. Salve e atualize o componente.

Se o tipo do campo já criado não puder ser alterado, exclua somente a definição
visual de `FAROL_REVISAO` no componente, mantenha a coluna na consulta, atualize
os campos da expressão e recrie-a como **Farol(P)**. Não excluir
`AD_REVISADO` nem qualquer campo da chave primária da `TGFCOM`.

Resultado esperado:

- `#28A745`: indicador verde para comissão revisada;
- `#DC3545`: indicador vermelho para comissão pendente.

## Colunas do Fechamento

Configurar as colunas visíveis nesta ordem:

| Ordem | Campo da consulta | Descrição na grade |
|---:|---|---|
| 1 | `NOMEPARC` | Parceiro |
| 2 | `COMISSAO` | Comissão |
| 3 | `PERCCOM` | % Comissão |
| 4 | `VLRBAIXA` | Vlr. Baixa |
| 5 | `NUMNOTA` | Nro. Nota |
| 6 | `NUNOTA` | Nro. Único Nota |
| 7 | `COMLIQ` | Comissão Parc. Líq. |
| 8 | `VLRCOM` | Comissão Total |
| 9 | `COMISSAO_PARCELA` | Comissão Parcela |
| 10 | `DESCROPER` | Tipo Operação |
| 11 | `FINNUFIN` | Nro. Único Fin. |
| 12 | `DESCRFORM` | Fórmula |
| 13 | `VLRDESDOB` | Vlr. Desdob. |
| 14 | `DHBAIXA` | Dt. Baixa |
| 15 | `CODVEND` | Cód. Vendedor |
| 16 | `APELIDO` | Vendedor |
| 17 | `NUNOTAORIG` | Nro Único Nota Orig. |
| 18 | `CODEMP` | Cód. Empresa |
| 19 | `NOMEFANTASIA` | Nome Fantasia |
| 20 | `CODPARC` | Cód. Parceiro |
| 21 | `CODPARCVEN` | Cód. Parceiro Vendedor |
| 22 | `TIPO` | Tip. Comissão |
| 23 | `VLRDESC` | Vlr Desc. |
| 24 | `DESCRNAT` | Natureza |
| 25 | `TIPCALC_DESCRICAO` | Tip.Pgt.Comis. |
| 26 | `DESDOBRAMENTO` | Desdob. |
| 27 | `DTVENC` | Dt. Venc. |
| 28 | `CODTIPTIT` | Cód. Tip. Título |
| 29 | `DHCONCILIACAO` | Dt. Conciliação |
| 30 | `NOSSONUM` | Nosso Número |
| 31 | `DTNEG` | Dt. Negoc. |
| 32 | `DTFATUR` | Dt. Fatur. |
| 33 | `CODTIPVENDA` | Cód. Tip. Negoc. |
| 34 | `NUMOSORIG` | Nro. O.S. |
| 35 | `NUMITEMORIG` | Nro. Item O.S. |
| 36 | `TIPMOV_DESCRICAO` | Tip.Movto |
| 37 | `NUFINORIG` | Nro. Fin. Orig. |
| 38 | `TAXA` | Taxa Cartão |
| 39 | `CODEVENTO` | Cód. Evento |
| 40 | `TIPINTEGRA` | Tipo de Integração |
| 41 | `CODFORM` | Cód. Fórmula |
| 42 | `INICEXEC` | Dh. Execução |
| 43 | `DTMOV` | Dt. Movto. |
| 44 | `CODCENCUSPAD` | Cód.Centro Resultado Padrão |
| 45 | `RECDESP_DESCRICAO` | Receita/Despesa |

## Campos técnicos ocultos

Manter no resultado da consulta e marcar como não visíveis:

- `AD_REVISADO`;
- `AD_CODUSUREV`;
- `EMAIL_USUARIO_REVISOR`;
- `NUFIN`;
- `POSICAO_QUERY`;
- `TIPFATSERV`.

O campo `NUFIN` não pode ser removido, pois integra a chave primária da
`TGFCOM`. Os demais campos da chave também precisam permanecer no resultado,
mesmo quando apresentados com as descrições funcionais da tela.

## Configurações do componente

- Entidade: `ValorComissaoVendedor`.
- Permitir seleção de múltiplas linhas: habilitado.
- Ação: `Marcar Comissão como Revisada`.
- Ordenação inicial: vendedor, fórmula, nota de origem, comissão líquida e tipo.
- Formatação sugerida para valores: `#,##0.00`.
- Formatação sugerida para datas: `dd/MM/yyyy`.
- Formatação sugerida para data/hora da revisão: `dd/MM/yyyy HH:mm:ss`.
