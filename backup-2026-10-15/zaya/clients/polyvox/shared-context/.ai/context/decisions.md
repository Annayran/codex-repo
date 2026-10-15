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

### A confirmar — Organização incremental por feature no Telecontrol

**Contexto:**  
O documento `PolyvoxTelecontrol/docs/telecontrol-organizacao.md` descreve uma reorganização incremental do pacote `br.zaya.telecontrol`.

**Decisão:**  
Aproximar o fluxo de um desenho MVC/feature-based, com `actions`, `application`, `infrastructure`, adapters, interfaces e serviços legados preservados.

**Motivo:**  
Melhorar separação de responsabilidades sem quebrar compatibilidade com classes e bindings do Sankhya.

**Impacto:**  
Refatorações devem manter bridges legadas e migrar metadata Sankhya antes de remover entradas antigas.

---

### A confirmar — Manter compatibilidade com actions legadas

**Contexto:**  
O projeto possui classes antigas em `actions/*` e classes por feature em `*/actions/*`.

**Decisão:**  
Manter classes de bridge legadas até que a metadata do Sankhya seja atualizada e validada.

**Motivo:**  
Evitar quebra operacional de rotinas e botões cadastrados no Sankhya.

**Impacto:**  
Não remover bridges por limpeza; qualquer remoção precisa de plano de migração.

---

### 2026-06-18 — Estrutura inicial de contexto para IA

**Contexto:**  
O projeto passou a usar uma pasta de contexto para orientar interações futuras com IA.

**Decisão:**  
Centralizar arquitetura, stack, padrões, regras, decisões, pendências e checkpoints em `/.ai/context`.

**Motivo:**  
Reduzir consumo de tokens e preservar contexto útil entre sessões.

**Impacto:**  
Novas conversas com IA devem começar lendo os arquivos desta pasta.
