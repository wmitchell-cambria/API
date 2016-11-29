package gov.ca.cwds.rest.elasticsearch.db;

import gov.ca.cwds.rest.api.ApiException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.LoggerFactory;

/**
 * A DAO for Elasticsearch
 * 
 * @author CWDS API Team
 *
 */
public class ElasticsearchDao {


  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ElasticsearchDao.class);
  private Client client;
  private final String host;
  private final String port;
  private final String clusterName;
  private String indexName;
  private String indexType;

  /**
   * Constructor
   * 
   * @param host The host
   * @param port The port
   * @param clusterName The cluster name // * @param clientName
   */
  public ElasticsearchDao(String host, String port, String clusterName) {
    this.host = host;
    this.port = port;
    this.clusterName = clusterName;
  }

  /**
   * Constructor
   * 
   * @param elasticsearchConfiguration The configuration
   */
  public ElasticsearchDao(gov.ca.cwds.rest.ElasticsearchConfiguration elasticsearchConfiguration) {
    this.host = elasticsearchConfiguration.getElasticsearchHost();
    this.clusterName = elasticsearchConfiguration.getElasticsearchCluster();
    this.port = elasticsearchConfiguration.getElasticsearchPort();
  }

  protected synchronized void init() throws UnknownHostException {
    if (this.client == null) {
      Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).build();
      this.client = TransportClient.builder().settings(settings).build().addTransportAddress(
          new InetSocketTransportAddress(InetAddress.getByName(host), Integer.parseInt(port)));
    }
  }

  protected void start() throws Exception {
    LOGGER.info("ElasticSearchDao.start()");

    if (this.client == null) {
      init();
    }
  }

  /**
   * Only stop the ElasticSearch client, when the container stops or if the connection becomes
   * unhealthy.
   * 
   * @throws Exception if ElasticSearch client fails to close properly.
   */
  protected void stop() throws Exception {
    LOGGER.info("ElasticSearchDao.stop()");
    this.client.close();
  }

  /**
   * Create an ElasticSearch document with the given index and document type.
   * 
   * @param document JSON of document
   * @param id the unique identifier
   * @return true if document is indexed false if updated
   * @throws Exception exception on create document
   */
  public boolean index(String document, String id) throws Exception {
    LOGGER.info("ElasticSearchDao.createDocument(): " + document);

    IndexResponse response = client.prepareIndex(indexName, indexType, id)
        .setConsistencyLevel(WriteConsistencyLevel.DEFAULT).setSource(document).execute()
        .actionGet();

    LOGGER.info("Created document:\nindex: " + response.getIndex() + "\ndoc type: "
        + response.getType() + "\nid: " + response.getId() + "\nversion: " + response.getVersion()
        + "\ncreated: " + response.isCreated());

    return response.isCreated();
  }

  public SearchHit[] queryAllPersons() throws Exception {
    // Initialize and start ElasticSearch client, if not started.
    start();

    SearchResponse response = client.prepareSearch(indexName).setTypes(indexType)
        .setSearchType(SearchType.QUERY_AND_FETCH)
        // .setQuery(QueryBuilders.termQuery("first_name", "bart"))
        .setQuery(QueryBuilders.matchQuery("first_name", "bart")).setFrom(0).setSize(60)
        .setExplain(true).execute().actionGet();

    return response.getHits().getHits();
  }

  /**
   * @return the client
   */
  public Client getClient() {
    return client;
  }

  /**
   * @return the host
   */
  public String getHost() {
    return host;
  }

  /**
   * @return the port
   */
  public String getPort() {
    return port;
  }

  /**
   * @return the clusterName
   */
  public String getClusterName() {
    return clusterName;
  }

  /**
   * @param indexName the indexName to set
   */
  public void setIndexName(String indexName) {
    if (StringUtils.isNotBlank(indexName)) {
      this.indexName = indexName;
    } else {
      throw new ApiException("Elasticsearch Index Name must be provided");
    }
  }

  /**
   * @param indexType the indexType to set
   */
  public void setIndexType(String indexType) {
    if (StringUtils.isNotBlank(indexType)) {
      this.indexType = indexType;
    } else {
      throw new ApiException("Elasticsearch Index Type must be provided");
    }
  }

}
