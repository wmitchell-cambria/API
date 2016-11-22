package gov.ca.cwds.rest.elasticsearch.db;

import java.net.InetAddress;

import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.LoggerFactory;

/**
 * A DAO for Elasticsearch
 * 
 * @author CWDS API Team
 *
 */
public class ElasticsearchDao {


  private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ElasticsearchDao.class);
  private Client client;
  private final String host;
  private final String port; // port on which the node runs in client machine.
  private final String clusterName;
  private final String clientName;
  private String indexName;
  private String indexType;

  public ElasticsearchDao(String host, String port, String clusterName, String clientName,
      String indexName, String indexType) {
    this.host = host;
    this.port = port;
    this.clusterName = clusterName;
    this.clientName = clientName;
    this.indexName = indexName;
    this.indexType = indexType;
  }

  public ElasticsearchDao(gov.ca.cwds.rest.ElasticsearchConfiguration elasticsearchConfiguration) {
    this.host = elasticsearchConfiguration.getElasticsearchHost();
    this.clusterName = elasticsearchConfiguration.getElasticsearchCluster();
    this.clientName = elasticsearchConfiguration.getElasticsearchClientNodeName();
    this.port = elasticsearchConfiguration.getElasticsearchPort();
  }

  public void start() throws Exception {
    LOG.info("ElasticSearchDao.start()");

    Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).build();
    this.client =
        TransportClient
            .builder()
            .settings(settings)
            .build()
            .addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName(host), Integer.parseInt(port)));
  }

  public void stop() throws Exception {
    LOG.info("ElasticSearchDao.stop()");
    this.client.close();
  }

  /**
   * Create an ElasticSearch document with the given index and document type.
   * 
   * @param document JSON of document
   * @param id the unique identifier
   * @return true if document is successfully indexed
   * @throws Exception exception on create document
   */
  public boolean createDocument(String document, String id) throws Exception {
    LOG.info("ElasticSearchDao.createDocument(): " + document);

    // Creates a NEW doc.
    // The id is optional, if it is not provided, one will be generated automatically.
    // Params: index, doc type, id
    IndexResponse response =
        client.prepareIndex(indexName, indexType, id)
            .setConsistencyLevel(WriteConsistencyLevel.DEFAULT).setSource(document).execute()
            .actionGet();

    // Document ID (generated or not).
    // Version (if it's the first time you index this document, you will get: 1).
    // isCreated() is true if the document is a new one, false if it has been updated.

    LOG.info("Created document:\nindex: " + response.getIndex() + "\ndoc type: "
        + response.getType() + "\nid: " + response.getId() + "\nversion: " + response.getVersion()
        + "\ncreated: " + response.isCreated());

    return response.isCreated();
  }

  public String getHost() {
    return host;
  }

  public String getPort() {
    return port;
  }

  public String getClusterName() {
    return clusterName;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  /**
   * @param indexName the indexName to set
   */
  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  /**
   * @param indexType the indexType to set
   */
  public void setIndexType(String indexType) {
    this.indexType = indexType;
  }

}
