# file-transfer-service

The file-transfer-service is to receive the file from the RabbitMQ and upload the file to the intranet database. It will also generate a reconciliation file to allow the [recon-batch-service](https://github.com/amirahadam/recon-batch-service) to delete the files on the internet temp db accordingly.

**Contributors: Lian Chong Chun (LianChongChun)**
