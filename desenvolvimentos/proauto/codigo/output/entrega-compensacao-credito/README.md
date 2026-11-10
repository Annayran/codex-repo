# PROAUTO — Compensação automática de crédito de cliente

Pacote de entrega técnica para revisão gerencial — 21/09/2026.

## Situação da entrega

Código implementado e JAR candidato de homologação gerado. Compilação local concluída; a suíte do repositório passou com 20 testes (17 desta funcionalidade e 3 da rotina independente de fila de reserva). O pacote inclui os 17 testes da compensação. Não houve publicação nem execução comprovada no ERP. A supressão do diálogo, o momento de disponibilidade dos financeiros e o rollback ainda precisam de homologação na versão Sankhya Om 4.36b139.

## Conteúdo

- `src/main/java`: todo o código Java da compensação, incluindo o evento, acesso a dados, configuração, regras, integração com o helper e controle da pergunta nativa.
- `src/test/java`: testes unitários desta funcionalidade.
- `dist/proauto-compensacao-credito-evento-hom.jar`: candidato para homologação.
- `docs/REGRA-DE-NEGOCIO.md`: resumo funcional, configuração, limites e pendências.
- `docs/compensacao-credito-homologacao.md`: instruções técnicas de instalação, logs, testes no ERP e reversão.
- `scripts/Compilar-Testar.ps1`: compilação e testes do módulo isolado, mediante fornecimento das dependências locais.
- `SHA256SUMS.txt`: integridade dos arquivos entregues.

As bibliotecas proprietárias do Sankhya e as bibliotecas de testes não estão redistribuídas. Fornecer um diretório com SDK compatível, JUnit 4.13.2 e Hamcrest Core 1.3 para recompilar. O script utiliza javac/java/jar disponíveis no PATH e gera bytecode Java 8. O candidato foi compilado com SDK local 4.23b244; isso não comprova compatibilidade com a versão destino 4.36b139.

Exemplo de execução na raiz extraída:

```powershell
./scripts/Compilar-Testar.ps1 -DependenciesPath 'C:/SDK/Sankhya/Jar' -JunitJar 'C:/libs/junit-4.13.2.jar' -HamcrestJar 'C:/libs/hamcrest-core-1.3.jar'
```

O ZIP contém exclusivamente a funcionalidade de compensação. Não inclui dados de clientes, credenciais de acesso, cópias de banco, caches, outros projetos ou o processo independente de fila de reserva. Não altera preferências nem instala o evento automaticamente.
