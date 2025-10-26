import json
import pandas as pd
from pathlib import Path


files = [
    "results/output_small.json",
    "results/output_medium.json",
    "results/output_large.json"
]

rows = []

for file in files:
    path = Path(file)
    if not path.exists():
        print(f"⚠️  File not found: {file}")
        continue

    dataset = path.stem.replace("output_", "")
    with open(path, "r", encoding="utf-8") as f:
        data = json.load(f)

    for g in data:
        kr = g.get("kruskal", {})
        pr = g.get("prim", {})

        rows.append({
            "Dataset": dataset,
            "Graph": g.get("graph"),
            "Vertices": g.get("vertexCount"),
            "Edges": g.get("edgeCount"),
            "Kruskal Weight": kr.get("totalWeight"),
            "Prim Weight": pr.get("totalWeight"),
            "Kruskal Time (ms)": kr.get("timeMs"),
            "Prim Time (ms)": pr.get("timeMs"),
            "Kruskal Comparisons": kr.get("comparisons"),
            "Prim Comparisons": pr.get("comparisons"),
            "Kruskal Ops": kr.get("unions"),
            "Prim Ops": pr.get("heapOps"),
        })


df = pd.DataFrame(rows)


df["Δ Time (ms)"] = df["Prim Time (ms)"] - df["Kruskal Time (ms)"]


output_csv = "results/mst_summary.csv"
df.to_csv(output_csv, index=False, encoding="utf-8-sig")

print("Table saved to", output_csv)
print(df)
