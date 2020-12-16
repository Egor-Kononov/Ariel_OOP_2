package gameClient;


import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;


public class Ex2 implements Runnable{
    private static GraphFrame _win;
    private static Arena _ar;

    public static void main(String[] a) {
        Thread client = new Thread(new Ex2());
        client.start();
    }

    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter id and level of game");
        int id = s.nextInt();
        int scenario_num = s.nextInt();
        s.close();
        //int scenario_num = 23;
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        System.out.println(game.getGraph());
        //int id = 342426608;
        game.login(id);
        String g = game.getGraph();
        String pks = game.getPokemons();
        String a = game.getAgents();
       // directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        dw_graph_algorithms al = new DWGraph_Algo();
        al.load(g);
        init(game);

        game.startGame();
        //_win.setTitle("Ex2 - OOP:"+game.toString());
        int ind=0;
        long dt=100;

        while(game.isRunning()) {

            moveAgents(game, al.getGraph());
            try {
                if(ind%1==0) {_win.repaint();}
                Thread.sleep(dt);
                ind++;
            }
                catch(Exception e) {
                e.printStackTrace();
                }
        }


        String res = game.toString();

        System.out.println(res);
        System.exit(0);

    }
    private static  void  moveAgents (game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs = game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        _ar.setPokemons(ffs);
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(gg);
        for (int i = 0; i < log.size(); i++) {
            CL_Agent ag = log.get(i);
            CL_Pokemon mainPok = null;
            int dest = ag.getNextNode();
            int edgeSrc = -1;
            int edgeDest = -1;
            double v = 0;
            double distance = Integer.MAX_VALUE;
            if (dest == -1) {
                for (int j = 0; j < ffs.size(); j++) {
                    CL_Pokemon pok = ffs.get(j);
                    if(pok.getMin_ro()==-1){
                        Arena.updateEdge(pok, gg);
                        edge_data edge = pok.get_edge();
                        double dist = algo.shortestPathDist(ag.getSrcNode(), edge.getSrc());
                        if (dist < distance) {
                            mainPok = pok;
                            distance = dist;
                            edgeSrc = edge.getSrc();
                            edgeDest = edge.getDest();
                            v = pok.getValue();
                        }
                    }

                }
                mainPok.setMin_ro(0);
                List<node_data> list = algo.shortestPath(ag.getSrcNode(), edgeSrc);

                if(list == null)
                    break;

                if (list.size() == 1) {
                    game.chooseNextEdge(ag.getID(), edgeDest);
                    System.out.println("Agent: " + ag.getID() + ", val: " + v + "   turned to node: " + edgeDest);
                } else {
                    for (int x = 1; x < list.size(); x++) {
                        game.chooseNextEdge(ag.getID(), list.get(x).getKey());
                        System.out.println("Agent: " + ag.getID() + ", val: " + v + "   turned to node: " + list.get(x).getKey());
                    }
                    game.chooseNextEdge(ag.getID(), edgeDest);
                    System.out.println("Agent: " + ag.getID() + ", val: " + v + "   turned to node: " + edgeDest);
                }
            }

        }
    }

    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        dw_graph_algorithms gg = new DWGraph_Algo();
        gg.load(g);
        _ar = new Arena();
        _ar.setGraph(gg.getGraph());
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _ar.setGame(game);
        _win = new GraphFrame();
        _win.setSize(1000, 700);
        _win.init(_ar);



        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg.getGraph());}
             for(int a = 0;a<rs;a++) {
                int ind = a%cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
                int nn = c.get_edge().getDest();
                if(c.getType()<0 ) {
                    nn = c.get_edge().getSrc();

                }
                game.addAgent(nn);
            }




        }
        catch (JSONException e) {e.printStackTrace();}
    }
}
