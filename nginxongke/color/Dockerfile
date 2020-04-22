#Copyright [2020] Karthik.V
#
#Licensed under the Apache License, Version 2.0 (the "License");
#you may not use this file except in compliance with the License.
#You may obtain a copy of the License at

#    http://www.apache.org/licenses/LICENSE-2.0

#Unless required by applicable law or agreed to in writing, software
#distributed under the License is distributed on an "AS IS" BASIS,
#WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#See the License for the specific language governing permissions and
#limitations under the License.

FROM maven:3.5-jdk-8 as BUILD
ADD repository.tar.gz /usr/share/maven/ref/
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn -s /usr/share/maven/ref/settings-docker.xml clean package

FROM openjdk:8-jre
EXPOSE 32345
#EXPOSE 5005
COPY --from=BUILD /usr/src/app/target /opt/target
WORKDIR /opt/target
#ENV _JAVA_OPTIONS '-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005'
CMD ["java", "-jar", "color-1.0.war"]