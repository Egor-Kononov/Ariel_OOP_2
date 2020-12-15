Ariel_OOP_2020
this project deals with undirected weighted graph.
this graph consist of 3 interfaces, 2 classes and inner class
Inner class NodeData designed to create vertices and edge of the graph
NodeData has 5 main functions:
1 construcor receives integer key, create vertice with this unique key
2 getTag return tag of vertice
3 setTag receives real number, and set this number in tag
4 getInfo return string info of vertice
5 setInfo receives string, and set this string in info
class WGraph_DS disigned to create graph
data structure for this class is HashMap because each vertices has a unique key and this allows us retrieve information faster.
WGraph_DS has 13 main functions:
1 Constructor, creates empty graph.
2 getNode receives integer, return vertice if he exist in graph.
3 hasEdge receives 2 integer keys , return true if exist edge.
4 getEdge receives 2 integer keys , return value of edge if exist edge else return -1.
5 addNode receives integer, create vertice and adds to grap.
6 connect receives 2 keys and value of edge, connects two vertices with edge.
7 getV returns all vertice of graph.
8 getV receives key of vertice and return all of its neighbors.
9 removeNode receives integer key of vertice, removes thats vertice and all edges associated with this vertex.
10 removeEdge receives 2 integer keys, removes an edge between two vertices.
11 nodeSize return number of vertices.
12 edgeSize return number of edge.
13 getMc number of changes in graph.
class WGraph_Algo disigned to work with a graph
WGraph_Algo has 8 main functions:
1 Init method is a predefine method to initialize an object after its creation.
2 getGraph return all vertices of graph.
3 copy deep copy of the graph.
4 isConnected retun true when graph linked, using an algorithm BFS (https://en.wikipedia.org/wiki/Breadth-first_search)
5 shortestPathDist receives source key and destanation key, return shortest distance of path using an algorithm(https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)
6 shortestPath receives source key and destanation key, return shortest path using an algorithm(https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)
7 save, return true if managed to create a file
8 load, return true if managed to upload a file
