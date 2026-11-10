# Entrega do JAR isolado de migracao de modelos

Demanda DEM-b5f5adb804fe6bd8. Solicitacao em 2026-10-23: fornecer o modulo JAR
para o solicitante inserir. Preparacao e entrega local, sem upload pelo agente,
sem commit/push, sem mudar configuracoes adicionais e sem nova chamada a API.

## Artefato

- `output/hotfix-modelo-20261023/MAHospitalar-Modelo-Hotfix.jar`
- SHA256: B4F107209563E8609D01AB2209F97992B5B04390C9A7D3EA6221671CAADC87F1.
- Montagem reproduzivel: `tools/BuildGenesisModeloHotfix.ps1`.
- Verificador: `tools/VerifyGenesisModeloHotfix.java`.

Corrige a referencia invertida HASH_PAYLOAD para PAYLOAD_HASH no repositorio
da migracao, em cinco SQLs. Nao cria coluna, nao altera contratos/public API,
nao desabilita lease/CAS, nao modifica POST guard nem consolida identidades.
Somente a entrada MigracaoModeloGenesisRepository.class foi substituida;
130 entradas, nenhuma extra/duplicada e todas as outras byte-identicas a base.
Mantem M1 como filtro de equipamentos; nao inclui a ampliacao M1/M3.

## Proveniencia e limite

Base local testada ANTES da melhoria M1/M3:
`build/homologacao-20261022-153434/MAHospitalar.jar`.
SHA256: 4E744181F269052C8F531899B99B34C111C90E3DBA4E257478F3F46C74A077A9.
Fonte da unica classe corrigida: `build/homologacao-20261023-110752/MAHospitalar.jar`,
SHA256 9DE476B2FC647225B9C795B92214523DE882025558026CAD09CB3A6CF1427D93.
Os arquivos originais foram preservados.

A base NAO foi baixada nem comparada com a biblioteca atualmente instalada em
producao. Logo, isolamento em relacao a base local nao comprova que a troca
preserve qualquer alteracao instalada por terceiros depois de 22/10. Conferir
versao e guardar a biblioteca instalada antes da substituicao; se ela divergir,
reaplicar o hotfix sobre uma base instalada validada, sem sobrescrever melhorias.
Nenhum artefato e declarado homologado Oracle/Genesis por testes locais.

## Validacao executada

Coordenador: javap -c -p das classes base/corrigida, normalizando indices do
constant pool e a troca de coluna: codigo equivalente exceto cinco literais SQL.
Execucao contra o JAR final (nao classes atuais): M1 aceito/M3 rejeitado e
18/18 testes da migracao aprovados. SDK e JUnit existentes, sem novas dependencias.
O teste de metadado usa a fonte atual; integridade e isolamento do JAR foram
conferidos separadamente. Suite anterior de fontes completas: 186/186, incluindo
M1/M3, nao confundir com a cobertura especifica de 18 testes deste candidato.

Reviewer independente: 130 entradas legiveis; somente a classe alvo alterada e
identica a do JAR corrigido; demais SHA preservados, inclusive manifest, preco
e guards. Sem assinatura detectada. Hash final conferido apos o ultimo teste.
Sem publicacao remota nem teste de API real.

## Insercao e verificacao operacional

1. Pausar os jobs Genesis durante a troca e baixar/guardar a biblioteca instalada
   do modulo 11 como rollback. Conferir a base conforme a ressalva acima.
2. Inserir o JAR no modulo Java Genesis correspondente e concluir a aplicacao
   da biblioteca no Sankhya pelo procedimento autorizado do ambiente.
3. Nao aplicar a view M1/M3 ou o pacote completo110752 nesta etapa. Nao precisa
   DDL para este hotfix. Manter Transacao automatica desmarcada no job6.
4. Retomar de forma controlada e conferir o fluxo MIGRACAO_MODELO_HSP. A rotina
   atual migra um modelo por execucao; foram observados candidatos 13847,14862
   e16072. GET por ID e por destino precedem PUT; GET posterior confirma dados.
5. Confirmar controles MIGRA, codigo local, identidade e persistencia antes de
   considerar corrigidos os16 movimentos. Observar erros de conflito ou API;
   nunca liberar novo POST por ausencia de confirmacao.

Os3 bloqueios de material,1 serie ambigua e1 movimento antigo continuam exigindo
as verificacoes/decisoes do diagnostico dos21; o JAR nao elimina esses registros.
Ele tambem nao zera contadores automaticamente.

Rollback: pausar jobs e restaurar a biblioteca realmente salva do ambiente.
Isso nao desfaz PUTs remotos ja confirmados. Reversao de dados requer analise
por ID, GET atual, evidencias anteriores, conflitos e autorizacao especifica.

## Estado da entrega

JAR e instrucoes disponiveis localmente e registrados no Obsidian. Insercao e
validacao em producao ficam com o solicitante; nenhuma implantacao foi feita
pelo agente nesta solicitacao. Nao considerar os21 resolvidos por esta entrega.
