from playwright.sync_api import sync_playwright

SEARCH_URLS = [
    "https://www.workana.com/jobs?language=pt",
    "https://www.workana.com/jobs?language=en"
]

JOB_PATH_HINTS = ["/job/", "/jobs/", "/projects/"]

def looks_like_job_link(href: str) -> bool:
    href_lower = href.lower()
    return any(h in href_lower for h in JOB_PATH_HINTS)

def collect_projects():
    projetos = []
    links_vistos = set()

    with sync_playwright() as p:
        browser = p.chromium.launch_persistent_context(
            user_data_dir="workana_session",
            headless=False
        )

        page = browser.new_page()

        for url in SEARCH_URLS:
            print(f"\nAbrindo: {url}")
            page.goto(url, wait_until="domcontentloaded")
            page.wait_for_timeout(6000)

            for _ in range(5):
                page.mouse.wheel(0, 4000)
                page.wait_for_timeout(1500)

            anchors = page.locator("a").all()
            print(f"Total de links encontrados em {url}: {len(anchors)}")

            candidatos = 0

            for a in anchors:
                try:
                    href = a.get_attribute("href")
                    texto = a.inner_text().strip()

                    if not href:
                        continue

                    if not looks_like_job_link(href):
                        continue

                    full_link = href if href.startswith("http") else f"https://www.workana.com{href}"

                    if full_link in links_vistos:
                        continue

                    candidatos += 1
                    links_vistos.add(full_link)

                    titulo = texto.split("\n")[0].strip() if texto else "Sem título"
                    descricao = texto.strip() if texto else ""

                    projetos.append({
                        "titulo": titulo[:180],
                        "descricao": descricao[:3000],
                        "link": full_link
                    })

                except Exception as e:
                    print("Erro ao processar link:", e)

            print(f"Candidatos capturados em {url}: {candidatos}")

        print(f"\nProjetos capturados no total: {len(projetos)}")
        browser.close()

    return projetos