# Percentuais e codificação — 29/10/2026

Demanda ZY-BLM-2026-RATEIO-001. Java/dashboard7.11.

## Evidência

Retorno7.10: COMPOSICAO_INVALIDA na etapa VALIDAR_COMPOSICAO, com mensagem de formato do percentual. Essa etapa precede runtime.operacoes().simular; esse retorno não confirma se os bloqueios e a persistência passaram nessa tentativa. O payload concreto não foi recebido, portanto o valor rejeitado e a linha no ambiente ainda não são conhecidos. Cliente também apresentou acentos substituídos e símbolos transformados em interrogação.

## Alteração

O modal já convertia vírgula para ponto; foi acrescentada validação por linha antes do envio. Aceita decimal com vírgula ou ponto na tela, normaliza para ponto sem converter em ponto flutuante, arredondar ou truncar, e preserva os destinos para correção. Símbolo de porcentagem, linha vazia e precisão inválida são rejeitados com número da linha.

O parser Java diferencia percentual ausente/nulo, tipo/formato inválido, precisão acima de seis casas, valor não positivo e acima100. A resposta mantém COMPOSICAO_INVALIDA, mas informa a linha e orientação segura. Não retorna o valor recebido. Mensagens são construídas por tokens controlados e whitelist; origem, percentuais e fechamento continuam validados pelo servidor.

JSON da ação agora usa escapes Unicode em todos os caracteres não ASCII, preservando inclusive pares substitutos. O bridge JSON reconstrói os textos em Unicode. O JavaScript gerado pelo esbuild passa a charsetascii; isso protege os textos e símbolos do modal contra reinterpretação do JSP por uma codificação legada. Não foi confirmada a configuração exata de charset do servidor. Nenhuma configuração global do Sankhya foi alterada.

Sem alteração de layout/CSS, tabelas, cálculos, permissões ou bloqueios. Validação de percentual inválido acontece mais cedo e impede solicitação defeituosa de chegar à simulação; não há sucesso artificial nem descarte silencioso de linhas.

## Validação e entrega

138 cenários Java com SDK:21 domínio,40 aplicação,41 JDBC,13 persistência,23 ações. Casos incluem percentual vazio/nulo/tipo inválido, formato, precisão, limites e roundtrip JSON ASCII de acentos/símbolos/caractere suplementar. Bridge aprovado. Navegador verifica linha inválida sem chamada ao servidor, preservação de destinos, conversão25,000000 para25.000000, painel técnico e reenvio idêntico. JSP compilado Java8 com zero erros.

Revisão independente apontou falta de identificação da linha em percentual nulo; corrigida e coberta por teste. Nenhum outro achado concreto na revisão estática.

Entregar build/libs/ZAYA-BLM-V7.11.jar e build/315_html5Component.zip, atualizando ambos. Sem implantação, publicação ou execução no banco real. Rollback possível para o par7.10.

Próxima ação: repetir a composição na versão7.11 e corrigir a linha indicada caso a entrada permaneça inválida. A execução ponta a ponta ainda exige confirmação no ambiente.
