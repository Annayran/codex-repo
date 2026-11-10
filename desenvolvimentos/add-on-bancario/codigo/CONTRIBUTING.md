# Colaboração no Zaya API Boletos

Comece por [Guia do desenvolvedor](docs/GUIA_DESENVOLVEDOR.md) e pela [matriz de homologação](docs/HOMOLOGACAO.md).

1. Clone o repositório privado autorizado e configure JDK 17 e SNK_LIB. O SDK não é distribuído no Git.
2. Trabalhe em branch própria e abra pull request para main. Descreva problema, resultado, evidência de testes e impacto financeiro.
3. Execute `./gradlew :model:test`, `python3 scripts/check_project.py` e `node scripts/frontend.test.cjs`. Mudança na tela também exige `node scripts/ui_smoke.cjs` com Playwright e navegador configurados.
4. Para empacotar, obtenha do mantenedor a identidade original em `.local/addon-identity.gradle` ou use variáveis de ambiente autorizadas. Nunca copie AppKey, licença, credenciais bancárias ou chave AES para o Git.
5. Use apenas fixtures fictícios ou saneados. Os PKCS12 base64 em test/resources são exclusivamente de testes e têm senha pública documentada.
6. Migrações novas recebem versão posterior; não alterar scripts já distribuídos. Preserve Oracle e SQL Server.
7. Acrescente regressão para timeout, estados e deduplicação quando tocar na esteira. Nunca repetir inclusão incerta, baixar recebimento provisório ou contornar homologação.
8. Atualize documentação e changelog. O pacote deve ser instalado primeiro em homologação; testes locais não autorizam produção.

Solicite ao administrador acesso de escrita somente a este repositório. Acesso ao banco, Cloud, portal e identidade do produto depende de concessões separadas. Não convide usuários ou publique informações do cliente sem a autorização do responsável.
