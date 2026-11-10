# Contrato proposto de simulação - A5 / I1

Proposta local para alinhamento com Fred/Duílio em G1. Ainda não define API
HTTP, endpoint Sankhya, nome de ação, componente HTML5 ou nomes físicos.
Implementação: `br.com.zaya.belmicro.rateio.aplicacao.SimulacaoService`.

## Entradas

- `abrirContexto(origem, usuario)`: origem tem `tipo` (NOTA ou TITULO) e `id`.
- `simular(origem, entrada, usuario, solicitacaoId)`.
- `recalcular(simulacaoId, revisaoEsperada, entrada, motivo, usuario, solicitacaoId)`.
- `usuario`: exclusivamente da sessão autenticada do servidor; não é campo
  confiável de JSON do navegador. O adaptador deve obtê-lo da sessão.
- `solicitacaoId`: chave não vazia, até 80 caracteres, conforme CHAVESOLIC no manual. Gerar uma por intenção
  e reutilizá-la em reenvios após timeout. Não reutilizar para outra operação.
- `motivo`: obrigatório no recálculo, até 255 caracteres, conforme MOTIVORECALC no manual, sem dados pessoais.

Manual: `EntradaSimulacao.manual(percentuais)` recebe somente empresa, CR e
percentual global positivo por linha, até seis casas e soma exata de 100.
Critério: `EntradaSimulacao.criterio(id, versao)` exige seleção explícita.
Não há base, competência arbitrária, cabeçalho ou valor final na entrada do
navegador. Esses dados vêm dos mapeamentos autoritativos dos repositórios.

## Respostas

`ContextoModal`: origem canônica e base/competência mapeadas; lista de critérios;
fotografia corrente, quando existir; indicador de efetivação existente e
permissão de simular. A lista `pendencias` apresenta códigos funcionais quando
base/competência ou critérios estiverem incompletos, sem ocultar o contexto.
Não recalcula nem escreve. Falhas técnicas continuam propagando.

`SimulacaoRateio`: ID estável por origem, revisão, dados mapeados da origem,
entrada imutável, cálculo (parcelas, grupos e percentuais), hash de consistência,
situação, código da pendência, usuário, instante UTC e motivo.

- VALIDA: cálculo e hash disponíveis, sem pendência. Ainda exige revalidação
  e autorização na efetivação futura; não comprova representação nativa.
- COM_PENDENCIA: código funcional, sem cálculo nem hash reaproveitados.
- SUBSTITUIDA: cópia histórica anterior; preservar entrada, cálculo e auditoria.

Mensagens de adaptadores podem conter dados privados. Somente o código de
pendência é persistido pelo serviço; mapear mensagens próprias no modal.

## Comportamento

- A origem financeira ligada a nota deve convergir para NUNOTA no adaptador.
  Auxiliar marcado é rejeitado; permissões também são conferidas na origem
  canônica, antes de qualquer retorno idempotente.
- Uma simulação inicial já existente exige recálculo com revisão esperada.
  Isso evita sobrescrever silenciosamente trabalho de outro usuário.
- Mesmo comando, origem, usuário, entrada, revisão e motivo com a mesma chave
  retorna a fotografia original, sem ler fontes de pesos ou criar nova revisão.
  Mudanças de ordem de linhas/escala decimal não alteram essa identidade.
- Outra identidade com a mesma chave retorna SOLICITACAO_INCOMPATIVEL.
- Recálculo obsoleto retorna SIMULACAO_DESATUALIZADA. Recálculo com pendência
  ainda cria nova revisão corrente e substitui a anterior.
- Origem já efetivada: novos comandos de simulação retornam
  RATEIO_JA_EFETIVADO; o cliente deve abrir o contexto de consulta. Replay de
  solicitação anterior válida conserva a resposta original, sem geração.
- Falhas técnicas propagam e exigem rollback. Nenhum registro financeiro é
  criado por este serviço. Registro técnico sanitizado em transação separada
  ficará no futuro adaptador de infraestrutura.

## Obrigações dos adaptadores de produção

- OrigemRepository: resolver identidade canônica, marcador, competência/base,
  campos materiais normalizados, destinos e mapeamento de criação aprovados.
- CriterioRepository: somente critérios ativos/aprovados/vigentes/aplicáveis,
  rejeitar versões conflitantes, validar percentuais da matriz e fontes
  analíticas por categoria sem fallback silencioso.
- SimulacaoRepository: transação única e locks por origem e solicitação,
  compartilhados com efetivação; ID nativo, unicidade de chave e revisão
  corrente; preservar histórico; salvar revisão e solicitação atomicamente.
  A referência de uma solicitação deve apontar para sua revisão original,
  mesmo quando essa revisão já não for a corrente.
- AutorizacaoRateio: obter sessão e validar escopo de empresas e operações.
- ExecucaoRepository: consultar a efetivação canônica, sem gerar títulos.
- A resposta do modal deve ser restrita ao escopo autorizado; referências de
  evidência não devem conter detalhes de saúde/consumo ou credenciais.

O repositório em memória está somente em `test/`. Seus locks são de processo
único e não comprovam concorrência distribuída ou isolamento no Sankhya.

## Pendências para integração

F1/F2, definição de G1, SDK/build da Belmicro, homologação e contrato de
efetivação. A implementação Jape, AcaoRotinaJava e modal foi acrescentada na
etapa 4.0, com detalhes em integracao-sankhya.md. Falta ligar o provider da
configuração real, permissões físicas e transporte do componente; não há
instalação ou criação financeira executadas.

Atualização após recebimento do manual: o modelo físico adotado é AD_RAT*.
Existem adaptadores JDBC de leitura para critérios/matriz, marcador e execução;
as correspondências de revisões, hash, entrada e solicitações foram implementadas
em fotografias estruturadas no evento existente, sem criar campos. Esse desenho
precisa ser validado em homologação. No adaptador
de critério, `criterioId` é CODCRITERIO e `criterioVersao` é SEQUENCIA, a chave
física da versão; NROVERSAO aparece na descrição para apresentação.
