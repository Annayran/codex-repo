# Decisões Técnicas

## Como registrar uma decisão

Use o formato:

### AAAA-MM-DD — Título da decisão

**Contexto:**  
Descrição breve.

**Decisão:**  
O que foi decidido.

**Motivo:**  
Por que foi decidido.

**Impacto:**  
Consequências técnicas ou operacionais.

---

## Decisões Registradas

### A confirmar — Estrutura inicial de contexto para IA

**Contexto:**  
O projeto passou a usar uma pasta de contexto para orientar interações futuras com IA.

**Decisão:**  
Centralizar arquitetura, stack, padrões, regras, decisões, pendências e checkpoints em `/.ai/context`.

**Motivo:**  
Reduzir consumo de tokens e preservar contexto útil entre sessões.

**Impacto:**  
Novas conversas com IA devem começar lendo os arquivos desta pasta.

---

### 2026-06-22 — Dependências locais Sankhya em `C:/zy-tools`

**Contexto:**  
O `build.gradle` do projeto foi ajustado para deixar de usar caminhos locais antigos em `C:/sk-java/workspace/APIs`.

**Decisão:**  
Usar `C:/zy-tools/modulos-sankhya/Dependencias` como base de dependências locais, com jars Sankhya em `compileOnly` e dependências de teste equivalentes.

**Motivo:**  
Adequar o build ao ambiente local atual informado para este projeto e evitar empacotar dependências do ambiente Sankhya junto com a aplicação.

**Impacto:**  
O build depende da existência desses diretórios locais. Novos ambientes precisam replicar a estrutura ou parametrizar o caminho.

---

### A confirmar — Manter Java 8

**Contexto:**  
O `build.gradle` configura source/target compatibility para Java 8.

**Decisão:**  
Manter compatibilidade Java 8 até confirmação do runtime Sankhya.

**Motivo:**  
Ambientes Sankhya legados frequentemente exigem compatibilidade com versões antigas de Java, mas a versão real ainda precisa ser confirmada.

**Impacto:**  
Evitar APIs modernas do Java em alterações futuras até validação do ambiente de execução.