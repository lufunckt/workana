from database import init_db, save_project
from scraper import collect_projects
from ai import score_project

MIN_SCORE = 4

def main():
    print("Iniciando coleta de projetos...")
    init_db()

    projetos = collect_projects()
    print(f"\nTotal capturado pelo scraper: {len(projetos)}")

    if not projetos:
        print("Nenhum candidato foi capturado.")
        return

    aprovados = 0

    for projeto in projetos:
        analise = score_project(projeto)

        if analise["score"] < MIN_SCORE:
            continue

        save_project(
            titulo=projeto["titulo"],
            descricao=projeto["descricao"],
            link=projeto["link"],
            score=analise["score"],
            motivo=analise["motivo"],
            proposta=analise["proposta"],
            status="novo"
        )

        aprovados += 1

        print("\n" + "=" * 70)
        print(f"TÍTULO: {projeto['titulo']}")
        print(f"SCORE: {analise['score']}")
        print(f"MOTIVO: {analise['motivo']}")
        print(f"LINK: {projeto['link']}")
        print("=" * 70)

    print(f"\nProjetos aprovados e salvos: {aprovados}")

if __name__ == "__main__":
    main()