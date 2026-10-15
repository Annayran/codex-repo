# Escopo do projeto

Aplicar as regras globais Zaya.IT e a skill zaya-sankhya-development. Trabalhar apenas nesta raiz; confirmar contexto antes de mudanças.

- Coleta consultiva de suporte para Portais e ocorrência relatada de estoque insuficiente.
- Preservar isolamento entre clientes e ambientes; obter identidade do contexto autenticado, nunca confiar apenas no payload.
- Não alterar documentos, estoque, financeiro ou configuração operacional.
- Não presumir que saldo físico representa disponibilidade; validar local, empresa, controle, unidade, reservas e regra aplicável.
- Distinguir ocorrência de coleta. Ausência de informação não significa zero ou ausência de problema.
- Não criar consultas genéricas, acesso livre a SQL, captura permanente de logs ou dependências sem escopo e compatibilidade definidos.
- Não armazenar credenciais, logs reais ou dados de clientes neste projeto.
- Regras específicas ficam na documentação do cliente/projeto, sem promoção automática à biblioteca corporativa.
- Sem git init, commit, push, publicação ou alteração de infraestrutura sem autorização explícita.
