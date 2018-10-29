import csv
import boto3
import json
import decimal

dynamodb = boto3.resource('dynamodb', region_name='us-east-1', endpoint_url="http://localhost:8000")
client = boto3.client('dynamodb', region_name='us-east-1', endpoint_url="http://localhost:8000")
#Create table in dynamodb
table = dynamodb.Table('Lyrics')
try:
    table.load()
    print(f'Table status is {table.table_status}')
    print(f'table alread exists, proceeding..')

except client.exceptions.ResourceNotFoundException:
    print(f'Table doesnt exist. Creating one..')
    table = dynamodb.create_table(
        TableName = 'Lyrics',
        KeySchema = [
            {
                'AttributeName' : 'artist',
                'KeyType' : 'HASH'
            },
            {
                'AttributeName' : 'song',
                'KeyType' : 'RANGE'
            }
            
        ],
        AttributeDefinitions=[
            {
                'AttributeName' : 'artist',
                'AttributeType' : 'S'
                },
            {
                'AttributeName' : 'song',
                'AttributeType' : 'S'
                }
            ],
        ProvisionedThroughput=
            {
                'ReadCapacityUnits' : 100,
                'WriteCapacityUnits' : 100
            }
        )
    


#Load table with data
with open('songdata.csv') as csv_file:
    print(f'reading from csv file')
    csv_reader = csv.reader(csv_file, delimiter=",")
    #print(csv_reader)
    line_count = 0
    for row in csv_reader:
        if line_count == 0:
            line_count += 1;
            continue
        else:
            #Load data
            print(f'adding song data for artist and song - {row[0]} and {row[1]}')
            table.put_item(
                Item={
                    'artist' : row[0],
                    'song' : row[1],
                    'link' : row[2],
                    'text' : row[3]
                }
            )
            line_count += 1;
            
         
