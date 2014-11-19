import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String,List<String>> map = new HashMap<>();
        List la = new ArrayList<>();
        System.out.println(la);

        if(la == null) {
            System.out.println("la is null");
        }else
            System.out.println("la size: " + la.size());
        la.add("a");
        la.add("b");
        map.put("a",la);
        System.out.println(map);


    }
}