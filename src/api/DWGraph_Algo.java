package api;

import java.util.*;

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
            run.setTag(Integer.MAX_VALUE);
            run.setInfo("white");
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if(src == dest)
            return 0;
        if(graph.getV().contains(graph.getNode(src))&&graph.getV().contains(graph.getNode(dest))) {
            dijkatra(src, dest);
            double dist = this.graph.getNode(dest).getTag();
            if(dist == Integer.MAX_VALUE)
                return -1;
            nullify();
            return dist;
        }
        return -1;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        LinkedList<node_data> path = new LinkedList<node_data>();
        if(src == dest) {
            node_data s = graph.getNode(dest);
            path.addFirst(s);
            return path;
        }
        if(graph.getV().contains(graph.getNode(src))&&graph.getV().contains(graph.getNode(dest))) {
            //HashMap with path
            HashMap<node_data, Integer> h = dijkatra(src, dest);
            node_data tempVariable = graph.getNode(dest);
            //check if got to destination
            if(tempVariable.getTag()==Integer.MAX_VALUE )
                return null;
            path.addFirst(tempVariable);
            int keyOfparent = h.get(tempVariable);
            while (keyOfparent != src) {
                keyOfparent = h.get(tempVariable);
                tempVariable = graph.getNode(keyOfparent);
                path.addFirst(tempVariable);
            }
            nullify();
            return path;
        }
        return null;
    }
    public HashMap<node_data,Integer> dijkatra(int src, int dest){
        PriorityQueue<node_data> pq = new PriorityQueue<>(new Comparator<node_data>() {
            @Override
            public int compare(node_data o1, node_data o2) {
                return  Double.compare(o1.getTag(), o2.getTag());
            }
        });
        HashMap<node_data,Integer> parent = new HashMap();
        node_data p = this.graph.getNode(src);
        p.setTag(0);
        pq.add(p);
        while(!pq.isEmpty()){
            node_data temp = pq.poll();
            // blue the node which was visited
            if(temp.getInfo() != "blue"){
                temp.setInfo("blue");
                if(temp.getKey() == dest){
                    return parent;
                }
                // check all neighbors of visited node
                for(edge_data run : this.graph.getE(temp.getKey())){
                    if(graph.getNode(run.getDest()).getInfo() != "blue" ){
                        edge_data edge = graph.getEdge(run.getSrc(),run.getDest());
                        double dist = temp.getTag() + edge.getWeight();
                        if(dist < graph.getNode(run.getDest()).getTag()){
                            graph.getNode(run.getDest()).setTag(dist);
                            pq.add(graph.getNode(run.getDest()));
                            if(parent.containsKey(graph.getNode(run.getDest()))){
                                parent.remove(graph.getNode(run.getDest()));
                            }
                            parent.put(graph.getNode(run.getDest()),temp.getKey());
                        }
                    }
                }
            }
        }
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
