package api;

public class NodeData implements node_data{


    private class EdgeData implements edge_data{

        @Override
        public int getSrc() {
            return 0;
        }

        @Override
        public int getDest() {
            return 0;
        }

        @Override
        public double getWeight() {
            return 0;
        }

        @Override
        public String getInfo() {
            return null;
        }

        @Override
        public void setInfo(String s) {

        }

        @Override
        public int getTag() {
            return 0;
        }

        @Override
        public void setTag(int t) {

        }
    }
    @Override
    public int getKey() {
        return 0;
    }

    @Override
    public geo_location getLocation() {
        return null;
    }

    @Override
    public void setLocation(geo_location p) {

    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}
