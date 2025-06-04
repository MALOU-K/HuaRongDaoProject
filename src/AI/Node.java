package AI;

import model.MapModel;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public MapModel model;
    public List<Long> path = new ArrayList<>();
    public int step = 0;

    public  Node(MapModel model,List<Long> path) {
        this.model = model;
        this.path = path;
    }

    public MapModel getModel() {
        return model;
    }

    public List<Long> getPath() {
        return path;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }




}
