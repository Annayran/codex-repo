# Evento de Sinal CRM — V2.0

## Regra atual (17/09/2026)

Evento afterUpdate de CabecalhoNota (TGFCAB), com sinais independentes:

| Transicao | Sinal |
| --- | --- |
| AD_SOLMIN de diferente de S para S | br.zaya.solMinComercial |
| AD_LIBPROC de diferente de N para N | br.zaya.libProcComercial |

NUNOTA e AD_IDNEGOCIO sao obtidos diretamente do VO atual. AD_IDNEGOCIO
contem o ID da instancia enviado ao Flow, conforme orientacao do projeto.
Nao existe consulta SQL. AD_IDINSTPRN e IDNEGOCIO nao sao usados nesta versao.
O preenchimento anterior de AD_IDINSTPRN nao substitui AD_IDNEGOCIO: garantir
que o novo campo esteja preenchido antes da transicao.

Se ambos mudarem para os valores esperados no mesmo update, envia primeiro
minuta e depois liberacao. Manter os valores em edicoes seguintes nao reenvia.
Insercoes nao sinalizam. Uma nova transicao pode sinalizar novamente.
No afterUpdate, os valores anteriores sao obtidos de getModifingFields(),
usando getOldValue apenas para campos alterados. Campos nao alterados usam o
valor atual como anterior. Nao usar getOldVO(), exclusivo de beforeUpdate no SDK.
ID ausente ou invalido gera erro com a NUNOTA. Falhas do Flow sao propagadas.
Nao ha commit manual, fila, deduplicacao persistente ou retry.

## Cadastro e homologacao

Preservado: br.com.zaya.teleworld.flow.SinalLiberacaoProcesso.
Cadastrar como evento Java de CabecalhoNota. Artefato: ZAYA-CRM-V2.0.jar.
O ambiente precisa disponibilizar as APIs Sankhya/Flow em runtime.

Validar no servidor:
- Cada sinal isoladamente, com outra instancia aguardando para verificar isolamento.
- Ambos no mesmo update e repeticao de edicoes sem transicao.
- AD_IDNEGOCIO correto no VO e correspondencia com a instancia ativa.
- Processo ja posicionado no evento de recepcao do respectivo sinal.
- Falha no segundo sinal e rollback: atomicidade do Flow ainda nao comprovada.
- Liberacao anterior a espera: nao ha recuperacao automatica de sinal perdido.
- Tipo dos elementos no modelador: os IDs dos prints usam IntermediateThrowEvent;
  confirmar que sao elementos de espera/recepcao, sem inferir o tipo pelo ID.

Nao houve deploy. Reversao: desativar cadastro do evento; isso nao desfaz
instancias ja continuadas. Build e arquitetura no README da raiz.
