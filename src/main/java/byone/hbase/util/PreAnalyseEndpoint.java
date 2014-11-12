package byone.hbase.util;

import byone.hbase.filter.ByParseFilter;
import byone.hbase.filter.CompareFilter;
import byone.hbase.filter.EventComparator;
import byone.hbase.filter.RowFilter;
import byone.hbase.protobuf.PreAnalyseProtos;
import com.google.protobuf.ByteString;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.Coprocessor;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.coprocessor.CoprocessorException;
import org.apache.hadoop.hbase.coprocessor.CoprocessorService;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.protobuf.ResponseConverter;
import org.apache.hadoop.hbase.regionserver.InternalScanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyou on 14/11/11.
 */
public class PreAnalyseEndpoint extends PreAnalyseProtos.PreAnalyseService
    implements Coprocessor, CoprocessorService {

    private RegionCoprocessorEnvironment env;

    public  PreAnalyseEndpoint() {

    }

    private FilterList hbaseFilter(String args,List<ByteString> events) {
        FilterList flist = new FilterList(FilterList.Operator.MUST_PASS_ALL);

        if (args.isEmpty() && events.isEmpty()) {
            return null;
        }
        else if(!args.isEmpty()){
            try {
                flist.addFilter(new ByParseFilter().parseFilterString(args));
            }catch (Exception e){
                ;
            }

        }
        else if(!events.isEmpty()) {

            List<Filter> rowfilterlist = new ArrayList<Filter>();
            for (ByteString event: events) {
                Filter rowfilter = new RowFilter(
                        CompareFilter.CompareOp.EQUAL,new EventComparator(event.toByteArray()));
                rowfilterlist.add(rowfilter);
            }
            Filter rowlist = new FilterList(FilterList.Operator.MUST_PASS_ONE,rowfilterlist);
            flist.addFilter(rowlist);
        }
        return flist;

    }

    @Override
    public void getPreData(RpcController controller, PreAnalyseProtos.AnalyseRequest request,
                           RpcCallback<PreAnalyseProtos.AnalyseResponse> done) {
        Scan scan = new Scan();
        String filterString = request.getFilterString();
        List<ByteString> events = request.getEventsList();

        Filter fl = hbaseFilter(filterString,events);
        scan.setFilter(fl);

        PreAnalyseProtos.AnalyseResponse response = null;
        InternalScanner scanner = null;

        //PreAnalyseProtos.MapEntry mp = new PreAnalyseProtos.MapEntry();
       // Map <String, String> kvMap = new HashMap<String, String>();
       // PreAnalyseProtos.AnalyseResponse.KvList.newBuilder().s

        List<PreAnalyseProtos.MapEntry> maplist = new ArrayList<PreAnalyseProtos.MapEntry>();
        try {
            scanner = env.getRegion().getScanner(scan);
            List<Cell> results = new ArrayList<Cell>();
            boolean hasMore = false;
            byte[] lastRow = null;
            do {
                hasMore = scanner.next(results);
                for (Cell kv: results) {
                    PreAnalyseProtos.MapEntry mp = PreAnalyseProtos.MapEntry.newBuilder()
                            .setKey(new String(kv.getRow()))
                            .setValue(new String(kv.getValue())).build();
                    maplist.add(mp);
                    //response = PreAnalyseProtos.AnalyseResponse.newBuilder().setItems(results.indexOf(kv),mp).build();
                    //kvMap.put(new String(kv.getRow()),new String(kv.getValue()));
                }

            }while (hasMore);
            java.lang.Iterable<PreAnalyseProtos.MapEntry> list = new ArrayList<PreAnalyseProtos.MapEntry>(maplist);
            //maplist.listIterator();
            response = PreAnalyseProtos.AnalyseResponse.newBuilder().addAllItems(list).build();
            //response = PreAnalyseProtos.AnalyseResponse.newBuilder().


        }catch (IOException ioe) {
            ResponseConverter.setControllerException(controller, ioe);
        } finally {
            if (scanner != null) {
                try {
                    scanner.close();
                } catch (IOException ignored) {}
            }
        }
        done.run(response);
    }


    @Override
    public Service getService() {
        return this;
    }


    @Override
    public  void start(CoprocessorEnvironment env) throws IOException {
        if (env instanceof RegionCoprocessorEnvironment) {
            this.env = (RegionCoprocessorEnvironment)env;
        } else {
            throw new CoprocessorException("Must be loaded on a table region!");
        }
    }

    @Override
    public void stop(CoprocessorEnvironment env) throws IOException {
        // nothing to do
    }

}
