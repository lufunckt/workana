from playwright.sync_api import sync_playwright

def main():
    with sync_playwright() as p:
        browser = p.chromium.launch_persistent_context(
            user_data_dir="workana_session",
            headless=False
        )

        page = browser.new_page()
        page.goto("https://www.workana.com", wait_until="domcontentloaded")
        print("Faça login manualmente na Workana.")
        print("Depois que terminar, volte aqui no terminal e pressione Enter.")
        input()
        browser.close()

if __name__ == "__main__":
    main()