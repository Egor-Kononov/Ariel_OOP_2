package api;

import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms {
    directed_weighted_graph graph;

    public DWGraph_Algo(){
        this.graph = new DWGraph_DS();
    }
    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph g = new DWGraph_DS();
        for(node_data run : graph.getV()){
            node_data var = new NodeData(run);
            g.addNode(var);
        }
        for(node_data run : graph.getV()){
            for(edge_data run1 : graph.getE(run.getKey())){
                g.connect(run1.getSrc(),run1.getDest(),run1.getWeight());
            }
        }
        return g;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
