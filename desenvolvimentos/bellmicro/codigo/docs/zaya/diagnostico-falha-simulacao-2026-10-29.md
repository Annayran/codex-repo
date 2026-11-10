# Diagnóstico da falha ao simular — 29/10/2026

Demanda: ZY-BLM-2026-RATEIO-001. Java/dashboard 7.7, entrega local.

## Evidência e conclusão

O novo print mostra base carregada de R$ 24.210,48 e falha ao simular, com resposta não confirmada. Isso difere do título anterior com RECDESP=1. Não houve acesso ao servidor ou banco; a exceção concreta ainda não foi identificada.

Foi confirmado no código que AcaoFinanceiroRateio descartava a informação de operação presente em FalhaConsultaRateio. A camada transacional também substituía a etapa específica por TRANSACAO_JAPE. O frontend não reconhecia o prefixo HTML nativo no erro JSON.

## Alterações

- Falha técnica retorna JSON com código ERRO_TECNICO, etapa permitida explicitamente, versão 7.7 e referência UUID. Código numérico do banco é incluído quando disponível.
- Persistência preserva a etapa específica e extrai somente o código numérico de SQLException, incluindo causas e nextException, sem reter mensagens/SQL. Extração tolera ciclos.
- Log registra somente versão, operação, etapa, referência e código numérico. Não registra mensagens da exceção, payload, SQL, dados financeiros ou credenciais.
- Bridge reconhece o prefixo nativo conhecido e mostra a mensagem do JSON. ERRO_TECNICO continua exigindo reenvio com a mesma solicitação; não é tratado como sucesso ou erro funcional confirmado.

Não foram alterados filtros, cálculos, permissões, comandos transacionais, IDs, FQCNs ou layout. A efetivação financeira permanece indisponível. Não foi executado DML nem implantação.

## Validação

Build com SDK local aprovado: 130 cenários (19 ações, 21 domínio, 40 aplicação, 37 JDBC, 13 persistência). Bridge testado com erros nativos funcionais e técnicos. Dashboard testado no navegador, CSS original preservado. JSP final compilado com alvo Java 8 e zero erros.

Revisão independente apontou código SQL zero em wrapper ocultando causa útil; corrigido e coberto com casos de causa e nextException. Demais alterações sem achados concretos na revisão estática.

## Entrega e pendência

Arquivos locais: `build/libs/ZAYA-BLM-V7.7.jar` e `build/315_html5Component.zip`. Atualizar ambos e obter o novo texto completo do erro, com etapa, código do banco e referência. Se continuar aparecendo somente o texto antigo, confirmar qual versão do módulo foi carregada.

Esta entrega melhora o diagnóstico. Não confirma resolução da falha operacional de simulação no ambiente do cliente.
