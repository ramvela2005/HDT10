import networkx as nx
import matplotlib.pyplot as plt

def load_graph_from_file(filename):
    G = nx.DiGraph()
    try:
        with open(filename, 'r') as file:
            for line in file:
                city1, city2, distance = line.strip().split(',')
                G.add_edge(city1.strip(), city2.strip(), weight=int(distance.strip()))
    except FileNotFoundError:
        print(f"El archivo {filename} no se encuentra en el directorio.")
        return None
    return G

def floyd_warshall_shortest_paths(G):
    pred, dist = nx.floyd_warshall_predecessor_and_distance(G)
    return pred, dist

def get_path(pred, source, target):
    if source not in pred or target not in pred:
        return None
    path = [target]
    while target != source:
        target = pred[source][target]
        path.append(target)
    path.reverse()
    return path

def get_graph_center(G):
    length = dict(nx.floyd_warshall(G))
    ecc = nx.eccentricity(G, sp=length)
    return nx.center(G, e=ecc)[0]

def show_graph(G):
    pos = nx.spring_layout(G)
    edge_labels = nx.get_edge_attributes(G, 'weight')
    nx.draw(G, pos, with_labels=True, node_color='skyblue', node_size=3000, font_size=8, font_weight='bold', arrows=True)
    nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels)
    plt.title("Grafo de Ciudades")
    plt.show()

# Ejemplo de uso
filename = 'guategrafos.txt'  
G = load_graph_from_file(filename)
if G is not None:
    pred, dist = floyd_warshall_shortest_paths(G)
    source, target = 'Mixco', 'Escuintla'
    print(f"Ruta más corta de {source} a {target}: {get_path(pred, source, target)}")
    print(f"Centro del grafo: {get_graph_center(G)}")
    show_graph(G)


