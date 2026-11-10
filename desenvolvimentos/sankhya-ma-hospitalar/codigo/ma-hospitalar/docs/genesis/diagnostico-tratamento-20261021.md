# Diagnostico e tratamento dos bloqueios

## Entrega

- Dashboard JSP e central React compartilham classificacao dos bloqueios.
- Detalhes exibem motivo, etapa, evidencia, responsavel e orientacao.
- Mensagem tecnica original permanece disponivel para suporte.
- HTTP recebido, identidade validada e persistencia confirmada continuam distintos.
- Varias series na mesma nota sao validas; controle do item usa produto e serie.
- Novas falhas de POST sem ID registram entidade, HTTP, codigo externo e corpo sanitizado.
- Ambiguidades locais incluem quantidade e ate dez pares de registro local e ID Genesis.

## Casos conhecidos e limites

1. POST 2xx sem ID: reserva preservada; reconciliacao por GET sem repetir POST.
   O retorno vazio nao explica a causa interna da API nem comprova ausencia remota.
2. Falha de transporte: permanece em aberto por solicitacao do usuario.
   Um 503 produzido pelo cliente nao comprova recebimento no servidor Genesis.
3. Material com varias linhas locais: bloquear selecao automatica. Comparar dependencias
   e consolidar somente com aprovacao, backup e rollback. Nenhuma exclusao automatica.
4. Equipamento com mesmo produto e serie em varios clientes: nao escolher maior ID.
   Se nenhum cliente conferir, esclarecer transferencia versus inconsistencia cadastral.
5. Material herdado do modelo: manter o grupo conciliado do material; nao substituir pelo
   grupo do produto. A protecao existente permanece ativa.

O historico antigo nao foi reescrito. Mensagens detalhadas novas surgem nas proximas
tentativas. O dashboard traduz mensagens antigas conhecidas sem inventar evidencias.
As consultas React sao recortes de ate 100 registros: ausencia no recorte nao e ausencia
na base. O dashboard nao altera cadastros e nao oferece reprocessamento manual.

## Implantacao e validacao

1. Guardar copia do JAR e ZIP do componente atualmente instalados.
2. Homologar o JAR e apenas o pacote do dashboard usado: JSP original ou central React.
3. Validar compilacao JSP, permissoes Sankhya e um caso de cada categoria no ambiente.
4. Conferir historico do controle antes/depois de qualquer conciliacao autorizada.
5. Nao executar UPDATE de liberacao em massa nem POST manual para limpar indicadores.

Rollback: reinstalar o JAR e ZIP anteriores. Esta entrega nao cria campos, nao executa
DML de saneamento e nao faz chamadas remotas durante o build. A troca de pacote nao
desfaz operacoes que o agendador eventualmente executar depois do deploy.

## Validacao local

Suite Java com UnitOnly (sem testes HTTP loopback e sem Oracle/Jape em execucao real),
testes puros do classificador e testes Playwright da central React com dados sinteticos,
incluindo mobile, temas, XSS e ausencia de chamadas externas. Homologacao no Sankhya
e verificacao dos dados reais continuam necessarias.
