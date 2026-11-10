# Homologacao consolidada - 08/10/2026

## Conteudo e limites

Atualizacao de reprocessamento: os tres agendamentos nao encerram por quantidade
de tentativas. Equipamentos retomam ERRO/REVISAO/REPROCESSAR; catalogo reavalia
apos PROXIMATENTATIVA; cancelamentos retomam PENDENTE/ERRO/REVISAO. Backoff de
2 a 60 minutos e cooldown compartilhado permanecem. MAXTENTATIVAS legado nao
limita nem bloqueia a execucao. Nenhum botao manual e necessario.
ENVIADO/CANCELADO nao voltam ao envio; nota cancelada ou presente na EXCFILA
continua excluida da selecao. Inativacao exige cancelamento efetivo, origem
CRIADO/protocolo correspondente e item ainda vinculado ao mesmo movimento.
Identidade incerta e reavaliada sem DELETE ate que a evidencia seja segura.
O catalogo e independente de notas: sua inativacao segue o cadastro do produto.
Deploy: substituir JAR e homologar retries/cancelamento antes de ativar; rollback
desativar os jobs e restaurar JAR anterior, sem limpar controles ou identidades.

Atualizacao do catalogo: inclui EstoqueGenesisScheduleAction e
AcaoReprocessarEstoqueGenesis, com controle AD_GENESTCTRL a cadastrar manualmente.
Preco pela tabela 101/helper nativo; custo medio seguindo referencia Protheus,
bloqueado quando contexto de custo nao e unico. Consulte catalogo-estoques.md.
Dashboard mostra controle separado e paginado; ainda nao e uma subaba integrada
a tabela de equipamentos. Pre-requisitos e pendencias atuais estao descritos abaixo.

Pacote candidato revisado: compilacao Java 8 e 87 testes sem servidor HTTP local.
Oito testes HTTP bloqueados pela falha de loopback do Java no ambiente de build.
Nao aprovado para producao; executar testes HTTP e validacoes Oracle/JSP no
ambiente de homologacao. O script padrao exige todos os testes; -UnitOnly gera
explicitamente apenas o pacote candidato com essa restricao.

## Correcao do empacotamento e do fluxo

O candidato build/homologacao-20261008-135238 foi substituido. Nao implantar
esse pacote anterior. O layout revisado segue java/, webapp/genesis/,
docs/genesis/ e sql/genesis/, com CSS/JS incorporados ao JSP no formato usado
por prepareGenesisDashboard. O script alternativo e apenas fallback de build,
nao substitui build.gradle como definicao canonica do projeto.

JSP preserva sankhyaUtil.tld, core_rt, snk:load, snk:query, rows e c:out. O aviso
fica na secao de monitoramento existente, sem fetch ou servico HTTP paralelo.
O botao real AcaoReprocessarGenesis foi corrigido via Registro.setCampo/save:
nao limpa HTTP/mensagem 429 enquanto a espera esta vigente. A correcao anterior
somente no metodo do repository nao protegia esse caminho real.

SQL do cooldown ainda precisa ser executado e validado no Oracle do cliente;
o JSP ainda precisa de compilacao/abertura no Sankhya. Teste de contrato por
texto nao comprova compatibilidade em execucao. Nao ativar jobs antes disso.

JAR unico com fontes atuais de MAHospitalar, incluindo equipamentos, cadastros,
eventos de cancelamento, EXCFILA, catalogo de estoques e modulo de preco dinamico
preexistente. Dashboard tambem entregue em ZIP proprio, index.jsp na raiz.
Nao confundir o preco dinamico de notas com o preco do catalogo na tabela 101.

Cooldown: minimo 60 segundos, ampliado por Retry-After (segundos/data HTTP) ou
mensagem Genesis com prazo em milliseconds. Persistido nos controles existentes
como COOLDOWN_MS no erro HTTP 429; consultado pelos dois executores antes de
qualquer chamada. Nao apagar controles nem editar mensagens durante a espera.
Reprocessamento manual de movimentos nao limpa um 429 ainda em cooldown.
Nao ha Thread.sleep nem novo CREATE TABLE. O dashboard exibe o fim da espera.

## Pre-requisitos

- Conferir todos os campos ja previstos para GENMOVEST, GENITEMMAT e EXCFILA.
- MENSAGEM_ERRO deve manter o tipo CLOB utilizado pelo dashboard atual.
- HSP_TIPOPRODUTO deve existir; AD e fallback quando HSP estiver vazio.
- Conferir eventos TGFCAB/TGFCAN e configuracao de transacao dos jobs.
- Manter backup do JAR e dashboard anteriores para rollback conjunto.

## Aceite antes de liberar

1. Sucesso confirmado nao regressa por erro ou reprocessamento manual.
2. GET 500/400/405 nao autoriza POST de cliente; identidade incerta nao duplica item.
3. 429 interrompe lote; nova execucao de envio ou EXCFILA aguarda o prazo registrado.
4. Cancelamento efetivo gera fila segura; rollback e movimento posterior nao inativam indevidamente.
5. Dashboard abre, mostra espera e mantem paginacao da base instalada.
6. Validar os SQLs do cooldown no Oracle do ambiente antes de ativar jobs.

## Pendencias do catalogo de estoques/precos

Criar AD_GENESTCTRL pelo Construtor, validar preco real da tabela 101 e custo
medio, confirmar categoria 1/fornecedor igual fabricante, validar SQL Oracle e
reativacao por PUT no Genesis. Cadastro sem contexto unico de custo exige revisao.
Nao foram realizadas chamadas remotas nem cadastro/ativacao de jobs nesta etapa.

O cooldown utiliza evidencia dos controles existentes, nao um registro imutavel
independente. Uma limpeza manual dessas tabelas remove tambem a evidencia de
espera. Checkpoints e historico separado de tentativas permanecem evolucoes.
