# Connecting to MySQL client pods
# Connect to master instance
kubectl run -it --rm --image=mysql:5.7 --restart=Never mysql-client -- mysql -h mysql-0.mysql
# Connect to reader/slave instance
kubectl run -it --rm --image=mysql:5.7 --restart=Never mysql-client -- mysql -h mysql-read