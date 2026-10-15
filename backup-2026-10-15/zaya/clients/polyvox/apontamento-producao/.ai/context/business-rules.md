# Regras de Negócio

## Entidades Principais

- `AD_VGFAPO`: origem dos apontamentos pendentes. O SQL atual seleciona registros com `STATUS = 'P'`.
- `CabecalhoApontamento`: DAO usado para buscar o apontamento criado pelo `NUAPO`.
- `ApontamentoPA`: entidade atualizada com `QTDAPONTADA` e `AD_SEQVOL`.
- `SerieProduto`: entidade usada para gravar séries associadas à nota/produto.
- `TPRAPO`: tabela atualizada em caso de erro, preenchendo `OBSERVACAO`.
- `TPRAPF`: origem de `NUNOTA` por `NUAPO`.
- `TPRSERPA`: tabela atualizada com `LIBERADO='S'` e `NUAPO`.
- Tabelas auxiliares na consulta de série: `TPRIATV`, `TGFCAB`, `TGFCON2`, `TGFIVC`, `TGFPRO`.

## Processos

- Buscar apontamentos pendentes em `AD_VGFAPO` com `STATUS = 'P'`.
- Para cada apontamento pendente:
  - ler `IDIATV`, `QTDAPONTADA`, `SEQVOL` e `IDIPROC`;
  - criar apontamento de atividade usando `ApontamentoHelper`;
  - alterar a quantidade apontada em `ApontamentoPA`;
  - validar/confirmar apontamento;
  - confirmar apontamento via `OperacaoProducaoSPBean.confirmarApontamento`;
  - atualizar `AD_SEQVOL` em `ApontamentoPA`;
  - localizar nota e séries associadas;
  - gravar séries em `SerieProduto`;
  - liberar série em `TPRSERPA`.

## Validações

- O código chama `ApontamentoHelper.validarConfirmacaoApontamento(apontamentoVO, true)` antes da confirmação principal.
- A confirmação monta parâmetros XML como `ACEITARQTDMAIOR=false` e respostas de último apontamento/série.
- Regras internas dessas validações pertencem às APIs Sankhya e precisam ser confirmadas na plataforma.

## Cálculos

- Não há cálculos complexos identificados.
- O código usa `BigDecimalUtil.getValueOrZero` para normalizar `NUNOTA`, `IDIPROC` e `CODPROD` quando consulta séries.
- `CODUSU` é usado como `BigDecimal.ZERO` em pontos da confirmação/gravação de série.

## Restrições

- Apenas apontamentos com `STATUS = 'P'` são processados pela consulta atual.
- O usuário de autenticação usado no agendamento é `SUP`.
- A rotina define propriedades de sessão relacionadas à geração de nota de produção, último apontamento e liberação de série.
- O update em `TPRSERPA` depende de `IDIPROC`, `CODPRODPA` e `SERIEPA` encontrados na consulta.

## Regras a Confirmar

- Significado funcional completo de `STATUS = 'P'` em `AD_VGFAPO`.
- Responsável por alterar o status do registro processado em `AD_VGFAPO`; o código atual não mostra atualização direta desse status.
- Motivo de autenticar sempre como usuário `SUP`.
- Se `ACEITARQTDMAIOR=false` é regra permanente ou configuração de negócio.
- Se `RESPOSTA_SERIE_LIBERADO=true` e demais flags são fixas para todos os cenários.
- Se `AD_SEQVOL` é obrigatório para todos os apontamentos PA.
- Como tratar falhas parciais quando um registro é criado, mas série ou confirmação falha.
- Se `org.example.Main` deve existir no projeto ou é apenas resíduo de template.