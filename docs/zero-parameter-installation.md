# Instalação sem parâmetros manuais

Decisão de 09/10/2026: o usuário não cadastra endpoint, identidade, senha ou versão ERP nos Parâmetros do Sistema. A V1.2 remove a leitura de getParametroSistema e System.getenv da ação. MENSAGEM_ERRO continua sendo o relato do incidente, preenchido ao acionar o botão, e não uma configuração de integração.

A configuração de integração será provisionada pela Zaya por ButtonConfigurationProvider (ServiceLoader). O contrato existente permite fornecer destino HTTPS, identidade, credencial protegida e versão da instalação. A ausência desse provedor interrompe a ação antes de qualquer envio e informa que a instalação está pendente. Não há provedor concreto nesta versão: retirar parâmetros não cria uma identidade autenticada automaticamente.

## Pendências de implantação

1. Disponibilizar o receptor central e sua URL HTTPS definitiva. Em 09/10/2026 o usuário confirmou que não existe servidor/domínio.
2. Homologar um armazenamento protegido acessível à rotina na hospedagem e implementar o provedor específico. Não colocar senha compartilhada ou segredo individual em texto no JAR. Identificação declarada pelo remetente não substitui autenticação.
3. Provisionar identidade e vínculo cliente/ambiente no receptor e testar envio pela hospedagem.

O JAR V1.2 é uma versão de desenvolvimento não provisionada, não um instalador pronto para operação. SDK, runtime e FQCN da ação foram preservados.

## Referência LogManager

Fonte: https://github.com/Luisricardo2825/LogManager (README consultado em 09/10/2026).

O README descreve uma aplicação externa que cria módulo Java br.com.ricardo.utilityLib e ação na instância ArquivoModulo, invocando a ação via API para retornar o log. Esse fluxo busca dados do ERP pela aplicação externa; o Nexo envia evidências do ERP para seu receptor. O padrão comprova a proposta do autor, não compatibilidade ou permissões na hospedagem alvo.

Fonte de implementação indicada pelo usuário: https://github.com/Luisricardo2825/UtilityLibJava/blob/master/src/main/java/BtnGetLog.java . Não foi possível recuperar esse arquivo nesta análise. Caminho do log, API usada, limites de leitura e tratamento de conteúdo ainda não verificados. Nenhum código ou JAR desse projeto foi incorporado.

Antes de coletar logs: validar acesso na hospedagem, limitar tamanho e janela de leitura, filtrar conteúdo sensível e estabelecer vínculo com o incidente. As últimas linhas do servidor podem pertencer a outros usuários ou processos; não comprovam a causa da nota selecionada. O botão atual continua enviando apenas nota e relato.
