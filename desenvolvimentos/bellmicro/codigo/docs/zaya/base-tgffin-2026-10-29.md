# Base TGFFIN e parcelas — confirmação de 29/10/2026

Atualização posterior: no Java7.5 o cliente autorizou retirar temporariamente
AUTORIZACAO_PENDENTE. Ver [decisão e alcance](autorizacao-temporaria.md).
As referências a operações bloqueadas por passo2 abaixo são históricas.

O cliente confirmou, por respostas no chat:

- A origem dos dados é o financeiro selecionado na TGFFIN.
- Havendo três parcelas de R$100 da mesma nota, a base é R$300: documento inteiro.
- A competência usa TGFFIN.DTVENC da parcela selecionada, mesmo que as outras
  parcelas tenham vencimentos em meses diferentes.

## Regras implementadas

ResumoFinanceiroJdbc lê as parcelas por NUNOTA. Para avulso sem nota, lê somente
o NUFIN; não infere grupo por NUMNOTA, que pode se repetir. Soma VLRDESDOB com
BigDecimal e conta os NUFINs elegíveis. A quantidade não é o valor do campo
DESDOBRAMENTO, que é somente identificação textual da parcela selecionada.

O total nominal inclui parcelas baixadas: a base não é saldo em aberto nem
VLRBAIXA. A consulta considera despesas RECDESP=-1 não provisionadas e exclui
auxiliares marcados em AD_RATFIN. Renegociação com NURENEG diferente de zero
gera pendência, porque sua cadeia precisa de regra própria para não duplicar
o documento. Valores ausentes/não positivos ou fora de centavos e identidades
inválidas são pendências, não valores inventados. Não há DML nessa leitura.

Esses campos foram consultados seletivamente no dicionário local4.35b815:
DESDOBRAMENTO é campo textual e NURENEG identifica renegociação. Isso não
substitui a validação física no banco do cliente.

MapeamentoFinanceiroTgffin usa o total do documento e o vencimento exato da
parcela selecionada (AAAA-MM-DD). A interface continua mostrando competência
por mês e o vencimento completo na ficha. Mantém a origem econômica NUNOTA;
NUFIN_REFERENCIA é referência material da simulação, não uma nova origem para
gerar outro conjunto. Guarda também quantidade e dados das parcelas no mapa
material usado no hash e na fotografia persistida. Sem parcela elegível ou
sem o seu vencimento, o mapeamento permanece com pendência; não usa a data
atual nem o primeiro vencimento como substituto.

A configuração concreta do passo1 agora liga esse mapeamento aprovado.
Permissões do passo2 continuam adiadas e bloqueadas por AUTORIZACAO_PENDENTE.
A efetivação financeira permanece não implementada/bloqueada.

## Revisão, referência e reenvio

A releitura dentro da transação usa o NUFIN selecionado, preservando a origem
canônica da nota. No recálculo sem referência explícita, recupera o NUFIN da
fotografia. Para replay antigo, recupera a referência da solicitação original
antes de resolver a parcela; não depende da parcela escolhida em uma revisão
posterior. Origem e referência efetivamente usadas são autorizadas antes do
retorno. Revisões anteriores não são alteradas.

A identidade da solicitação usa versão2 com referência quando o mapeamento
inclui NUFIN_REFERENCIA. Reutilizar a mesma chave para outra parcela da mesma
nota é SOLICITACAO_INCOMPATIVEL. Adaptadores antigos sem referência material
mantêm a identidadev1. Mudança de parcela/vencimento no contexto exige recálculo
explícito; não muda automaticamente o resultado registrado. O DTO da fotografia
inclui base, competência e NUFIN de referência para indicar essa divergência
na interface.

O futuro serviço de efetivação terá de recuperar NUFIN_REFERENCIA da fotografia
para revalidar a competência, mantendo locks e unicidade por origem canônica.
Resolver apenas a NUNOTA não basta para escolher o vencimento aprovado.

## Dashboard e entrega

Ficha: valor da parcela escolhida, total do documento, quantidade de parcelas,
DESDOBRAMENTO e vencimento, além dos dados anteriores. Esses campos são lidos
do servidor e não são instruções de cálculo enviadas pelo navegador.
O resumo também expõe as parcelas para rastreabilidade. Não criar colunas novas;
a referência material é preservada pelo mecanismo de fotografias já existente.

Pacotes: Java7.4 / dashboard7.5. Atualizar JAR e ZIP mantendo os IDs das três ações.
Sem mudança no CSS original e sem necessidade de trocar o launcher desta etapa.

## Validação e limites

125 cenários Java: soma das três parcelas, vínculo por NUNOTA/NUFIN, vencimento
da parcela escolhida, ausência de data, renegociação ambígua, fechamento de
recursos, releitura transacional, chave em outra parcela, mudança explícita de
competência e replay antigo após referência posterior perder autorização.
Testes usam banco/SDK simulados; não executam cálculos no cliente.

Navegador: total, quantidade, ficha/iframe, sugestões, simulação/recálculo e
reenvio com transporte simulado. JSP compilado localmente para Java8.
Revisão independente estática dos trechos novos: achado sobre replay corrigido
e confirmação em leitura. Sem deploy, aceite, permissões liberadas ou escrita
na base cliente. As demais pendências da auditoria permanecem.
