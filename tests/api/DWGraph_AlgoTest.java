package api;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {


    @Test
    void copy() {
        dw_graph_algorithms al = new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();
        for(int i=0; i<10;i++){
            node_data node = new NodeData(i);
            g.addNode(node);
        }
        al.init(g);
        directed_weighted_graph g1 = al.copy();
        assertEquals(g,g1);
        assertFalse(g==g1);
    }

    @Test
    void isConnected() {
        DWGraph_Algo al = new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();
        for(int i=0; i<10;i++){
            node_data node = new NodeData(i);
            g.addNode(node);
        }
        al.init(g);
        assertFalse(al.isConnected());

        for(int i=0; i<9;i++){
            g.connect(i,i+1,i+2);
        }

        assertFalse(al.isConnected());
        assertTrue(g.getMC()==19);

        for(int i=0; i<9;i++){
            g.connect(i+1,i,i+2);
        }

        assertTrue(al.isConnected());
        assertTrue(g.edgeSize()== 18);

        g.addNode(new NodeData(10));
        assertFalse(al.isConnected());
    }

    @Test
    void shortestPathDist() {
        dw_graph_algorithms al = new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();
        al.init(g);
        for(int i=0; i<5;i++){
            node_data node = new NodeData(i);
            g.addNode(node);
        }
        g.connect(0,1,10);
        g.connect(1,2,7);
        g.connect(1,4,20);
        g.connect(2,3,6);
        g.connect(3,4,5);

        assertTrue(al.shortestPathDist(0,4)==28);
        assertTrue(al.shortestPathDist(0,5)==-1);

        g.connect(2,4,10);
        assertTrue(al.shortestPathDist(0,4)==27);
        assertTrue(al.shortestPathDist(4,4)==0);
        assertTrue(al.shortestPathDist(5,5)==-1);



    }

    @Test
    void shortestPath() {
        dw_graph_algorithms al = new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();
        al.init(g);
        for(int i=0; i<5;i++){
            node_data node = new NodeData(i);
            g.addNode(node);
        }
        g.connect(0,1,10);
        g.connect(1,2,7);
        g.connect(1,4,20);
        g.connect(2,3,6);
        g.connect(3,4,5);
        g.connect(2,4,10);

        List<node_data> l = al.shortestPath(0,4);
        assertTrue(l.size() == 4);

         l = al.shortestPath(0,0);
         assertTrue(l.size()==1);

        l = al.shortestPath(0,5);
        assertNull(l);


    }

    @Test
    void saveAndLoad()  {
        directed_weighted_graph g = new DWGraph_DS();
        node_data node = new NodeData();
        node_data node1 = new NodeData();
        node_data node2 = new NodeData();
        node_data node3 = new NodeData();
        node_data node4 = new NodeData();
        g.addNode(node);
        g.addNode(node1);
        g.addNode(node2);
        g.addNode(node3);
        g.addNode(node4);
        g.connect(node.getKey(),node1.getKey(),10);
        g.connect(node1.getKey(),node2.getKey(),7);
        g.connect(node1.getKey(),node4.getKey(),20);
        g.connect(node2.getKey(),node3.getKey(),6);
        g.connect(node3.getKey(),node4.getKey(),5);
        g.connect(node2.getKey(),node4.getKey(),10);
        dw_graph_algorithms g0 = new DWGraph_Algo();
        g0.init(g);
        String str = "graph.jason";
        assertTrue(g0.save(str));

    }

}