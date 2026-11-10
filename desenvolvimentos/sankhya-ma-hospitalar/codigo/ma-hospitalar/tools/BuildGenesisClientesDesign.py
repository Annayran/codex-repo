"""Branded client-facing edition; no changes to integration behavior."""
from pathlib import Path
from xml.sax.saxutils import escape
import ast
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, PageBreak, Flowable, KeepTogether
from reportlab.lib.styles import ParagraphStyle
from reportlab.lib import colors
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont

ROOT=Path(__file__).resolve().parents[1]
OUT=ROOT/'output/levantamento-clientes'
pdfmetrics.registerFont(TTFont('ZA','C:/Windows/Fonts/arial.ttf'))
pdfmetrics.registerFont(TTFont('ZB','C:/Windows/Fonts/arialbd.ttf'))
GREEN=colors.HexColor('#009D78')
LIME=colors.HexColor('#79C646')
INK=colors.HexColor('#20272B')
MUTED=colors.HexColor('#5B6B70')
LINE=colors.HexColor('#DCE5E1')
PALE=colors.HexColor('#EDF6EF')
W=507
S={
 'body':ParagraphStyle('body',fontName='ZA',fontSize=10,leading=15,textColor=INK,spaceAfter=9),
 'lead':ParagraphStyle('lead',fontName='ZA',fontSize=11,leading=16,textColor=MUTED,spaceAfter=15),
 'h1':ParagraphStyle('h1',fontName='ZB',fontSize=26,leading=31,textColor=INK,spaceAfter=10),
 'h2':ParagraphStyle('h2',fontName='ZB',fontSize=12,leading=17,textColor=GREEN,spaceBefore=10,spaceAfter=9),
 'kicker':ParagraphStyle('kicker',fontName='ZB',fontSize=8,leading=12,textColor=GREEN,spaceAfter=10),
 'cell':ParagraphStyle('cell',fontName='ZA',fontSize=8.6,leading=12,textColor=INK),
 'cellb':ParagraphStyle('cellb',fontName='ZB',fontSize=8.6,leading=12,textColor=INK),
 'th':ParagraphStyle('th',fontName='ZB',fontSize=8.2,leading=11,textColor=colors.white),
 'note':ParagraphStyle('note',fontName='ZA',fontSize=8.5,leading=12,textColor=MUTED,spaceAfter=6),
 'title':ParagraphStyle('title',fontName='ZB',fontSize=42,leading=48,textColor=INK,spaceAfter=12),
}
def p(t,style='body'):
 return Paragraph(escape(t).replace('\n','<br/>'),S[style])
def tag(t):return p(t.upper(),'kicker')
def heading(n,title,subtitle):
 return [tag(f'REQUISITOS DE INTEGRAÇÃO  /  {n:02d}'),p(title,'h1'),p(subtitle,'lead')]
def table(head,rows,widths):
 data=[[p(x,'th') for x in head]]+[[p(x,'cellb' if j==0 else 'cell') for j,x in enumerate(row)] for row in rows]
 t=Table(data,colWidths=widths,repeatRows=1,hAlign='LEFT')
 t.setStyle(TableStyle([
 ('BACKGROUND',(0,0),(-1,0),GREEN),
 ('ROWBACKGROUNDS',(0,1),(-1,-1),[colors.white,colors.HexColor('#F4F7F5')]),
 ('VALIGN',(0,0),(-1,-1),'TOP'),('LEFTPADDING',(0,0),(-1,-1),9),('RIGHTPADDING',(0,0),(-1,-1),9),
 ('TOPPADDING',(0,0),(-1,0),10),('BOTTOMPADDING',(0,0),(-1,0),10),
 ('TOPPADDING',(0,1),(-1,-1),9),('BOTTOMPADDING',(0,1),(-1,-1),9),
 ('LINEBELOW',(0,1),(-1,-1),0.4,LINE)]))
 return t
def callout(title,text,tone='green'):
 bg=PALE if tone=='green' else colors.HexColor('#FFF5E4')
 accent=GREEN if tone=='green' else colors.HexColor('#A56B19')
 t=Table([[p(title,'cellb')],[p(text,'body')]],colWidths=[W])
 t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,-1),bg),('LINEBEFORE',(0,0),(0,-1),3,accent),('LEFTPADDING',(0,0),(-1,-1),13),('RIGHTPADDING',(0,0),(-1,-1),13),('TOPPADDING',(0,0),(-1,0),11),('BOTTOMPADDING',(0,0),(-1,0),2),('BOTTOMPADDING',(0,1),(-1,-1),4)]))
 return KeepTogether([Spacer(1,12),t,Spacer(1,8)])
def labeltext(title,text):return KeepTogether([p(title,'h2'),p(text)])

class Flow(Flowable):
 def __init__(self):Flowable.__init__(self);self.width=W;self.height=106
 def draw(self):
  c=self.canv
  labels=['CLIENTE','SETOR','MATERIAL','FABRICANTE','MODELO','EQUIPAMENTO']
  step=86
  for i,name in enumerate(labels):
   x=i*step
   c.setFillColor(GREEN if i<2 else PALE);c.rect(x,42,76,36,fill=1,stroke=0)
   c.setFillColor(colors.white if i<2 else INK);c.setFont('ZB',7.2);c.drawCentredString(x+38,56,name)
   if i<5:
    c.setStrokeColor(GREEN);c.setLineWidth(1);c.line(x+78,60,x+84,60);c.line(x+82,62,x+84,60);c.line(x+82,58,x+84,60)
  c.setFillColor(MUTED);c.setFont('ZA',8);c.drawString(0,17,'Dependências confirmadas antes de concluir o equipamento.')

def frame(c,d):
 w,h=d.pagesize
 if d.page==1:
  c.setFillColor(GREEN);c.rect(0,h-8,w,8,fill=1,stroke=0)
  c.drawImage(str(ROOT/'output/manual-genesis/marca-zaya.png'),44,h-89,width=168,height=37,mask='auto')
 else:
  c.drawImage(str(ROOT/'output/manual-genesis/marca-zaya.png'),44,h-53,width=100,height=22,mask='auto')
  c.setFillColor(MUTED);c.setFont('ZA',8);c.drawRightString(w-44,h-43,'MA HOSPITALAR  /  GENESIS')
  c.setStrokeColor(LINE);c.line(44,h-65,w-44,h-65)
 c.setFillColor(GREEN);c.rect(0,0,w,9,fill=1,stroke=0)
 c.setFillColor(LIME);c.rect(w-115,0,115,9,fill=1,stroke=0)
 c.setStrokeColor(LINE);c.line(44,47,w-44,47)
 c.setFillColor(MUTED);c.setFont('ZA',7.5);c.drawString(44,31,'Zaya.IT  |  Levantamento de requisitos  |  v1.3  |  23/10/2026')
 c.setFillColor(GREEN);c.setFont('ZB',9);c.drawRightString(w-44,30,f'{d.page:02d}')

story=[]
story += [Spacer(1,44),tag('MA HOSPITALAR  /  EVOLUÇÃO DA INTEGRAÇÃO'),Spacer(1,14),p('Clientes\nSankhya × Genesis','title'),p('Levantamento de requisitos\ne proposta de evolução do fluxo','lead'),Spacer(1,14)]
story += [table(['PONTO DE PARTIDA','EVOLUÇÃO A DELIMITAR'],[
 ('Cliente vinculado ao equipamento','Processamento independente de clientes'),
 ('Consulta e inclusão protegida','Critérios de seleção e manutenção aprovados'),
 ('Identidade e controles existentes','Reaproveitar vínculos sem criar duplicidades')],[253.5,253.5])]
story += [callout('OBJETIVO','Definir como os dados de Parceiros no Sankhya devem alimentar a Genesis, preservando a identificação e as proteções já usadas no envio de equipamentos.'),Spacer(1,10)]
story += [table(['ABRANGÊNCIA','PLANEJAMENTO','SITUAÇÃO'],[('Clientes, saneamento, telas e sincronização bidirecional','Escopo ampliado\nEsforço a reestimar','Seleção definida pelo solicitante\nContrato e regras técnicas a validar')],[169,169,169]),Spacer(1,16),p('Guia de leitura','h2'),p('02  Escopo  ·  03  Fluxo atual  ·  04-05  Campos\n06  API  ·  07  Requisitos  ·  08  Aceite  ·  09  Planejamento','note'),p('Não representa desenvolvimento concluído, autorização de implantação ou aceite do cliente.','note'),PageBreak()]

story += heading(1,'O que estamos propondo','Uma evolução do fluxo existente, com responsabilidades e limites claros.')
story += [labeltext('Seleção definida','Integrar todos os parceiros da base instalada e os parceiros necessários aos itens-materiais em que o cliente não foi localizado. Não é envio indiscriminado de todos os parceiros do ERP. Sem vínculo de origem ou com identidade ambígua, tratar a pendência antes de associar o cliente.')]
story += [table(['HOJE','NA EVOLUÇÃO PROPOSTA'],[
 ('Seleção a partir do movimento de equipamento.','Seleção própria de parceiros, com regra aprovada.'),
 ('Cliente resolvido como dependência do equipamento.','Rotina independente, compartilhando a mesma identidade.'),
 ('Consulta, reutilização e inclusão segura.','Manter as proteções e definir se haverá atualização por PUT.'),
 ('Retorno acompanhado pelo fluxo cadastral existente.','Acompanhar clientes sem misturar seus controles com equipamentos.')],[253.5,253.5])]
story += [labeltext('Resultado esperado','Cliente identificado de forma única, dados necessários mapeados, vínculo local confirmado e retomada segura após falhas. Setor e equipamento continuam dependendo de confirmação dos respectivos cadastros.')]
story += [callout('CONTRATO A CONFIRMAR','Os campos e recursos apresentados refletem o projeto revisado. Obrigatoriedade, limites de tamanho, nulabilidade, paginação e regras vigentes de PUT precisam ser confirmados com a Genesis. Não houve chamada real à API neste levantamento.','amber')]
story += [p('Incluídos por solicitação: saneamento de duplicidades, novas telas, sincronização bidirecional, tratamento de exclusões e regras de inativação/setores. Apenas a carga inicial em massa fica fora. Seleção contínua do universo definido não equivale a uma operação de carga inicial.','note'),PageBreak()]

story += heading(2,'Como o fluxo funciona hoje','O cliente é a primeira dependência do equipamento. Confirmação em uma etapa não conclui as demais.')
story += [Flow(),table(['ETAPA','COMPORTAMENTO ATUAL'],[
 ('01  Selecionar','Movimentos elegíveis M1/M3, com série e tipo de operação configurado. O cliente deriva do parceiro da nota.'),
 ('02  Consultar','Com ID local ou protocolo confirmado: consultar por ID e validar identidade. Sem ID: pesquisar código externo antes de considerar inclusão.'),
 ('03  Proteger','Ausência confirmada permite reserva durável e POST. Falha ou erro de consulta não significa ausência.'),
 ('04  Confirmar','Guardar o ID retornado e concluir o vínculo local. A preparação resolve no máximo uma dependência por rodada.'),
 ('05  Retomar','Envio incerto exige reconciliação antes de repetir inclusão. ID já confirmado permite recuperação por consulta.')],[105,402])]
story += [callout('IDENTIDADE PRESERVADA','Novos clientes usam SNK-CLI-<código do parceiro>; códigos legados conciliados são preservados. Conferir CPF/CNPJ e vínculo confirmado. Nome semelhante não autoriza reaproveitamento.')]
story += [p('O caminho atual de preparação de clientes não executa PUT. Migração de modelos e catálogo de estoques possuem processos próprios; não devem ser confundidos com a manutenção de clientes.','note'),PageBreak()]

story += heading(3,'Identidade e cadastro','Matriz Sankhya × API: origem dos dados, comportamento atual e requisito de evolução.')
story += [table(['CAMPO NO SANKHYA / API','O QUE EXISTE HOJE','REQUISITO / VALIDAÇÃO'],[
 ('Código do parceiro\ncodigoExterno','Gerado como SNK-CLI-<código>. Vínculo legado pode preservar outro código.','Manter chave estável e única. Não trocar código para contornar duplicidade.'),
 ('CPF/CNPJ\ncpf ou cnpj','Normaliza dígitos e exige documento. Até 11 dígitos segue como CPF; acima disso, CNPJ.','Validar 11/14 dígitos, tipo de pessoa e dígitos verificadores. Tratar exceções explicitamente.'),
 ('Nome do parceiro\nnomeFantasia','Mapeamento direto do nome do parceiro.','Confirmar obrigatoriedade e tamanho; definir tratamento de pessoa física.'),
 ('Razão social\nrazaoSocial','Mapeamento direto da razão social.','Definir ausência de razão social e regra de composição, sem inventar dados.'),
 ('Ativo\nstatus','Traduz situação para Ativo/Inativo. Situação desconhecida pode cair no padrão Ativo.','Confirmar domínio aceito; bloquear valor inválido e decidir propagação de inativação.')],[133,177,197])]
story += [callout('VALIDAR NÃO É APENAS NORMALIZAR','Retirar pontuação ou decidir o tipo pelo comprimento não comprova que o CPF/CNPJ é válido. Documento, código externo e ID precisam apontar para a mesma identidade.')]
story += [labeltext('Regra de preenchimento','Os campos acima existem no modelo utilizado pelo projeto. Isso não comprova que todos os parceiros estejam preenchidos corretamente. A obrigatoriedade final depende do contrato vigente da API.')]
story += [p('Pessoa física, documento inválido, nome ausente e código legado devem ter cenários próprios de homologação.','note'),PageBreak()]

story += heading(4,'Contato e endereço','Os atributos existem no payload, mas parte do carregamento precisa ser completada.')
story += [table(['ORIGEM / ATRIBUTO API','SITUAÇÃO ATUAL','AÇÃO NECESSÁRIA'],[
 ('Telefone / telefone','Carregado e normalizado para dígitos.','Acordar DDD, país, tamanho e telefone alternativo. Não criar números fictícios.'),
 ('CEP / cep','Carregado com normalização de dígitos.','Preservar zeros à esquerda e validar formato conforme país e contrato.'),
 ('Endereço / endereco','Descrição única reúne logradouro, número, complemento, bairro, cidade, UF e CEP.','Alinhar formato e evitar duplicação ao enviar campos separados.'),
 ('E-mail / email','Previsto na fábrica e no payload; não atribuído ao candidato no repositório revisado.','Selecionar origem correta em Parceiros, carregar explicitamente e validar formato.'),
 ('Bairro / enderecoBairro','Previsto no payload; não carregado separadamente no candidato revisado.','Mapear explicitamente a partir do endereço do parceiro.'),
 ('Cidade / enderecoCidade\nUF / enderecoUF','Fontes disponíveis; candidato revisado não recebe os atributos separados.','Completar a seleção e o mapeamento. Validar nome de cidade e UF.'),
 ('Número / enderecoNumero','Persistência reconhece o retorno; DTO atual de envio não possui esse atributo.','Confirmar suporte e necessidade na API antes de incluí-lo no envio.')],[125,182,200])]
story += [callout('LACUNA DE MAPEAMENTO','E-mail, bairro, cidade e UF precisam de atribuição explícita no novo fluxo. Não confundir presença no endereço consolidado com envio correto de cada atributo.')]
story += [p('Contato e endereço não foram classificados como obrigatórios sem o contrato vigente. Confirmar nulabilidade, tamanhos e política de atualização antes de implementar.','note'),PageBreak()]

story += heading(5,'Comunicação com a API','Cada operação exige identidade confirmada e tratamento próprio do resultado.')
story += [table(['OPERAÇÃO / RECURSO','USO E CONDIÇÃO'],[
 ('GET\n/clientes/?codigoExterno=…','Já utilizado para busca sem ID. Distinguir vazio, único, múltiplo e erro. Apenas ausência confirmada permite considerar inclusão.'),
 ('GET\n/clientes/{id}','Já utilizado com vínculo conhecido. Comparar ID e documento. Erro ou 404 não autoriza recriação automática.'),
 ('POST\n/clientes/','Já existente no fluxo dependente. Exige reserva durável, retorno com ID positivo e persistência do vínculo.'),
 ('PUT\n/clientes/{id}','Atualização incluída na evolução. Detalhar campos editáveis, omissão/null, concorrência e consulta posterior; confirmar contrato vigente.'),
 ('DELETE / exclusões','Tratamento incluído no escopo. Confirmar suporte da API, dependências, retenção e autorização por caso; não presumir exclusão automática de cadastros vinculados.')],[160,347])]
story += [p('Recursos relativos a /api/v1. Endereço e credencial não são expostos neste documento.','note')]
story += [labeltext('Conciliação antes de inclusão','Não presumir pesquisa por parâmetro cnpj: o diagnóstico histórico registrou rejeição dessa consulta. Confirmar alternativas atuais. Busca por nome não autoriza escolher o primeiro resultado sem documento correspondente.')]
story += [callout('CLIENTE NÃO CONFIRMA SETOR','O setor usa recurso separado: setores/ e setores/{id}. Reaproveitar apenas setor pertencente ao cliente correto e evitar duplicação de setor padrão.')]
story += [p('HTTP 2xx isolado não confirma toda a integração: validar conteúdo, identidade e gravação local.','note'),PageBreak()]

story += heading(6,'Requisitos da evolução','Proposta para implementar clientes sem perder as proteções do fluxo de equipamentos.')
story += [table(['ID / REQUISITO','DEFINIÇÃO E LIMITE'],[
 ('RF01  Seleção própria','Todos os parceiros da base instalada e os necessários aos itens-materiais sem cliente localizado. Resolver vínculo de origem; não criar cliente sem identidade comprovada.'),
 ('RF02  Cadastro completo','Consulta própria de Parceiros e endereço; carregar e-mail, bairro, cidade e UF. Não alterar a seleção de equipamentos para obter clientes.'),
 ('RF03  Saneamento','Incluído: identificar duplicidades, comparar dados e dependências, conciliar e registrar decisões. Não escolher ID arbitrariamente nem unir cadastros de pessoas distintas.'),
 ('RF04  Inclusão segura','Reaproveitar consulta prévia, reserva e confirmação duráveis. Compartilhar controle de identidade com equipamentos para impedir POST simultâneo.'),
 ('RF05  Bidirecional','Incluída troca de dados nos dois sentidos. Definir origem mestre por campo, conflitos e prevenção de ciclos; atualizar pelo mesmo ID e confirmar resultado.'),
 ('RF06  Agendamento','Definir frequência, lote, ordem, retomada, concorrência e limites de chamadas. Não presumir horários sem parametrização aprovada.'),
 ('RF07  Novas telas','Incluídas telas para acompanhar, conciliar e tratar pendências, com autorização e auditoria. Definir ações disponíveis; não contabilizar clientes como equipamentos.'),
 ('RF08  Ciclo de vida','Incluídos setor padrão, inativação e tratamento de exclusões. Validar dependências, permissões e impacto; não executar exclusão remota apenas por ausência na seleção.')],[132,375])]
story += [callout('MESMO CONTROLE, RESPONSABILIDADES DISTINTAS','Cliente e equipamento podem ter seleções próprias, mas devem reconhecer o mesmo cliente já confirmado. A nova rotina não pode duplicar o cadastro utilizado pelos equipamentos.')]
story += [PageBreak()]

story += heading(7,'Como validar a entrega','Critérios objetivos para homologação e aceite, antes de autorizar produção.')
story += [table(['CENÁRIO','RESULTADO ESPERADO'],[
 ('Novo cliente válido','Filtro aprovado, consulta sem correspondência, uma inclusão e vínculo persistido. Nova execução não duplica.'),
 ('Existente / legado','ID e documento conferem. Preservar vínculo legítimo. Código legado divergente segue para conciliação, sem POST cego.'),
 ('Cadastro incompleto','Documento ausente, inválido ou duplicado gera bloqueio compreensível. Contato/endereço corretos refletem a origem do Sankhya.'),
 ('Envio incerto','Timeout ou resposta sem ID preserva reserva. Reconciliar sem repetir inclusão. Falha local após sucesso remoto deve recuperar ID confirmado.'),
 ('Falha de API','429 respeita espera compartilhada; 401/403 exige análise de acesso; 5xx não significa cadastro ausente.'),
 ('Concorrência','Rotina de cliente e equipamento não criam duplicata. Equipamento espera cliente/setor confirmados e preserva as regras M1/M3.'),
 ('Bidirecional / telas','Testar atualização nos dois sentidos, resolução de conflito, prevenção de ciclos, permissões de tela e trilha de auditoria.'),
 ('Saneamento / ciclo de vida','Validar duplicidades sem perda de vínculos; setor correto; inativação e exclusão apenas com critérios, dependências e autorização verificados.')],[137,370])]
story += [callout('EVIDÊNCIA MÍNIMA DE ACEITE','Identificação do caso, retorno sanitizado, consulta posterior e vínculo local. Luiz ou responsável designado registra resultado, data e canal de aprovação, sem expor credenciais ou documento integral.')]
story += [p('Validação em andamento não equivale a aprovação. Os cenários acima são critérios propostos; não foram executados nesta revisão documental.','note'),PageBreak()]

story += heading(8,'Planejamento e decisões','Esforço preliminar, condições de entrega e pontos que precisam de aprovação.')
story += [table(['FRENTE INCLUÍDA','ESFORÇO'],[
 ('Seleção, mapeamento e manutenção de clientes','A reestimar'),('Conciliação e saneamento de duplicidades','A reestimar'),('Novas telas, permissões e auditoria','A reestimar'),('Sincronização bidirecional e conflitos','A reestimar'),('Setores, inativação, exclusões e validação','A reestimar')],[417,90])]
story += [callout('ESTIMATIVA ANTERIOR SUPERADA','As 12-16h referiam-se à evolução restrita. Não cobrem o escopo ampliado solicitado. Recalcular após dimensionar telas, duplicidades, integração bidirecional e regras de ciclo de vida; nenhuma nova hora foi presumida.')]
story += [p('Condições e decisões','h2'),table(['PONTO','ALINHAMENTO NECESSÁRIO'],[
 ('Premissas','API compatível, campos e permissões disponíveis, massa controlada e reaproveitamento dos controles. Reestimar se houver nova estrutura.'),
 ('Fora do escopo','Somente carga inicial em massa. Os demais itens antes excluídos passam a fazer parte da evolução.'),
 ('Seleção confirmada','Parceiros da base instalada e parceiros necessários aos itens-materiais sem cliente localizado. Ausência de vínculo exige tratamento, não associação presumida.'),
 ('Detalhar execução','Mestre por campo e conflitos; frequência/lote; telas e ações; regra de setor; impacto de exclusões/inativos; responsáveis por saneamento e aceite.'),
 ('Próximo passo','Validar contrato e dimensionar escopo ampliado. Aprovar esforço → homologar cenários → registrar aceite → autorizar implantação.')],[110,397])]
story += [Spacer(1,12),p('A estimativa não é contratação aprovada. O desenvolvimento de clientes não foi iniciado neste levantamento.','note')]

pdf=OUT/'MA-Hospitalar-Genesis-Requisitos-Clientes.pdf'
SimpleDocTemplate(str(pdf),pagesize=(595.28,841.89),leftMargin=44,rightMargin=44,topMargin=87,bottomMargin=64,title='MA Hospitalar | Clientes Sankhya x Genesis | Requisitos',author='Zaya.IT').build(story,onFirstPage=frame,onLaterPages=frame)

# Preserve the approved scope text as a consultable source; sections removed by the client stay excluded.
tree=ast.parse((ROOT/'tools/BuildGenesisClientesRequisitos.py').read_text(encoding='utf-8'))
source=next(ast.literal_eval(n.value) for n in tree.body if isinstance(n,ast.Assign) and any(isinstance(t,ast.Name) and t.id=='pages' for t in n.targets))[:8]
md=['# Clientes no Sankhya × Genesis - Levantamento de requisitos','\nVersão 1.3 - 23/10/2026. Escopo ampliado pelo solicitante. Proposta, não implementação.\n']
from pypdf import PdfReader
for n,page in enumerate(PdfReader(pdf).pages,1):
 md.extend([f'## Página {n}',page.extract_text()])
md+=['\n## Apresentação editorial\n','PDF reorganizado em capa executiva, comparação atual/proposto, fluxo, matrizes de campos, operações da API, requisitos, critérios de aceite e planejamento em tabelas. Sem as seções de horas e fontes retiradas por solicitação.']
(OUT/'Requisitos-Clientes.md').write_text('\n\n'.join(md),encoding='utf-8')
print(pdf)
