import sqlite3

DB_NAME = "projetos.db"

def get_connection():
    conn = sqlite3.connect(DB_NAME)
    conn.row_factory = sqlite3.Row
    return conn

def init_db():
    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute("""
    CREATE TABLE IF NOT EXISTS projetos (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        titulo TEXT NOT NULL,
        descricao TEXT,
        link TEXT UNIQUE,
        score REAL,
        motivo TEXT,
        proposta TEXT,
        status TEXT DEFAULT 'novo',
        data_coleta TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    )
    """)

    conn.commit()
    conn.close()

def save_project(titulo, descricao, link, score=None, motivo=None, proposta=None, status="novo"):
    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute("""
    INSERT OR IGNORE INTO projetos (titulo, descricao, link, score, motivo, proposta, status)
    VALUES (?, ?, ?, ?, ?, ?, ?)
    """, (titulo, descricao, link, score, motivo, proposta, status))

    conn.commit()
    conn.close()