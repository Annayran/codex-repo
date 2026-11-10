# Fechamento necessário para homologação real — 29/10/2026

Atualização: cliente determinou manter DESCRICAO100. O Java7.14 adapta a
fragmentação sem truncar, conforme [histórico em campo100](eventos-100-caracteres-2026-10-29.md).
A orientação de ampliação abaixo descreve a análise anterior e foi substituída.

Demanda ZY-BLM-2026-RATEIO-001. Base autoritativa: IntPDD v1.1,20/10/2026.

## Evidência atual e bloqueio da simulação

Cliente confirmou SQLSERVER na consulta e INSERIR_EVENTO na simulação7.13. A imagem da consulta de estrutura mostra SANKHYA.AD_RATEVT.DESCRICAO varchar(100), nulo permitido. O manual Criacao-Manual-Telas.html, seção8, exige Texto tamanho4000. O código SimulacaoRepositoryAdRat grava fotografias em fragmentos3500 mais envelope, abaixo4000 e acima100. Essa divergência é compatível com a falha; a mensagem interna Jape não foi obtida, portanto não excluir outras validações apenas por esse print.

Correção requerida no ambiente: na mesma ferramenta Sankhya usada para criar os campos, abrir AD_RATEVT/DESCRICAO e ajustar de100 para4000 caracteres. Aplicar a alteração ao banco e à metadata da instância; confirmar/refrescar metadados conforme a ferramenta da instalação. Não basta alterar somente a coluna SQL se a metadata continuar limitando100. Não truncar a fotografia nem remover eventos para permitir sucesso: perderia o histórico necessário ao recálculo e reenvio.

Depois, repetir a consulta de estrutura e a simulação. Essa correção não exige novo JAR: o contrato4000 já é usado na7.13. Nenhuma alteração de campo foi executada por esta sessão; não há acesso remoto ao ambiente.

## A biblioteca já contempla tudo?

Não. O SDK Sankhya fornece Jape/JapeWrapper, sessão/transação, entidade Financeiro e a instância lógica RateioRecDesp. O dicionário local4.35b815 identifica RateioRecDesp como TGFRAT; isso deve ser conferido com a versão e a metadata efetivamente instaladas. SDK genérico não entrega a regra de negócio Belmicro pronta.

O projeto contém cálculo autoritativo, consulta real de TGFFIN/cadastros, distribuição e agrupamento, simulação/recálculo persistidos por adaptadores AD_RAT*, revisão/hash e idempotência. Os testes dessas camadas passaram, mas o ciclo real da simulação ainda está bloqueado pelo erro de persistência apresentado.

A efetivação financeira não está implementada. ConfiguracaoServicosRateio.efetivacao ainda lança EFETIVACAO_NAO_DISPONIVEL; DTO bloqueia podeEfetivar e o frontend também não trata uma efetivação como concluída. Não há gerador real integrado de auxiliares, escrita de TGFRAT, fechamento de execução e vínculos que possa ser declarado pronto. Ações Java e portas existentes não equivalem à implementação financeira.

## Fluxo final exigido pelo IntPDD

1. Simular/Recálculo: calcular com dados reais e gravar proposta, revisão, detalhes e fotografia em AD_RATSIM/PAR/GRP/INT/EVT. Não criar financeiro nessa etapa.
2. Efetivar: autorizar, bloquear, revalidar origem/entrada/hash, garantir idempotência/uma execução por origem e recalcular com o mesmo núcleo.
3. Criar auxiliares na TGFFIN pela entidade suportada, com NUFIN nativo, empresa beneficiária, valor do grupo e RECDESP0. TOP, tipo de título e demais obrigatoriedades devem vir da configuração aprovada.
4. Criar linhas nativas pela instância RateioRecDesp/TGFRAT vinculadas ao NUFIN auxiliar: CODCENCUS, CODNAT, PERCRATEIO, ORIGEM='F', demais campos obrigatórios e RATEADO='S' no financeiro conforme o contrato real. Conferir percentuais/centavos na precisão suportada pelo ambiente.
5. Persistir execução, vínculos e marcador, sem duplicar auxiliares no reenvio. Rollback integral em falha; erro auditável após rollback.
6. Consultar resultado efetivo, reabrir a execução e validar isolamento de pagamento/remessa/baixa/exportação dos auxiliares. RECDESP0 sozinho não comprova isolamento.

TGFRAT guarda o rateio nativo; não substitui tabelas de simulação, histórico, revisão e vínculos. IntPDD pp.13–16 prevê original preservado e rateio nativo dos auxiliares. Cliente foi consultado sobre incluir também rateio no original; resposta ainda pendente. Não interpretar “tudo em TGFRAT” como autorização para alterar o financeiro original durante a simulação.

## Pendências concretas

- Corrigir DESCRICAO4000 no ambiente e confirmar que simulação/recálculo reabrem do banco.
- Confirmar estrutura/PK/precisão reais de TGFRAT e campos de Financeiro. Script de consulta: scripts/sql/validar-rateio-homologacao.sql. Somente leitura; não executado no cliente.
- Confirmar TOP CODTIPOPER e CODTIPTIT próprios do auxiliar RECDESP0, sua versão vigente e regras de natureza/parceiro/datas/campos obrigatórios por empresa.
- Implementar e conectar efetivação transacional, gerador Financeiro/RateioRecDesp, representação nativa, execução, vínculos, recuperação e consulta final. Remover bloqueios da UI somente junto do contrato real validado.
- Definir e homologar as fontes analíticas por categoria quando aplicáveis; a modalidade analítica não está pronta. Não inferir beneficiários ou regras médicas/alimentação/transporte só do cadastro de funcionários.
- Executar cenários reais de sucesso, concorrência, rollback, reenvio, arredondamento e isolamento; não chamar teste com driver simulado de homologação concluída.

Cliente respondeu que TOP/tipo de título serão verificados na reunião de amanhã. Não foram inventados códigos nem habilitada criação financeira sem essa configuração. Não houve DML/DDL, deploy ou publicação nesta etapa.

## Origem e validação desta análise

Fontes: print de estrutura fornecido pelo cliente; Criacao-Manual-Telas.html seção8; IntPDD pp.13–16; dicionário TGFRAT4.35b815; configuração/portas e repositórios do projeto. Análise de código/documentos e revisão estática das consultas; sem execução em SQL Server nesta etapa. Não foi produzido novo JAR.
