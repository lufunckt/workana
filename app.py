from flask import Flask, render_template
import sqlite3

app = Flask(__name__)

def get_db():
    conn = sqlite3.connect("projetos.db")
    conn.row_factory = sqlite3.Row
    return conn

@app.route("/")
def dashboard():
    conn = get_db()
    projetos = conn.execute("""
        SELECT * FROM projetos
        ORDER BY score DESC, data_coleta DESC
    """).fetchall()
    conn.close()
    return render_template("index.html", projetos=projetos)

if __name__ == "__main__":
    app.run(debug=True)