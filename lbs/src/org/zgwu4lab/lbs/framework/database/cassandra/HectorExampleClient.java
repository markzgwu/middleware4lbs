package org.zgwu4lab.lbs.framework.database.cassandra;
 
//import org.apache.cassandra.service.Column;
//import org.apache.cassandra.service.ColumnPath;
 
public class HectorExampleClient {
 
  public static void main(String[] args) throws Exception {
    //CassandraClientPool pool = CassandraClientPoolFactory.INSTANCE.get();
    //CassandraClient client = pool.borrowClient("localhost", 9160);
    // A load balanced version would look like this:
    // CassandraClient client = pool.borrowClient(new String[] {"cas1:9160", "cas2:9160", "cas3:9160"});
 
    try {
      //Keyspace keyspace = client.getKeyspace("Keyspace1");
      //ColumnPath columnPath = new ColumnPath("Standard1", null, bytes("网址"));
 
      // insert
      //keyspace.insert("逖靖寒的世界", columnPath, bytes("http://gpcuster.cnblogs.com"));
 
      // read
      //Column col = keyspace.getColumn("逖靖寒的世界", columnPath);
 
      //System.out.println("Read from cassandra: " + string(col.getValue()));
 
    } finally {
      // return client to pool. do it in a finally block to make sure it's executed
      //pool.releaseClient(client);
    }
  }
}
