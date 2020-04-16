#!/bin/bash
gcloud container clusters create simplecluster \
    --num-nodes=3 --enable-autoupgrade --no-enable-basic-auth \
    --no-issue-client-certificate --enable-ip-alias --metadata \
    disable-legacy-endpoints=true
gcloud container clusters get-credentials simplecluster
INSTANCE_NAME=sqlinst
gcloud sql instances create $INSTANCE_NAME
export INSTANCE_CONNECTION_NAME=$(gcloud sql instances describe $INSTANCE_NAME \
    --format='value(connectionName)')
kubectl create secret generic cloudsql-instance-credentials --from-file serviceaccount.json
kubectl create secret generic cloudsql-db-credentials --from-literal username=root --from-literal password=Sql123
kubectl apply -f wordpress.yaml