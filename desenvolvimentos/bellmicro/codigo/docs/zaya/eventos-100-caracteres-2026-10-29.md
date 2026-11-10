# Histórico em campo100 — 29/10/2026

Demanda ZY-BLM-2026-RATEIO-001. Java7.14, compatível com dashboard7.13.

## Decisão do cliente

Cliente determinou manter AD_RATEVT.DESCRICAO em100 caracteres por enquanto. Isso substitui a orientação anterior de exigir ampliação para4000. Nenhuma mudança de tabela, campo ou metadata é necessária para esta entrega.

## Implementação

SimulacaoRepositoryAdRat continua usando RAT1|hashSHA256hex64|indice|total|fragmento. Apenas o tamanho do corpo foi reduzido de3500 para19 caracteres. Com até4000 fragmentos, cabeçalho tem no máximo80 e registro completo no máximo99 caracteres ASCII. O SHA256 permanece inteiro, sem encurtamento, e a fotografia continua completa, compactada pelo mesmo FotografiaCodec. Não se corta nem descarta conteúdo.

O leitor e o prefixo de busca permanecem compatíveis com o formato existente. Fotografias antigas de fragmentos3500 reabrem; recálculo escreve fragmentos curtos e preserva replay das revisões anteriores. Nenhum novo protocolo de evento foi implantado.

O teto4000 fragmentos é verificado antes de inserir qualquer evento. Isso limita novas fotografias compactadas/Base64 a76000 caracteres. Excesso gera FOTOGRAFIA_EXCEDE_LIMITE e propaga erro para rollback de cabeçalho/detalhes, sem sucesso parcial ou fotografia irrecuperável. Limites anteriores de leitura do codec permanecem para o legado.

O custo é um número maior de linhas/eventos e chamadas Jape. Não há medição de latência no banco do cliente. Não houve alteração de financeiro, TGFRAT, cálculo, permissões, bloqueios ou layout; efetivação real continua pendente de implementação/configuração, com TOP/tipo de título para a reunião conforme decisão anterior.

## Validação

146 cenários Java aprovados com SDK:21 domínio,40 aplicação,45 JDBC,16 persistência,24 ações. Gateway dos testes recusa qualquer novo evento com DESCRICAO maior100. Regressões incluem mais de1000 fragmentos e índices de quatro dígitos, reconstrução exata, reabertura com nova instância, legado3500, recálculo, replay, hash64, rollback e rejeição por excesso. Revisão independente estática sem achados concretos.

Testes usam gateways/driver simulados. Não foi executado banco real nem teste de desempenho nessa homologação. UI/JSP não mudaram nesta entrega.

## Entrega e operação

Atualizar somente build/libs/ZAYA-BLM-V7.14.jar, mantendo dashboard7.13 e o campo100. Não recriar ações ou tabelas. Repetir a simulação e conferir retorno com ID/revisão e reabertura da simulação. Não considerar sucesso sem confirmação do servidor.

Rollback de código para7.13 mantém leitura dos fragmentos curtos RAT1, mas volta a escrever fragmentos3500 e reintroduz o erro em campo100; portanto não serve para continuar gravando nessa estrutura. Não há migração destrutiva de dados ou schema a reverter.

Fonte: print do cliente confirmando varchar100, instrução explícita para mantê-lo e código do repositório. Nenhuma implantação, DML/DDL ou publicação foi executada nesta sessão.
