from reportlab.lib import colors
from reportlab.lib.enums import TA_LEFT, TA_CENTER
from reportlab.lib.pagesizes import letter
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import inch
from reportlab.platypus import (
    SimpleDocTemplate,
    Paragraph,
    Spacer,
    Table,
    TableStyle,
    PageBreak,
)


OUT = r"C:\Users\annayran.ferreira\Documents\Codex\2026-06-05\files-mentioned-by-the-user-intpdd\outputs\ZY-SKALA-2026-PART-INTPDD-001_Escopo_vs_Nao_Escopado.pdf"

GREEN = colors.HexColor("#063F2D")
BRAND = colors.HexColor("#00A859")
MINT = colors.HexColor("#E7F7ED")
YELLOW = colors.HexColor("#FFD84D")
LIGHT = colors.HexColor("#F5FAF6")
RED = colors.HexColor("#FCE8E6")
LINE = colors.HexColor("#D6E4DB")
INK = colors.HexColor("#173329")
MUTED = colors.HexColor("#66756D")


styles = getSampleStyleSheet()
styles.add(ParagraphStyle("SkTitle", parent=styles["Title"], fontName="Helvetica-Bold", fontSize=22, leading=26, textColor=GREEN, alignment=TA_LEFT, spaceAfter=12))
styles.add(ParagraphStyle("SkH1", parent=styles["Heading1"], fontName="Helvetica-Bold", fontSize=15, leading=18, textColor=GREEN, spaceBefore=12, spaceAfter=7))
styles.add(ParagraphStyle("SkH2", parent=styles["Heading2"], fontName="Helvetica-Bold", fontSize=12, leading=15, textColor=GREEN, spaceBefore=10, spaceAfter=5))
styles.add(ParagraphStyle("SkBody", parent=styles["BodyText"], fontName="Helvetica", fontSize=9.5, leading=12.5, textColor=INK, spaceAfter=6))
styles.add(ParagraphStyle("SkSmall", parent=styles["BodyText"], fontName="Helvetica", fontSize=8, leading=10.2, textColor=INK))
styles.add(ParagraphStyle("SkCell", parent=styles["BodyText"], fontName="Helvetica", fontSize=7.2, leading=9, textColor=INK))
styles.add(ParagraphStyle("SkHeadCell", parent=styles["BodyText"], fontName="Helvetica-Bold", fontSize=7.4, leading=9, textColor=colors.white, alignment=TA_CENTER))
styles.add(ParagraphStyle("SkMeta", parent=styles["BodyText"], fontName="Helvetica", fontSize=8.5, leading=11, textColor=INK))


def p(text, style="SkBody"):
    return Paragraph(text, styles[style])


def footer(canvas, doc):
    canvas.saveState()
    canvas.setFont("Helvetica", 7.5)
    canvas.setFillColor(MUTED)
    canvas.drawString(0.65 * inch, 0.45 * inch, "Zaya IT - Gestao de Negocios | ZY-SKALA-2026-PART-INTPDD-001 | Confidencial")
    canvas.drawRightString(letter[0] - 0.65 * inch, 0.45 * inch, "Pagina %s" % doc.page)
    canvas.restoreState()


def callout(title, text, fill=MINT):
    table = Table([[p("<b>%s</b><br/>%s" % (title, text), "SkBody")]], colWidths=[7.15 * inch])
    table.setStyle(TableStyle([
        ("BACKGROUND", (0, 0), (-1, -1), fill),
        ("BOX", (0, 0), (-1, -1), 0.6, LINE),
        ("LEFTPADDING", (0, 0), (-1, -1), 8),
        ("RIGHTPADDING", (0, 0), (-1, -1), 8),
        ("TOPPADDING", (0, 0), (-1, -1), 7),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 7),
    ]))
    return table


def matrix(title, rows, header_color=GREEN):
    story = [p(title, "SkH2")]
    data = [[p("Item", "SkHeadCell"), p("Entendimento", "SkHeadCell"), p("Evidencia / origem", "SkHeadCell"), p("Status", "SkHeadCell")]]
    for row in rows:
        data.append([p(row[0], "SkCell"), p(row[1], "SkCell"), p(row[2], "SkCell"), p(row[3], "SkCell")])
    table = Table(data, colWidths=[1.25 * inch, 2.65 * inch, 2.25 * inch, 1.0 * inch], repeatRows=1)
    style = [
        ("BACKGROUND", (0, 0), (-1, 0), header_color),
        ("GRID", (0, 0), (-1, -1), 0.35, LINE),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("LEFTPADDING", (0, 0), (-1, -1), 4),
        ("RIGHTPADDING", (0, 0), (-1, -1), 4),
        ("TOPPADDING", (0, 0), (-1, -1), 4),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 4),
    ]
    for i, row in enumerate(rows, start=1):
        status = row[3].lower()
        if "escopado" in status and "nao" not in status:
            style.append(("BACKGROUND", (3, i), (3, i), MINT))
        elif "nao escopado" in status or "fora" in status:
            style.append(("BACKGROUND", (3, i), (3, i), RED))
        elif "validar" in status or "parcial" in status:
            style.append(("BACKGROUND", (3, i), (3, i), colors.HexColor("#FFF7D6")))
    table.setStyle(TableStyle(style))
    story.append(table)
    story.append(Spacer(1, 10))
    return story


escopado_rows = [
    ("Venda nacional / OE origem", "Customizacao voltada ao particionamento de pedidos de vendas nacionais, partindo de pedido OE.", "Documento de levantamento tecnico, item Objetivo do Projeto.", "Escopado"),
    ("Quantidade de paletes", "Usuario informa quantidade de paletes por pedido; padrao previsto de 28 paletes.", "Documento tecnico e e-mail reforcam o botao de particionamento por quantidade de paletes.", "Escopado"),
    ("Geracao de OEP", "Geracao automatica de pedidos intermediarios / OE Particionado a partir do pedido origem.", "Documento tecnico, modelo proposto TO-BE e lista de entregaveis.", "Escopado"),
    ("Mix de SKUs", "Distribuicao dos produtos nos pedidos particionados como mix dos produtos contidos no pedido origem.", "Documento tecnico e e-mail: mix na OE particionada ja faz parte do escopo aprovado.", "Escopado"),
    ("Produtos priorizados", "Usuario pode marcar produtos para priorizacao; selecao nao obrigatoria.", "Fluxo Proposto TO-BE.", "Escopado"),
    ("Ordem de prioridade", "Usuario define ordem de prioridade por campo adicional.", "Fluxo Proposto TO-BE.", "Escopado"),
    ("Distribuicao por grupo", "Sem prioridade, distribuir produtos conforme percentual que o grupo representa no total do pedido.", "Documento tecnico e e-mail.", "Escopado"),
    ("Regra da planilha atual", "Considerar logica de 28 paletes, paletes inteiros primeiro e quebras/fracionados ao final.", "Documento tecnico descreve VBA atual e lista de entregaveis menciona mesmas regras da planilha.", "Escopado"),
    ("Campo TGFCAB", "Criar campo adicional no pedido para identificar que houve particionamento automatico.", "Lista de entregaveis.", "Escopado"),
    ("Campo TOP", "Criar campo adicional no cadastro de TOP para permitir particionamento de quantidade nas TOPs de origem.", "Fluxo Proposto e lista de entregaveis.", "Escopado"),
    ("Controle de acesso", "A acao devera ter controle de acesso por usuario.", "Fluxo Proposto TO-BE.", "Escopado"),
]

not_rows = [
    ("Paletizacao por cliente especifico", "Regras especificas por cliente foram apresentadas no e-mail como inclusao/nova solicitacao a validar.", "E-mail menciona que, apos aprovacao desta nova inclusao, seria solicitada nova proposta.", "Nao escopado / Mudanca"),
    ("Multicom - minimo 5 SKUs", "Restricao de carga com minimo de 5 SKUs diferentes por palete.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("GPA - 1 item por SKU", "Regra de apenas 1 item por SKU para Companhia Brasileira de Distribuicao / GPA Osasco.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("GPA - palete PBR novo", "Definicao operacional de utilizar palete PBR novo, com possibilidade de sobreposicao.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("GPA - altura maxima", "Limite estrito de 1,20 m para KG, dependente de validacao por tipos de caixa.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("GPA - quebras do mesmo item", "Nao pode haver 2 paletes de quebras do mesmo item.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("GPA - bitrem e cortes", "Cliente nao aceita bitrem e nao permite cortes.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("2h Distribuicao", "Nao recebe mais de um SKU por palete; excecao somente para mesmo SKU na vertical, nunca horizontal.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("Custos por caixa/palete", "Valores de referencia de R$ 2,00 por caixa e R$ 25,00 por palete aparecem como informacao operacional.", "Cadeia de e-mail para 2h Distribuicao.", "Nao escopado / Validar"),
    ("Carga parametrica da planilha", "A planilha foi enviada como base por parceiro/familia e possui 'Aceita Saldo?'. Tela/carga/manutencao nao estao explicitadas no escopo tecnico original.", "Planilha Clientes Paletizacao Especial.xlsx.", "Parcial / Validar"),
    ("Regra detalhada de saldo", "Existe coluna 'Aceita Saldo?', mas nao ha algoritmo formal de como tratar sobra, quebra, corte ou remanejamento.", "Planilha contem o indicador; e-mail/documento nao fecham o algoritmo.", "Parcial / Validar"),
    ("Processamento em lote multi-OE", "O documento original descreve o usuario selecionando 'o pedido OE'. Processamento massivo/multiplas OEs nao aparece como regra fechada.", "Documento tecnico usa fluxo no singular.", "Nao escopado / Validar"),
]

doc = SimpleDocTemplate(
    OUT,
    pagesize=letter,
    rightMargin=0.65 * inch,
    leftMargin=0.65 * inch,
    topMargin=0.65 * inch,
    bottomMargin=0.65 * inch,
)

story = []
story.append(p("<b>ZAYA IT</b>", "SkH1"))
story.append(p("G E S T A O   D E   N E G O C I O S", "SkSmall"))
story.append(Spacer(1, 10))
story.append(p("I N T P D D  -  D O C U M E N T O   D E   I N T E G R A C A O<br/>E   D E S E N V O L V I M E N T O", "SkH2"))
story.append(Spacer(1, 12))
story.append(p("Skala - Particionamento de Pedidos V2", "SkTitle"))
story.append(p("Matriz de escopo: o que foi escopado x o que nao foi escopado", "SkH1"))
story.append(p("<b>PARECER TECNICO DE ESCOPO - MIX DE SKUS, PRIORIZACAO E PALETIZACAO ESPECIAL</b>", "SkBody"))
meta = Table([
    [p("<b>Documento</b>", "SkMeta"), p("ZY-SKALA-2026-PART-INTPDD-001", "SkMeta")],
    [p("<b>Projeto</b>", "SkMeta"), p("Skala - Particionamento de Pedidos V2", "SkMeta")],
    [p("<b>Versao</b>", "SkMeta"), p("v1.0", "SkMeta")],
    [p("<b>Data</b>", "SkMeta"), p("17 de junho de 2026", "SkMeta")],
    [p("<b>Responsavel Tecnico</b>", "SkMeta"), p("Zaya IT - Gestao de Negocios", "SkMeta")],
    [p("<b>Tipo de Documento</b>", "SkMeta"), p("Parecer tecnico de escopo", "SkMeta")],
    [p("<b>Tecnologia</b>", "SkMeta"), p("Sankhya - Pedido OE / Action Java / Oracle SQL / HTML5", "SkMeta")],
    [p("<b>Status</b>", "SkMeta"), p("Documento para validacao", "SkMeta")],
], colWidths=[1.7 * inch, 5.45 * inch])
meta.setStyle(TableStyle([
    ("GRID", (0, 0), (-1, -1), 0.35, LINE),
    ("BACKGROUND", (0, 0), (0, -1), MINT),
    ("VALIGN", (0, 0), (-1, -1), "TOP"),
    ("LEFTPADDING", (0, 0), (-1, -1), 6),
    ("RIGHTPADDING", (0, 0), (-1, -1), 6),
    ("TOPPADDING", (0, 0), (-1, -1), 5),
    ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
]))
story.append(meta)
story.append(Spacer(1, 14))
story.append(callout("Resumo executivo", "O mix de SKUs na OE particionada esta contemplado no escopo original e foi reforcado na cadeia de e-mail como parte do projeto aprovado. Ja as regras especificas de paletizacao por cliente aparecem como inclusao/validacao posterior e devem ser tratadas como ampliacao de escopo ou fase complementar, salvo aceite formal em contrario."))
story.append(PageBreak())

story.append(p("1. Fontes analisadas", "SkH1"))
for item in [
    "Documento: 1.0-Levantamento - Escopo Tecnico Orcamento - Skala - Particionamento de Pedidos V2.docx.",
    "E-mail: ENC_ Projeto Particionamento de Pedidos - Adicao de Fluxos atual e proposto.eml.",
    "Planilha: Clientes Paletizacao Especial.xlsx.",
    "Design system aplicado: identidade visual Skala, com uso de verde institucional, verde escuro, amarelo de destaque e layout executivo.",
]:
    story.append(p("- " + item, "SkBody"))

story.append(p("2. Leitura consolidada", "SkH1"))
for para in [
    "O escopo tecnico original define a customizacao para venda nacional, partindo de pedido OE, com geracao automatica de OEs particionadas, quantidade de paletes informada pelo usuario, padrao de 28 paletes e distribuicao dos produtos como mix do pedido origem.",
    "O documento tambem preve priorizacao opcional de produtos, ordem de prioridade por campo adicional, controle de TOP que permite particionamento, campo no pedido para identificar particionamento automatico e controle de acesso por usuario.",
    "A cadeia de e-mail reforca que o mix de produtos na OE particionada ja fazia parte do escopo previamente alinhado. No mesmo e-mail, as regras especificas por cliente sao apresentadas como inclusao/nova solicitacao a ser validada.",
    "A planilha de paletizacao especial fornece uma base parametrica por parceiro e familia de produto, contendo tambem a informacao 'Aceita Saldo?'. Ela serve como insumo operacional relevante, mas nao detalha sozinha o algoritmo completo de corte, saldo, altura, bitrem, verticalizacao ou outras restricoes logisticas.",
]:
    story.append(p(para, "SkBody"))

story += matrix("3. Itens escopados", escopado_rows)
story.append(PageBreak())
story += matrix("4. Itens nao escopados ou pendentes de validacao", not_rows, colors.HexColor("#7A5A00"))

story.append(p("5. Pontos de atencao", "SkH1"))
story.append(callout("Mix nao deve ser tratado como extra", "A combinacao entre documento tecnico e e-mail sustenta que o mix de SKUs faz parte do escopo aprovado. O e-mail inclusive registra que nao se trata de inclusao ou alteracao de requisito."))
story.append(Spacer(1, 8))
story.append(callout("Paletizacao especial deve ser tratada como mudanca", "As regras por cliente especifico aparecem no e-mail como inclusao/nova solicitacao e dependem de aprovacao e proposta. A planilha ajuda a parametrizar capacidade por familia, mas nao resolve todas as restricoes logisticas.", colors.HexColor("#FFF7D6")))

story.append(p("6. Recomendacao de faseamento", "SkH1"))
phase_data = [
    [p("Fase", "SkHeadCell"), p("Conteudo recomendado", "SkHeadCell"), p("Observacao", "SkHeadCell")],
    [p("Fase 1 - Escopo base", "SkCell"), p("Particionamento de OE, 28 paletes parametrizavel, OEP, mix, prioridade, grupos, regra da planilha atual, campos TGFCAB/TOP e controle de acesso.", "SkCell"), p("Aderente ao documento tecnico original.", "SkCell")],
    [p("Fase 2 - Parametrizacao operacional", "SkCell"), p("Tabela/tela para capacidade por parceiro e familia, incluindo indicador Aceita Saldo?.", "SkCell"), p("Baseada na planilha enviada; requer fechamento do comportamento de saldo.", "SkCell")],
    [p("Fase 3 - Restricoes por cliente", "SkCell"), p("Multicom, GPA, 2h Distribuicao, altura, bitrem, cortes, verticalizacao e restricoes especiais.", "SkCell"), p("Tratar como mudanca ou aditivo de escopo.", "SkCell")],
]
phase = Table(phase_data, colWidths=[1.25 * inch, 4.25 * inch, 1.65 * inch], repeatRows=1)
phase.setStyle(TableStyle([
    ("BACKGROUND", (0, 0), (-1, 0), GREEN),
    ("BACKGROUND", (0, 1), (0, -1), MINT),
    ("GRID", (0, 0), (-1, -1), 0.35, LINE),
    ("VALIGN", (0, 0), (-1, -1), "TOP"),
    ("LEFTPADDING", (0, 0), (-1, -1), 4),
    ("RIGHTPADDING", (0, 0), (-1, -1), 4),
    ("TOPPADDING", (0, 0), (-1, -1), 4),
    ("BOTTOMPADDING", (0, 0), (-1, -1), 4),
]))
story.append(phase)
story.append(Spacer(1, 10))
story.append(p("7. Conclusao", "SkH1"))
story.append(p("Com base nos documentos analisados, o desenvolvimento do particionamento com mix de SKUs, priorizacao e distribuicao por percentual de grupo deve ser considerado dentro do escopo. Ja as regras especificas de paletizacao por cliente, embora importantes e documentadas na cadeia de e-mail, aparecem como necessidade posterior de validacao e devem ser conduzidas por fase complementar ou gestao formal de mudanca.", "SkBody"))
story.append(p("8. Anexos logicos utilizados", "SkH1"))
for item in [
    "Documento de levantamento tecnico assinado em 13/05/2026.",
    "Cadeia de e-mail de 11/06/2026 a 16/06/2026 sobre fluxos atual/proposto e regras adicionais.",
    "Planilha Clientes Paletizacao Especial, com 24 linhas e 27 colunas, contendo parceiros, indicador de saldo e familias de produto.",
]:
    story.append(p("- " + item, "SkBody"))

doc.build(story, onFirstPage=footer, onLaterPages=footer)
print(OUT)
