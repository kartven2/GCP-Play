#!/bin/bash
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

gcloud pubsub topics create EventsTopic
gcloud pubsub subscriptions create EventsSubscription \
    --topic EventsTopic \
    --push-endpoint \
    https://half-life-277322.ue.r.appspot.com/events/subscriber?reqToken=8r0teb6778939sfbe07nn0mm271 \
    --ack-deadline 10
mvn clean package appengine:deploy