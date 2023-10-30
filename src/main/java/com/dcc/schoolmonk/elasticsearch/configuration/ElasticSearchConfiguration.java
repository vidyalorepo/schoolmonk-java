package com.dcc.schoolmonk.elasticsearch.configuration;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
@EnableElasticsearchRepositories(basePackages = {
        "com.dcc.schoolmonk.elasticsearch.repository" })
@ComponentScan(basePackages = { "com.dcc.schoolmonk.elasticsearch" })
// extends AbstractElasticsearchConfiguration
public class ElasticSearchConfiguration  extends AbstractElasticsearchConfiguration{

    @Value("${elasticsearch.rest.uris}")
    private String elasticHostUri;

    @Value("${elasticsearch.rest.username}")
    private String elasticUserName;

    @Value("${elasticsearch.rest.password}")
    private String elasticUserPass;

    @Value("${elasticsearch.rest.host}")
    private String hostString;

    // @Override
    // @Bean
    // public RestHighLevelClient elasticsearchClient() {

    // return RestClients.create(null).rest();
    // }
//     @Override
//     @Bean
//     public RestHighLevelClient elasticsearchClient() {

//         HttpHeaders defaultCompatibilityHeaders = new HttpHeaders();
//         defaultCompatibilityHeaders.add("Accept",
//                 "application/vnd.elasticsearch+json;compatible-with=8");
//         defaultCompatibilityHeaders.add("Content-Type",
//                 "application/vnd.elasticsearch+json;compatible-with=8");
//         ClientConfiguration clientConfiguration = null;
//         try {
//             SSLContextBuilder sslBuilder = new SSLContextBuilder();
//  sslBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
//             // SSLContextBuilder sslBuilder = SSLContexts.custom()
//             //         .loadTrustMaterial(null, (x509Certificates, s) -> true);
//             final javax.net.ssl.SSLContext sslContext = sslBuilder.build();
//             clientConfiguration = ClientConfiguration.builder()
//                     .connectedTo("13.127.197.214:9200")
//                     .usingSsl(sslContext)
                    
//                     .withConnectTimeout(Duration.ofSeconds(5))
//                     .withSocketTimeout(Duration.ofSeconds(3))
//                     .withBasicAuth(elasticUserName, elasticUserPass)
//                     .build();

//         } catch (KeyManagementException e) {
//             e.printStackTrace();
//         } catch (NoSuchAlgorithmException e) {
//             e.printStackTrace();
//         } catch (KeyStoreException e) {
//             e.printStackTrace();
//         }
//         return RestClients.create(clientConfiguration).rest();

//     }

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(elasticUserName,elasticUserPass));
            SSLContextBuilder sslBuilder = SSLContexts.custom()
                    .loadTrustMaterial(null, TrustAllStrategy.INSTANCE);
            final javax.net.ssl.SSLContext sslContext = sslBuilder.build();
            // Create the low-level client
            RestHighLevelClient restClient = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(hostString, 9200, "https"))
                            .setHttpClientConfigCallback(new HttpClientConfigCallback() {
                                @Override
                                public HttpAsyncClientBuilder customizeHttpClient(
                                        HttpAsyncClientBuilder httpClientBuilder) {

                                    return httpClientBuilder
                                            .setDefaultCredentialsProvider(credentialsProvider)
                                            .setSSLContext(sslContext)
                                            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
                                }
                            })
                            .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                                @Override
                                public RequestConfig.Builder customizeRequestConfig(
                                        RequestConfig.Builder requestConfigBuilder) {
                                    return requestConfigBuilder.setConnectTimeout(5000)
                                            .setSocketTimeout(120000);
                                }
                            }));
                            return restClient;
            // Create the transport with a Jackson mapper
            // ElasticsearchTransport transport = new RestClientTransport(
            //         restClient.getLowLevelClient(), new JacksonJsonpMapper());

            // And create the API client

            // ElasticsearchClient client = new ElasticsearchClient(transport);
            // return client;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
