# Diagnóstico e persistência do rateio — 29/10/2026

Demanda: ZY-BLM-2026-RATEIO-001. Java/dashboard7.10, entrega local.

## Pedido e implementação

Cliente solicitou logs mais claros e apresentáveis, acessíveis sem log direto do servidor, e explicação da persistência e das bibliotecas utilizadas. A causa concreta do COMPOSICAO_INVALIDA no ambiente permanece sem confirmação, pois não foi fornecido o payload dessa tentativa.

DiagnosticoAcaoRateio identifica cada chamada por UUID e registra operação, versão, início UTC, duração monotônica, etapas, resultado/código e código numérico do banco quando disponível. Consulta, simulação, recálculo e efetivação recebem o mesmo contrato. No servidor, logger JUL br.com.zaya.belmicro.rateio usa INFO para comando concluído e WARNING para rejeição/falha, em JSON correlacionado. Conclusão do comando não equivale a confirmação financeira ou confirmação final de commit do transporte.

Não registra payload, RATEIO_JSON, SQL, mensagens livres de exceção, valores financeiros, dados do usuário ou credenciais. Mensagens detalhadas de composição vêm de whitelist de textos próprios da aplicação: lista ausente, percentual/formato inválido, código inválido, soma100, motivo ausente e outros casos conhecidos. Mensagens desconhecidas recebem orientação genérica, nunca eco do provider.

Modal mantém aviso e adiciona painel nativo recolhível “Histórico e detalhes técnicos”, com metadados e sequência de etapas. O histórico é limitado às últimas20 chamadas reconhecidas da instância do modal, só em memória; fecha/recarrega e perde o histórico. Não usa localStorage, cria tabela ou grava diagnóstico técnico em AD_RATEVT. Todos os campos são escapados antes de gerar HTML. Layout e CSS originais preservados.

Bridge preserva metadados de sucesso e erro, inclusive após prefixo nativo Regra Personalizada. ERRO_TECNICO continua resultado incerto: controles bloqueados e reenvio com a mesma solicitação. Códigos funcionais preservam seu tratamento anterior. Não há alteração em cálculos, filtros, permissões, bloqueios ou persistência.

## Onde a simulação é gravada

Implementação: SimulacaoRepositoryAdRat, usando TabelasAdRat implementada por PersistenciaJape.

| Tabela | Conteúdo |
| --- | --- |
| AD_RATSIM | Cabeçalho, origem, competência, base, revisão, situação e chave da solicitação |
| AD_RATPAR | Distribuição prevista por destino, valor e percentual global |
| AD_RATGRP | Grupos previstos dos auxiliares por empresa/compatibilidade |
| AD_RATINT | Composição interna dos grupos por centro de resultado |
| AD_RATEVT | Fotografia versionada fragmentada, eventos e dados para reenvio idempotente |

Recálculo substitui a projeção corrente e mantém as fotografias anteriores em eventos. Consulta não recalcula nem grava revisão. Simulações válidas ou pendentes podem ser registradas conforme o contrato de aplicação. O sucesso da ação contém identificação/revisão; erro técnico não confirma sucesso, devendo ser recuperado pelo reenvio idempotente.

Tudo ocorre na transação gerenciada do Sankhya. Erros propagados permitem rollback; não há commit/rollback JDBC manual. Persistência foi validada com gateways/driver simulados; funcionamento no ambiente real ainda depende da homologação.

## Bibliotecas e limites

AcaoRotinaJava/ContextoAcao são o contrato dos botões. JapeSession gerencia sessão/transação, EntityFacadeFactory/JdbcWrapper fornecem a conexão e JapeFactory/JapeWrapper fazem CRUD das instâncias AD_RAT*. Leituras e bloqueios usam JDBC/Oracle na conexão do Sankhya. O SDK local4.35b99 é compileOnly: suas classes não são empacotadas no JAR. O cálculo proporcional é código Java do projeto.

Simular não cria títulos financeiros auxiliares nem altera o valor do título original. O port padrão de efetivação permanece EFETIVACAO_NAO_DISPONIVEL; geração financeira, vínculos e fechamento efetivo ainda não estão implementados/homologados. Não confundir AD_RATPAR (distribuição de simulação) com parcelas financeiras reais.

## Validação e entrega

136 cenários Java com SDK aprovados (21 domínio,40 aplicação,41 JDBC,13 persistência,21 ações). Novas regressões cobrem detalhe seguro de composição, etapas, referência única e metadados de consulta/sucesso. Bridge verifica preservação de diagnóstico e limite20 em25 consultas. Navegador verifica painel com referência/versão/banco/etapas, mensagem nativa, reenvio idêntico e CSS original. JSP final compilado Java8 com zero erros. Revisão independente estática: sem achados concretos.

Entregas: build/libs/ZAYA-BLM-V7.10.jar e build/315_html5Component.zip. Atualizar ambos. Nenhuma implantação, publicação ou execução em banco real foi realizada. Se necessário, rollback para o par Java7.9/dashboard7.8.

Próxima ação: instalar o par7.10, repetir a operação e consultar o painel para identificar a validação concreta. Não há confirmação de resolução do COMPOSICAO_INVALIDA atual.
