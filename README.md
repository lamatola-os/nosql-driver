NoSQL write/read driver
--------------------------

DynamoDB
--------

Ok, so you only define the PrimaryKey while creating a schema for a document, 
that's what I misunderstood from the beginning.

http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/TicTacToe.Phase2.html#TicTacToe.Phase2.DataModel

```json
aws dynamodb describe-table --table-name Test_ConsumerOffset --profile aws-federated --region us-west-2

{  
   AttributeDefinitions:[  
      {  
         AttributeName:consumerOwner,
         AttributeType:S
      },
      {  
         AttributeName:offset,
         AttributeType:S
      }
   ],
   TableName:Test_ConsumerOffset,
   KeySchema:[  //PrimaryKey
      {  
         AttributeName:consumerOwner,
         KeyType:HASH
      },
      {  
         AttributeName:offset,
         KeyType:RANGE
      }
   ],
   ProvisionedThroughput:{  
      ReadCapacityUnits:10,
      WriteCapacityUnits:10
   }
}
```


![](dynamodb.png)
