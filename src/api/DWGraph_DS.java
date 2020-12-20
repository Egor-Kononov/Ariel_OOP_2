package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer,node_data> graph;
    private NodeData node;
    private int edgeSize;
    private int mc;


    public DWGraph_DS(){
        graph = new HashMap<>();
        edgeSize = 0;
        mc = 0;
    }

    /**
     * return vertex of this key
     * @param key - the node_id
     */
    @Override
    public node_data getNode(int key) {
        return this.graph.get(key);
    }

    /**
     * return edge if not exist return null
     * @param src the source of the edge
     * @param dest the destination of the edge
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if(graph.containsKey(src) && graph.containsKey(dest)){
            node = (NodeData) graph.get(src);
           return node.getEdge(dest);
        }
        return null;
    }

    /**
     * this method add vertex to the graph
     * @param n key of the graph
     */
    @Override
    public void addNode(node_data n) {
        if(graph.containsKey(n.getKey()))
            return;
        graph.put(n.getKey(),n);
        mc ++;
    }

    /**
     * this method connect vertexes
     * @param src - the source of the edge
     * @param dest - the destination of the edge
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if(w < 0  || src == dest || !graph.containsKey(src) ||!graph.containsKey(dest)) {
            return;
        }
        NodeData var = (NodeData)graph.get(src);
        if(var.getEdge(dest)!=null){
            if(var.getEdge(dest).getWeight() >= w) {
                return;
            }
            var.createEdge(src,dest,w);
            edgeSize ++;
            mc ++;
            return;
        }
            var.createEdge(src,dest,w);
            edgeSize ++;
            mc ++;
    }

    /**
     * return all vertexes of the graph
     */
    @Override
    public Collection<node_data> getV() {
        return this.graph.values();
    }

    /**
     * return all the edges of the vertex to witch it is attached
     * @param node_id of vertex
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        if(!graph.containsKey(node_id))
        return null;
        node = (NodeData)graph.get(node_id);
        return node.getNi() ;
    }

    /**
     * remove vertex of the graph, if vertex don't exist return null
     * @param key id of vertex
     */
    @Override
    public node_data removeNode(int key) {
        if(!graph.containsKey(key))
            return null;

        NodeData var = (NodeData) getNode(key);
        for(node_data i : this.getV()){
            edge_data ed = getEdge(i.getKey(),key);
            NodeData s = (NodeData) this.getNode(i.getKey());
            if( ed != null){
                 s.removeEdge(key);
                edgeSize --;
                mc ++;
            }
        }
        edgeSize -= var.getNi().size();
        mc += var.getNi().size()+1;
        return graph.remove(key);
    }

    /**
     * this method remove edge from graph, if edge don't exist return null
     * @param src from where
     * @param dest where to
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if(getEdge(src,dest) == null)
            return null;
        node = (NodeData)getNode(src);
        edgeSize --;
        mc ++;
        return node.removeEdge(dest);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        return edgeSize == that.edgeSize &&
                Objects.equals(graph, that.graph) ;
    }

    /**
     *  return number of vertex
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }

    /**
     * return number of edges
     */
    @Override
    public int edgeSize() {
        return edgeSize;
    }

    /**
     * return all changes of the graph
     */
    @Override
    public int getMC() {
        return mc;
    }


}
