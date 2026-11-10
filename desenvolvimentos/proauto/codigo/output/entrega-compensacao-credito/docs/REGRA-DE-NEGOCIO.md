# Regra de negócio — Compensação automática de crédito de cliente

## Objetivo e acionamento

Aplicar os créditos elegíveis do cliente às receitas da nota de venda em confirmação, sem exigir resposta à pergunta nativa de compensação quando o parceiro participa da automação.

A implementação atual é um EventoProgramavelJava, classe `br.com.zaya.proauto.compensacao.CompensacaoCreditoEvento`, na entidade CabecalhoNota/TGFCAB. Executa em beforeUpdate quando TIPMOV=V e STATUSNOTA muda de um valor diferente de L para L. Uma edição de nota já confirmada não dispara. Inserção direta já confirmada também não dispara. Não é ação agendada.

## Participação e preferências

| Campo/preferência | Uso |
| --- | --- |
| TGFPAR.AD_PRACCFLAG | S habilita a automação; N ou vazio mantém o comportamento manual existente |
| PRACCDIAS | Idade mínima do crédito em dias de calendário; valor acordado: 30 |
| TIPTITCREDCLI | Um código positivo de tipo de título elegível ao crédito |
| TOPBAIRECCOMP | TOP nativa de baixa da receita |
| TOPBAIDESPCOMP | TOP nativa de baixa da despesa |
| COMPENSACREDCLI | Deve permanecer desligado; o automático nativo poderia sobrepor o controle e gerar processamento adicional |
| AVISARCREDCLI | Preservar conforme a configuração manual existente |

PRACCSIM, PRACCFLAG, PRACCLIM, PRACCTREC e PRACCTDES não são utilizados. A flag AD_PRACCFLAG é campo de parceiro, não preferência global. Não existe modo de simulação: se instalado e acionado, o candidato solicita acertos reais ao helper.

## Seleção e compensação

1. Verificar a transição da nota e a flag do parceiro.
2. Para parceiro habilitado, validar contexto individual da Central e ausência do automático nativo global.
3. Buscar créditos do mesmo parceiro e empresa, do tipo definido em TIPTITCREDCLI, com idade mínima contada desde DHMOV. O dia de inclusão que completa 30 dias participa por inteiro; vencimento não determina a idade.
4. Buscar receitas exclusivamente da NUNOTA em confirmação, do mesmo parceiro e empresa.
5. Ordenar candidatos por vencimento e NUFIN, bloquear e revalidar os títulos; compensar o menor dos dois saldos pelo CompensacaoFinanceiraHelper.
6. Reconsultar enquanto houver pares elegíveis. Detectar repetição do mesmo par/saldos sem avanço.
7. Após sucesso, definir o controle de sessão que dispensa a pergunta e o registro nativo adicional por esse ramo. Isso também se aplica quando não há crédito elegível, evitando oferecer crédito mais novo que a regra.

Sem crédito elegível, a confirmação segue. Havendo crédito elegível e nenhuma receita elegível antes do primeiro acerto, o evento interrompe a confirmação para não ignorar o processamento silenciosamente. Qualquer erro é propagado para o serviço de confirmação. Não há commit próprio: o comportamento de rollback integral precisa ser demonstrado no ERP.

## Limites atuais do candidato

- Apenas confirmação individual, identificada explicitamente no pedido da Central. Lote ou contexto não reconhecido é recusado para o participante, antes de compensar. O controle nativo encontrado é de sessão, e não por nota.
- Títulos efetivos, abertos e com principal positivo; sem baixa parcial, moeda estrangeira, NOSSONUM, juros, multas, descontos ou retenções. Estes filtros são restrições da implementação atual, não afirmação de cobertura integral do escopo.
- A existência dos financeiros em beforeUpdate e sua não recriação em etapas posteriores precisam de validação na versão destino.
- Compensações parciais exigem verificar os resíduos e a preservação do vínculo NUNOTA pelo helper.
- A implementação usa um controle interno identificado nas bibliotecas locais, `central.notas.pode.compensar`, com a string false. Ele não é uma preferência a cadastrar. Sua eficácia e ciclo de vida no ambiente destino ainda não foram comprovados.
- Nenhum campo customizado de auditoria ou dashboard é preenchido. Dashboard está fora desta entrega.

## Rastreabilidade

Logs no servidor Java com prefixo PRACC, identificador de execução e NUNOTA: INICIO, IGNORADO, PROCESSADO_NA_TRANSACAO (acerto, títulos e valor), POPUP_NATIVO_DISPENSADO, FIM e FALHA.

PROCESSADO_NA_TRANSACAO/FIM não comprovam commit. POPUP_NATIVO_DISPENSADO indica apenas que o código aplicou a propriedade. Verificar nota confirmada e acerto no financeiro, além do comportamento da interface.

## Aceite pendente

Homologar com responsável funcional: parceiro S/N; créditos de 29/30/31 dias; ausência de crédito; falta de receita elegível; total e parcial; múltiplas parcelas; falha e rollback; concorrência; ausência de popup para participante; manutenção do popup para não participante; notas sequenciais sem herança de controle; recusa de lote.

Não há aceite de cliente/gestor registrado nesta entrega, nem data de homologação acordada. Não publicado no ERP. Para implantação e reversão, consultar o guia técnico incluso; desativar eventual agendamento anterior de compensação antes de ativar o evento, preservando outros processos.
