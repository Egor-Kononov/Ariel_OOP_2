package api;

import api.*;
import org.junit.jupiter.api.*;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {


    @Test
    void nodeSize() {
        directed_weighted_graph g = new DWGraph_DS();
        for(int i=0;i<5;i++){
            node_data node = new NodeData(i);
            g.addNode(node);
        }
        assertTrue(g.nodeSize() == 5);
        g.removeNode(3);
        g.removeNode(1);
        g.removeNode(1);
        assertTrue(g.nodeSize()==3);
        assertNull(g.getNode(50));


    }

    @Test
    void edgeSize() {
        directed_weighted_graph g = new DWGraph_DS();
        for(int i=0;i<5;i++){
            node_data node = new NodeData(i);
            g.addNode(node);
        }
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,0,1);
        assertEquals(3, g.edgeSize());
        g.connect(1,2,1);
        g.removeNode(0);
        assertEquals(1, g.edgeSize());
    }

    @Test
    void getV() {
        directed_weighted_graph g = new DWGraph_DS();
        for(int i=0;i<5;i++){
            node_data node = new NodeData(i);
            g.addNode(node);
        }

        g.connect(0,1,1);
        g.connect(1,2,2);
        g.connect(2,3,3);
        g.connect(3,4,1);
        g.connect(2,5,3);
        Collection<node_data> v = g.getV();
        Iterator<node_data> iter = v.iterator();
        while (iter.hasNext()) {
            node_data n = iter.next();
            assertNotNull(n);
        }
    }

    @Test
    void connect() {
        directed_weighted_graph g = new DWGraph_DS();
        for(int i=0;i<5;i++){
            node_data node = new NodeData(i);
            g.addNode(node);
        }

        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        assertTrue(g.edgeSize()==3);

        g.removeEdge(0,1);
        assertNull(g.getEdge(0,1));

        edge_data w = g.getEdge(0,3);
        assertEquals(w.getWeight(),3);
        assertNull(g.getEdge(1,0));

        g.connect(0,3,5);
         w = g.getEdge(0,3);
        assertTrue(w.getWeight()==5);

        g.connect(0,0,1);
        assertTrue(g.edgeSize()==3);
    }

    @Test
    void removeNode() {
        directed_weighted_graph g = new DWGraph_DS();
        for(int i=0;i<5;i++){
            node_data node = new NodeData(i);
            g.addNode(node);
        }
        assertTrue(g.nodeSize()==5);
        g.connect(0,1,2);
        g.connect(0,3,3);
        assertTrue(g.getMC()==7);

        g.removeNode(4);
        assertTrue(g.nodeSize()==4);

        g.removeNode(0);
        assertTrue(g.nodeSize()==3);
        assertTrue(g.edgeSize()==0);
        assertTrue(g.getMC()==11);
        assertNull(g.getNode(10));

    }


    @Test
    void removeEdge() {
        directed_weighted_graph g = new DWGraph_DS();
        for(int i=0;i<5;i++){
            node_data node = new NodeData(i);
            g.addNode(node);
        }
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,2,2);
        g.connect(0,4,3);
        g.removeEdge(0,1);
        assertTrue(g.edgeSize()==2);
        edge_data w = g.getEdge(0,1);
        assertNull(w);
        assertTrue(g.getMC()==9);
        assertNull(g.removeEdge(1,7));
        assertTrue(g.getMC()==9);
        g.connect(3,0,3);
        g.connect(2,0,3);
        g.connect(4,0,3);
        g.removeNode(0);
        assertTrue(g.edgeSize()==0);

    }





}