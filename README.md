# GCP-Play
Cloud projects

1. mysql - Production and HA ready MySQL deployment with 1 master and n slave replication support
           Ready for deployment in Kubernetes engine in GCloud.
2. wordpressongke - Wordpress deployed on GCloud with CloudSQL as backend.
3. nginxongke - Color App using spring boot to demonstrate use of different deployments in GCloud Kubernetes Engine
   
   a. color - Spring Boot java App
   
   b. standalone.yaml - Deployment of two services in GCloud Kubernetes Engine (GKE)
   
   c. color-with-gke-ingress.yaml - Deployment of three services in GKE 
                                    with GCloud Ingress (path based external load balancer)
                                    
   d. nginx-workloads.yaml - Production ready NGINX ingress Load Balancer Workloads.
   
   e. color-with-nginx-workloads.yaml - Deployment of two services in GKE
                                        with NGINX as the LB (path based external HTTP load balancer)
4. redis - Visitor application to demonstrate 

   a. App engine Java 11 flex deployment on Gcloud
   
   b. Fragmented Layout with Spring Thymeleaf
   
   c. Some jQuery hacks
   
   <Open Item/Issue> : With GAE Java 11 and Redis memory store in Gcloud 
   redis.clients.jedis.JedisPool couldn't be configured with org.springframework.data.redis.core.RedisTemplate
           
