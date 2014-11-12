import byone.hbase.protobuf.PreAnalyseProtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyou on 14/11/12.
 */
public class Testw {
    public static void main(String[] args) {
        List<PreAnalyseProtos.MapEntry> maplist = new ArrayList<PreAnalyseProtos.MapEntry>();

        PreAnalyseProtos.AnalyseResponse response = null;

        PreAnalyseProtos.MapEntry mp = PreAnalyseProtos.MapEntry.newBuilder()
                .setKey("a")
                .setValue("b").build();
        maplist.add(mp);

        PreAnalyseProtos.MapEntry mp2 = PreAnalyseProtos.MapEntry.newBuilder()
                .setKey("as")
                .setValue("bs").build();
        maplist.add(mp2);
        java.lang.Iterable<PreAnalyseProtos.MapEntry> list = new ArrayList<PreAnalyseProtos.MapEntry>(maplist);
        //maplist.listIterator();
        response = PreAnalyseProtos.AnalyseResponse.newBuilder().addAllItems(list).build();


        System.out.print(response.getItemsList());
    }
}
