# --------------------------------------------------------------------------
# Copyright [2020] Karthik.V
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# --------------------------------------------------------------------------
#!/bin/bash
gcloud compute networks vpc-access connectors create vpc-cache-connector --network default --region us-central1 --range 10.24.0.0/28
gcloud redis instances create visitor-redis --size 5 --region us-central1 --tier standard
mvn clean package appengine:deploy -Dapp.deploy.projectId=gcp-central-276303 -DREDIS_HOST=[GCLOUD_REDIS_MEMSTORE_IP] -DREDIS_PORT=6379