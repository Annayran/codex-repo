# Contrato de coleta v0.1

Status: proposta inicial, sem mapeamento físico homologado. Schema em contracts/stock-occurrence.schema.json.

| Grupo | Fonte candidata | Permissão/custo a validar | Se indisponível |
|---|---|---|---|
| Documento selecionado | Contexto do botão | Acesso ao registro; custo local | Impedir envio sem referência válida |
| Operação e mensagem | Relato do usuário | Texto limitado | Solicitar descrição; não inventar mensagem |
| Cliente/ambiente | Configuração e identidade autenticada | Validar no receptor | Rejeitar identidade inválida |
| Horário da ocorrência | Usuário | Aproximado quando informado | Omitir; nunca substituir por horário da coleta |
| Dados do documento/itens | Consulta autorizada por chave | Campos, paginação e limite de itens | Registrar coleta parcial |
| Tipo de operação | Configuração aplicável ao documento | Considerar versionamento histórico | Registrar lacuna |
| Saldos/reservas | Consulta específica aprovada | Empresa/local/controle/unidade e custo | Registrar indisponível, nunca saldo zero |
| Personalizações | Inventário mantido do ambiente | Componente e versão | Registrar desconhecido |
| Evidência técnica | Mensagem ou captura autorizada | Fonte, retenção e sanitização | Registrar não coletada |

## Semântica
Quantidades serão strings decimais para evitar perda de precisão; fonte, unidade, dimensões e instante devem acompanhar os valores quando a coleta for implementada. Não calcular saldo disponível até documentar a fórmula e a regra usadas pelo ambiente. A primeira versão permite evidências textuais com origem, sem alegar campos de banco validados.

Cada evidência exige source, capturedAt e summary. O resumo contém somente informação sanitizada pertinente. evidence.status distingue COLLECTED, UNAVAILABLE e FAILED. gaps lista lacunas gerais. Totais e cortes de coleta devem ser explícitos.

## Proteção de carga
Antes do piloto definir: número máximo de itens, consultas, tamanho do pacote, timeout por consulta e total, concorrência e política de reenvio. Valores não foram medidos. Não coletar o banco inteiro nem arquivos de log completos por padrão.

## Extensão do contrato
Versão inicial descreve envelope e evidências, não uma API de diagnóstico pronta. Acrescentar estruturas tipadas de itens/saldos após confirmar fontes e semântica. Não adicionar nome de campo físico por suposição.
