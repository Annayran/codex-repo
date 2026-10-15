from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import letter
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import inch
from reportlab.platypus import (
    BaseDocTemplate,
    Frame,
    PageBreak,
    PageTemplate,
    Paragraph,
    Spacer,
    Table,
    TableStyle,
)


OUT = r"C:\Users\annayran.ferreira\Documents\Codex\2026-05-07\files-mentioned-by-the-user-polyvoxtelecontrol\documentacao\IntPDD_Telecontrol_Composicao_Produtos_Pecas_Produtos.pdf"

GREEN = colors.HexColor("#43B649")
DARK_GREEN = colors.HexColor("#24823F")
NAVY = colors.HexColor("#0B2545")
BLUE = colors.HexColor("#2E74B5")
MUTED = colors.HexColor("#606C7A")
LIGHT_GREEN = colors.HexColor("#E8F5EA")
LIGHT_BLUE = colors.HexColor("#E8EEF5")
LIGHT_GRAY = colors.HexColor("#F2F4F7")
BORDER = colors.HexColor("#C9D2DC")


def footer(canvas, doc):
    canvas.saveState()
    canvas.setFont("Helvetica-Bold", 8)
    canvas.setFillColor(MUTED)
    canvas.drawString(inch, letter[1] - 0.55 * inch, "Polyvox Telecontrol | IntPDD - Composição, Produtos e Peças")
    canvas.setStrokeColor(BORDER)
    canvas.line(inch, 0.66 * inch, letter[0] - inch, 0.66 * inch)
    canvas.setFont("Helvetica", 8)
    canvas.drawCentredString(
        letter[0] / 2,
        0.43 * inch,
        f"Zaya IT - Gestão de Negócios | ZY-POLYVOX-2026-TLC-INTPDD-002 | Confidencial | Página {doc.page}",
    )
    canvas.restoreState()


def p(text, style):
    return Paragraph(text, style)


styles = getSampleStyleSheet()
BODY = ParagraphStyle(
    "Body",
    parent=styles["BodyText"],
    fontName="Helvetica",
    fontSize=9.8,
    leading=12.2,
    textColor=NAVY,
    spaceAfter=6,
)
BODY_SMALL = ParagraphStyle("BodySmall", parent=BODY, fontSize=8.7, leading=10.5)
TITLE = ParagraphStyle(
    "Title",
    parent=styles["Title"],
    fontName="Helvetica-Bold",
    fontSize=24,
    leading=28,
    textColor=NAVY,
    alignment=TA_LEFT,
    spaceAfter=5,
)
SUBTITLE = ParagraphStyle(
    "Subtitle",
    parent=BODY,
    fontName="Helvetica-Bold",
    fontSize=13.5,
    leading=16,
    textColor=DARK_GREEN,
    spaceAfter=15,
)
KICKER = ParagraphStyle(
    "Kicker",
    parent=BODY,
    fontName="Helvetica-Bold",
    fontSize=9,
    leading=11,
    textColor=DARK_GREEN,
    spaceAfter=5,
)
H1 = ParagraphStyle(
    "H1",
    parent=BODY,
    fontName="Helvetica-Bold",
    fontSize=14.5,
    leading=18,
    textColor=BLUE,
    spaceBefore=13,
    spaceAfter=7,
)
H2 = ParagraphStyle(
    "H2",
    parent=BODY,
    fontName="Helvetica-Bold",
    fontSize=11.5,
    leading=14,
    textColor=BLUE,
    spaceBefore=8,
    spaceAfter=4,
)
CENTER = ParagraphStyle("Center", parent=BODY, alignment=TA_CENTER)


def callout(title, body, fill=LIGHT_GREEN):
    data = [[p(f"<b>{title}</b>", BODY), p(body, BODY_SMALL)]]
    table = Table(data, colWidths=[1.55 * inch, 4.82 * inch], hAlign="LEFT")
    table.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, -1), fill),
                ("BOX", (0, 0), (-1, -1), 0.6, colors.HexColor("#9ACAA1")),
                ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
                ("LEFTPADDING", (0, 0), (-1, -1), 8),
                ("RIGHTPADDING", (0, 0), (-1, -1), 8),
                ("TOPPADDING", (0, 0), (-1, -1), 8),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 8),
            ]
        )
    )
    return table


def kv(rows, header=None):
    data = []
    if header:
        data.append([p(f"<b>{header}</b>", BODY), ""])
    for label, value in rows:
        data.append([p(f"<b>{label}</b>", BODY_SMALL), p(value, BODY_SMALL)])
    table = Table(data, colWidths=[1.75 * inch, 4.62 * inch], hAlign="LEFT")
    style = [
        ("GRID", (0, 0), (-1, -1), 0.45, BORDER),
        ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
        ("LEFTPADDING", (0, 0), (-1, -1), 7),
        ("RIGHTPADDING", (0, 0), (-1, -1), 7),
        ("TOPPADDING", (0, 0), (-1, -1), 5),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
        ("BACKGROUND", (0, 0), (0, -1), LIGHT_GRAY),
    ]
    if header:
        style.extend([("SPAN", (0, 0), (1, 0)), ("BACKGROUND", (0, 0), (1, 0), LIGHT_GRAY)])
    table.setStyle(TableStyle(style))
    return table


def matrix(headers, rows, widths=None, fill=LIGHT_BLUE):
    if widths is None:
        widths = [1.65 * inch, 4.72 * inch]
    data = [[p(f"<b>{h}</b>", BODY_SMALL) for h in headers]]
    data += [[p(str(c), BODY_SMALL) for c in row] for row in rows]
    table = Table(data, colWidths=widths, hAlign="LEFT", repeatRows=1)
    table.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, 0), fill),
                ("GRID", (0, 0), (-1, -1), 0.45, BORDER),
                ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
                ("LEFTPADDING", (0, 0), (-1, -1), 6),
                ("RIGHTPADDING", (0, 0), (-1, -1), 6),
                ("TOPPADDING", (0, 0), (-1, -1), 4),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 4),
            ]
        )
    )
    return table


def bullets(items):
    flow = []
    for item in items:
        flow.append(p(f"&bull;&nbsp; {item}", BODY))
    return flow


def build():
    doc = BaseDocTemplate(
        OUT,
        pagesize=letter,
        leftMargin=inch,
        rightMargin=inch,
        topMargin=0.95 * inch,
        bottomMargin=0.85 * inch,
    )
    frame = Frame(doc.leftMargin, doc.bottomMargin, doc.width, doc.height, id="normal")
    doc.addPageTemplates([PageTemplate(id="main", frames=[frame], onPage=footer)])
    story = []

    story += [
        p("ZAYA IT | GESTÃO DE NEGÓCIOS", KICKER),
        p("INTPDD - DOCUMENTO DE INTEGRAÇÃO E DESENVOLVIMENTO", BODY_SMALL),
        Spacer(1, 18),
        p("Integração Telecontrol Polyvox", TITLE),
        p("Composição de Produtos (TPRLMP), Lista Básica, Produtos e Peças", SUBTITLE),
        callout(
            "Resumo executivo",
            "Documento de apoio para homologação das melhorias implementadas no pacote Telecontrol. Registra a regra da composição de produtos para Lista Básica, a atualização dos cadastros de produtos e peças via GET e os controles criados para evitar duplicidade e perda de rastreabilidade.",
        ),
        Spacer(1, 12),
        kv(
            [
                ("Documento", "ZY-POLYVOX-2026-TLC-INTPDD-002"),
                ("Versão", "v1.0"),
                ("Data", "11 de junho de 2026"),
                ("Projeto", "Polyvox - Integração Telecontrol no Sankhya"),
                ("Tecnologia", "Java 8 - Sankhya MGE / AcaoRotinaJava / ScheduledAction / Gradle"),
                ("Status", "Em homologação e testes assistidos"),
            ],
            "Controle do documento",
        ),
        PageBreak(),
        p("Objetivo deste documento", H1),
        p(
            "Este documento descreve as melhorias criadas na integração Telecontrol para sustentar a Lista Básica a partir da Composição de Produtos do Sankhya e para atualizar os cadastros de produtos e peças já existentes na base.",
            BODY,
        ),
        p("Escopo entregue", H2),
    ]
    story += bullets(
        [
            "Leitura de matérias-primas da TPRLMP marcadas para envio à Lista Básica Telecontrol.",
            "Persistência controlada na tabela filha AD_TCLLTB, vinculada ao produto existente em AD_TCLPRO.",
            "Atualização de Produtos e Peças Telecontrol via GET, com busca por REFERENCIA e fallback na TGFPRO.",
            "Padronização de transações e correção de tipos numéricos usados pelo Jape.",
            "Melhoria de logs para recebidos, atualizados, inseridos, não encontrados e erros por referência.",
        ]
    )
    story += [
        p("Processo funcional", H1),
        p(
            "Antes das melhorias, a Lista Básica não era alimentada diretamente pela composição do produto. O fluxo novo usa a marcação AD_ENVLISTTLC para transformar matérias-primas elegíveis em registros de AD_TCLLTB.",
            BODY,
        ),
        matrix(
            ["Etapa", "Origem", "Validação", "Resultado esperado"],
            [
                ("1", "TPRLMP.AD_ENVLISTTLC = 'S'", "Produto e matéria-prima informados", "Elegível"),
                ("2", "AD_TCLPRO", "CODPRODPA = CODPROD", "Produto localizado"),
                ("3", "AD_TCLPCA", "CODPRODMP = CODPROD", "Peça localizada"),
                ("4", "TGFPRO", "REFERENCIA por CODPROD", "Refs preenchidas"),
                ("5", "AD_TCLLTB", "CODPROD + CODPECA inexistente", "Inserção sem duplicidade"),
            ],
            [0.55 * inch, 1.85 * inch, 2.25 * inch, 1.72 * inch],
            LIGHT_GREEN,
        ),
        p("Composição de Produtos (TPRLMP)", H1),
        p(
            "Foi criada sustentação para utilizar a tela de Composição de Produtos como origem da Lista Básica Telecontrol. A rotina respeita a estrutura nativa do Sankhya e evita envio indevido de itens não marcados.",
            BODY,
        ),
        kv(
            [
                ("Produto acabado", "TPRLMP.CODPRODPA -> AD_TCLPRO.CODPROD"),
                ("Matéria-prima / peça", "TPRLMP.CODPRODMP -> AD_TCLPCA.CODPROD"),
                ("Quantidade", "TPRLMP.QTDMISTURA -> AD_TCLLTB.QTD"),
                ("Referências", "TGFPRO.REFERENCIA -> REFPRODUTO e REFPECA"),
                ("Duplicidade", "Busca prévia em AD_TCLLTB por CODPROD + CODPECA"),
            ],
            "Mapeamento TPRLMP -> AD_TCLLTB",
        ),
        PageBreak(),
        p("Atualização de Produtos Telecontrol", H1),
        p(
            "A rotina de produtos consulta o endpoint GET do Telecontrol e atualiza a AD_TCLPRO com base na REFERENCIA. O COD_TELECONTROL é persistido, mas não é a chave de localização no Sankhya.",
            BODY,
        ),
        matrix(
            ["Regra", "Comportamento"],
            [
                ("Busca primária", "Localiza registro em AD_TCLPRO pela REFERENCIA."),
                ("Fallback", "Se não existir, consulta TGFPRO.REFERENCIA e usa o CODPROD local."),
                ("Não encontrado", "Registra log e não cria cadastro órfão."),
                ("Tipos", "Campos NUMBER/FLOAT são convertidos para BigDecimal."),
            ],
            [1.55 * inch, 4.82 * inch],
        ),
        p("Atualização de Peças Telecontrol", H1),
        p(
            "A rotina de peças aplica o mesmo padrão: busca por REFERENCIA em AD_TCLPCA, fallback na TGFPRO e gravação com CODPROD local. Assim, o cadastro Telecontrol fica amarrado ao produto real do Sankhya.",
            BODY,
        ),
        matrix(
            ["Regra", "Comportamento"],
            [
                ("Busca primária", "Localiza peça em AD_TCLPCA pela REFERENCIA."),
                ("Fallback", "Consulta TGFPRO.REFERENCIA quando ainda não existe na tela Telecontrol."),
                ("Inserção", "Insere apenas quando a referência existe na TGFPRO."),
                ("Tipos", "MULTIPLO, GARANTIADIFERENCIADA, ESTOQUE e COD_TELECONTROL são numéricos."),
            ],
            [1.55 * inch, 4.82 * inch],
        ),
        p("Rastreabilidade e logs", H1),
        matrix(
            ["Evento", "Uso na homologação"],
            [
                ("Pagina processada", "Mostra quantidade recebida da API e total acumulado."),
                ("Atualizado", "Confirma manutenção de cadastro existente."),
                ("Inserido a partir da TGFPRO", "Confirma criação com CODPROD local."),
                ("Não encontrado", "Indica saneamento cadastral necessário."),
                ("Erro ao sincronizar", "Mostra referência, código Telecontrol e mensagem técnica."),
            ],
            [2.05 * inch, 4.32 * inch],
            LIGHT_GREEN,
        ),
        PageBreak(),
        p("Componentes desenvolvidos", H1),
        matrix(
            ["Componente", "Responsabilidade", "Tipo"],
            [
                ("BuscaCadastrosTelecontrolService", "GET de produtos/peças, parse, validação e persistência.", "Service"),
                ("AtualizaProdutoBt / Sc", "Disparo manual ou agendado da atualização de produtos.", "Ação / Scheduled"),
                ("AtualizaPecaBt / Sc", "Disparo manual ou agendado da atualização de peças.", "Ação / Scheduled"),
                ("SincronizaListaBasicaService", "Lê TPRLMP e insere AD_TCLLTB.", "Service"),
                ("SincronizaListaBasicaScheduler", "Executa a Lista Básica de forma agendada.", "ScheduledAction"),
                ("LogUtil", "Registra logs respeitando transação ativa.", "Utilitário"),
            ],
            [1.95 * inch, 3.32 * inch, 1.1 * inch],
        ),
        p("Critérios de homologação", H1),
        matrix(
            ["Cenário", "Resultado esperado"],
            [
                ("Produto existe em AD_TCLPRO", "Atualizar sem duplicar."),
                ("Produto não existe em AD_TCLPRO, mas existe em TGFPRO", "Inserir com CODPROD local."),
                ("Produto não existe em TGFPRO", "Registrar não encontrado."),
                ("Peça existe em AD_TCLPCA", "Atualizar sem duplicar."),
                ("TPRLMP marcada", "Inserir AD_TCLLTB se produto, peça e combinação forem válidos."),
                ("TPRLMP sem marcação", "Ignorar sem movimentar Lista Básica."),
            ],
            [2.45 * inch, 3.92 * inch],
            LIGHT_GREEN,
        ),
        p("Próximos passos recomendados", H1),
    ]
    story += bullets(
        [
            "Executar homologação completa com amostra de referências conhecidas e não encontradas.",
            "Validar envio posterior da AD_TCLLTB para o Telecontrol e retorno de LISTABASICA/DHINTEG.",
            "Formalizar saneamento para referências divergentes entre Telecontrol e TGFPRO.",
            "Agendar execução recorrente das rotinas conforme janela operacional da Polyvox.",
        ]
    )
    story.append(Spacer(1, 16))
    story.append(p("Fim do documento.", CENTER))
    doc.build(story)
    print(OUT)


if __name__ == "__main__":
    build()
