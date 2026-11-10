from pathlib import Path
from xml.sax.saxutils import escape
import hashlib
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, PageBreak
from reportlab.lib.styles import ParagraphStyle
from reportlab.lib import colors
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont

ROOT=Path(__file__).resolve().parents[1]
OUT=ROOT/'output/levantamento-clientes'
OUT.mkdir(parents=True,exist_ok=True)
pdfmetrics.registerFont(TTFont('Arial','C:/Windows/Fonts/arial.ttf'))
pdfmetrics.registerFont(TTFont('ArialB','C:/Windows/Fonts/arialbd.ttf'))
G=colors.HexColor('#00AA83')
styles={
 'body':ParagraphStyle('body',fontName='Arial',fontSize=10,leading=15,spaceAfter=10,textColor=colors.HexColor('#252A30')),
 'small':ParagraphStyle('small',fontName='Arial',fontSize=8,leading=11,spaceAfter=8,textColor=colors.HexColor('#64716D')),
 'band':ParagraphStyle('band',fontName='ArialB',fontSize=12,leading=16,textColor=colors.white),
 'title':ParagraphStyle('title',fontName='ArialB',fontSize=24,leading=30,alignment=1,spaceAfter=20),
 'sub':ParagraphStyle('sub',fontName='ArialB',fontSize=12,leading=17,spaceAfter=12,textColor=G),
 'cell':ParagraphStyle('cell',fontName='Arial',fontSize=8.3,leading=12),
}
def p(t,style='body'):return Paragraph(escape(t).replace('\n','<br/>'),styles[style])
def box(t):
 t=Table([[p('Atenção\n'+t)]],colWidths=[511]);t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,-1),colors.HexColor('#D9EBD2')),('LINEBEFORE',(0,0),(0,-1),2,colors.HexColor('#70A956')),('LEFTPADDING',(0,0),(-1,-1),10),('TOPPADDING',(0,0),(-1,-1),9)]));return t
def band(t):
 t=Table([[p(t,'band')]],colWidths=[511]);t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,-1),G),('LEFTPADDING',(0,0),(-1,-1),10),('TOPPADDING',(0,0),(-1,-1),7),('BOTTOMPADDING',(0,0),(-1,-1),7)]));return t
def frame(c,d):
 w,h=d.pagesize
 c.drawImage(str(ROOT/'output/manual-genesis/marca-zaya.png'),w/2-66,h-59,width=132,height=29,mask='auto')
 c.saveState();path=c.beginPath();path.rect(0,0,w,24);c.clipPath(path,stroke=0);c.linearGradient(0,0,w,0,[colors.HexColor('#11990F'),colors.HexColor('#5BE000')]);c.restoreState()
 c.setFillColor(colors.white);c.setFont('ArialB',8);c.drawString(42,9,f'{d.page:02d} | Zaya.IT - MA Hospitalar')
 c.setFillColor(colors.HexColor('#64716D'));c.setFont('Arial',7);c.drawRightString(w-42,36,'REQUISITOS CLIENTES | 1.1 | 23/10/2026')

pages=[
('1. Objetivo, escopo e evidências',[
'Solicitação relatada pelo solicitante: Luiz mencionou integrar também o endpoint de clientes. Este levantamento propõe manter as proteções e a rastreabilidade do fluxo de equipamentos, sem presumir aprovação de uma sincronização global ou de atualizações por PUT.',
'Situação atual: cliente já é uma dependência do movimento de equipamento elegível. Não existe, nesse caminho revisado, seleção autônoma de todos os clientes do Sankhya. A preparação resolve no máximo uma dependência por rodada; a conclusão pode exigir execuções sucessivas.',
'Evidência consultada: DTO e fábrica do payload de cliente, repositório de candidatos, consulta de seleção, serviço de resolução de cadastros e preparação de dependências, persistência local e documentação de base única, controles e escopo.',
'Contrato da API: os recursos e campos abaixo são os usados pelo projeto. O OpenAPI/Swagger vigente completo não foi localizado nesta revisão. Obrigatoriedade, limites de tamanho, nulabilidade, paginação e regras atuais de PUT precisam ser confirmados com a Genesis. Não foram feitas chamadas reais à API neste levantamento.',
'Natureza da entrega: análise estática e proposta de requisitos. Não houve alteração da integração, consulta de dados cadastrais em produção, execução de testes novos ou início do desenvolvimento de clientes.'
]),
('2. Como o processo funciona hoje',[
'1. A seleção parte de movimentos elegíveis de equipamentos M1/M3, com série e tipo de operação configurado. O cliente deriva do parceiro da nota; portanto, um cliente sem movimento elegível pode não entrar nesse processo.',
'2. A preparação segue cliente → setor → material → fabricante → modelo → equipamento. Cliente e setor precisam estar confirmados antes do equipamento.',
'3. Com ID local ou protocolo durável, o serviço consulta por ID e valida identidade. Sem ID, consulta por código externo antes de considerar inclusão. Um ID que retorna erro não autoriza recriação automática.',
'4. O POST ocorre somente após ausência confirmada e reserva durável. Se o retorno for incerto, o controle permanece bloqueado para nova inclusão até reconciliação. Uma queda após confirmação do ID pode ser recuperada por consulta, sem novo POST.',
'5. O código externo novo de cliente é SNK-CLI-<código do parceiro>. Códigos legados conciliados devem ser preservados. A identificação remota usa CPF/CNPJ quando disponível; código externo não substitui a confirmação de identidade.',
'6. O caminho atual de cadastros não faz PUT de cliente. Isso é diferente da migração dedicada de modelos e do catálogo de estoques, que possuem regras próprias. Reproduzir o fluxo de equipamentos significa reaproveitar consulta, inclusão segura e confirmação, não prometer atualização automática já existente.'
]),
('3. Campos: identidade e dados cadastrais',[
'A matriz usa nomes das telas e campos para a operação e nomes JSON para o contrato técnico. Disponibilidade no modelo do Sankhya não comprova preenchimento dos cadastros reais.',
'Código do parceiro → codigoExterno. Origem: Parceiros / Código. Atual: código SNK-CLI gerado a partir do parceiro, preservando vínculo legado. Requisito: código estável, único por cadastro, sem alteração automática para contornar duplicidade.',
'CPF/CNPJ → cpf ou cnpj. Origem: Parceiros / CPF/CNPJ. Atual: somente dígitos; o código escolhe CPF se o comprimento for até 11 e CNPJ caso contrário. A ausência é bloqueada. Lacuna: essa escolha por comprimento não comprova documento válido. Proposta: validar exatamente 11 ou 14 dígitos, tipo de pessoa e dígitos verificadores, com regra explícita para exceções.',
'Nome do parceiro → nomeFantasia; Razão social → razaoSocial. Origem: Parceiros. Atual: os dois campos são mapeados diretamente. Requisito: acordar nome obrigatório, tratamento de pessoa física, ausência de razão social e limite de tamanho, sem inventar dados de preenchimento.',
'Ativo → status. Origem: Parceiros / Ativo. Atual: status Ativo/Inativo, com padrão Ativo para situação desconhecida. Requisito: confirmar valores aceitos, bloquear situação inválida em vez de ativar silenciosamente e decidir se a inativação será propagada.',
'Documento e código externo devem ser verificados em conjunto com o vínculo confirmado. Mesmo nome não autoriza apropriar cadastro de outro parceiro.'
]),
('4. Campos: contato e endereço',[
'Telefone → telefone. Origem: Parceiros / Telefone. Atual: carregado, enviado apenas com dígitos. Requisito: definir DDD, código de país, tamanho e telefone alternativo. Não converter ausência em número fictício.',
'CEP → cep. Origem: Parceiros / CEP. Atual: carregado e normalizado para dígitos. Requisito: validar formato conforme país e contrato; não eliminar zeros à esquerda.',
'Endereço → endereco. Origem: endereço do parceiro, número e complemento. Atual: a consulta compõe logradouro, número, complemento, bairro, cidade, UF e CEP em uma única descrição. Requisito: alinhar o formato esperado e evitar duplicação se também houver campos separados.',
'E-mail → email. O DTO e a fábrica suportam o campo, mas o repositório de candidatos revisado não atribui o e-mail ao candidato. Proposta: selecionar a origem correta no cadastro de Parceiros, carregar explicitamente e validar o formato e a política de contato.',
'Bairro → enderecoBairro; Cidade → enderecoCidade; UF → enderecoUF. Existem fontes relacionais de endereço no Sankhya e o DTO prevê os três campos. Porém, o candidato revisado não recebe essas três atribuições separadas, mesmo havendo cidade na consulta e dados no endereço consolidado. Proposta: completar seleção e mapeamento explícitos.',
'Número do endereço: a persistência reconhece enderecoNumero em retornos, mas o DTO de envio atual não possui esse atributo. Confirmar se a API o aceita/exige separadamente antes de acrescentá-lo.',
'Não há confirmação de que contato e endereço sejam obrigatórios na API atual. A definição deve constar na matriz de contrato aprovada, com nulabilidade e limites; não assumir que campo do DTO é obrigatório no servidor.'
]),
('5. Recursos da API e confirmação',[
'GET /api/v1/clientes/?codigoExterno=...: consulta já utilizada para localizar cliente sem ID conhecido. Requisito: documentar resposta vazia, resultado único, múltiplos resultados e erro técnico; só ausência confirmada permite considerar inclusão.',
'GET /api/v1/clientes/{id}: consulta de ID já conhecido. Requisito: comparar o ID retornado e documento esperado. Divergência ou 404 com vínculo existente deve seguir para revisão, sem excluir vínculo nem criar cadastro substituto.',
'POST /api/v1/clientes/: inclusão já existente no fluxo dependente. Requisito: reserva antes do envio, retorno com ID positivo, confirmação durável e gravação do vínculo. HTTP 2xx isolado não comprova toda a persistência.',
'PUT /api/v1/clientes/{id}: possível evolução para atualizar clientes. Não executado pelo caminho atual de preparação. Exige contrato vigente, definição dos campos que o Sankhya pode sobrescrever, semântica de null/omissão e proteção contra alterações concorrentes.',
'Pesquisa por documento: não presumir parâmetro cnpj. O diagnóstico histórico do projeto registra rejeição dessa consulta e exige nome ou codigoExterno. Confirmar alternativas atuais; nunca usar primeiro resultado por nome sem correspondência exata de documento.',
'DELETE: fora da proposta-base. Nenhuma exclusão de cliente, setor ou equipamento deve ser incluída como consequência implícita de inativação no Sankhya.',
'O setor é um recurso separado: setores/ e setores/{id}. Integrar cliente não confirma setor nem equipamento. Reaproveitar setor correto por cliente; não duplicar setor padrão nem alterar sua titularidade.'
]),
('6. Requisitos da evolução proposta',[
'RF01 - Seleção autônoma: definir clientes elegíveis sem depender de nota ou série. Decisão pendente: todos os clientes ativos, apenas clientes com equipamentos, marcação específica ou outra regra. Não reutilizar o filtro M1/M3 como classificação de cliente.',
'RF02 - Cadastro completo: criar consulta própria de Parceiros e endereço; completar e-mail, bairro, cidade e UF. Não mudar a seleção de equipamentos apenas para obter clientes.',
'RF03 - Conciliação: aproveitar a base local e IDs já confirmados, conferir documento, impedir múltiplos parceiros apropriando o mesmo cliente remoto e encaminhar duplicidade ao responsável.',
'RF04 - Inclusão segura: manter reserva durável, consulta prévia, ID confirmado e proteção ENVIO_INCERTO. Compartilhar identidade e controle com o fluxo de equipamentos para que os dois processos não incluam o mesmo cliente simultaneamente.',
'RF05 - Atualização: somente se aprovada. Detectar alteração dos campos autorizados, consultar cadastro atual, atualizar pelo mesmo ID e confirmar por nova consulta. Não sobrescrever campos de manutenção exclusiva da Genesis.',
'RF06 - Agendamento: estabelecer frequência, tamanho do lote, ordenação, controle de concorrência, retomada e limites de chamadas. São decisões de configuração/homologação, não horários presumidos neste documento.',
'RF07 - Acompanhamento: separar fila de clientes dos movimentos de equipamento. Exibir cliente, etapa, tentativa, motivo, ID confirmado e próxima ação; não inflar indicador de equipamentos com controles de clientes.',
'RF08 - Setor e inativação: acordar se fazem parte da entrega. A sincronização do cliente isoladamente não autoriza criar setores indiscriminadamente nem inativar dependências.'
]),
('7. Proteções e critérios de aceite',[
'Cliente novo válido: selecionado pelo filtro aprovado, consulta sem correspondência, uma inclusão confirmada e vínculo persistido. Nova execução não gera duplicata.',
'Cliente existente conciliado: consulta confirma ID e documento; vínculo e código externo legítimo preservados, sem POST. Existente com código legado diferente: revisão/conciliação controlada, sem inferir ausência remota.',
'Documento inválido, ausente ou duplicado: registro bloqueado com causa compreensível, sem chamada de inclusão. Bairro/cidade/UF/e-mail devem refletir a origem correta quando preenchidos e fazer parte dos testes de mapeamento.',
'Timeout ou retorno sem ID: reserva preservada, consulta de reconciliação e nenhum POST cego. Sucesso remoto com falha local deve permitir recuperação do ID já confirmado.',
'HTTP 429: respeitar cooldown compartilhado. HTTP 401/403: interromper tentativas incompatíveis com falha de acesso. HTTP 5xx não representa ausência de cadastro.',
'Concorrência: rotina de clientes e envio de equipamento não criam dois clientes para a mesma identidade. Equipamento continua aguardando cliente/setor confirmado e mantém suas regras M1/M3.',
'Atualização, se contratada: conferir campo antes/depois, ID inalterado, campos remotos exclusivos preservados e consulta posterior confirmada. Inativação só pode ser testada após regra e massa aprovadas.',
'Aceite: Luiz ou responsável designado registra data, cenário, resultado e canal de aprovação. Evidência mínima: identificação do caso, retorno sanitizado, consulta posterior e vínculo local, sem senha ou documento integral.'
]),
('8. Planejamento e decisões pendentes',[
'Estimativa preliminar para sincronização independente, com inclusão, atualização delimitada e reaproveitamento da estrutura: 12 a 16 horas. Sugestão de reserva: 16 horas. Não representa horas executadas nem contratação aprovada.',
'Composição: requisitos e filtros 2h; seleção/rotina/criação/atualização 4-6h; testes e proteções 3-4h; implantação e apoio à homologação 3-4h. Tempo de espera por retorno externo não equivale a esforço apontado.',
'Condições: API atual compatível, campos e permissões disponíveis, massa controlada e reaproveitamento dos controles existentes. Se o contrato impedir atualização segura ou exigir nova estrutura, reestimar antes de implementar.',
'Fora da estimativa-base: carga inicial em massa, saneamento de duplicidades legadas, novas telas completas, sincronização bidirecional, exclusões e regras adicionais de inativação ou setores não delimitadas.',
'Decisões para Luiz/cliente: quais parceiros integrar; qual cadastro é mestre por campo; se haverá PUT; como tratar inativos; regra de setor; volume inicial; frequência; responsável pelo saneamento e aceite.',
'Próximo passo: revisar esta matriz com Luiz e obter o contrato vigente da API. Após aprovar regras e estimativa, implementar em homologação, executar critérios de aceite e só então autorizar implantação em produção.'
]),
('9. Horas e entregas já registradas',[
'Fonte: Relatório de Apontamento de Horas ZOne, gerado em 23/10/2026, período 21/10 a 23/10/2026. OS 8271. O documento recebido registra total geral de 7h30.',
'21/10/2026 | 09:00-12:00 | 3h: correção da identificação dos modelos por HSP_IDEXTERNO, adequação do PUT dos modelos existentes e do POST de novos modelos. Publicação em produção registrada; evidências conclusivas e aceite permanecem pendentes no próprio apontamento.',
'22/10/2026 | 09:00-12:00 | 3h: melhoria M1/M3 no fluxo de equipamentos e correção do campo de controle da atualização de modelos. O relatório registra 186 testes locais aprovados e disponibilização em produção, com Luiz validando.',
'23/10/2026 | 10:30-12:00 | 1h30: manual de 25 páginas, capturas reais, regras, orientação para erros e revisão visual. Documentação revisada para o padrão do Guia CIOT fornecido.',
'As datas acima reproduzem o relatório administrativo; não foram inferidas da data de geração dos artefatos. O levantamento de clientes e sua estimativa não foram somados às 7h30 nem lançados como horas executadas.',
'Conferência administrativa: o campo Cliente do relatório está como DEV TRIANGULO/SUDESTE, embora as descrições sejam de MA Hospitalar. Confirmar a alocação antes do fechamento ou encaminhamento; nenhum dado do relatório foi alterado.'
]),
('10. Fontes e limites de validação',[
'Relatório de horas fornecido pelo solicitante: Relatorio_Horas_2026-10-23.pdf. Referência visual: Guia_do_Usuario_CIOT_Sankhya_Rio_Pardo.pdf. O conteúdo de outro cliente não foi incorporado.',
'Código: ClienteGenesisDto.java e PayloadGenesisFactory.java (campos e formação do payload); CandidatoGenesisRepository.java e 01_ad_vw_genesis_estoque.sql (origem e lacunas); CadastroGenesisPreparationService.java e GenesisCadastroSyncService.java (sequência, GET/POST e identidade); BaseGenesisApiRepository.java (persistência).',
'Documentos: base-unica-post-get.md, rastreabilidade-controles.md, justificativa-ajuste-escopo-integracao-genesis.md e postman/README.md. O exemplo histórico de pesquisa por documento é evidência de limitação anterior, não confirmação do contrato atual.',
'Origem técnica canônica: diretórios src/, sql/genesis/ e docs/ do projeto MA Hospitalar. Esta entrega é análise local; não comprova obrigatoriedades remotas, preenchimento do cadastro em produção nem versão instalada.',
'Sem exposição de URL interna, token, CPF/CNPJ real, senha ou massa do cliente. Não houve execução de chamadas remotas, mudança de banco, publicação Git ou envio de e-mail nesta atividade.'
])]

def grid(headers, rows, widths):
 data=[[p(t,'cell') for t in headers]]+[[p(t,'cell') for t in row] for row in rows]
 t=Table(data,colWidths=widths,repeatRows=1,hAlign='LEFT')
 t.setStyle(TableStyle([
 ('BACKGROUND',(0,0),(-1,0),colors.HexColor('#D9EBD2')),
 ('ROWBACKGROUNDS',(0,1),(-1,-1),[colors.white,colors.HexColor('#F4F7F6')]),
 ('VALIGN',(0,0),(-1,-1),'TOP'),('LEFTPADDING',(0,0),(-1,-1),8),
 ('RIGHTPADDING',(0,0),(-1,-1),8),('TOPPADDING',(0,0),(-1,-1),7),
 ('BOTTOMPADDING',(0,0),(-1,-1),7),
 ('LINEBELOW',(0,0),(-1,0),0.8,G),
 ('LINEBELOW',(0,1),(-1,-1),0.4,colors.HexColor('#DCE3DF'))]))
 return t

pages=pages[:8]
story=[band('LEVANTAMENTO DE REQUISITOS'),Spacer(1,70),p('Clientes no Sankhya × Genesis\nMapeamento e evolução do fluxo','title'),p('MA Hospitalar\nProposta para alinhamento funcional e técnico','body'),Spacer(1,18),box('O cliente já integra como dependência do equipamento. A nova demanda é delimitar sua integração independente, sem repetir cadastros nem alterar vínculos sem confirmação.'),Spacer(1,18),p('Resumo da entrega','sub'),p('Campos disponíveis e lacunas de mapeamento; recursos da API; requisitos, critérios de aceite, decisões pendentes e estimativa de esforço.'),p('Versão 1.1 - 23/10/2026. Levantamento preliminar, sujeito à validação de Luiz e confirmação do contrato da Genesis.','small'),PageBreak()]
md=['# Clientes no Sankhya × Genesis - Levantamento de requisitos','\nVersão 1.1 - 23/10/2026. Proposta, não implementação.\n']
for title,paras in pages:
 story.extend([band(title),Spacer(1,16)])
 md.append('\n## '+title+'\n')
 if title.startswith('8.'):
  story.append(p('Estimativa de esforço','sub'))
  effort=[('Requisitos e filtros','2h'),('Seleção, rotina, inclusão e atualização delimitada','4-6h'),('Testes, proteção contra duplicidade e retomada','3-4h'),('Implantação e apoio à homologação','3-4h'),('Total estimado','12-16h')]
  story.extend([grid(['Atividade','Esforço'],effort,[421,90]),Spacer(1,14),box('Reserva recomendada: 16 horas. Estimativa preliminar, não executada e sujeita à aprovação. Espera por retorno externo não equivale a esforço apontado.'),Spacer(1,14),p('Premissas e alinhamentos','sub')])
  decisions=[
   ('Condições','API compatível, campos e permissões disponíveis, massa controlada e reaproveitamento dos controles. Reestimar se o contrato exigir nova estrutura.'),
   ('Fora da estimativa','Carga inicial em massa, saneamento de duplicidades, novas telas completas, sincronização bidirecional, exclusões e regras adicionais de inativação ou setores.'),
   ('Definir com Luiz/cliente','Parceiros elegíveis; cadastro mestre por campo; uso de PUT; inativação; setor padrão; volume inicial; frequência; responsável pelo saneamento e aceite.'),
   ('Próximo passo','Revisar requisitos com Luiz e obter contrato vigente. Após aprovação de escopo e esforço: implementar em homologação, validar critérios de aceite e solicitar autorização para produção.')]
  story.append(grid(['Ponto','Definição / encaminhamento'],decisions,[110,401]))
  md.extend(['### Estimativa de esforço\n','| Atividade | Esforço |','| --- | --- |']+['| '+a+' | '+b+' |' for a,b in effort]+['\nReserva recomendada: 16h. Estimativa, não executada.\n','### Premissas e alinhamentos\n','| Ponto | Definição / encaminhamento |','| --- | --- |']+['| '+a+' | '+b+' |' for a,b in decisions])
 else:
  for text in paras:story.append(p(text));md.append(text+'\n')
 story.append(PageBreak())
SimpleDocTemplate(str(OUT/'MA-Hospitalar-Genesis-Requisitos-Clientes.pdf'),pagesize=(595.28,841.89),leftMargin=42,rightMargin=42,topMargin=83,bottomMargin=55,title='MA Hospitalar - Genesis - Requisitos de clientes',author='Zaya.IT').build(story,onFirstPage=frame,onLaterPages=frame)
(OUT/'Requisitos-Clientes.md').write_text('\n'.join(md),encoding='utf-8')
print('PDF e levantamento Markdown gerados:',OUT)
