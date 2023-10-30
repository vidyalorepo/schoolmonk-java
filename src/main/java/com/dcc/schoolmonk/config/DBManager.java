package com.dcc.schoolmonk.config;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.dcc.schoolmonk.SchoolmonkApp.ExtPropertyReader;


public class DBManager {

		private static AmazonDynamoDB client = null;
		private static DynamoDBMapper mapper = null;	
		private static DynamoDB dynamoDB = null;
		private static DynamoDB dynamoDBProd = null;
		private static AmazonDynamoDB clientProd = null;
		
	    /**
	     * This method is used save JSON data into DynamoDB table
	     * @param primaryKey primary key of the table
	     * @param accountInfo JSON data
	     * @param tableName table name
	     * @return Void 
	     **/
		public static AmazonDynamoDB getDynamoDBClient() {
			
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(ExtPropertyReader.accessKey, ExtPropertyReader.secretKey);
			
			if (client == null) {
				client = AmazonDynamoDBClientBuilder.standard()
											.withRegion(Regions.US_EAST_1)
											.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
											.build();
			}
			
			return client;
			
		}
		

		public static AmazonS3 getS3Client() {
			AmazonS3 s3Client = null;
			try {
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(ExtPropertyReader.accessKey,
					ExtPropertyReader.secretKey);
			s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
			} catch (AmazonServiceException e) {
			e.printStackTrace();
			} catch (SdkClientException e) {
			e.printStackTrace();
			}
			return s3Client;
		}
		
		public static DynamoDBMapper getDynamoDBMapper() {
			
			if (mapper == null) {
				mapper = new DynamoDBMapper(DBManager.getDynamoDBClient());
			}
			
			return mapper;
		}
		
		public static DynamoDB getDynamoDB () {
			
			if (dynamoDB == null) {
				dynamoDB = new DynamoDB(DBManager.getDynamoDBClient());
			}
			
			return dynamoDB;
		}
		//for demo purpose this method is created for production access
		public static AmazonDynamoDB getDynamoDBClientProd() {
			
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(ExtPropertyReader.accessKey, ExtPropertyReader.secretKey);
			
			if (clientProd == null) {
				clientProd = AmazonDynamoDBClientBuilder.standard()
											.withRegion(Regions.US_EAST_1)
											.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
											.build();
			}
			
			return clientProd;
			
		}
		//for demo purpose this method is created for production access
		public static AmazonS3 getS3ClientProduction() {
			AmazonS3 s3Client = null;
			try {
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(ExtPropertyReader.accessKey,
					ExtPropertyReader.secretKey);
			s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1)
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
			} catch (AmazonServiceException e) {
			e.printStackTrace();
			} catch (SdkClientException e) {
			e.printStackTrace();
			}
			return s3Client;
		}
		//for demo purpose this method is created for production access
		public static DynamoDB getDynamoDBProduction () {
			if (dynamoDBProd == null) {
				dynamoDBProd = new DynamoDB(DBManager.getDynamoDBClientProd());
			}

			return dynamoDBProd;
		}
		
}


