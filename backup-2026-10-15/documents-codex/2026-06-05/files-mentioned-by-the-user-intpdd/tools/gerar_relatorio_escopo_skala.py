from docx import Document
from docx.enum.section import WD_SECTION
from docx.enum.table import WD_ALIGN_VERTICAL
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Cm, Inches, Pt, RGBColor


OUT = r"C:\Users\annayran.ferreira\Documents\Codex\2026-06-05\files-mentioned-by-the-user-intpdd\outputs\ZY-SKALA-2026-PART-INTPDD-001_Escopo_vs_Nao_Escopado.docx"

GREEN = "063F2D"
BRAND = "00A859"
MINT = "E7F7ED"
YELLOW = "FFD84D"
LIGHT = "F5FAF6"
GRAY = "F2F4F7"
RED = "FCE8E6"
INK = "173329"


def set_cell_shading(cell, fill):
    tc_pr = cell._tc.get_or_add_tcPr()
    shd = tc_pr.find(qn("w:shd"))
    if shd is None:
        shd = OxmlElement("w:shd")
        tc_pr.append(shd)
    shd.set(qn("w:fill"), fill)


def set_cell_text(cell, text, bold=False, color=INK, size=9):
    cell.text = ""
    p = cell.paragraphs[0]
    p.paragraph_format.space_after = Pt(0)
    r = p.add_run(text)
    r.bold = bold
    r.font.name = "Calibri"
    r.font.size = Pt(size)
    r.font.color.rgb = RGBColor.from_string(color)
    cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER


def set_borders(table, color="D6E4DB"):
    tbl = table._tbl
    tbl_pr = tbl.tblPr
    borders = tbl_pr.first_child_found_in("w:tblBorders")
    if borders is None:
        borders = OxmlElement("w:tblBorders")
        tbl_pr.append(borders)
    for edge in ("top", "left", "bottom", "right", "insideH", "insideV"):
        tag = "w:%s" % edge
        element = borders.find(qn(tag))
        if element is None:
            element = OxmlElement(tag)
            borders.append(element)
        element.set(qn("w:val"), "single")
        element.set(qn("w:sz"), "6")
        element.set(qn("w:space"), "0")
        element.set(qn("w:color"), color)


def set_width(cell, width_cm):
    cell.width = Cm(width_cm)
    tc_pr = cell._tc.get_or_add_tcPr()
    tc_w = tc_pr.find(qn("w:tcW"))
    if tc_w is None:
        tc_w = OxmlElement("w:tcW")
        tc_pr.append(tc_w)
    tc_w.set(qn("w:w"), str(int(width_cm / 2.54 * 1440)))
    tc_w.set(qn("w:type"), "dxa")


def add_para(doc, text="", style=None, bold_label=None):
    p = doc.add_paragraph(style=style)
    if bold_label:
        r = p.add_run(bold_label)
        r.bold = True
        r.font.color.rgb = RGBColor.from_string(GREEN)
        p.add_run(text)
    else:
        p.add_run(text)
    return p


def add_bullet(doc, text):
    p = doc.add_paragraph(style="List Bullet")
    p.paragraph_format.space_after = Pt(4)
    p.add_run(text)
    return p


def add_callout(doc, title, text, fill=MINT):
    table = doc.add_table(rows=1, cols=1)
    table.autofit = False
    set_borders(table, color="D6E4DB")
    cell = table.cell(0, 0)
    set_cell_shading(cell, fill)
    p = cell.paragraphs[0]
    p.paragraph_format.space_after = Pt(2)
    r = p.add_run(title)
    r.bold = True
    r.font.color.rgb = RGBColor.from_string(GREEN)
    r.font.size = Pt(10)
    p.add_run("\n" + text)
    for run in p.runs:
        run.font.name = "Calibri"
    doc.add_paragraph()


def add_matrix(doc, title, rows, header_fill=GREEN):
    doc.add_heading(title, level=2)
    table = doc.add_table(rows=1, cols=4)
    table.autofit = False
    set_borders(table)
    widths = [3.2, 6.1, 5.0, 2.7]
    headers = ["Item", "Entendimento", "Evidencia / origem", "Status"]
    for i, h in enumerate(headers):
        set_cell_text(table.rows[0].cells[i], h, bold=True, color="FFFFFF", size=9)
        set_cell_shading(table.rows[0].cells[i], header_fill)
        set_width(table.rows[0].cells[i], widths[i])
    for row in rows:
        cells = table.add_row().cells
        for i, value in enumerate(row):
            set_cell_text(cells[i], value, size=8.7)
            set_width(cells[i], widths[i])
        status = row[3].lower()
        if "escopado" in status and "nao" not in status:
            set_cell_shading(cells[3], MINT)
        elif "fora" in status or "nao escopado" in status:
            set_cell_shading(cells[3], RED)
        elif "pendente" in status or "validar" in status:
            set_cell_shading(cells[3], "FFF7D6")
    doc.add_paragraph()


doc = Document()
section = doc.sections[0]
section.top_margin = Inches(0.75)
section.bottom_margin = Inches(0.75)
section.left_margin = Inches(0.75)
section.right_margin = Inches(0.75)

styles = doc.styles
styles["Normal"].font.name = "Calibri"
styles["Normal"].font.size = Pt(11)
styles["Normal"].font.color.rgb = RGBColor.from_string(INK)
styles["Normal"].paragraph_format.space_after = Pt(6)
styles["Normal"].paragraph_format.line_spacing = 1.10

for name, size, color, before, after in [
    ("Heading 1", 16, GREEN, 14, 7),
    ("Heading 2", 13, GREEN, 10, 5),
    ("Heading 3", 12, "1F4D78", 8, 4),
]:
    style = styles[name]
    style.font.name = "Calibri"
    style.font.size = Pt(size)
    style.font.color.rgb = RGBColor.from_string(color)
    style.font.bold = True
    style.paragraph_format.space_before = Pt(before)
    style.paragraph_format.space_after = Pt(after)

# Cover
p = doc.add_paragraph()
p.alignment = WD_ALIGN_PARAGRAPH.LEFT
r = p.add_run("ZAYA IT")
r.bold = True
r.font.size = Pt(18)
r.font.color.rgb = RGBColor.from_string(GREEN)
r2 = p.add_run("\nG E S T A O   D E   N E G O C I O S")
r2.font.size = Pt(9)
r2.font.color.rgb = RGBColor.from_string(INK)

p = doc.add_paragraph()
p.paragraph_format.space_after = Pt(6)
r = p.add_run("I N T P D D  -  D O C U M E N T O   D E   I N T E G R A C A O\nE   D E S E N V O L V I M E N T O")
r.bold = True
r.font.size = Pt(10)
r.font.color.rgb = RGBColor.from_string(GREEN)

p = doc.add_paragraph()
p.paragraph_format.space_after = Pt(16)
r = p.add_run("Skala - Particionamento de Pedidos V2\nMatriz de escopo: o que foi escopado x o que nao foi escopado")
r.bold = True
r.font.size = Pt(22)
r.font.color.rgb = RGBColor.from_string(GREEN)

p = doc.add_paragraph()
p.paragraph_format.space_after = Pt(12)
r = p.add_run("PARECER TECNICO DE ESCOPO - MIX DE SKUS, PRIORIZACAO E PALETIZACAO ESPECIAL")
r.bold = True
r.font.size = Pt(11)
r.font.color.rgb = RGBColor.from_string(INK)

meta_rows = [
    ("Documento", "ZY-SKALA-2026-PART-INTPDD-001"),
    ("Projeto", "Skala - Particionamento de Pedidos V2"),
    ("Versao", "v1.0"),
    ("Data", "17 de junho de 2026"),
    ("Responsavel Tecnico", "Zaya IT - Gestao de Negocios"),
    ("Tipo de Documento", "Parecer tecnico de escopo"),
    ("Tecnologia", "Sankhya - Pedido OE / Action Java / Oracle SQL / HTML5"),
    ("Status", "Documento para validacao"),
]
meta = doc.add_table(rows=len(meta_rows), cols=2)
meta.autofit = False
set_borders(meta)
for idx, (label, value) in enumerate(meta_rows):
    set_cell_text(meta.rows[idx].cells[0], label, bold=True, color=GREEN)
    set_cell_text(meta.rows[idx].cells[1], value)
    set_width(meta.rows[idx].cells[0], 4.0)
    set_width(meta.rows[idx].cells[1], 13.0)
    set_cell_shading(meta.rows[idx].cells[0], MINT)
doc.add_paragraph()

add_callout(
    doc,
    "Resumo executivo",
    "O mix de SKUs na OE particionada esta contemplado no escopo original e foi reforcado na cadeia de e-mail como parte do projeto aprovado. Ja as regras especificas de paletizacao por cliente aparecem como inclusao/validacao posterior e devem ser tratadas como ampliacao de escopo ou fase complementar, salvo aceite formal em contrario.",
    fill=MINT,
)

doc.add_page_break()

doc.add_heading("1. Fontes analisadas", level=1)
add_bullet(doc, "Documento: 1.0-Levantamento - Escopo Tecnico Orcamento - Skala - Particionamento de Pedidos V2.docx.")
add_bullet(doc, "E-mail: ENC_ Projeto Particionamento de Pedidos - Adicao de Fluxos atual e proposto.eml.")
add_bullet(doc, "Planilha: Clientes Paletizacao Especial.xlsx.")
add_bullet(doc, "Design system aplicado: identidade visual Skala, com uso de verde institucional, verde escuro, amarelo de destaque e layout executivo.")

doc.add_heading("2. Leitura consolidada", level=1)
add_para(
    doc,
    "O escopo tecnico original define a customizacao para venda nacional, partindo de pedido OE, com geracao automatica de OEs particionadas, quantidade de paletes informada pelo usuario, padrao de 28 paletes e distribuicao dos produtos como mix do pedido origem."
)
add_para(
    doc,
    "O mesmo documento tambem preve priorizacao opcional de produtos, ordem de prioridade por campo adicional, controle de TOP que permite particionamento, campo no pedido para identificar particionamento automatico e controle de acesso por usuario."
)
add_para(
    doc,
    "A cadeia de e-mail reforca que o mix de produtos na OE particionada ja fazia parte do escopo previamente alinhado. No mesmo e-mail, as regras especificas por cliente sao apresentadas como inclusao/nova solicitacao a ser validada, com observacao de que, apos aprovacao, seria solicitada nova proposta."
)
add_para(
    doc,
    "A planilha de paletizacao especial fornece uma base parametrica por parceiro e familia de produto, contendo tambem a informacao 'Aceita Saldo?'. Ela serve como insumo operacional relevante, mas nao detalha sozinha o algoritmo completo de corte, saldo, altura, bitrem, verticalizacao ou outras restricoes logisticas."
)

escopado_rows = [
    ("Venda nacional / OE origem", "Customizacao voltada ao particionamento de pedidos de vendas nacionais, partindo de pedido OE.", "Documento de levantamento tecnico, item Objetivo do Projeto.", "Escopado"),
    ("Quantidade de paletes", "Usuario informa quantidade de paletes por pedido; padrao previsto de 28 paletes.", "Documento tecnico e e-mail reforcam 'Particionar Pedidos por Qtde de Paletes'.", "Escopado"),
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
add_matrix(doc, "3. Itens escopados", escopado_rows)

doc.add_page_break()

not_rows = [
    ("Paletizacao por cliente especifico", "Regras especificas por cliente foram apresentadas no e-mail como inclusao/nova solicitacao a validar.", "E-mail menciona: apos aprovacao desta nova inclusao, solicitar nova proposta.", "Nao escopado / Mudanca"),
    ("Multicom - minimo 5 SKUs por palete", "Restricao de carga com minimo de 5 SKUs diferentes por palete.", "Cadeia de e-mail, regra referente a paletizacao por clientes especificos.", "Nao escopado / Validar"),
    ("GPA - 1 item por SKU", "Regra de apenas 1 item por SKU para Companhia Brasileira de Distribuicao / GPA Osasco.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("GPA - palete PBR novo", "Definicao operacional de utilizar palete PBR novo, com possibilidade de sobreposicao.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("GPA - altura maxima", "Limite estrito de 1,20 m para KG, dependente de validacao por tipos de caixa.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("GPA - quebras do mesmo item", "Nao pode haver 2 paletes de quebras do mesmo item.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("GPA - bitrem e cortes", "Cliente nao aceita bitrem e nao permite cortes.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("2h Distribuicao", "Nao recebe mais de um SKU por palete; excecao somente para mesmo SKU na vertical, nunca horizontal.", "Cadeia de e-mail.", "Nao escopado / Validar"),
    ("Custos por caixa/palete", "Valores de referencia de R$ 2,00 por caixa e R$ 25,00 por palete aparecem como informacao operacional.", "Cadeia de e-mail para 2h Distribuicao.", "Nao escopado / Validar"),
    ("Carga parametrica da planilha", "A planilha foi enviada como base de caixas por palete por familia e possui 'Aceita Saldo?'. A implementacao de tela/carga/manutencao nao esta explicitada no escopo tecnico original.", "Planilha Clientes Paletizacao Especial.xlsx.", "Parcial / Validar"),
    ("Regra detalhada de saldo", "Existe coluna 'Aceita Saldo?', mas nao ha algoritmo formal de como tratar sobra, quebra, corte ou remanejamento.", "Planilha contem o indicador; e-mail/documento nao fecham o algoritmo.", "Parcial / Validar"),
    ("Processamento em lote multi-OE", "O documento original descreve o usuario selecionando 'o pedido OE'. Processamento massivo/multiplas OEs nao aparece como regra fechada.", "Documento tecnico usa fluxo no singular.", "Nao escopado / Validar"),
]
add_matrix(doc, "4. Itens nao escopados ou pendentes de validacao", not_rows, header_fill="7A5A00")

doc.add_heading("5. Pontos de atencao", level=1)
add_callout(
    doc,
    "Mix nao deve ser tratado como extra",
    "A combinacao entre documento tecnico e e-mail sustenta que o mix de SKUs faz parte do escopo aprovado. O e-mail inclusive registra que nao se trata de inclusao ou alteracao de requisito.",
    fill=MINT,
)
add_callout(
    doc,
    "Paletizacao especial deve ser tratada como mudanca",
    "As regras por cliente especifico aparecem no e-mail como inclusao/nova solicitacao e dependem de aprovacao e proposta. A planilha ajuda a parametrizar capacidade por familia, mas nao resolve todas as restricoes logisticas.",
    fill="FFF7D6",
)

doc.add_heading("6. Recomendacao de faseamento", level=1)
phase = doc.add_table(rows=1, cols=3)
phase.autofit = False
set_borders(phase)
for i, h in enumerate(["Fase", "Conteudo recomendado", "Observacao"]):
    set_cell_text(phase.rows[0].cells[i], h, bold=True, color="FFFFFF")
    set_cell_shading(phase.rows[0].cells[i], GREEN)
    set_width(phase.rows[0].cells[i], [2.6, 9.4, 5.0][i])
phase_rows = [
    ("Fase 1 - Escopo base", "Particionamento de OE, 28 paletes parametrizavel, OEP, mix, prioridade, grupos, regra da planilha atual, campos TGFCAB/TOP e controle de acesso.", "Aderente ao documento tecnico original."),
    ("Fase 2 - Parametrizacao operacional", "Tabela/tela para capacidade por parceiro e familia, incluindo indicador Aceita Saldo?.", "Baseada na planilha enviada; requer fechamento do comportamento de saldo."),
    ("Fase 3 - Restricoes por cliente", "Multicom, GPA, 2h Distribuicao, altura, bitrem, cortes, verticalizacao e restricoes especiais.", "Tratar como mudanca ou aditivo de escopo."),
]
for row in phase_rows:
    cells = phase.add_row().cells
    for i, value in enumerate(row):
        set_cell_text(cells[i], value, size=8.8)
        set_width(cells[i], [2.6, 9.4, 5.0][i])
    set_cell_shading(cells[0], MINT)
doc.add_paragraph()

doc.add_heading("7. Conclusao", level=1)
add_para(
    doc,
    "Com base nos documentos analisados, o desenvolvimento do particionamento com mix de SKUs, priorizacao e distribuicao por percentual de grupo deve ser considerado dentro do escopo. Ja as regras especificas de paletizacao por cliente, embora importantes e documentadas na cadeia de e-mail, aparecem como necessidade posterior de validacao e devem ser conduzidas por fase complementar ou gestao formal de mudanca."
)

doc.add_heading("8. Anexos logicos utilizados", level=1)
add_bullet(doc, "Documento de levantamento tecnico assinado em 13/05/2026.")
add_bullet(doc, "Cadeia de e-mail de 11/06/2026 a 16/06/2026 sobre fluxos atual/proposto e regras adicionais.")
add_bullet(doc, "Planilha Clientes Paletizacao Especial, com 24 linhas e 27 colunas, contendo parceiros, indicador de saldo e familias de produto.")

footer = section.footer.paragraphs[0]
footer.alignment = WD_ALIGN_PARAGRAPH.RIGHT
run = footer.add_run("Zaya IT - Gestao de Negocios | ZY-SKALA-2026-PART-INTPDD-001 | Confidencial")
run.font.size = Pt(8)
run.font.color.rgb = RGBColor.from_string("66756D")

doc.save(OUT)
print(OUT)
