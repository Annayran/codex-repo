---
entregavel:
validacao_por:
validacao_canal:
validacao_evidencia:
pendencia:
pendencia_resolvida: false
pendencia_acordada_com:
pendencia_responsavel:
pendencia_fup:
previsao:
id_apontamento:
horarios_confirmados: false
origem_horarios: informado
confirmado_em:
tipo_registro: apontamento
data: "{{date:YYYY-MM-DD}}"
empresa:
projeto:
demanda:
produto:
servico:
consultor:
numero_os:
item_os:
total_itens_os:
numero_os_parceiro:
numero_os_relacionada:
status_os: pendente
motivo:
inicio:
fim:
intervalo_minutos: 0
quantidade_horas:
descricao:
status_lancamento: nao_tentado
horas_lancadas: false
tentativa_lancamento_em:
quantidade_tentativas: 0
motivo_nao_lancamento:
mensagem_erro:
bloqueado_por:
proxima_tentativa:
data_lancamento:
conferencia_administrativa: false
tags:
  - apontamento
---

# {{title}}

> [!info] Fluxo administrativo
> Use `status_lancamento: nao_tentado`, `falhou` ou `lancado`. Quando uma tentativa falhar, mantenha `horas_lancadas: false` e registre os detalhes da falha abaixo.

## Identificação da OS

| Campo | Informação |
|---|---|
| Produto |  |
| Serviço |  |
| Consultor |  |
| OS |  |
| Item / total de itens |  |
| OS parceiro |  |
| OS relacionada |  |
| Status da OS | Pendente |
| Motivo |  |

## Execução

| Data | Início | Fim | Intervalo | Duração total |
|---|---|---|---|---|
| {{date:YYYY-MM-DD}} |  |  | 0 min |  |

## Atividade realizada

**ENTREGÁVEL:** <Informe o que foi feito ou entregue, incluindo análise ou proposta para testes.>

**VALIDAÇÃO:** <Informe quem validou (nome/e-mail), o canal e a evidência. Se não houve validação, registre isso explicitamente.>

**PENDÊNCIA:** <Informe o que falta, com quem foi acordado, responsável do cliente quando aplicável e data do FUP. Não presuma acordos.>

**PREVISÃO:** <Informe a próxima data de execução acordada. Se não foi definida, registre “Não informada”.>

Para registros gerenciados pelo utilitário, preencha os campos correspondentes nas propriedades ou pelo comando `os`; a exportação monta os quatro blocos. Não considera testes técnicos como aprovação do cliente.

## Tentativa de lançamento

- **Resultado:** Não tentado
- **Data e hora da tentativa:**
- **Quantidade de tentativas:** 0
- **Motivo de não lançamento:**
- **Mensagem apresentada pelo sistema:**
- **Bloqueado por:**
- **Próxima tentativa:**

### Providência necessária

- [ ] Identificar e corrigir o impedimento.
- [ ] Realizar nova tentativa.
- [ ] Ao concluir, usar `status_lancamento: lancado`, marcar `horas_lancadas: true` e preencher `data_lancamento`.

## Evidências e links

## Conferência dos horários

Confirme início, fim e pausas antes de lançar. A duração do agente não comprova horas humanas. Não some subagentes simultâneos.

## Encerrar um retorno

Quando a pendência do cliente for resolvida, marque `pendencia_resolvida: true` e registre a solução no histórico. Lançar as horas não encerra automaticamente uma pendência do cliente.
