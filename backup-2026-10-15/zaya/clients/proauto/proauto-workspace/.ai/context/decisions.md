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

### 2026-06-30 — Estrutura inicial de contexto para IA

**Contexto:**  
O projeto passou a usar uma pasta de contexto para orientar interações futuras com IA.

**Decisão:**  
Centralizar arquitetura, stack, padrões, regras, decisões, pendências e checkpoints em `/.ai/context`.

**Motivo:**  
Reduzir consumo de tokens e preservar contexto útil entre sessões.

**Impacto:**  
Novas conversas com IA devem começar lendo os arquivos desta pasta.

---

### 2026-06-30 — Informações não confirmadas devem permanecer explícitas

**Contexto:**  
O checkout analisado não contém código-fonte ou documentação de aplicação, e o repositório Git ainda não possui commits.

**Decisão:**  
Registrar linguagem, arquitetura, stack, regras e módulos como “A confirmar” até que haja evidência verificável no projeto.

**Motivo:**  
Evitar que futuras interações com IA partam de suposições incorretas.

**Impacto:**  
A próxima análise técnica deve complementar estes arquivos assim que o código-fonte estiver disponível.
