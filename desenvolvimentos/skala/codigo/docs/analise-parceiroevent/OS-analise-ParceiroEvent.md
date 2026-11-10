# OS — Análise do evento br.zaya.events.ParceiroEvent

## ENTREGÁVEL
Realizada análise técnica do evento br.zaya.events.ParceiroEvent e da classe br.zaya.rules.ParceiroRegras, presentes no 70_Skala.jar do arquivo jares_tsijar.zip disponibilizado.

Identificado que a RN0006 exige Empresa Preferencial (CODEMPPREF) e Tabela de Preço (CODTAB) não nulas quando o parceiro está marcado como cliente e não fornecedor. Para esse mesmo perfil, se estiver ativo, exige também Vendedor Preferencial (CODVEND) preenchido e diferente de zero.

A validação é chamada após a inclusão e após qualquer atualização do parceiro, independentemente do campo alterado. Para pessoa jurídica, também é chamada antes da inclusão. Por isso, a alteração somente do Grupo de Autorização pode ser bloqueada por ausência dos campos obrigatórios. O lançamento da exceção visa impedir a conclusão da gravação; o comportamento transacional não foi testado no ambiente.

Identificado também que a RN0007 exige AD_PERMALTLIM=S no usuário ao alterar Grupo de Autorização (GRUPOAUTOR), Limite de Crédito (LIMCRED) ou Limite de Crédito Mensal (LIMCREDMENSAL). Essa permissão não dispensa a RN0006. Tipo de Negociação não é consultado nem alterado pelas classes analisadas.

Complementarmente, o evento converte Nome e Razão Social para maiúsculas na inclusão e quando esses campos são alterados. Antes da inclusão, se qualquer campo entre LIMCRED, LIMCREDMENSAL e GRUPOAUTOR estiver não nulo, os três são definidos como nulos.

Produzida explicação das condicionais para alinhamento funcional. Nenhuma alteração de código, banco ou implantação foi realizada.

## VALIDAÇÃO
Conferência técnica por inspeção estática do bytecode com javap. Evidências: docs/analise-parceiroevent/ParceiroEvent.javap.txt, ParceiroRegras.javap.txt e analise.md no repositório SKala.

Validação ou aprovação pelo cliente: não informada. Nome/e-mail do validador, canal e evidência de aceite: não informados. Não houve simulação no Sankhya nem confirmação de que o JAR analisado corresponde ao implantado.

## PENDÊNCIA
Confirmar o artefato implantado e a configuração do evento; conferir os cadastros e o histórico para esclarecer o comportamento anterior. Caso seja solicitado ajuste, definir formalmente em qual etapa os campos devem se tornar obrigatórios e validar o fluxo completo de inclusão e atualização.

Responsável, acordo com o cliente e data de FUP: não informados. Análise técnica concluída; eventual implementação permanece pendente de definição funcional e autorização.

## PREVISÃO
Próxima data acordada: não informada.

---
Fonte canônica: repositório local SKala, docs/analise-parceiroevent/OS-analise-ParceiroEvent.md. Documento elaborado em 23/10/2026 a partir da análise desta demanda. Número da OS e horas não informados; este relato não representa lançamento no Z-One.
