import org.apache.solr.client.solrj.impl.CloudSolrServer
import org.apache.solr.common.SolrInputDocument
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.response.QueryResponse

object RwSolr {
  def main(args: Array[String]) {
    val zk_conn = "172.31.113.184:2181/solr"
    val server = new CloudSolrServer(zk_conn)
    server.setDefaultCollection("tutorial_col");
    for (i <- 1 to 100) {
      val date = util.Random.nextInt(1500000000).toString()
      val lat = "%2.2f".format(-90+180*util.Random.nextFloat).toString()
      val lng = "%2.2f".format(-180+360*util.Random.nextFloat).toString()
      val value = util.Random.nextInt(101).toString()
      val id = date + lat + lng
      val doc = new SolrInputDocument()
      doc.addField( "id", id)
      doc.addField( "date", date)
      doc.addField( "lat", lat)
      doc.addField( "lng", lng)
      doc.addField( "value", value)
      server.add(doc)
      server.commit()
    }

    val query = new SolrQuery();
    query.set("q", "*")
    query.set("rows",1000)
    //query.addFilterQuery("value:\"50\"")
    val response = server.query(query)
    val docs = response.getResults()
    for (i <- 0 to docs.getNumFound.toInt-1){
      println("date: " + docs.get(i).getFieldValue("date"))
      println("lat: " + docs.get(i).getFieldValue("lat"))
      println("lng: " + docs.get(i).getFieldValue("lng"))
      println("value: " + docs.get(i).getFieldValue("value"))
      println("--------------------------------------------")
    }
  }
}
