# Compensação na confirmação da nota — candidato de homologação

## Entrega

Classe `br.com.zaya.proauto.compensacao.CompensacaoCreditoEvento`, implementando `EventoProgramavelJava`, vinculada à entidade `CabecalhoNota` (TGFCAB). O processamento ocorre em `beforeUpdate`, exclusivamente quando TIPMOV=V e STATUSNOTA muda de outro valor para L. Inserção direta já liberada não dispara. Alteração comum de nota liberada não dispara.

Não cadastrar esta classe como ação agendada. Desativar o agendamento anterior da compensação antes de ativar o evento. A rotina de fila de reserva é outro processo e permanece inalterada.

## Configuração mantida

| Preferência/campo | Preenchimento |
| --- | --- |
| TGFPAR.AD_PRACCFLAG | S para participar; N/nulo não participa |
| PRACCDIAS | Inteiro, 30; idade pela inclusão do crédito (DHMOV), não pelo vencimento |
| TIPTITCREDCLI | Tipo de título nativo, um código positivo |
| TOPBAIRECCOMP | TOP nativa de baixa da receita |
| TOPBAIDESPCOMP | TOP nativa de baixa da despesa |

Não usa PRACCSIM, PRACCFLAG, PRACCLIM, PRACCTREC ou PRACCTDES. Não há simulação: o helper executa compensação real.

## Execução e transação

Receitas restritas à NUNOTA em confirmação, mesmo parceiro/empresa. Créditos pelo parceiro/empresa, tipo nativo e idade mínima. A situação da nota é lida do VO em alteração; não se exige STATUSNOTA=L já persistido no banco antes do update.

Usa JdbcWrapper e TransactionContext recebidos do evento. Não abre/fecha a conexão do chamador, não cria transações próprias e não faz commit. Marcador por nota na transação evita reentrada. Locks NOWAIT do parceiro e dos títulos, com revalidação, evitam consumo concorrente. Erros são propagados; o rollback da confirmação e dos acertos precisa ser demonstrado no ERP.

Reconsulta pares após cada acerto enquanto houver crédito e receita elegíveis. Detecta repetição do mesmo par/saldos sem avanço. Diferentemente do agendamento anterior, não depende de uma próxima rodada. O vínculo NUNOTA e a elegibilidade dos resíduos gerados pelo helper precisam ser validados em compensações parciais.

Preserva os filtros do candidato anterior: títulos efetivos, abertos, sem baixa parcial, moeda local, sem NOSSONUM, encargos, descontos ou retenções. Portanto ainda não representa cobertura de todos os cenários financeiros do escopo. Se há crédito elegível e nenhuma receita elegível antes do primeiro acerto, bloqueia a confirmação para não ignorar silenciosamente o processo. Sem crédito elegível, permite seguir.

## Logs

Logger Java `br.com.zaya.proauto.compensacao.CompensacaoCreditoEvento`, prefixo PRACC, identificador de execução e NUNOTA. Registra início, parceiro desabilitado, acerto/NUFIN/valor, fim e classe da falha. PROCESSADO_NA_TRANSACAO não comprova commit: logs podem permanecer mesmo após rollback. Não grava campo customizado de controle/dashboard. Escritas financeiras pertencem a CompensacaoFinanceiraHelper.

## Publicação em homologação

1. Usar exclusivamente o novo `proauto-compensacao-credito-evento-hom.jar` produzido pelo script `scripts/compensacao/Validar-Compensacao.ps1`; cada build usa pasta isolada para não incluir classes antigas.
2. Desativar a ação agendada de compensação existente e guardar sua configuração para referência. Não manter os dois gatilhos ativos.
3. Carregar o JAR no módulo Java de homologação e cadastrar o evento Java na entidade CabecalhoNota/TGFCAB, classe completa acima. Não usar instruções de transação automática da antiga ação agendada.
4. Testar primeiro com nota e parceiro controlados, sem concorrência operacional. Não habilitar em produção antes de validar os itens abaixo.

## Validação obrigatória no Sankhya Om 4.36b139

- Financeiros efetivos da nota devem existir em beforeUpdate. Verificar se alguma etapa posterior recalcula/recria os títulos; se ocorrer, este ponto não está homologado e será necessário rever o momento de execução com o responsável.
- Validar 29/30/31 dias desde DHMOV, flag N/S e isolamento entre notas do mesmo parceiro e entre empresas.
- Testar ausência de crédito, ausência de financeiro elegível, várias parcelas/créditos, sobra parcial, vínculo do resíduo e inexistência de compensação duplicada.
- Provocar erro posterior ao helper na confirmação e comprovar rollback integral de nota, acerto e títulos. Testar falha de TOP e concorrência/lock.
- Confirmar que alteração de nota já liberada não aciona; testar desfazer/reconfirmar conforme permissões nativas.
- Conferir NUACERTO e títulos no ERP; log isolado não comprova sucesso.

Compilação local usa SDK disponível 4.23b244, diferente do destino 4.36b139. Testes unitários e build não comprovam compatibilidade binária nem a ordem dos listeners da versão destino. Instalação/execução no ERP ainda pendentes.

## Reversão

Desativar o evento e restaurar o módulo anterior se necessário. Não reativar automaticamente o agendamento. Acertos já confirmados exigem desfazimento pelo processo nativo autorizado; retirar o JAR não desfaz lançamentos.

## Correção da pergunta nativa — 21/09/2026

O evento, após processar com sucesso um parceiro habilitado, define a propriedade de sessão `central.notas.pode.compensar` como STRING `false`. Na biblioteca local, `CompensacaoDevolucaoHelper.validaIniciaCompensacao` consulta essa propriedade: sem valor monta a pergunta, com `true` segue o registro nativo e com `false` não solicita a pergunta nem registra outra compensação por esse ramo. Não é um parâmetro TSIPAR. Não se responde Sim automaticamente.

A escrita acontece também quando não há crédito elegível pelos 30 dias, para não oferecer automaticamente créditos fora da regra. Se houver erro, o evento propaga a exceção antes de instalar essa supressão. Parceiro sem AD_PRACCFLAG=S não altera a propriedade.

Como o controle é de sessão, o candidato aceita apenas confirmação individual com identificação explícita da mesma nota no requestBody: nota/@NUNOTA ou notas/nota (texto ou atributo NUNOTA). Lote, contexto ausente, estrutura desconhecida ou divergência bloqueiam a confirmação do parceiro habilitado antes de qualquer acerto. Uma segunda nota na mesma sessão marcada também é recusada. Não há suporte homologado a confirmação em lote, integração ou outros serviços.

Pré-condição: COMPENSACREDCLI (chave interna mge.com.compensacao.credito.automatica) desligado. Se ligado, o helper nativo sobrepõe sua decisão, por isso o evento interrompe antes de compensar. AVISARCREDCLI permanece conforme o fluxo manual existente. Nenhuma preferência foi alterada automaticamente.

A propriedade permanece até o encerramento da sessão Jape pelo serviço chamador; não é restaurada no finally do evento, pois isso ocorreria antes da validação nativa posterior. Esse ciclo e a ausência de sobrescrita posterior precisam ser comprovados na 4.36b139. O candidato não substitui classes nativas nem altera parâmetros globais.

Testes locais: 20 testes passaram, incluindo identificação individual, rejeição de lote, divergência/ausência de contexto e reutilização para outra nota. Não são testes de integração com o ERP.

Homologar: flag S com crédito elegível (um acerto sem pergunta); flag S só com crédito jovem (sem acerto e sem pergunta); flag N (pergunta nativa preservada); falha de compensação (confirmação cancelada); duas notas sequenciais de parceiros S/N (sem herança de supressão); lote (mensagem de recusa); automático nativo ligado (recusa). Conferir financeiro e rollback, além do log POPUP_NATIVO_DISPENSADO. Esse log comprova apenas que a propriedade foi definida, não que a interface efetivamente deixou de exibir o diálogo.
