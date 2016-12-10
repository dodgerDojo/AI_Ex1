import java.util.Comparator;

public class UcsComparator implements Comparator<Node>{
    public UcsComparator() {
    }

    @Override
    public int compare(Node first,Node second){
        if(first.getCost()<second.getCost()){
            return -1;
        }
        else if(first.getCost()>second.getCost()){
            return 1;
        }
        else if(first.getTimestamp()<second.getTimestamp()){
            return -1;
        }
        else if(first.getTimestamp()>second.getTimestamp()){
            return 1;
        }
        else if(first.getPriority()>second.getPriority()){
            return -1;
        }
        else{
            return 1;
        }
    }
}