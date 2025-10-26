import json
import math
import pandas as pd
import matplotlib.pyplot as plt
from pathlib import Path
import numpy as np

files = [
    "results/output_small.json",
    "results/output_medium.json",
    "results/output_large.json"
]

rows = []

for file in files:
    path = Path(file)
    if not path.exists():
        print(f"⚠️ File not found: {file}")
        continue

    dataset = path.stem.replace("output_", "")
    with open(path, "r", encoding="utf-8") as f:
        data = json.load(f)

    for g in data:
        kr = g.get("kruskal", {})
        pr = g.get("prim", {})
        V = g.get("vertexCount", 0)
        E = g.get("edgeCount", 0)

        # Теоретические оценки (операции)
        theo_kruskal = E * math.log2(V + 1)
        theo_prim = E * math.log2(V + 1)
        theo_prim_matrix = V ** 2

        rows.append({
            "Dataset": dataset,
            "Vertices": V,
            "Edges": E,
            "Kruskal Time (ms)": kr.get("timeMs", 0),
            "Prim Time (ms)": pr.get("timeMs", 0),
            "Theo Kruskal": theo_kruskal,
            "Theo Prim": theo_prim,
            "Theo Prim Matrix": theo_prim_matrix
        })

df = pd.DataFrame(rows)

# Нормируем теоретические значения под реальные времена
for col in ["Theo Kruskal", "Theo Prim", "Theo Prim Matrix"]:
    scale = df["Kruskal Time (ms)"].max() / df[col].max() if df[col].max() > 0 else 1
    df[col] = df[col] * scale

# Сохраняем
df.to_csv("results/mst_realistic.csv", index=False, encoding="utf-8-sig")
print("✅ CSV saved to results/mst_realistic.csv")
print(df)

# ===============
# 📊 Построение
# ===============

datasets = df["Dataset"].unique()
x = np.arange(len(datasets))

plt.figure(figsize=(10, 6))

plt.plot(datasets, df.groupby("Dataset")["Kruskal Time (ms)"].mean(), "o-", label="Практика Kruskal")
plt.plot(datasets, df.groupby("Dataset")["Prim Time (ms)"].mean(), "o-", label="Практика Prim")
plt.plot(datasets, df.groupby("Dataset")["Theo Kruskal"].mean(), "s--", label="Теория Kruskal O(E log V)")
plt.plot(datasets, df.groupby("Dataset")["Theo Prim Matrix"].mean(), "d--", label="Теория Prim O(V²)")

plt.title("Рост времени алгоритмов: практические и теоретические значения")
plt.xlabel("Dataset (размер графа)")
plt.ylabel("Относительное время (нормировано)")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig("results/mst_realistic_plot.png", dpi=200)
plt.show()
