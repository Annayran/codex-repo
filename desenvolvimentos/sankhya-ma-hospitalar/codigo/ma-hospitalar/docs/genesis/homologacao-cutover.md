# Homologação, cutover e rollback — Genesis

## Gates obrigatórios

- contrato autenticado e fixtures dos seis endpoints aprovados;
- extração oficial carregada e reconciliada;
- fabricantes, produtos, TOPs e situações aprovados;
- token rotacionado e provisionado fora do artefato;
- dry-run com árvore e payloads esperados;
- evidência de que a carga não alterou `TGFEST`;
- testes automatizados e funcionais aprovados.

## Cenários funcionais

Validar cliente existente/novo, setor padrão, material/fabricante/modelo existentes e
novos, item novo, item legado sem duplicidade, legado sem nota visível no espelho,
primeira remessa mudando origem só após PUT, entrada/saída/retorno por TOP, série
ambígua bloqueada, reprocessamento, replay idempotente e coerência do dashboard.

## Cutover alvo — 5 de outubro de 2026

Publicar banco, metadados, JAR e dashboard desativados; carregar e reconciliar a base;
homologar; congelar jobs Protheus; carregar o delta; reconciliar IDs e ambiguidades;
ativar a agenda Sankhya e monitorar fila/API/divergências. A data depende da aprovação
dos gates e deve ser formalmente replanejada se eles não forem cumpridos.

## Rollback operacional

Definir `AD_CENTPARAM.GEN_ATIVO='N'` para `NUPAR=1`, interromper a agenda Sankhya e, se autorizado,
reativar temporariamente o Protheus. Não excluir mapa, fila ou Base Instalada: eles
são necessários para rastreabilidade e retomada sem duplicidade.
