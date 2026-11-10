from pathlib import Path
from xml.sax.saxutils import escape
import json
from PIL import Image as PILImage, ImageDraw
from reportlab.pdfgen import canvas
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Image, PageBreak, Table, TableStyle
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib import colors
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / 'output/manual-genesis'
OUT.mkdir(parents=True, exist_ok=True)
pdfmetrics.registerFont(TTFont('Arial', 'C:/Windows/Fonts/arial.ttf'))
pdfmetrics.registerFont(TTFont('ArialB', 'C:/Windows/Fonts/arialbd.ttf'))
GREEN = colors.HexColor('#00AA83')
INK = colors.HexColor('#20252A')
styles = getSampleStyleSheet()
styles.add(ParagraphStyle(name='BodyGuide', fontName='Arial', fontSize=10.2, leading=15, textColor=INK, spaceAfter=9))
styles.add(ParagraphStyle(name='TitleGuide', fontName='ArialB', fontSize=24, leading=29, textColor=INK, spaceAfter=17))
styles.add(ParagraphStyle(name='SmallGuide', fontName='Arial', fontSize=8, leading=11, textColor=colors.HexColor('#657180'), spaceAfter=9))
styles.add(ParagraphStyle(name='SubGuide', fontName='ArialB', fontSize=12, leading=17, textColor=GREEN, spaceAfter=9))
styles.add(ParagraphStyle(name='BandGuide', fontName='ArialB', fontSize=12, leading=15, textColor=colors.white))
styles.add(ParagraphStyle(name='CenterGuide', fontName='ArialB', fontSize=24, leading=30, alignment=1, textColor=INK, spaceAfter=18))
styles.add(ParagraphStyle(name='CenterBody', fontName='Arial', fontSize=11, leading=16, alignment=1, textColor=INK, spaceAfter=8))

sections = [
('Como usar este guia', None, [
'Este guia orienta a consulta da Integração Genesis no Sankhya. As telas foram capturadas diretamente no sistema em 23/10/2026. Os números apresentados são um retrato da consulta, não metas nem quantidades fixas.',
'A interface é somente leitura: permite pesquisar, filtrar e abrir detalhes. Não envia equipamentos, não altera cadastros e não executa reprocessamento manual. Os processos de integração são executados no servidor.',
'A versão M1/M3 e a correção da atualização de modelos foram informadas como publicadas em produção pelo responsável do projeto. Luiz está validando o processo; este guia não representa aceite final nem auditoria do binário instalado.',
'Quando aparecer uma mensagem de consulta indisponível, não interprete como ausência de registros. Solicite verificação de estrutura e acesso. Durante a captura, esse aviso estava presente em Configurações.',
'Os nomes operacionais foram priorizados. Instruções do documento de exemplo não foram incorporadas: apenas sua identidade visual serviu como referência.' ]),
('Acesso e navegação', '01-visao-geral.png', [
'1. No menu do Sankhya, localize [ZAYA.IT] - Integração Genesis. O acesso depende das permissões do usuário.',
'2. Escolha a área no menu lateral: Visão geral, Envios e pendências, Base instalada, Conciliação e diagnóstico, Estoques e preços, Cancelamentos, Requisições ou Configurações.',
'3. Use Atualizar consultas para reler os dados do Sankhya. Esse botão não dispara chamadas à Genesis. O ícone de sol/lua alterna a apresentação; não altera os dados.',
'As consultas de listas trabalham com recorte de até 100 registros por área. A paginação e os filtros operam nesse recorte. Um registro não encontrado aqui pode existir fora dele.' ]),
('Visão geral e indicadores', '01-visao-geral.png', [
'Use esta área para perceber a situação geral antes de investigar um equipamento. Os indicadores de movimentos representam o último estado registrado; o gráfico mostra respostas HTTP por período.',
'Envios confirmados, erros/revisão e aguardando/reprocessar são situações diferentes. Não some indicadores sem verificar o universo e a descrição de cada um.',
'No gráfico, selecione 7, 30 ou 90 dias, ou um intervalo personalizado dentro do período disponível. Informe início e fim em ordem. A alteração afeta somente o gráfico, não os indicadores de movimentos.',
'Uma resposta HTTP de sucesso não confirma, sozinha, o equipamento: também é necessário identificar corretamente o cadastro e concluir a gravação local. Últimos equipamentos mostra a base instalada, não uma lista exclusiva de novos POSTs.' ]),
('Envios e pendências', '02-envios.png', [
'1. Pesquise a nota, a série ou o identificador do movimento no campo de busca. 2. Restrinja pelo estado. 3. Abra o ícone de detalhes da linha desejada.',
'Confira nota, produto, série, etapa bloqueada e data da atualização. Uma nota pode conter várias séries; cada equipamento precisa ser acompanhado individualmente.',
'PENDENTE indica processamento ainda não concluído. REPROCESSAR indica nova avaliação prevista. ENVIADO indica conclusão registrada para aquele movimento, podendo decorrer de reutilização de cadastro já existente.',
'REVISAO e ENVIO_INCERTO exigem atenção à causa. Não apague controles nem repita inclusões para tentar limpar o indicador. A orientação específica está na seção de tratamento de erros.' ]),
('Base instalada: visão consolidada', '03-base.png', [
'A Base instalada reúne Clientes, Domínios, Empresas, Itens instalados, Materiais, Modelos e Setores. A aba Geral da tela cadastral não foi reproduzida.',
'Estado Genesis informa o estado remoto registrado. Conciliação informa a situação do vínculo local. Um cadastro Ativo não significa, por si só, envio confirmado de uma nota.',
'Use a aba correspondente à informação desejada; depois pesquise pelos campos disponíveis e abra os detalhes. As quantidades nas abas são do recorte carregado, não totais globais.',
'Não altere identificadores para eliminar divergências. A identificação incorreta de um cadastro pode vincular equipamento ao cliente, setor ou modelo errado.' ]),
('Base instalada: Clientes', 'base-clientes.png', [
'Consulte a relação entre o cliente no Sankhya e seu cadastro na Genesis. Os filtros disponíveis incluem código Sankhya, nome/descrição e código externo.',
'Abra os detalhes para conferir os identificadores e a situação de conciliação. Ao investigar uma série, confirme que o cliente da nota corresponde ao cliente vinculado ao equipamento.',
'O fluxo atual resolve o cliente como dependência de um movimento elegível. Não é uma sincronização independente de todos os clientes ativos do Sankhya.',
'Cliente existente não significa equipamento enviado. Havendo divergência de documento ou mais de uma correspondência, encaminhe para conciliação; não crie outro cadastro apenas porque uma busca não o exibiu.' ]),
('Base instalada: Domínios', 'base-dominios.png', [
'Domínios reúne valores de referência da Genesis, como situações e propriedades. Filtre pelo tipo de domínio e pelo nome.',
'Esses registros ajudam a interpretar classificações usadas pelos equipamentos. Não representam produtos nem clientes.',
'Antes de solicitar alteração de um valor de referência, identifique seu tipo e significado. A tela apenas consulta; uma necessidade de manutenção deve ser avaliada pelo responsável técnico.' ]),
('Base instalada: Empresas', 'base-empresas.png', [
'Esta aba reúne cadastros de empresas da Genesis e seus papéis de fabricante e fornecedor. Não confunda com a seleção de empresas do Sankhya usada no escopo de estoque.',
'Pesquise por código Sankhya, nome/descrição ou código externo. Confira os indicadores de fabricante e fornecedor e a conciliação.',
'Um modelo depende de fabricante corretamente identificado. Uma marca sem correspondência única pode bloquear o processo mesmo quando cliente e série estão preenchidos.' ]),
('Base instalada: Itens instalados', '03-base.png', [
'Cada item instalado representa um equipamento individual, relacionado ao modelo, cliente, setor e número de série. Confira produto, descrição, série, ID Genesis e estado.',
'Para validar uma nota, compare a série exata e o cliente. O nome do produto sozinho não é identificação suficiente.',
'A mesma nota pode conter várias séries. Já a combinação produto/série com vários cadastros possíveis deve ser revisada, especialmente quando eles pertencem a clientes diferentes.',
'A ausência no recorte não comprova ausência na Genesis. Solicite consulta direcionada quando o registro esperado não estiver visível.' ]),
('Base instalada: Materiais', 'base-materiais.png', [
'Materiais são referências cadastrais usadas pelos modelos; não são a mesma coisa que a lista de equipamentos por série nem o catálogo de estoques e preços.',
'Pesquise o código ou a descrição, confira o ID Genesis e a conciliação. Quando um modelo já possui material, o vínculo é preservado; o grupo do produto atual não é imposto automaticamente.',
'Se houver mais de um registro local possível, o sistema não escolhe arbitrariamente. É necessário comparar o cadastro remoto e os modelos dependentes antes de consolidar vínculos.' ]),
('Base instalada: Modelos', 'base-modelos.png', [
'Modelos representam o tipo/modelo do equipamento. O código externo esperado vem de Código Produto Protheus, na tela Produtos.',
'Códigos antigos gerados pela integração, como os iniciados por SNK-MOD, exigem reconciliação do cadastro existente. A rotina dedicada consulta e atualiza pelo ID, sem criar outro modelo para contornar a divergência.',
'A validação deve comparar código esperado, ID do modelo, retorno da atualização e consulta posterior na Genesis. A alteração do modelo não confirma automaticamente o envio de todas as séries relacionadas.' ]),
('Base instalada: Setores', 'base-setores.png', [
'Setores identificam a associação operacional do equipamento ao cliente na Genesis. Consulte o nome, código externo e dados de vínculo disponíveis nos detalhes.',
'O setor depende de cliente identificado. Não reutilize um setor pertencente a outro cliente.',
'Quando não há setorização detalhada, o fluxo pode trabalhar com setor padrão conforme a regra da integração. Ambiguidade de setor precisa ser conciliada antes de continuar o equipamento.' ]),
('Conciliação e diagnóstico', '04-conciliacao.png', [
'Esta área organiza casos que exigem análise. Localize a série ou o movimento e use Analisar caso para abrir a explicação disponível.',
'Conciliação é reconhecer qual cadastro da Genesis corresponde ao registro do Sankhya. Não é uma nova inclusão nem uma autorização para sobrescrever dados.',
'Confira a etapa, a causa, os candidatos e a próxima ação. Se a identificação depender de informação comercial ou operacional, peça confirmação ao responsável pela validação.',
'Duas linhas com o mesmo ID remoto podem ter códigos e dependências diferentes. Não consolide apenas pelo ID; compare dados e vínculos primeiro.' ]),
('Estoques e preços', '05-estoques.png', [
'Esta área acompanha um fluxo separado do envio de equipamentos por série. No código atual, produtos elegíveis M2, M3 e SV integram o catálogo; produtos já vinculados também podem exigir acompanhamento após mudança de situação.',
'O catálogo usa preço de venda da tabela 101 e IPI, conforme a regra documentada. O custo não é enviado nessa regra. Fabricante e identidade do cadastro precisam estar conciliados.',
'Catálogo e saldo físico são conceitos distintos: o catálogo possui referência com quantidade 1 e sem controle de estoque; o fluxo de saldo depende das empresas e locais configurados e considera disponível como estoque menos reserva.',
'Um produto M3 pode participar do fluxo de equipamentos com série e do fluxo de estoque/catálogo, desde que cumpra as condições de cada um. Um resultado não comprova o outro.' ]),
('Cancelamentos', '06-cancelamentos.png', [
'Acompanhe nesta área o tratamento dos vínculos de integração após cancelamento da nota. O cancelamento no Sankhya e sua conclusão na Genesis não são uma única etapa instantânea.',
'O evento local prepara o controle de cancelamento; a rotina posterior avalia origem do item e identidade remota antes de qualquer exclusão permitida.',
'Itens criados pela integração possuem tratamento diferente dos reutilizados. Cadastro preexistente, origem desconhecida ou identificação divergente não deve ser excluído automaticamente.',
'Não execute um cancelamento para testar este guia. Se permanecer pendente, envie ao suporte a nota, a série, o estado e a mensagem registrada.' ]),
('Requisições e evidências', '07-requisicoes.png', [
'Use Requisições para consultar o histórico de tentativas. Abra os detalhes para verificar operação, entidade, resultado HTTP e informações de confirmação disponíveis.',
'GET é consulta; POST é inclusão; PUT é atualização; DELETE é exclusão/inativação conforme o recurso. A presença de um GET de cliente não significa que houve POST do equipamento.',
'HTTP 2xx indica resposta de sucesso da operação, mas a confirmação exige conteúdo válido, identidade correta e persistência local concluída. Em falha de gravação local após sucesso remoto, reconciliar antes de tentar nova inclusão.',
'Ao compartilhar evidências, remova credenciais, documentos e contatos pessoais. Não encaminhe o conteúdo integral de requisições sem revisar os dados.' ]),
('Configurações e escopo', '08-configuracoes.png', [
'Configurações organiza Parâmetros, Empresas e locais e Regras de processamento. A área não oferece edição e não contém uma aba de Execuções.',
'Parâmetros informa a situação da Central de Parâmetros e se endereço e credencial estão preenchidos. Os valores de acesso permanecem protegidos. Preenchido não significa credencial válida.',
'Referência da versão do projeto apresenta valores da interface/pacote. Não comprova o ambiente de destino, o JAR instalado nem a frequência real das ações agendadas.',
'Na captura, Empresas e locais apresentou consulta indisponível. Portanto, não foi possível confirmar o escopo real por essa aba. O aviso deve ser verificado pelo suporte, sem concluir que a configuração está vazia.' ]),
('Regras de processamento', '10-regras.png', [
'Equipamentos: seleção M1/M3, série preenchida, operação parametrizada e movimento elegível. Notas canceladas e itens em inativação não seguem para nova inclusão.',
'Dependências: cliente, setor, material, fabricante, modelo e item instalado. A falta ou divergência em uma etapa impede a conclusão das seguintes. Etapas podem avançar em execuções sucessivas.',
'Modelo: Código Produto Protheus deve corresponder ao código do modelo na Genesis. A correção do legado usa o mesmo cadastro remoto, com confirmação posterior.',
'Retroativos: podem ser processados quando continuam elegíveis na consulta de seleção. Instalar uma versão não inclui automaticamente todo o histórico nem libera casos ambíguos.',
'A tela capturada ainda descreve genericamente produtos do tipo Equipamentos. A regra M1/M3 foi confirmada na versão local entregue e informada como instalada; Luiz permanece validando seu resultado em produção.' ]),
('Estados: o que fazer em cada situação', None, [
'PENDENTE: aguarde o processamento agendado e confira a evolução da etapa. Se não avançar, registre desde quando está nessa situação.',
'REPROCESSAR: o registro será reavaliado conforme regras de espera e elegibilidade. Corrija a causa, quando indicada; não apague seu histórico.',
'ENVIADO: conclusão registrada no movimento. Pode ter sido criado um cadastro ou reutilizado um existente. Confira ID e série para comprovar o equipamento específico.',
'REVISAO ou ERRO: leia a mensagem. Cadastro ambíguo exige identificação; parâmetro ausente exige configuração pelo responsável; falha remota exige análise do retorno.',
'ENVIO_INCERTO: o sistema não tem confirmação suficiente para repetir a inclusão com segurança. Consulte a Genesis e reconcilie o controle. Não repetir POST nem limpar o bloqueio sem evidência.',
'CANCELADO: movimento não deve retornar ao fluxo normal. Acompanhe separadamente o resultado da fila de cancelamento quando houver ação remota aplicável.' ]),
('Erros frequentes e encaminhamento', None, [
'Código do modelo diferente: cadastro legado mantém código divergente do produto. Ação: consultar o modelo por ID, validar o destino, atualizar o mesmo cadastro e confirmar por nova consulta.',
'Mais de uma correspondência de material: existem candidatos locais concorrentes. Ação: comparar o material remoto, os códigos e os modelos dependentes; consolidar somente com plano aprovado.',
'Mesma série em vários equipamentos/clientes: não há identidade única suficiente. Ação: confirmar com o negócio qual é o equipamento e se existe transferência, duplicidade ou reutilização de série.',
'Vínculo pertence a outro cadastro: uma referência não corresponde ao cadastro atual. Ação: comparar histórico da nota e vínculos; não sobrescrever o cliente ou material para forçar continuidade.',
'HTTP 429: limite de chamadas. Respeitar a espera compartilhada; não disparar tentativas sucessivas. HTTP 401/403: suporte deve verificar autenticação/permissão, sem divulgar credenciais.',
'Consulta indisponível no painel: verificar acesso e estrutura do componente. Não equivale a erro do envio e não demonstra que os dados deixaram de existir.' ]),
('Roteiro de validação com Luiz', None, [
'1. Escolha uma nota e anote produto, cliente e todas as séries esperadas. Para o caso M3 solicitado, acompanhe as notas 120671 e 120872, produto 11919, verificando cada uma das cinco séries.',
'2. Em Envios e pendências, consulte cada movimento e sua etapa. 3. Em Requisições, confirme qual operação ocorreu; não presuma que todo envio gera uma inclusão.',
'4. Em Base instalada, compare série, modelo, cliente, setor e ID Genesis. 5. Na Genesis, confirme o cadastro correspondente e seus relacionamentos.',
'6. Registre o resultado por série, a data, o responsável e o canal do retorno. Um aceite deve ser explícito; estar em validação não significa aprovado.',
'O suporte deve receber: nota, produto, série, estado, mensagem completa revisada, horário observado e print do detalhe. Não inclua senha, token ou dados pessoais sem necessidade.' ]),
('Funcionamento atual e limites da revisão', None, [
'O navegador consulta dados do Sankhya. O React organiza navegação, filtros, detalhes e apresentação; não realiza chamadas diretas à Genesis nem guarda sua credencial.',
'As rotinas Java no servidor selecionam movimentos e resolvem cadastros, enviam equipamentos e acompanham controles. Há responsabilidades distintas para cancelamentos, estoques/preços e migração dos códigos de modelos.',
'O fluxo de cadastros/equipamentos atual não deve ser descrito como PUT genérico de todos os mestres: ele consulta/reutiliza ou inclui com proteção. A atualização de código de modelo pertence à rotina dedicada de migração; o catálogo possui ciclo próprio de atualização.',
'Foi revisado o código local dos serviços de preparação, validação e cancelamento e a documentação vigente de base única, estoques, M1/M3 e migração de modelos. Textos históricos da arquitetura não foram tratados como regra atual.',
'Esta revisão não executou POST, PUT, DELETE, cancelamentos nem mudança de parâmetros. Não verificou todas as permissões de usuários, o binário instalado, todos os endpoints em execução ou o aceite final do cliente. O guia cobre as áreas visíveis e as regras documentadas, não certifica segurança integral nem homologação.' ]),
]

sections.insert(4, ('Detalhes de um movimento', '11-detalhe.png', [
'Abra o ícone de detalhes da linha. A janela oferece Resumo, Vínculos, Histórico e JSON. Essas abas consultam informações; não autorizam edição nem reenvio.',
'Resumo apresenta nota de origem, produto, série, estado, ID Genesis, etapa, entidade bloqueadora, método, tentativas e datas. Não informado significa que aquele dado não está disponível no registro consultado.',
'No exemplo capturado, o movimento está PENDENTE, em AGUARDANDO_PROXIMA_ETAPA e sem ID Genesis informado. Um GET registrado não comprova envio final do equipamento.',
'Use Vínculos para conferir relacionamentos; Histórico para acompanhar os eventos disponíveis no recorte; JSON para inspecionar a evidência disponível. Não compartilhe seu conteúdo sem revisar dados pessoais e informações sensíveis.',
'Feche a janela pelo botão Fechar detalhes para retornar à lista. Conciliação e edição permanecem dependentes de serviços com autorização e auditoria.' ]))

def paragraph(text, style='BodyGuide'):
    return Paragraph(escape(text).replace('&lt;br/&gt;', '<br/>').replace('\n','<br/>'), styles[style])

def page_frame(c, doc):
    w,h=doc.pagesize
    c.drawImage(str(OUT/'marca-zaya.png'),w/2-66,h-59,width=132,height=29,mask='auto')
    c.saveState()
    p=c.beginPath();p.rect(0,0,w,24);c.clipPath(p,stroke=0)
    c.linearGradient(0,0,w,0,[colors.HexColor('#11990F'),colors.HexColor('#5BE000')])
    c.restoreState()
    c.setFillColor(colors.white);c.setFont('ArialB',8)
    c.drawString(42,9,f'{doc.page:02d} | Zaya.IT - MA Hospitalar')
    c.setFillColor(colors.HexColor('#657180'));c.setFont('Arial',7)
    c.drawRightString(w-42,36,'GUIA DO USUÁRIO | 1.0 | 23/10/2026')

def band(text):
    t=Table([[paragraph(text,'BandGuide')]],colWidths=[511])
    t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,-1),GREEN),('LEFTPADDING',(0,0),(-1,-1),10),('TOPPADDING',(0,0),(-1,-1),7),('BOTTOMPADDING',(0,0),(-1,-1),7)]))
    return t

def attention(text):
    t=Table([[Paragraph('<b>Atenção</b><br/>'+escape(text),styles['BodyGuide'])]],colWidths=[511])
    t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,-1),colors.HexColor('#D9EBD2')),('LINEBEFORE',(0,0),(0,-1),2,colors.HexColor('#70A956')),('LEFTPADDING',(0,0),(-1,-1),10),('TOPPADDING',(0,0),(-1,-1),9),('BOTTOMPADDING',(0,0),(-1,-1),3)]))
    return t

story=[]
reference=ROOT.parent/'output/referencia-manual.png'
with PILImage.open(reference) as im:
    im.crop((175,220,670,330)).save(OUT/'marca-zaya.png')
story += [band('GUIA DO USUÁRIO'),Spacer(1,75),paragraph('Integração Genesis no Sankhya<br/>Como consultar e acompanhar','CenterGuide'),paragraph('MA Hospitalar','CenterBody'),paragraph('Manual de apoio à operação e validação','CenterBody'),Spacer(1,20),attention('Siga as telas deste guia para consultar movimentos, cadastros e retornos. O painel é somente leitura; não realiza envios nem alterações.'),Spacer(1,13),paragraph('O que consultar','SubGuide')]
for text in ['1. Visão geral: indicadores e movimentação da integração.','2. Envios: estado de cada nota, produto e série.','3. Base instalada: clientes, setores, modelos e equipamentos.','4. Diagnóstico: causas dos bloqueios e orientação de tratamento.']:
    story.append(paragraph(text))
story += [attention('Não copie valores dos exemplos. Não repita inclusões incertas nem altere vínculos para eliminar mensagens de erro.'),Spacer(1,12),paragraph('Versão 1.0 - 23/10/2026\nResponsabilidade técnica: Zaya.IT. Validação por Luiz em andamento. Nomes pessoais foram ocultados na aba Clientes.','SmallGuide'),PageBreak()]
story += [band('Conteúdo'),Spacer(1,16)]
for i,(title,_,_) in enumerate(sections,3):
    story.append(paragraph(f'{i:02d}  {title}','BodyGuide'))
story.append(PageBreak())
md=['# Integração Genesis - Manual do usuário','\nVersão 1.0 - 23/10/2026. Validação por Luiz em andamento.\n']
for idx,(title,filename,paragraphs) in enumerate(sections,1):
    story.append(band(f'{idx}. {title}'))
    story.append(Spacer(1,14))
    story.append(paragraph(('Onde acessar: [ZAYA.IT] - Integração Genesis → '+title.split(':')[0]) if filename and filename!='11-detalhe.png' else 'Referência: [ZAYA.IT] - Integração Genesis', 'SmallGuide'))
    md += ['\n## '+title+'\n']
    if filename:
        path=OUT/'prints'/filename
        with PILImage.open(path) as im:
            if filename == 'base-clientes.png':
                im=im.copy()
                draw=ImageDraw.Draw(im)
                draw.rectangle((415,730,950,im.height-36),fill='#202b31')
                draw.text((430,750),'Nomes pessoais omitidos neste guia',fill='white')
            # Preserve original screenshot; publish a readable operational crop without browser chrome.
            crop=im.crop((20,65,im.width-20,im.height-35))
            safe=OUT/'prints'/('guia-'+filename)
            crop.save(safe)
            width,height=crop.size
        story.append(Image(str(safe),width=510,height=510*height/width))
        story.append(paragraph('Captura real do Sankhya em 23/10/2026. Valores e situações podem mudar. Na aba Clientes, nomes pessoais foram ocultados.','SmallGuide'))
        md.append(f'![Tela real](prints/guia-{filename})\n')
    story.append(paragraph('Como consultar' if filename else 'Orientações','SubGuide'))
    for j,t in enumerate(paragraphs):
        story.append(attention(t) if j==len(paragraphs)-1 else paragraph(t if t[:1].isdigit() else f'{j+1}. {t}'))
        md.append(t+'\n')
    story.append(PageBreak())
doc=SimpleDocTemplate(str(OUT/'MA-Hospitalar-Genesis-Manual-do-Usuario.pdf'),pagesize=(595.28,841.89),rightMargin=42,leftMargin=42,topMargin=83,bottomMargin=54,title='MA Hospitalar - Integração Genesis - Manual do usuário',author='Zaya.IT')
doc.build(story,onFirstPage=page_frame,onLaterPages=page_frame)
(OUT/'Manual-do-Usuario.md').write_text('\n'.join(md),encoding='utf-8')
(OUT/'Fontes-e-validacao.md').write_text('''# Fontes e validação do guia

Data da captura: 23/10/2026. Navegação direta no Sankhya via computer-use/cua_repl.
O PDF de exemplo foi usado apenas para identidade visual e recorte da marca Zaya.IT.

## Fontes canônicas consultadas
- docs/genesis/central-base-configuracoes.md
- docs/genesis/base-unica-post-get.md
- docs/genesis/catalogo-estoques.md (incluindo regra posterior de valores)
- docs/genesis/modelo-hsp-migracao.md
- docs/genesis/melhoria-selecao-m1-m3.md
- docs/genesis/arquitetura.md (separando seções históricas)
- src/br/com/zaya/mahospitalar/genesis/service/CadastroGenesisPreparationService.java
- src/br/com/zaya/mahospitalar/genesis/service/GenesisSyncService.java
- src/br/com/zaya/mahospitalar/genesis/event/CancelamentoNotaGenesisEvent.java

## Cobertura
Oito áreas de navegação; sete abas da base instalada; três abas de configurações;
janela de resumo de movimento. Vínculos, Histórico e JSON foram identificados como
controles disponíveis, mas não auditados integralmente por caso.
Prints capturados no sistema, não reconstruídos. Originais preservados na pasta prints;
versões guia-* recortam apenas moldura e ocultam nomes pessoais na aba Clientes.

## Limites
Sem POST/PUT/DELETE, DML, alteração de parâmetros, instalação, commit ou push.
Consulta de Empresas e locais indisponível na captura: causa não diagnosticada aqui.
Não foi inspecionado o JAR instalado. Produção informada pelo usuário; aceite pendente.
Não foram auditadas todas as permissões, eventos, filas e respostas remotas.
Estimativa de sincronização independente de clientes preservada: 12-16h, reserva 16h;
sem início autorizado, carga massiva, saneamento e novas telas fora dessa estimativa.
''',encoding='utf-8')
print('PDF e fonte Markdown gerados:',OUT)
