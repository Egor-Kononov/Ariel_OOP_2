package api;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

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
        if(graph.nodeSize()==0 || graph.nodeSize()==1)
            return true;
        boolean flag;
        node_data start = null;
        for (node_data run : this.graph.getV()){
            start = run;
            break;
        }
        flag = bfs(start,this.graph);
        if(flag == false)
            return false;

        nullify();
        directed_weighted_graph g = transpose();
        start = g.getNode(start.getKey());
        return bfs(start,g);
    }

    public boolean bfs(node_data start,directed_weighted_graph graph){
        Queue<node_data> st1 = new ArrayDeque<node_data>();
        start.setInfo("blue");
        int count = 1;
        st1.add(start);
        while (!st1.isEmpty()){
            node_data p = st1.poll();
            for(edge_data run : graph.getE(p.getKey())){
                if(graph.getNode(run.getDest()).getInfo() == "white") {
                    graph.getNode(run.getDest()).setInfo("blue");
                    count ++;
                    st1.add(graph.getNode(run.getDest()));
                }
            }
        }
        return graph.nodeSize() == count;
    }
    public directed_weighted_graph transpose(){
        directed_weighted_graph g = new DWGraph_DS();
        for(node_data run : graph.getV()){
            node_data var = new NodeData(run);
            g.addNode(var);
        }
        for(node_data run : graph.getV()){
            for(edge_data run1 : graph.getE(run.getKey())){
                g.connect(run1.getDest(),run1.getSrc(),run1.getWeight());
            }
        }
        return g;
    }
    public void nullify(){
        for(node_data run : graph.getV()){
            //run.setTag(Integer.MAX_VALUE);
            run.setInfo("white");
        }
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
