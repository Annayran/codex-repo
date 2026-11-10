# Entrega combinada M1/M3 e hotfix de modelos

Em 2026-10-23 o solicitante pediu incluir M3 no JAR entregue. Esta escolha
substitui o pedido anterior de artefato isolado apenas M1, sem apagar aquela
entrega. Nao autoriza instalacao automatica pelo agente nem baixa dos21 erros.
Demandas: DEM-8f6a2e25170d6c25 e DEM-b5f5adb804fe6bd8.

## Arquivos

Diretorio: output/genesis-m1-m3-hotfix-20261023/.

- MAHospitalar-M1-M3-Hotfix.jar.
- 01_ad_vw_genesis_estoque.sql: definicao da view com M1/M3.
- 02_validar_ad_vw_genesis_estoque.sql: consultas de validacao.
- 04_rollback_m1.sql: retorno da view para M1, se correspondente a base salva.

JAR copiado integralmente do build testado build/homologacao-20261023-110752,
sem remendo binario. SHA256 de origem e entrega:
9DE476B2FC647225B9C795B92214523DE882025558026CAD09CB3A6CF1427D93.

Inclui selecao e guarda M1/M3, com prioridade HSP/fallback AD e serie obrigatoria,
e as cinco referencias corrigidas PAYLOAD_HASH na migracao de modelos.
Cliente continua primeira dependencia; estoque separado M2/M3/SV e protecoes
de identidade/POST incerto permanecem. Materiais/serie ambigua nao sao liberados.
Nao confundir com MAHospitalar-Modelo-Hotfix.jar, que preserva apenas M1.

## Validacao

186/186 testes Java via runner -UnitOnly executados novamente contra o JAR
entregue como primeiro no classpath, removendo as classes atuais do classpath.
Inclui guarda M1/M3 e regressao de hash; zero falhas. Testes HTTP loopback/smoke
excluidos. Contratos SQL sao textuais, nao homologacao da view no Oracle.
As mudancas de M1/M3 e hotfix receberam revisoes independentes anteriormente;
esta entrega apenas copia o build combinado ja validado, sem novo codigo Java.
Nenhuma dependencia instalada/atualizada; sem upload, Git remoto, API ou deploy.

## Implantacao manual e limites

1. Homologar no ambiente apropriado. Guardar a biblioteca instalada e exportar
   o DDL atual da view antes de qualquer troca. A versao instalada nao foi
   comparada byte a byte com este build local.
2. Pausar os jobs Genesis para nao executar combinacao de versoes.
3. Atualizar o modulo Java Genesis e aplicar 01_ad_vw_genesis_estoque.sql na
   mesma janela. Somente o JAR nao habilita M3 se a view instalada filtra M1.
4. Executar as consultas de validacao individualmente. Conferir volume M3:
   a regra alcanca todos os M3 elegiveis, nao somente produto11919.
5. Manter Transacao automatica desmarcada no job6 e retomar controladamente.
   Conferir GET/PUT/GET da migracao e seus controles antes de considerar os16
   movimentos de modelo corrigidos. Verificar tambem as5series das notas
   120671/120872; elegibilidade nao prova envio nem substitui a confirmacao.

Os5outros casos dos21 continuam exigindo consultas/decisoes de identidade e
historico. Nao limpar controles nem excluir vinculos para zerar contadores.
Rollback: pausar jobs, restaurar biblioteca e DDL efetivamente guardados;
04_rollback_m1.sql e alternativa da base local anterior. Isso nao reverte PUTs
remotos ja efetuados. Reversao de dados exige plano e autorizacao especificos.

## Continuidade

Artefatos prontos para entrega local. Instalacao, Oracle/Jape e validacao real
na Genesis continuam pendentes. Nenhum deploy foi feito pelo agente nesta
solicitacao. Relacionar ao diagnostico dos21 antes de declarar saneamento.
