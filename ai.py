POSITIVE_KEYWORDS = {
    "automação": 4,
    "automation": 4,
    "api": 3,
    "integração": 4,
    "integration": 4,
    "chatbot": 4,
    "ai": 4,
    "ia": 4,
    "agent": 4,
    "assistant": 2,
    "bot": 3,
    "crm": 4,
    "workflow": 4,
    "n8n": 5,
    "make": 4,
    "zapier": 4,
    "webhook": 4,
    "software": 3,
    "system": 3,
    "sistema": 3,
    "platform": 3,
    "plataforma": 3,
    "dashboard": 2,
    "saas": 4,
    "backend": 3,
    "frontend": 2,
    "python": 4,
    "javascript": 3,
    "flask": 3,
    "node": 3,
    "openai": 5,
    "llm": 5,
    "clinic": 5,
    "clínica": 5,
    "clinica": 5,
    "health": 5,
    "healthcare": 5,
    "saúde": 5,
    "saude": 5,
    "medical": 5,
    "médico": 5,
    "medico": 5,
    "consultório": 5,
    "consultorio": 5,
    "laboratório": 5,
    "laboratorio": 5
}

NEGATIVE_KEYWORDS = {
    "instagram": -6,
    "influencer": -6,
    "social media": -6,
    "redes sociais": -6,
    "marketing": -5,
    "tráfego": -5,
    "trafego": -5,
    "copywriter": -6,
    "copy": -4,
    "designer": -5,
    "design gráfico": -6,
    "design grafico": -6,
    "logo": -6,
    "canva": -6,
    "vídeo": -6,
    "video": -6,
    "edição": -6,
    "edicao": -6,
    "youtube": -5,
    "tiktok": -5,
    "closer": -6,
    "executivo de contas": -6,
    "vendas": -4,
    "cold call": -5,
    "appointment setter": -6,
    "setter": -6,
    "seo": -5,
    "landing page": -4,
    "wordpress": -3,
    "shopify": -4,
    "e-commerce": -3,
    "depoimento": -6,
    "prova social": -6
}

def score_project(projeto):
    texto = f"{projeto['titulo']} {projeto['descricao']}".lower()

    score = 0
    positivos = []
    negativos = []

    for keyword, points in POSITIVE_KEYWORDS.items():
        if keyword in texto:
            score += points
            positivos.append(keyword)

    for keyword, points in NEGATIVE_KEYWORDS.items():
        if keyword in texto:
            score += points
            negativos.append(keyword)

    if len(texto) > 500:
        score += 1

    score = max(0, min(score, 10))

    motivo_partes = []
    if positivos:
        motivo_partes.append("Sinais positivos: " + ", ".join(sorted(set(positivos))))
    else:
        motivo_partes.append("Sem sinais fortes dos nichos-alvo")

    if negativos:
        motivo_partes.append("Sinais negativos: " + ", ".join(sorted(set(negativos))))

    motivo = " | ".join(motivo_partes)

    proposta = (
        "Olá! Li seu projeto e ele parece ter potencial para uma solução bem estruturada, "
        "principalmente em automação, integrações e organização de fluxo. "
        "Posso ajudar a desenhar uma abordagem técnica prática, escalável e alinhada ao objetivo do negócio."
    )

    return {
        "score": score,
        "motivo": motivo,
        "proposta": proposta
    }