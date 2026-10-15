# Decisões locais — Nexo AI

## Recebimento central de evidências

Em 2026-10-06 o usuário informou que cada cliente possui hospedagem própria, incluindo SankhyaCloud ou Datacom, e não podemos instalar nosso serviço no servidor do cliente. O fluxo autorizado é um botão de ação enviando dados ao serviço hospedado pela Zaya.

Decisão: receptor central não inicia conexão ao ERP. O botão será o ponto de coleta permitido; dados ausentes geram diagnóstico limitado. A implementação atual permite receber pacotes completos, mantém fontes, contexto reproduzível e isolamento, mas o coletor de botão entregue é mínimo enquanto SDK, campos e políticas reais não forem homologados.

## Alvo informado pelo usuário

- Sankhya OM: `4.36b159`, publicação informada 25/09/2026 17:27.
- ERP Core: `5.21.4`, publicação informada 25/09/2026 11:26:49.
- Fonte: versão relatada pelo usuário neste projeto; não inspeção direta do ERP.
- Biblioteca disponível: catálogo `4.35b815`; o caminho canônico para dicionário `4.36b159` não existe nesta sessão.
- Dependências de compilação locais posteriormente encontradas: `C:/dev/zaya/platform/modulos-sankhya/Dependencias/4_35`, APIs públicas do botão em `mge-modelcore-4.35b99.jar`. Build corporativo aplicado somente ao módulo do botão; artefato ZAYA-NEXO-V1.0.jar compilado e inspecionado.
- JAR de extensões exato 4.36b159, versão DB, mecanismo de segredos nativo, permissões da ação e saída HTTPS: não fornecidos ou não verificados.

Esses fatos são do projeto e não foram promovidos à biblioteca corporativa. Aprovação de disponibilidade é específica de cliente/ambiente e versões exatas; não há política real aprovada.


## Homologação temporária — 09/10/2026

Usuário autorizou explicitamente uma sessão de 60 minutos via Cloudflare Quick Tunnel e JAR com credencial individual extraível. Não é solução de produção. Receptor normal com escopo fixture, IA e gateway desligados, credencial exclusiva, encerramento automático. LogSignals envia somente contagens e tipos de exceções padrão; SQL arbitrário de BtnRunQuery não foi incorporado. Ver docs/temporary-homologation.md. DNS desta rede impediu validação do endereço público; pacote de conectividade deve ser apresentado com HTTPS não validado.


## Coleta de produto e estoque — 09/10/2026

Usuário solicitou ampliar o botão. Implementadas duas consultas SELECT fixas, NUNOTA por bind, timeout 3s e limites. Não usar SQL livre, calcular disponibilidade ou agregar saldos de parceiros/tipos diferentes. Contrato adiciona productActive/stockRows opcionais e receptor detecta produto inativo reportado. Consultas ainda precisam de execução na instalação. Serviço reiniciado na sessão existente sem mudar expiração ou credencial. Ver docs/product-stock-collection.md.


## Trecho de log por incidente — 09/10/2026

Usuário solicitou coletar o erro do log e trazer ao receptor. V1.6 procura termos fixos extraídos do relato em janela limitada e envia fragmentos filtrados com correlação textual não comprovada. Receptor guarda JSON/anexo .log por protocolo, com download autenticado e isolamento. Não envia conteúdo à IA nem determina causa com o log. Filtragem heurística exige ACL e retenção. Sem mudança de banco. Ver docs/incident-log-excerpts.md.
