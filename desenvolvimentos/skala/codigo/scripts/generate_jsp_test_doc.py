from pathlib import Path
from datetime import date

from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import cm
from reportlab.platypus import (
    SimpleDocTemplate,
    Paragraph,
    Spacer,
    Table,
    TableStyle,
    PageBreak,
    KeepTogether,
)


ROOT = Path(__file__).resolve().parents[1]
OUT_DIR = ROOT / "output" / "pdf"
OUT_DIR.mkdir(parents=True, exist_ok=True)

PDF_PATH = OUT_DIR / "ZY-SKALA-2026-PART-JSP-GUIA-TESTES.pdf"
MD_PATH = OUT_DIR / "ZY-SKALA-2026-PART-JSP-GUIA-TESTES.md"

DOC_CODE = "ZY-SKALA-2026-PART-JSP-GUIA-TESTES-001"
DOC_TITLE = "Guia de Testes do JSP de Particionamento"
DOC_SUBTITLE = "Mix de SKUs, prioridade, simulacao, agendamento e envio para action Java"

GREEN = colors.HexColor("#004B32")
ACCENT = colors.HexColor("#00A85A")
MINT = colors.HexColor("#EDF8EE")
YELLOW = colors.HexColor("#FFDF2F")
INK = colors.HexColor("#14382B")
MUTED = colors.HexColor("#66756F")
LINE = colors.HexColor("#DCE8DD")
PAGE_BG = colors.HexColor("#F6FAF4")
WARN = colors.HexColor("#8A5700")
WARN_BG = colors.HexColor("#FFF7E8")
BLUE = colors.HexColor("#28559C")
BLUE_BG = colors.HexColor("#F4F8FF")


def today_ptbr():
    today = date.today()
    meses = [
        "janeiro",
        "fevereiro",
        "marco",
        "abril",
        "maio",
        "junho",
        "julho",
        "agosto",
        "setembro",
        "outubro",
        "novembro",
        "dezembro",
    ]
    return f"{today.day:02d} de {meses[today.month - 1]} de {today.year}"


styles = getSampleStyleSheet()
styles.add(
    ParagraphStyle(
        name="CoverBrand",
        fontName="Helvetica-Bold",
        fontSize=26,
        leading=30,
        textColor=GREEN,
        alignment=TA_LEFT,
        spaceAfter=4,
    )
)
styles.add(
    ParagraphStyle(
        name="CoverKicker",
        fontName="Helvetica",
        fontSize=10,
        leading=15,
        textColor=MUTED,
        alignment=TA_LEFT,
        uppercase=True,
        spaceAfter=24,
    )
)
styles.add(
    ParagraphStyle(
        name="CoverTitle",
        fontName="Helvetica-Bold",
        fontSize=19,
        leading=25,
        textColor=INK,
        alignment=TA_LEFT,
        spaceAfter=8,
    )
)
styles.add(
    ParagraphStyle(
        name="CoverSubtitle",
        fontName="Helvetica",
        fontSize=11,
        leading=16,
        textColor=MUTED,
        alignment=TA_LEFT,
        spaceAfter=20,
    )
)
styles.add(
    ParagraphStyle(
        name="H1",
        fontName="Helvetica-Bold",
        fontSize=15,
        leading=20,
        textColor=GREEN,
        spaceBefore=8,
        spaceAfter=9,
    )
)
styles.add(
    ParagraphStyle(
        name="H2",
        fontName="Helvetica-Bold",
        fontSize=11.5,
        leading=15,
        textColor=INK,
        spaceBefore=8,
        spaceAfter=5,
    )
)
styles.add(
    ParagraphStyle(
        name="Body",
        fontName="Helvetica",
        fontSize=9.2,
        leading=13,
        textColor=INK,
        spaceAfter=7,
    )
)
styles.add(
    ParagraphStyle(
        name="Small",
        fontName="Helvetica",
        fontSize=8,
        leading=11,
        textColor=MUTED,
    )
)
styles.add(
    ParagraphStyle(
        name="Callout",
        fontName="Helvetica",
        fontSize=9,
        leading=13,
        textColor=INK,
        backColor=colors.white,
        borderColor=LINE,
        borderWidth=0.6,
        borderPadding=8,
        spaceAfter=10,
    )
)
styles.add(
    ParagraphStyle(
        name="TableHead",
        fontName="Helvetica-Bold",
        fontSize=8.2,
        leading=10.5,
        textColor=GREEN,
        alignment=TA_LEFT,
    )
)
styles.add(
    ParagraphStyle(
        name="TableCell",
        fontName="Helvetica",
        fontSize=8,
        leading=10.5,
        textColor=INK,
    )
)
styles.add(
    ParagraphStyle(
        name="BadgeText",
        fontName="Helvetica-Bold",
        fontSize=7.5,
        leading=9,
        alignment=TA_CENTER,
        textColor=GREEN,
    )
)


def p(text, style="Body"):
    return Paragraph(text, styles[style])


def table(data, widths, header=True):
    converted = []
    for r, row in enumerate(data):
        converted.append([p(str(cell), "TableHead" if header and r == 0 else "TableCell") for cell in row])
    t = Table(converted, colWidths=widths, repeatRows=1 if header else 0, hAlign="LEFT")
    cmd = [
        ("GRID", (0, 0), (-1, -1), 0.4, LINE),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("LEFTPADDING", (0, 0), (-1, -1), 6),
        ("RIGHTPADDING", (0, 0), (-1, -1), 6),
        ("TOPPADDING", (0, 0), (-1, -1), 5),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
    ]
    if header:
        cmd.extend(
            [
                ("BACKGROUND", (0, 0), (-1, 0), MINT),
                ("TEXTCOLOR", (0, 0), (-1, 0), GREEN),
            ]
        )
    for idx in range(1 if header else 0, len(data)):
        if idx % 2 == 0:
            cmd.append(("BACKGROUND", (0, idx), (-1, idx), colors.HexColor("#FBFDFB")))
    t.setStyle(TableStyle(cmd))
    return t


def badge(label, bg=MINT, fg=GREEN):
    t = Table([[p(label, "BadgeText")]], colWidths=[2.6 * cm])
    t.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, -1), bg),
                ("BOX", (0, 0), (-1, -1), 0.7, fg),
                ("ROUNDEDCORNERS", [7]),
                ("LEFTPADDING", (0, 0), (-1, -1), 6),
                ("RIGHTPADDING", (0, 0), (-1, -1), 6),
                ("TOPPADDING", (0, 0), (-1, -1), 4),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 4),
            ]
        )
    )
    return t


def footer(canvas, doc):
    canvas.saveState()
    w, h = A4
    canvas.setStrokeColor(LINE)
    canvas.line(1.45 * cm, 1.35 * cm, w - 1.45 * cm, 1.35 * cm)
    canvas.setFont("Helvetica", 7.2)
    canvas.setFillColor(MUTED)
    canvas.drawString(1.5 * cm, 0.92 * cm, f"Zaya IT - Gestao de Negocios | {DOC_CODE} | Documento de Uso Interno")
    canvas.drawRightString(w - 1.5 * cm, 0.92 * cm, f"Pagina {doc.page}")
    canvas.restoreState()


def cover():
    meta = [
        ["Documento", DOC_CODE],
        ["Projeto", "Skala - Particionamento de Pedidos"],
        ["Versao", "v1.0"],
        ["Data", today_ptbr()],
        ["Responsavel tecnico", "Zaya IT - Gestao de Negocios"],
        ["Tipo de documento", "Guia de teste funcional e operacional"],
        ["Tecnologia", "Sankhya - JSP HTML5 Component / Action Java / Oracle SQL"],
        ["Status", "Pronto para teste assistido"],
    ]
    return [
        Spacer(1, 0.4 * cm),
        p("ZAYA IT", "CoverBrand"),
        p("G E S T A O   D E   N E G O C I O S", "CoverKicker"),
        p("INTPDD - DOCUMENTO DE TESTE E OPERACAO", "CoverKicker"),
        p(DOC_TITLE, "CoverTitle"),
        p(DOC_SUBTITLE, "CoverSubtitle"),
        Spacer(1, 0.5 * cm),
        table(meta, [4.4 * cm, 11.0 * cm], header=False),
        Spacer(1, 0.7 * cm),
        p(
            "Este guia explica como testar o JSP de particionamento no Sankhya, como interpretar as legendas da tela, "
            "como validar o mix de SKUs e como confirmar que o payload enviado para a Action Java esta coerente.",
            "Callout",
        ),
        PageBreak(),
    ]


def build_story():
    story = []
    story.extend(cover())

    story += [
        p("OBJETIVO DESTE DOCUMENTO", "H1"),
        p(
            "Orientar o teste funcional do modal JSP de particionamento de pedidos, cobrindo a leitura dos itens da OE, "
            "calculo de paletes, composicao por mix de SKUs, prioridade, grupos, simulacao das OEPs, agendamento por OEP "
            "e envio para a rotina Java no Sankhya.",
        ),
        p(
            "O documento foi montado no mesmo padrao visual do material ZY enviado como referencia: capa institucional, "
            "secao objetiva, tabelas de teste, criterios de aceite e controle final.",
        ),
        p("VISUAL E LEGENDAS DA TELA", "H1"),
        table(
            [
                ["Elemento", "Onde aparece", "Como interpretar"],
                ["OE", "Aba OEs e resumo lateral", "Representa a nota/pedido de origem selecionado no Sankhya."],
                ["28 / capacidade", "Painel de configuracao", "Quantidade maxima de paletes por OEP. Em teste pode ser 1 para forcar varias OEPs."],
                ["Mix", "Resumo lateral e simulacao", "Composicao da OEP com varios SKUs do pedido origem."],
                ["Prioridade", "Tabela da OE aberta", "SKU priorizado entra primeiro na ordem informada, mas ainda respeita a regra de mix."],
                ["Grupo", "Tabela da OE e abas de grupo/simulacao", "Classifica SKUs para leitura do mix. Nao consome um grupo inteiro sozinho."],
                ["Calculo", "Aba Calculo de Palete", "Paletes = QTDNEG / (LASTRO * CAMADAS). Caixas = paletes * (LASTRO * CAMADAS)."],
                ["Mix OK", "Card da OEP simulada", "A OEP possui mais de um SKU, conforme a regra esperada."],
                ["Sobra final", "Card da OEP simulada", "A OEP ficou incompleta porque nao havia saldo suficiente para fechar outra OEP."],
                ["SKU unico", "Card da OEP simulada", "Ponto de atencao: OEP cheia com apenas um SKU quando havia mix disponivel."],
            ],
            [3.0 * cm, 4.0 * cm, 9.0 * cm],
        ),
        Spacer(1, 0.25 * cm),
        p("FLUXO RECOMENDADO DE TESTE", "H1"),
        table(
            [
                ["Etapa", "Acao do usuario", "Resultado esperado"],
                ["1", "Abrir o modal a partir da OE/pedido selecionado.", "A aba OEs deve carregar a nota e os itens da TGFITE/TGFPRO."],
                ["2", "Conferir a capacidade por OEP.", "O valor padrao pode ser 28; para teste de estresse usar 1 ou outro valor menor."],
                ["3", "Abrir a OE na aba OEs.", "A lista de SKUs aparece com uso, prioridade, grupo e ordem."],
                ["4", "Marcar/remover itens do mix, se necessario.", "Resumo deve atualizar quantidade de itens, grupos e paletes."],
                ["5", "Conferir Calculo de Palete.", "Cada SKU deve mostrar QTDNEG, fator LASTRO * CAMADAS e paletes calculados."],
                ["6", "Clicar em Simular.", "A aba Simulacao deve mostrar as OEPs previstas, ocupacao, SKUs e status."],
                ["7", "Informar Agendamento da OEP em cada card.", "Todos os cards devem possuir data/hora antes do envio."],
                ["8", "Enviar para particionar.", "A Action Java deve receber o payload, validar STATUSNOTA = L e gerar as OEs."],
            ],
            [1.3 * cm, 6.2 * cm, 8.5 * cm],
        ),
        PageBreak(),
        p("COMO O MIX FUNCIONA", "H1"),
        p(
            "A regra atual e por SKU selecionado. A rotina considera apenas os itens marcados para uso no mix. "
            "Quando a simulacao monta uma OEP, ela distribui a capacidade entre os SKUs que ainda possuem saldo. "
            "O objetivo e evitar uma OEP completa composta por um unico SKU quando existem outros SKUs disponiveis.",
        ),
        table(
            [
                ["Regra", "Comportamento esperado"],
                ["Selecao", "Somente SKUs marcados em Usar entram no calculo e no payload."],
                ["Calculo base", "Paletes = QTDNEG / (LASTRO * CAMADAS). Nao existe fallback para AD_UNDPALETE."],
                ["Prioridade", "Prioridades entram primeiro, na ordem informada. Prioridade nao significa consumir o SKU sozinho."],
                ["Distribuicao", "A capacidade da OEP e rateada entre SKUs ativos com saldo."],
                ["Sobra final", "Se ao final sobrar um unico SKU ou capacidade incompleta, a ultima OEP pode ficar como sobra."],
                ["Validacao visual", "OEP completa deve aparecer como Mix OK sempre que houver mais de um SKU disponivel."],
            ],
            [3.8 * cm, 12.2 * cm],
        ),
        p("Exemplo de leitura", "H2"),
        p(
            "Se a capacidade for 1 palete e o total selecionado for 1,79 paletes, a simulacao deve prever 2 OEPs: "
            "a primeira com 1,00/1,00 e a segunda com aproximadamente 0,79/1,00. A primeira deve aparecer como Mix OK "
            "se tiver mais de um SKU; a segunda pode ser Mix OK ou Sobra final conforme o saldo restante.",
        ),
        p("AGENDAMENTO POR OEP", "H1"),
        table(
            [
                ["Campo", "Obrigatoriedade", "Destino tecnico", "Como testar"],
                ["Agendamento da OEP", "Obrigatorio para cada OEP simulada", "TGFCAB.AD_DHAGENDAMENTO da OE criada", "Preencher data e hora em todos os cards antes de enviar."],
                ["Bloqueio visual", "Sim", "JSP", "Deixar um card sem data/hora e tentar enviar. A tela deve bloquear."],
                ["Bloqueio backend", "Sim", "Action Java", "Se o payload vier sem agendamento, a action deve retornar erro e nao gerar OEs."],
            ],
            [3.7 * cm, 3.2 * cm, 4.3 * cm, 4.8 * cm],
        ),
        p("VALIDACOES DO BACKEND", "H1"),
        table(
            [
                ["Validacao", "Origem", "Resultado esperado"],
                ["TGFCAB.STATUSNOTA = L", "Pedido/OE origem", "Somente pedido liberado pode ser particionado."],
                ["TOP permite particionamento", "TGFTOP.AD_PERMITEPART", "Se nao permitir, bloquear antes de gerar OEs."],
                ["Pedido ja particionado", "TGFCAB.AD_PARTICIONADO", "Nao permitir particionar novamente pelo mesmo fluxo."],
                ["Paletizacao obrigatoria", "TGFPRO.LASTRO e TGFPRO.CAMADAS", "Produto sem fator valido deve bloquear o teste."],
                ["Agendamento", "Payload JSP", "Cada OEP deve ter AD_DHAGENDAMENTO informado."],
                ["Rastreabilidade", "AD_PARTCAB, AD_PARTOE, AD_PARTITEM", "Registrar origem, OEs criadas e itens alocados."],
            ],
            [4.2 * cm, 4.2 * cm, 7.6 * cm],
        ),
        PageBreak(),
        p("CENARIOS DE TESTE", "H1"),
        table(
            [
                ["Cenario", "Entrada sugerida", "Resultado esperado"],
                ["Teste basico", "OE liberada, TOP permitida, capacidade 28.", "Gerar OEPs conforme total de paletes, com rastreabilidade."],
                ["Teste de mix forçado", "Capacidade 1, varios SKUs selecionados.", "Gerar varias OEPs com status Mix OK nas OEPs completas."],
                ["Teste de prioridade", "Marcar alguns SKUs com ordem 1, 2, 3.", "SKUs priorizados aparecem primeiro, sem quebrar o mix."],
                ["Teste de sobra", "Total de paletes nao multiplo da capacidade.", "Ultima OEP fica incompleta, indicada como Sobra final quando aplicavel."],
                ["Teste sem agendamento", "Simular e deixar uma OEP sem data/hora.", "JSP bloqueia o envio e solicita agendamento."],
                ["Teste pedido nao liberado", "TGFCAB.STATUSNOTA diferente de L.", "Action bloqueia com mensagem de pedido nao liberado."],
                ["Teste produto sem paletizacao", "Produto sem LASTRO ou CAMADAS.", "Rotina bloqueia informando produto sem paletizacao valida."],
                ["Teste desfazer", "Particionamento ja criado e OEs abertas.", "Botao de desfazer remove OEs particionadas e atualiza status/rastro."],
            ],
            [3.3 * cm, 5.4 * cm, 7.3 * cm],
        ),
        p("CHECKLIST DE ACEITE", "H1"),
        table(
            [
                ["#", "Criterio"],
                ["1", "A tela abre com a OE selecionada e carrega os itens reais da TGFITE/TGFPRO."],
                ["2", "A aba Calculo mostra paletes por QTDNEG / (LASTRO * CAMADAS)."],
                ["3", "A aba Simulacao mostra OEPs, ocupacao, SKUs, grupos e status de mix."],
                ["4", "OEP completa com varios SKUs aparece como Mix OK."],
                ["5", "OEP final incompleta aparece como sobra quando aplicavel."],
                ["6", "Todas as OEPs exigem Agendamento da OEP antes do envio."],
                ["7", "Pedido sem TGFCAB.STATUSNOTA = L nao particiona."],
                ["8", "A action Java gera OEs e grava TGFCAB.AD_DHAGENDAMENTO em cada nota criada."],
                ["9", "A rastreabilidade permite consultar origem, OEs criadas e itens particionados."],
                ["10", "O ZIP do HTML5 Component contem somente modalParticionamentoPedidos.jsp na raiz."],
            ],
            [1.0 * cm, 15.0 * cm],
        ),
        p("PROBLEMAS COMUNS E COMO LER", "H1"),
        table(
            [
                ["Sintoma", "Causa provavel", "Acao sugerida"],
                ["Texto com caracteres quebrados", "Encoding do HTML5 Component.", "Usar a versao atual do JSP com textos ASCII."],
                ["Simulacao gera poucas OEPs", "Capacidade alta ou total de paletes baixo.", "Reduzir capacidade para 1 e simular novamente."],
                ["SKU unico em OEP completa", "Saldo/conjunto selecionado gerou composicao sem mix.", "Conferir itens selecionados, prioridade e saldo por SKU."],
                ["Erro de estoque", "Triggers/estoque na base, mesmo com TOP sem validacao aparente.", "Validar local/controle/trigger do ambiente de teste."],
                ["Erro de agendamento", "Alguma OEP sem data/hora no payload.", "Preencher todos os cards de OEP antes de enviar."],
            ],
            [4.0 * cm, 5.0 * cm, 7.0 * cm],
        ),
        PageBreak(),
        p("CONTROLE DO DOCUMENTO", "H1"),
        table(
            [
                ["Versao", "Data", "Autor", "Alteracoes"],
                ["v1.0", today_ptbr(), "Zaya IT - Gestao de Negocios", "Criacao do guia de testes do JSP de particionamento, com legendas, fluxo de teste, mix de SKUs, agendamento e criterios de aceite."],
            ],
            [2.0 * cm, 3.0 * cm, 4.0 * cm, 7.0 * cm],
        ),
        Spacer(1, 0.8 * cm),
        p("Elaborado por Zaya IT - Responsavel Tecnico | Zaya IT - Gestao de Negocios", "Body"),
        p("Guarulhos/SP - " + today_ptbr(), "Small"),
    ]
    return story


def write_markdown():
    md = f"""# {DOC_TITLE}

**Documento:** {DOC_CODE}  
**Projeto:** Skala - Particionamento de Pedidos  
**Versao:** v1.0  
**Data:** {today_ptbr()}  
**Tecnologia:** Sankhya - JSP HTML5 Component / Action Java / Oracle SQL

## Objetivo

Orientar o teste funcional do modal JSP de particionamento de pedidos, cobrindo leitura dos itens da OE, calculo de paletes, composicao por mix de SKUs, prioridade, grupos, simulacao das OEPs, agendamento por OEP e envio para a rotina Java no Sankhya.

## Visual e legendas

| Elemento | Como interpretar |
|---|---|
| OE | Nota/pedido de origem selecionado no Sankhya. |
| Capacidade | Quantidade maxima de paletes por OEP. Para teste, usar 1 ajuda a forcar mais OEPs. |
| Mix | Composicao da OEP com varios SKUs do pedido origem. |
| Prioridade | SKU priorizado entra primeiro, mas ainda respeita a regra de mix. |
| Grupo | Classificacao visual/operacional dos SKUs. Nao significa consumir um grupo inteiro sozinho. |
| Calculo | Paletes = QTDNEG / (LASTRO * CAMADAS). |
| Mix OK | A OEP possui mais de um SKU. |
| Sobra final | OEP incompleta por falta de saldo para fechar outra OEP. |
| SKU unico | Ponto de atencao: OEP cheia com apenas um SKU quando havia mix disponivel. |

## Fluxo recomendado de teste

1. Abrir o modal a partir da OE/pedido selecionado.
2. Conferir a capacidade por OEP.
3. Abrir a OE na aba OEs e revisar SKUs, prioridade, grupo e ordem.
4. Conferir a aba Calculo de Palete.
5. Clicar em Simular.
6. Conferir OEPs previstas, ocupacao, SKUs e status.
7. Informar Agendamento da OEP em cada card.
8. Enviar para particionar.

## Como o mix funciona

A regra atual e por SKU selecionado. A rotina considera apenas itens marcados para uso. Ao montar uma OEP, ela distribui a capacidade entre os SKUs que ainda possuem saldo, evitando uma OEP completa com um unico SKU quando existem outros SKUs disponiveis.

- Se houver prioridade, os priorizados entram primeiro na ordem informada.
- Prioridade nao significa consumir o SKU sozinho.
- Se sobrar apenas um SKU no final, a ultima OEP pode ficar como sobra final.
- O calculo oficial e `QTDNEG / (LASTRO * CAMADAS)`.

## Agendamento por OEP

Cada OEP simulada deve receber data/hora no campo **Agendamento da OEP**. Esse valor e enviado no payload e gravado em `TGFCAB.AD_DHAGENDAMENTO` na OE criada. Se faltar agendamento, o JSP bloqueia o envio.

## Validacoes do backend

| Validacao | Resultado esperado |
|---|---|
| `TGFCAB.STATUSNOTA = 'L'` | Somente pedido liberado pode particionar. |
| TOP permite particionamento | Bloqueia quando `TGFTOP.AD_PERMITEPART` nao permite. |
| Pedido ja particionado | Bloqueia novo particionamento. |
| LASTRO e CAMADAS | Produto sem paletizacao valida bloqueia. |
| Agendamento por OEP | Toda OEP deve ter `AD_DHAGENDAMENTO`. |
| Rastreabilidade | Registra origem, OEs criadas e itens alocados. |

## Cenarios de teste

| Cenario | Entrada sugerida | Resultado esperado |
|---|---|---|
| Teste basico | OE liberada, TOP permitida, capacidade 28 | Gerar OEPs conforme total de paletes. |
| Mix forcado | Capacidade 1, varios SKUs selecionados | OEPs completas com Mix OK. |
| Prioridade | Marcar SKUs com ordem 1, 2, 3 | Priorizados entram primeiro sem quebrar o mix. |
| Sobra | Total de paletes nao multiplo da capacidade | Ultima OEP incompleta/sobra final. |
| Sem agendamento | Deixar uma OEP sem data/hora | JSP bloqueia envio. |
| Pedido nao liberado | `STATUSNOTA <> 'L'` | Action bloqueia. |
| Produto sem paletizacao | Sem LASTRO ou CAMADAS | Rotina bloqueia. |
| Desfazer | OEs abertas e rastreadas | Desfaz somente o particionamento daquela origem. |

## Checklist de aceite

- [ ] Modal abre com OE selecionada.
- [ ] Itens reais carregam da TGFITE/TGFPRO.
- [ ] Calculo usa QTDNEG / (LASTRO * CAMADAS).
- [ ] Simulacao mostra OEPs, ocupacao, SKUs, grupos e status.
- [ ] OEP completa com varios SKUs aparece como Mix OK.
- [ ] Toda OEP exige agendamento antes do envio.
- [ ] Pedido nao liberado nao particiona.
- [ ] Action grava `TGFCAB.AD_DHAGENDAMENTO` em cada OE criada.
- [ ] Rastreabilidade registra origem, OEs e itens.
- [ ] ZIP do HTML5 Component contem somente `modalParticionamentoPedidos.jsp` na raiz.
"""
    MD_PATH.write_text(md, encoding="utf-8")


def generate_pdf():
    doc = SimpleDocTemplate(
        str(PDF_PATH),
        pagesize=A4,
        rightMargin=1.45 * cm,
        leftMargin=1.45 * cm,
        topMargin=1.45 * cm,
        bottomMargin=1.6 * cm,
        title=DOC_TITLE,
        author="Zaya IT - Gestao de Negocios",
    )
    doc.build(build_story(), onFirstPage=footer, onLaterPages=footer)


if __name__ == "__main__":
    write_markdown()
    generate_pdf()
    print(PDF_PATH)
    print(MD_PATH)
