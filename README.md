 Assignment 3 — Minimum Spanning Tree (MST)

Course: Design and Analysis of Algorithms
Author: Adilzhan Kadyrov
Language: Java 21
Tools: Maven, IntelliJ IDEA, Python (for data analysis)

 Objective

The goal of this project is to implement and compare two classical algorithms for finding a Minimum Spanning Tree (MST):

Kruskal’s Algorithm

Prim’s Algorithm

The comparison includes both theoretical complexity and practical performance using datasets of different sizes:
small.json, medium.json, and large.json.




⚙️ Installation & Execution
1️⃣ Clone the project
git clone https://github.com/adilzhankad/daa3.git
cd daa3

2️⃣ Install dependencies
mvn clean install

3️⃣ Run the program
mvn compile exec:java -Dexec.mainClass="org.example.Main"


After execution, the folder results/ will contain:

output_small.json
output_medium.json
output_large.json

 Data Analysis (Python)
1️⃣ Install required libraries
pip install pandas matplotlib

2️⃣ Run analysis
python analyze_results_realistic.py

3️⃣ Generated output

mst_summary.csv — comparison table (Kruskal vs Prim)

mst_realistic_plot.png — runtime growth visualization

mst_theory_comparison.png — theory vs practice chart

Theoretical Complexity
Algorithm	Data Structure	Time Complexity	Best For
Kruskal	Edge list + Union–Find	O(E log V)	Sparse graphs
Prim (heap)	Priority Queue + adjacency list	O(E log V)	Dense graphs
Prim (matrix)	Adjacency matrix	O(V²)	Small graphs


Practical Results
Dataset	Vertices	Edges	Kruskal Time (ms)	Prim Time (ms)	Observation
small	4	4	0	0	identical
medium	12	21	1–3	2–4	slight difference
large	30	60+	8–12	10–15	Kruskal faster

 Analysis & Discussion

Both algorithms produced identical MST weights.

Kruskal generally runs faster on small and sparse graphs.

Prim performs better on dense graphs but requires more memory.

The experimental results align with theoretical time complexities:

Kruskal → O(E log V)

Prim → O(V²) for matrix-based implementation

For larger or sparse graphs, Kruskal is more efficient and simpler to implement.