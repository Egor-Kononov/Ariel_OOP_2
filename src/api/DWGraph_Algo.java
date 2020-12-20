package api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {
    directed_weighted_graph graph;

    public DWGraph_Algo(){
        this.graph = new DWGraph_DS();
    }

    /**
     * connect graph to algorithm
     * @param g directed weighted graph
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
    }

    /**
     *return graph
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    /**
     * this method do deep copy of the graph
     * @return copy of that graph
     */
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

    /**
     * check if this graph linked
     */
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
        if(start == null)
            return false;
        flag = bfs(start,this.graph);
        if(flag == false){
            nullify();
            return false;
        }
        nullify();
        directed_weighted_graph g = transpose();
        start = g.getNode(start.getKey());
        return bfs(start,g);
    }

    /**
     * Auxiliary function to isConnect function
     * Bread-first search algorithms
     * @param start start point
     * @param graph directed weighted graph
     * @return true if that graph connected
     */
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

    /**
     *Turns all edges of the graph in another direction
     */
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

    /**
     * updates all values that were used to find the short path
     * and to check connections
     */
    public void nullify(){
        NodeData run = new NodeData();
        for(node_data run1 : graph.getV()){
            run = (NodeData) run1;
            run.setDist(Integer.MAX_VALUE);
            run.setTag(Integer.MAX_VALUE);
            run.setInfo("white");
        }
    }

    /**
     * return distance from src to dest, return -1 if path don't exist
     * @param src - start node
     * @param dest - end (target) node
     */
    @Override
    public double shortestPathDist(int src, int dest) {

        if(graph.getV().contains(graph.getNode(src))&&graph.getV().contains(graph.getNode(dest))) {
            if(src == dest)
                return 0;
            dijkstra(src, dest);
            NodeData var = (NodeData) this.graph.getNode(dest);
            double dist = var.getDist();
            if(dist == Integer.MAX_VALUE){
                nullify();
                return -1;
            }

            nullify();
            return dist;
        }
        return -1;
    }

    /**
     * return route,if rout don't exist return null
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        LinkedList<node_data> path = new LinkedList<node_data>();
        if(src == dest) {
            node_data s = graph.getNode(dest);
            path.addFirst(s);
            return path;
        }
        if(graph.getV().contains(graph.getNode(src))&&graph.getV().contains(graph.getNode(dest))) {
            if(src == dest) {
                node_data s = graph.getNode(dest);
                path.addFirst(s);
                return path;
            }
            //HashMap with path
            dijkstra(src, dest);
            node_data tempVariable = graph.getNode(dest);
            //check if got to destination
            if(tempVariable.getTag()==Integer.MAX_VALUE ) {
                nullify();
                return null;
            }
            path.addFirst(tempVariable);
            int keyOfparent = tempVariable.getTag();
            while (keyOfparent != src){
                tempVariable = graph.getNode(keyOfparent);
                path.addFirst(tempVariable);
                keyOfparent = tempVariable.getTag();
            }
            tempVariable = graph.getNode(keyOfparent);
            path.addFirst(tempVariable);
            nullify();
            return path;
        }
        return null;
    }

    /**
     *Auxiliary function to shortPath and shortPathDist functions
     * dijkstra algorithm
     * @param src start node
     * @param dest end (target) node
     */
    public void dijkstra(int src, int dest) {
            PriorityQueue<NodeData> pq = new PriorityQueue<>(new Comparator<NodeData>() {
                @Override
                public int compare(NodeData o1, NodeData o2) {
                    return Double.compare(o1.getDist(), o2.getDist());
                }
            });
            NodeData p = (NodeData) this.graph.getNode(src);
            p.setDist(0);
            pq.add(p);
            while (!pq.isEmpty()) {
                NodeData temp = (NodeData) pq.poll();
                // blue the node which was visited
                if (temp.getInfo() != "blue") {
                    temp.setInfo("blue");
                    if (temp.getKey() == dest) {
                        return;
                    }
                    // check all neighbors of visited node
                    for (edge_data run : this.graph.getE(temp.getKey())) {
                        if (graph.getNode(run.getDest()).getInfo() != "blue") {
                            edge_data edge = graph.getEdge(run.getSrc(), run.getDest());
                            double dist = temp.getDist() + edge.getWeight();
                            NodeData var = (NodeData) graph.getNode(run.getDest());
                            if (dist < var.getDist()) {
                                var.setDist(dist);
                                pq.add(var);
                                var.setTag(temp.getKey());

                            }
                        }
                    }
                }
            }
        }

    /**
     * Save that graph in jason format
     * @param file - the file name (may include a relative path).
     * @return true if this method completed successfully
     */
    @Override
    public boolean save(String file) {
        JsonObject graph = new JsonObject();
        JsonArray nodes=new JsonArray();
        JsonArray edges=new JsonArray();

        for(node_data run : this.graph.getV()){
            JsonObject node = new JsonObject();
            node.addProperty("id", run.getKey());
            String pos = run.getLocation().x()+","+run.getLocation().y()+","+run.getLocation().z();
            node.addProperty("pos",pos);
            nodes.add(node);
            for(edge_data runner : this.graph.getE(run.getKey())){
                JsonObject edge = new JsonObject();
                edge.addProperty("src",runner.getSrc());
                edge.addProperty("w",runner.getWeight());
                edge.addProperty("dest",runner.getDest());
                edges.add(edge);
            }
        }
        graph.add("Edges",edges);
        graph.add("Nodes",nodes);
        try{
            Gson gson = new Gson();
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(gson.toJson(graph));
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * create new graph from string
     * @param file - file name of JSON file
     * @return true
     */
    @Override
    public boolean load (String file) {
        try {
            Scanner scanner = new Scanner(file);
            String jsonString = scanner.useDelimiter("\\A").next();
            scanner.close();
            directed_weighted_graph g = new DWGraph_DS();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject tempObj = new JSONObject();
            JSONArray nodes = jsonObject.getJSONArray("Nodes");
            JSONArray edges = jsonObject.getJSONArray("Edges");

            for (int i = 0; i < nodes.length(); i++) {
                tempObj = nodes.getJSONObject(i);
                int key = (int) tempObj.get("id");
                String geo = (String) tempObj.get("pos");
                NodeData node = new NodeData(key);
                ArrayList<Double> points = new ArrayList<Double>(3);
                for(String part : geo.split(",")){
                    points.add(Double.parseDouble(part));
                }
                node.setLocation(points.get(0),points.get(1),points.get(2));
                g.addNode(node);
            }
            for (int i = 0; i < edges.length(); i++) {
                tempObj = edges.optJSONObject(i);
                int src = (int) tempObj.get("src");
                int dest = (int) tempObj.get("dest");
                double weight = (double) tempObj.get("w");
                g.connect(src, dest, weight);
            }
            this.graph = g;
        } catch (JSONException  e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
